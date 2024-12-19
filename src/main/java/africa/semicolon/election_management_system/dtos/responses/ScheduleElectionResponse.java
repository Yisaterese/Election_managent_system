package africa.semicolon.election_management_system.dtos.responses;

import africa.semicolon.election_management_system.data.constants.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleElectionResponse {
    @JsonProperty("election_id")
    private Long id;
    private Category category;
    @JsonFormat(pattern = "dd/MMM/yyyy 'at' hh:mm a")
    private LocalDateTime timeCreated;
    @JsonFormat(pattern = "dd/MMM/yyyy 'at' hh:mm a")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "dd/MMM/yyyy 'at' hh:mm a")
    private LocalDateTime endDate;
    private String message;
}
