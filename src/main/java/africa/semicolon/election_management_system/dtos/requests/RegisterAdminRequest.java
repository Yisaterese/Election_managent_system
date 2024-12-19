package africa.semicolon.election_management_system.dtos.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAdminRequest {
    private String username;
    private String password;
}
