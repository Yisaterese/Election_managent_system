package africa.semicolon.election_management_system.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ViewElectionResultsResponse {
    private int totalVotes;
    private List<CandidateResultResponse> candidateResultResponses = new ArrayList<>();
}
