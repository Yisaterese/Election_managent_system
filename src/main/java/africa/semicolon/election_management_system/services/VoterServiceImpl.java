package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.data.models.Election;
import africa.semicolon.election_management_system.data.models.Vote;
import africa.semicolon.election_management_system.data.models.Voter;
import africa.semicolon.election_management_system.data.repositories.VoteRepository;
import africa.semicolon.election_management_system.data.repositories.VoterRepository;
import africa.semicolon.election_management_system.dtos.requests.CastVoteRequest;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.responses.CastVoteResponse;
import africa.semicolon.election_management_system.dtos.responses.CreateVoterResponse;
import africa.semicolon.election_management_system.dtos.responses.UpdateVoterResponse;
import africa.semicolon.election_management_system.exceptions.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import static africa.semicolon.election_management_system.data.constants.Role.VOTER;
import static java.time.LocalDateTime.now;

@Service
@Slf4j
public class VoterServiceImpl implements VoterService{

    private final VoterRepository voterRepository;
    private final ModelMapper modelMapper;
    private final VoteRepository voteRepository;
    private final PasswordEncoder passwordEncoder;

    private ElectionService electionService;
    private CandidateService candidateService;

    private final SecureRandom random = new SecureRandom();

    public VoterServiceImpl(VoterRepository voterRepository, VoteRepository voteRepository,
                            PasswordEncoder passwordEncoder) {
        this.voterRepository = voterRepository;
        this.voteRepository = voteRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
    }

    @Autowired
    @Lazy
    public void setElectionService(ElectionService electionService) {
        this.electionService = electionService;
    }

    @Autowired
    @Lazy
    public void setCandidateService(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Override
    public CreateVoterResponse registerVoter(CreateVoterRequest request) {
        String identificationNumber = request.getIdentificationNumber();
        validateIdentificationNumber(identificationNumber);
        Voter voter = modelMapper.map(request, Voter.class);
        validateVoterAge(voter);
        Long randomId = generateRandomId();
        voter.setVotingId(randomId);
        voter.setStatus(true);
        voter.setRole(VOTER);
        voter.setPassword(passwordEncoder.encode(request.getPassword()));
        Voter savedVoter = voterRepository.save(voter);
        var response = modelMapper.map(savedVoter, CreateVoterResponse.class);
        response.setMessage("Voter registered successfully");
        return response;
    }

    @Override
    public Voter getVoterById(Long id) {
        return voterRepository.findById(id)
                .orElseThrow(()-> new VoterNotFoundException("Voter not found"));
    }

    @Override
    public Voter getVoterByVotingId(Long votingId) {
        return voterRepository.findByVotingId(votingId)
                .orElseThrow(()-> new FailedVerificationException(
                String.format("Voter could not be verified with %s", votingId)));
    }

    @Override
    public UpdateVoterResponse updateVoter(Long votingId, JsonPatch jsonPatch) {
        try {
            Voter voter = getVoterByVotingId(votingId);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode voterNode = objectMapper.convertValue(voter, JsonNode.class);
            voterNode = jsonPatch.apply(voterNode);

            voter = objectMapper.convertValue(voterNode, Voter.class);
            voter = voterRepository.save(voter);
            return modelMapper.map(voter, UpdateVoterResponse.class);
        } catch (JsonPatchException exception) {
            throw new FailedVerificationException("Unable to verify voteId");
        }
    }

    @Override
    public CastVoteResponse castVote(CastVoteRequest castVoteRequest) {
        log.info("Cast vote method called with voting id: {}", castVoteRequest.getVotingId());
        Voter voter = getVoterByVotingId(castVoteRequest.getVotingId());
        Election election = electionService.getElectionBy(castVoteRequest.getElectionId());
        Candidate candidate = candidateService.getCandidateBy(castVoteRequest.getCandidateId());
        validate(election, candidate, voter);
        Vote newVote = buildVote(voter, candidate, election);
        newVote = voteRepository.save(newVote);
        CastVoteResponse response = modelMapper.map(newVote, CastVoteResponse.class);
        response.setMessage("Voting casted successfully");
        log.info("Vote casted successfully with voting id: {}", castVoteRequest.getVotingId());
        return response;
    }

    private void validate(Election election, Candidate candidate, Voter voter) {
        validate(election, candidate);
        validate(voter, election);
    }

    private void validate(Voter voter, Election election) {
        var votes = voteRepository.findVotesByVoterAndElection(voter.getId(), election.getId());
        if (!votes.isEmpty()) throw new InvalidVoteException("Vote has already been casted");
    }

    private void validate(Election election, Candidate candidate) {
        var startDate = election.getStartDate();
        var endDate = election.getEndDate();
        var currentDate = now();
        if (currentDate.isBefore(startDate) || currentDate.isAfter(endDate))
            throw new UnauthorizedException("Election is not open");
        if (!Objects.equals(election, candidate.getElection()))
            throw new InvalidVoteException("Selected candidate is not eligible for the selected election");
    }

    private long generateRandomId() {
        return random.nextLong(100000,1000000);
    }

    private static void validateVoterAge(Voter voter) {
        LocalDate currentDate = LocalDate.now();
        if (Period.between(voter.getDateOfBirth(), currentDate).getYears() < 18) {
            throw new IneligibleToVoteException("Voter must be at least 18 years old.");
        }
    }

    private void validateIdentificationNumber(String identificationNumber) {
        boolean condition = voterRepository.existsByIdentificationNumber(identificationNumber);
        if (condition) {
            throw new IdentificationNumberAlreadyExistsException("Identification number already exists.");
        }
    }

    private static Vote buildVote(Voter voter, Candidate candidate, Election election) {
        Vote newVote = new Vote();
        newVote.setVoter(voter);
        newVote.setCandidate(candidate);
        newVote.setElection(election);
        return newVote;
    }
}
