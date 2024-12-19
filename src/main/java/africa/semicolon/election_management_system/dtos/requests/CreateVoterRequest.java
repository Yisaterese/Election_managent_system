package africa.semicolon.election_management_system.dtos.requests;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class CreateVoterRequest {
    private String name;
    private String password;
    private String address;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;
    private String stateOfOrigin;
    private String identificationNumber;
}
