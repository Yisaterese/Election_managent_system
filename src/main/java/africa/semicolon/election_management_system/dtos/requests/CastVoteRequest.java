package africa.semicolon.election_management_system.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CastVoteRequest {
    private Long votingId;
    private Long candidateId;
    private Long electionId;
}
