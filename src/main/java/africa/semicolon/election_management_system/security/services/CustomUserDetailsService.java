package africa.semicolon.election_management_system.security.services;

import africa.semicolon.election_management_system.data.constants.Role;
import africa.semicolon.election_management_system.data.models.Admin;
import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.data.models.Voter;
import africa.semicolon.election_management_system.data.repositories.AdminRepository;
import africa.semicolon.election_management_system.data.repositories.CandidateRepository;
import africa.semicolon.election_management_system.data.repositories.VoterRepository;
import africa.semicolon.election_management_system.security.models.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private AdminRepository adminRepository;
    private CandidateRepository candidateRepository;
    private VoterRepository voterRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        log.info("Trying to get user by unique identifier");

        Voter voter = voterRepository.findByIdentificationNumber(identifier).orElse(null);
        if (voter != null) return buildUserDetails("VOTER", voter.getId(),
                voter.getIdentificationNumber(), voter.getPassword(), voter.getRole());
        else logUserNotFound("VOTER", "CANDIDATE", identifier);

        Candidate candidate = candidateRepository.findByIdentificationNumber(identifier).orElse(null);
        if (candidate != null) return buildUserDetails("CANDIDATE", candidate.getId(),
                candidate.getIdentificationNumber(), candidate.getPassword(), candidate.getRole());
        else logUserNotFound("CANDIDATE", "ADMIN", identifier);

        Admin admin = adminRepository.findByUsername(identifier)
                .orElseThrow(()-> {
                    log.error("ADMIN not found with unique identifier: {}", identifier);
                    return new UsernameNotFoundException("ADMIN not found with unique identifier: " + identifier);
                });

        return buildUserDetails("ADMIN", admin.getId(), admin.getUsername(), admin.getPassword(), admin.getRole());
    }

    private UserDetails buildUserDetails(String user, Long id, String username, String password, Role role) {
        log.info("Found {} user with identifier: {}", user, username);
        List<String> roles = List.of(role.name());
        return new UserDetailsImpl(id, username, password, roles);
    }

    private static void logUserNotFound(String user, String other, String identifier) {
        log.info("{} not found. Checking for {}: {}", user, other, identifier);
    }

}
