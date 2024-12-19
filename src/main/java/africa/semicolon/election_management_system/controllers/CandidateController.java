package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.dtos.requests.DeleteCandidateRequest;
import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import africa.semicolon.election_management_system.dtos.requests.UpdateCandidateRequest;
import africa.semicolon.election_management_system.dtos.responses.ApiResponse;
import africa.semicolon.election_management_system.services.CandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/candidate")
public class CandidateController {
    private final CandidateService candidaService;

    public CandidateController(CandidateService candidaService) {
        this.candidaService = candidaService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCandidate(@RequestBody RegisterCandidateRequest registerCandidateRequest){
        var result = candidaService.registerCandidate(registerCandidateRequest);
        ApiResponse response = getApiResponse(result);
        return ResponseEntity.status(CREATED).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCandidate(@RequestBody DeleteCandidateRequest deleteCandidateRequest){
        var result = candidaService.deleteCandidate(deleteCandidateRequest);
        ApiResponse response = getApiResponse(result);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateCandidate(@RequestBody UpdateCandidateRequest updateCandidateRequest){
        var result = candidaService.updateCandidate(updateCandidateRequest);
        ApiResponse response = getApiResponse(result);
        return ResponseEntity.ok().body(response);
    }

    private static ApiResponse getApiResponse(Object data) {
        return ApiResponse.builder()
                .requestTime(now())
                .success(true)
                .data(data)
                .build();
    }

}
