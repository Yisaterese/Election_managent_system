package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.data.models.Election;
import africa.semicolon.election_management_system.dtos.requests.LoginRequest;
import africa.semicolon.election_management_system.dtos.responses.ApiResponse;
import africa.semicolon.election_management_system.dtos.responses.GetElectionResponse;
import africa.semicolon.election_management_system.services.ElectionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/election")
public class ElectionController {

    private final ElectionService electionService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        var response = electionService.login(request);
        ApiResponse apiResponse = getApiResponse(response);
        return ResponseEntity.status(CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getElection(@PathVariable Long id) {
        Election election = electionService.getElectionBy(id);
        GetElectionResponse response = modelMapper.map(election, GetElectionResponse.class);
        ApiResponse apiResponse = getApiResponse(response);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllElections() {
        List<Election> elections = electionService.getAllElections();
        List<GetElectionResponse> response = List.of(modelMapper.map(elections, GetElectionResponse[].class));
        ApiResponse apiResponse = getApiResponse(response);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/view-results/{electionId}")
    public ResponseEntity<?> viewElectionResults(@PathVariable Long electionId) {
        var response = electionService.viewElectionResults(electionId);
        ApiResponse apiResponse = getApiResponse(response);
        return ResponseEntity.ok().body(apiResponse);
    }

    private static ApiResponse getApiResponse(Object data) {
        return ApiResponse.builder()
                .requestTime(now())
                .success(true)
                .data(data)
                .build();
    }

}
