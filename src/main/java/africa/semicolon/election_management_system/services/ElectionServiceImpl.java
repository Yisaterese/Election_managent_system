package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.data.models.Election;
import africa.semicolon.election_management_system.data.repositories.ElectionRepository;
import africa.semicolon.election_management_system.data.repositories.VoteRepository;
import africa.semicolon.election_management_system.dtos.requests.LoginRequest;
import africa.semicolon.election_management_system.dtos.responses.CandidateResultResponse;
import africa.semicolon.election_management_system.dtos.responses.LoginResponse;
import africa.semicolon.election_management_system.dtos.responses.ViewElectionResultsResponse;
import africa.semicolon.election_management_system.exceptions.ElectionNotFoundException;
import africa.semicolon.election_management_system.exceptions.FailedVerificationException;
import africa.semicolon.election_management_system.security.services.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;
    private final VoteRepository voteRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    private CandidateService candidateService;

    public ElectionServiceImpl(
            ElectionRepository electionRepository, VoteRepository voteRepository,
            TokenService tokenService, AuthenticationManager authenticationManager,
            ModelMapper modelMapper) {

        this.electionRepository = electionRepository;
        this.voteRepository = voteRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    @Autowired
    @Lazy
    public void setCandidateService(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("Login method called with unique identifier: {}", request.getUniqueIdentifier());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUniqueIdentifier().toLowerCase(), request.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            LoginResponse response = modelMapper.map(userDetails, LoginResponse.class);
            response.setMessage("User logged in successfully");
            String token = tokenService.generateToken(authentication);
            response.setToken(token);
            response.setTokenExpirationDate(now().plusHours(1));
            response.setType("Bearer");
            log.info("User logged in successfully with unique identifier: {}", request.getUniqueIdentifier());
            return response;
        } catch (AuthenticationException e) {
            log.info("Invalid identifier or password for unique identifier: {}", request.getUniqueIdentifier());
            throw new FailedVerificationException("Invalid identifier or password");
        }
    }

    @Override
    public Election getElectionBy(Long id) {
        log.info("Fetching election with id: {}", id);
        Election election = electionRepository.findById(id)
                .orElseThrow(()-> new ElectionNotFoundException("Election not found"));
        log.info("Election fetched successfully with id: {}", id);
        return election;
    }

    @Override
    public List<Election> getAllElections() {
        log.info("Fetching all elections");
        List<Election> elections = electionRepository.findAll();
        log.info("All elections fetched successfully");
        return elections;
    }

    @Override
    public ViewElectionResultsResponse viewElectionResults(Long electionId) {
        log.info("Fetching election results with election id: {}", electionId);
        if (!electionRepository.existsById(electionId)) throw new ElectionNotFoundException("Election not found");
        var candidates = candidateService.getCandidatesFor(electionId);
        var totalVotes = voteRepository.findVotesFor(electionId);
        ViewElectionResultsResponse response = new ViewElectionResultsResponse();
        response.setTotalVotes(totalVotes.size());
        candidates.forEach(candidate -> {
            CandidateResultResponse candidateResponse = getCandidateResponse(candidate, electionId);
            response.getCandidateResultResponses().add(candidateResponse);
        });
        log.info("Election results fetched successfully with election id: {}", electionId);
        return response;
    }

    private CandidateResultResponse getCandidateResponse(Candidate candidate, Long electionId) {
        CandidateResultResponse candidateResponse = modelMapper.map(candidate, CandidateResultResponse.class);
        var votes = voteRepository.findVotesByCandidateAndElection(candidate.getId(), electionId);
        candidateResponse.setNumberOfVotes(votes.size());
        return candidateResponse;
    }

}
