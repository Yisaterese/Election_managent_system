package africa.semicolon.election_management_system.dtos.responses;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CastVoteResponse {
    @JsonProperty("vote_id")
    private Long id;
    private String message;
    @JsonFormat(pattern = "dd/MMM/yyyy 'at' hh:mm a")
    private LocalDateTime dateCasted;
}
