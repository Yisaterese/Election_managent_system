package africa.semicolon.election_management_system.dtos.requests;

import africa.semicolon.election_management_system.data.models.Voter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class UpdateVoterRequest {
    private Voter voter;
    private Long votingId;
    private String name;
    private String password;
    private String address;
    private LocalDate dateOfBirth;
    private String stateOfOrigin;
}
