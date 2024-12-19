package africa.semicolon.election_management_system.data.repositories;

import africa.semicolon.election_management_system.data.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Query("SELECT c FROM Candidate c WHERE c.election.id=:electionId" )
    List<Candidate> findAllCandidatesFor(Long electionId);

    Optional<Candidate> findByIdentificationNumber(String identificationNumber);
}

