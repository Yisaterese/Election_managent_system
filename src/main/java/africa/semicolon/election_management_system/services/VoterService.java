package africa.semicolon.election_management_system.services;
import africa.semicolon.election_management_system.data.models.Voter;
import africa.semicolon.election_management_system.dtos.requests.CastVoteRequest;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.responses.CastVoteResponse;
import africa.semicolon.election_management_system.dtos.responses.CreateVoterResponse;
import africa.semicolon.election_management_system.dtos.responses.UpdateVoterResponse;
import com.github.fge.jsonpatch.JsonPatch;

public interface VoterService {

    CreateVoterResponse registerVoter(CreateVoterRequest request);
    Voter getVoterById(Long id);
    Voter getVoterByVotingId(Long votingId);
    UpdateVoterResponse updateVoter(Long votingId, JsonPatch jsonPatch);
    CastVoteResponse castVote(CastVoteRequest castVoteRequest);

}

