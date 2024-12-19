package africa.semicolon.election_management_system.utils;

import africa.semicolon.election_management_system.dtos.requests.CastVoteRequest;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;

import java.time.LocalDate;

import static africa.semicolon.election_management_system.data.constants.Category.NATIONAL;

public class TestUtils {

    public static CastVoteRequest buildCastVoteRequest() {
        CastVoteRequest castVoteRequest = new CastVoteRequest();
        castVoteRequest.setVotingId(654321L);
        castVoteRequest.setCandidateId(400L);
        castVoteRequest.setElectionId(301L);
        return castVoteRequest;
    }

    public static CreateVoterRequest buildCreateVoterRequest() {
        CreateVoterRequest request = new CreateVoterRequest();
        request.setName("John Doe");
        request.setPassword("password");
        request.setAddress("123 Main St");
        request.setIdentificationNumber("34567891");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setStateOfOrigin("Lagos");
        return request;
    }

    public static CreateVoterRequest buildCreateIneligibleVoterRequest() {
        CreateVoterRequest request = new CreateVoterRequest();
        request.setName("John Doe");
        request.setPassword("password");
        request.setAddress("123 Main St");
        request.setIdentificationNumber("34567891");
        request.setDateOfBirth(LocalDate.of(2008, 1, 1));
        request.setStateOfOrigin("Lagos");
        return request;
    }

    public static RegisterCandidateRequest buildCandidateRequest() {
        RegisterCandidateRequest request = new RegisterCandidateRequest();
        request.setName("John");
        request.setAddress("no 29 adewale str");
        request.setIdentificationNumber("12343487443");
        request.setPassword("password");
        request.setDateOfBirth(LocalDate.of(1980, 10, 2));
        request.setPartyAffiliation("P.D.P");
        request.setPositionContested(NATIONAL);
        request.setStateOfOrigin("Benue");
        request.setElectionId(300L);
        return request;
    }

}
