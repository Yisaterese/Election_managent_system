package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Admin;
import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.data.models.Election;
import africa.semicolon.election_management_system.data.repositories.AdminRepository;
import africa.semicolon.election_management_system.data.repositories.CandidateRepository;
import africa.semicolon.election_management_system.data.repositories.ElectionRepository;
import africa.semicolon.election_management_system.dtos.requests.*;
import africa.semicolon.election_management_system.dtos.responses.*;
import africa.semicolon.election_management_system.exceptions.AdminNotFoundException;
import africa.semicolon.election_management_system.exceptions.ElectionManagementSystemBaseException;
import africa.semicolon.election_management_system.exceptions.UsernameExistsException;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ElectionRepository electionRepository;
    private final CandidateService candidateService;
    private final VoterService voterService;

    private final CandidateRepository candidateRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, ElectionRepository electionRepository,
                            CandidateService candidateService, VoterService voterService, CandidateRepository candidateRepository,
                            ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.electionRepository = electionRepository;
        this.candidateService = candidateService;
        this.voterService = voterService;
        this.candidateRepository = candidateRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterAdminResponse register(RegisterAdminRequest request) {
        String username = request.getUsername();
        validate(username);
        request.setUsername(username.toLowerCase());
        Admin admin = modelMapper.map(request, Admin.class);
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin = adminRepository.save(admin);
        var adminResponse = modelMapper.map(admin, RegisterAdminResponse.class);
        adminResponse.setMessage("User registered successfully");
        return adminResponse;
    }

    @Override
    public ScheduleElectionResponse scheduleElection(ScheduleElectionRequest request) {
        log.info("Schedule election method called by admin");
        Election election = modelMapper.map(request, Election.class);
        election = electionRepository.save(election);
        var electionResponse = modelMapper.map(election, ScheduleElectionResponse.class);
        electionResponse.setTimeCreated(now());
        electionResponse.setMessage("Election scheduled successfully");
        log.info("Election scheduled successfully by admin");
        return electionResponse;
    }

    @Override
    public RegisterCandidateResponse registerCandidate(RegisterCandidateRequest request) {
        return candidateService.registerCandidate(request);
    }

    @Override
    public Candidate getCandidateBy(Long id) {
        return candidateService.getCandidateBy(id);
    }

    @Override
    public List<Candidate> getCandidatesFor(Long electionId) {
        return candidateService.getCandidatesFor(electionId);
    }

    @Override
    public DeleteCandidateResponse deleteCandidate(Long candidateId) {
        Candidate candidate = getCandidateBy(candidateId);
        candidateRepository.delete(candidate);
        DeleteCandidateResponse deleteCandidateResponse = new DeleteCandidateResponse();
        deleteCandidateResponse.setMessage("Candidate deleted successfully");
        return deleteCandidateResponse;
    }

    @Override
    public UpdateCandidateResponse updateCandidate(UpdateCandidateRequest updateCandidateRequest) {
        return candidateService.updateCandidate(updateCandidateRequest);
    }

    @Override
    public Admin getAdminBy(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(()-> new AdminNotFoundException("Admin not found"));
    }

    @Override
    public UpdateVoterResponse updateVoterAsAdmin(Long votingId, JsonPatch jsonPatch) {
        return voterService.updateVoter(votingId, jsonPatch);
    }

    private void validate(String username) {
        if (username == null || username.isEmpty()) {
            throw new ElectionManagementSystemBaseException("Username cannot be null or empty");
        }
        boolean usernameExists = adminRepository.existsByUsername(username.toLowerCase());
        if (usernameExists) throw new UsernameExistsException("Username already taken");
    }

}
