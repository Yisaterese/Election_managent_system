package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Admin;
import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.dtos.requests.*;
import africa.semicolon.election_management_system.dtos.responses.*;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface AdminService {
    RegisterAdminResponse register(RegisterAdminRequest request);
    ScheduleElectionResponse scheduleElection(ScheduleElectionRequest request);
    RegisterCandidateResponse registerCandidate(RegisterCandidateRequest request);
    Candidate getCandidateBy(Long id);
    List<Candidate> getCandidatesFor(Long electionId);
    DeleteCandidateResponse deleteCandidate(Long candidateId);
    UpdateCandidateResponse updateCandidate(UpdateCandidateRequest updateCandidateRequest);
    Admin getAdminBy(Long id);
    UpdateVoterResponse updateVoterAsAdmin(Long votingId, JsonPatch jsonPatch);
}
