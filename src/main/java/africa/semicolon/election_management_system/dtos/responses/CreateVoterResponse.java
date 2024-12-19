package africa.semicolon.election_management_system.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateVoterResponse {
    @JsonProperty("voter_id")
    private Long id;
    private String name;
    private String identificationNumber;
    private Long votingId;
    private String message;
    @JsonFormat(pattern = "dd/MMM/yyyy 'at' hh:mm a")
    private LocalDateTime dateRegistered;
}
