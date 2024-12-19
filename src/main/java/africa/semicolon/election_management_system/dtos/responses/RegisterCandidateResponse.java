package africa.semicolon.election_management_system.dtos.responses;

import africa.semicolon.election_management_system.data.constants.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RegisterCandidateResponse {
    @JsonProperty("candidate_id")
    private Long id;
    private String partyAffiliation;
    private Category positionContested;
    private Integer votingId;
    @JsonFormat(pattern = "dd/MMM/yyyy 'at' hh:mm a")
    private LocalDateTime dateRegistered;
    private String message;
}
