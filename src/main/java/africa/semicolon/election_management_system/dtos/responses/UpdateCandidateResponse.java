package africa.semicolon.election_management_system.dtos.responses;

import africa.semicolon.election_management_system.data.constants.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UpdateCandidateResponse {
    private String message;
    private Long id;

}
