package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Election;
import africa.semicolon.election_management_system.dtos.requests.LoginRequest;
import africa.semicolon.election_management_system.dtos.responses.LoginResponse;
import africa.semicolon.election_management_system.dtos.responses.ViewElectionResultsResponse;

import java.util.List;

public interface ElectionService {
    LoginResponse login(LoginRequest request);
    Election getElectionBy(Long id);
    List<Election> getAllElections();
    ViewElectionResultsResponse viewElectionResults(Long electionId);
}
