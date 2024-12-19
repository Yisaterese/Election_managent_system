package africa.semicolon.election_management_system.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ApiResponse {
    @JsonFormat(pattern = "dd-MMM-yyyy 'at' hh:mm:ss a")
    private LocalDateTime requestTime;
    private boolean success;
    private Object data;
}