package africa.semicolon.election_management_system.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetElectionResponse {
    @JsonProperty("election_id")
    private Long id;
    private String title;
    private String category;
    @JsonFormat(pattern = "dd-MMM-yyyy 'at' hh:mm:ss a")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "dd-MMM-yyyy 'at' hh:mm:ss a")
    private LocalDateTime endDate;

}
