package africa.semicolon.election_management_system.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class LoginResponse {
    @JsonProperty("user_id")
    private Long id;
    private String message;
    private String token;
    private String type;
    @JsonProperty("unique_identifier")
    private String username;
    private List<String> roles;
    @JsonFormat(pattern = "dd/MMM/yyyy 'at' hh:mm:ss a")
    private LocalDateTime tokenExpirationDate;
}
