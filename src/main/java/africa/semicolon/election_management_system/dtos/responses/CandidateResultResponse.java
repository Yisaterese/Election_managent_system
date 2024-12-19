package africa.semicolon.election_management_system.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class CandidateResultResponse {
    @JsonProperty("candidate_id")
    private Long id;
    @JsonProperty("candidate_name")
    private String name;
    private String partyAffiliation;
    @JsonProperty("position_contested")
    private String positionContested;
    private int numberOfVotes;
}
