package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Candidate;
import africa.semicolon.election_management_system.dtos.requests.DeleteCandidateRequest;
import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import africa.semicolon.election_management_system.dtos.requests.UpdateCandidateRequest;
import africa.semicolon.election_management_system.dtos.responses.DeleteCandidateResponse;
import africa.semicolon.election_management_system.dtos.responses.RegisterCandidateResponse;
import africa.semicolon.election_management_system.dtos.responses.UpdateCandidateResponse;
import africa.semicolon.election_management_system.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static africa.semicolon.election_management_system.utils.TestUtils.buildCandidateRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("/db/data.sql")
class CandidateServiceTest {

    @Autowired
    private CandidateService candidateService;


    @Test
    public void registerCandidateTest() {
        RegisterCandidateRequest request = buildCandidateRequest();
        RegisterCandidateResponse response = candidateService.registerCandidate(request);
        System.out.println(response);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("success");
    }

    @Test
    @DisplayName("test that candidate cannot register outside a scheduled election")
    public void registerCandidateTest2() {
        RegisterCandidateRequest request = buildCandidateRequest();
        request.setElectionId(200L);
        assertThrows(ResourceNotFoundException.class, ()-> candidateService.registerCandidate(request));
    }

    @Test
    public void deleteCandidateTest(){
        RegisterCandidateRequest request = buildCandidateRequest();
        request.setElectionId(300L);
        RegisterCandidateResponse response = candidateService.registerCandidate(request);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("Candidate registered successfully");
        DeleteCandidateRequest deleteCandidateRequest = new DeleteCandidateRequest();
        deleteCandidateRequest.setId(response.getId());
        DeleteCandidateResponse deleteCandidateResponse = candidateService.deleteCandidate(deleteCandidateRequest);
        assertThat(deleteCandidateResponse).isNotNull();
        assertThat(deleteCandidateResponse.getMessage()).contains("Candidate deleted successfully");

    }

    @Test
    public void updateCandidateTest(){
        RegisterCandidateRequest registerCandidateRequest = buildCandidateRequest();
        registerCandidateRequest.setElectionId(301L);
        buildCandidateRequest().setIdentificationNumber("4865389087");
        RegisterCandidateResponse registerCandidateResponse = candidateService.registerCandidate(registerCandidateRequest);
        assertThat(registerCandidateResponse).isNotNull();
        assertThat(registerCandidateResponse.getMessage()).isEqualTo("Candidate registered successfully");
        Candidate registeredCandidate = candidateService.getCandidateBy(registerCandidateResponse.getId());
        assertThat(registeredCandidate.getName()).isEqualTo("John");

        UpdateCandidateRequest updateCandidateRequest = new UpdateCandidateRequest();
        updateCandidateRequest.setName("Joseph");
        updateCandidateRequest.setIdentificationNumber(registeredCandidate.getIdentificationNumber());
        updateCandidateRequest.setElectionId(301L);
        registeredCandidate.setName(updateCandidateRequest.getName());
        UpdateCandidateResponse updateCandidateResponse = candidateService.updateCandidate(updateCandidateRequest);
        assertThat(updateCandidateResponse).isNotNull();
        assertThat(updateCandidateResponse.getMessage()).isEqualTo("Candidate updated successfully");
        registeredCandidate = candidateService.getCandidateBy(updateCandidateResponse.getId());
        assertThat(registeredCandidate.getName()).isEqualTo(updateCandidateRequest.getName());
    }

}