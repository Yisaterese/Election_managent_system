package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.dtos.requests.CastVoteRequest;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.responses.ApiResponse;
import africa.semicolon.election_management_system.dtos.responses.UpdateVoterResponse;
import africa.semicolon.election_management_system.services.VoterService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/voter")
@AllArgsConstructor
public class VoterController {
    private final VoterService voterService;

    @PostMapping("/register")
    public ResponseEntity<?> registerVoter(@RequestBody CreateVoterRequest createVoterRequest){
        var result = voterService.registerVoter(createVoterRequest);
        ApiResponse response = getApiResponse(result);
        return ResponseEntity.status(CREATED).body(response);
    }

    @PostMapping("/cast-vote")
    public ResponseEntity<?> castVote(@RequestBody CastVoteRequest request){
        var result = voterService.castVote(request);
        ApiResponse response = getApiResponse(result);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/update/{votingId}")
    public ResponseEntity<?> updateVotersDetail(@PathVariable Long votingId, @RequestBody JsonPatch jsonPatch) {
        UpdateVoterResponse result = voterService.updateVoter(votingId, jsonPatch);
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
