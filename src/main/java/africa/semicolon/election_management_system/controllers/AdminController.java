package africa.semicolon.election_management_system.controllers;


import africa.semicolon.election_management_system.dtos.requests.RegisterAdminRequest;
import africa.semicolon.election_management_system.dtos.requests.ScheduleElectionRequest;
import africa.semicolon.election_management_system.dtos.responses.ApiResponse;
import africa.semicolon.election_management_system.dtos.responses.UpdateVoterResponse;
import africa.semicolon.election_management_system.services.AdminService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterAdminRequest registerAdminRequest) {
        var result = adminService.register(registerAdminRequest);
        ApiResponse response = getApiResponse(result);
        return ResponseEntity.status(CREATED).body(response);
    }

    @PostMapping("/schedule-election")
    public ResponseEntity<?> scheduleElection(@RequestBody ScheduleElectionRequest scheduleElectionRequest) {
        var result = adminService.scheduleElection(scheduleElectionRequest);
        ApiResponse response = getApiResponse(result);
        return ResponseEntity.status(CREATED).body(response);
    }

    @PatchMapping("/update-as-admin/{votingId}")
    public ResponseEntity<?> updateVotersDetail(@PathVariable Long votingId, @RequestBody JsonPatch jsonPatch) {
        UpdateVoterResponse result = adminService.updateVoterAsAdmin(votingId, jsonPatch);
        ApiResponse response = getApiResponse(result);
        return ResponseEntity.ok(response);
    }

    private static ApiResponse getApiResponse(Object data) {
        return ApiResponse.builder()
                .requestTime(now())
                .success(true)
                .data(data)
                .build();
    }
}
