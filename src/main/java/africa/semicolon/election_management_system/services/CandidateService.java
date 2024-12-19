package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.dtos.requests.DeleteCandidateRequest;
import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import africa.semicolon.election_management_system.dtos.requests.UpdateCandidateRequest;
import africa.semicolon.election_management_system.dtos.responses.DeleteCandidateResponse;
import africa.semicolon.election_management_system.dtos.responses.RegisterCandidateResponse;
import africa.semicolon.election_management_system.dtos.responses.UpdateCandidateResponse;

import java.util.List;

public interface CandidateService {
    RegisterCandidateResponse registerCandidate(RegisterCandidateRequest request);
    Candidate getCandidateBy(Long id);
    List<Candidate> getCandidatesFor(Long electionId);
    DeleteCandidateResponse deleteCandidate(DeleteCandidateRequest deleteCandidateRequest);
    UpdateCandidateResponse updateCandidate(UpdateCandidateRequest updateCandidateRequest);
}
