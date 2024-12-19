package africa.semicolon.election_management_system.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAdminResponse {
    @JsonProperty("admin_id")
    private Long id;
    private String username;
    private String message;
}
