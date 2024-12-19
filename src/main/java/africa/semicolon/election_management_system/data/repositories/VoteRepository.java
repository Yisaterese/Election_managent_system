package africa.semicolon.election_management_system.data.repositories;

import africa.semicolon.election_management_system.data.models.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("SELECT v FROM Vote v WHERE v.voter.id=:voterId AND v.election.id=:electionId")
    List<Vote> findVotesByVoterAndElection(Long voterId, Long electionId);

    @Query("SELECT v FROM Vote v WHERE v.candidate.id=:candidateId AND v.election.id=:electionId")
    List<Vote> findVotesByCandidateAndElection(Long candidateId, Long electionId);

    @Query("SELECT v FROM Vote v WHERE v.election.id=:electionId")
    List<Vote> findVotesFor(Long electionId);
}
