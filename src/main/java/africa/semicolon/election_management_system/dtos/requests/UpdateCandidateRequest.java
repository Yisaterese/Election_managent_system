package africa.semicolon.election_management_system.dtos.requests;

import africa.semicolon.election_management_system.data.constants.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
public class UpdateCandidateRequest {
    private String name;
    private String identificationNumber;
    private String password;
    private String address;
    private LocalDate dateOfBirth;
    private String stateOfOrigin;
    private String partyAffiliation;
    private Category positionContested;
    private Long electionId;
}
