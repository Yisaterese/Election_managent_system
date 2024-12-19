package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.data.repositories.ElectionRepository;
import africa.semicolon.election_management_system.dtos.requests.CastVoteRequest;
import africa.semicolon.election_management_system.dtos.requests.CreateVoterRequest;
import africa.semicolon.election_management_system.dtos.responses.UpdateVoterResponse;
import africa.semicolon.election_management_system.utils.AuthUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static africa.semicolon.election_management_system.utils.TestUtils.buildCreateVoterRequest;
import static java.time.LocalDateTime.now;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class VoterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private AuthUtils authUtils;

    @Test
    public void testRegisterVoter() throws Exception {
        CreateVoterRequest request = buildCreateVoterRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/v1/voter/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isCreated())
                        .andDo(print());
    }


    @Test
    public void testCastVote() throws Exception {
        CastVoteRequest request = new CastVoteRequest();
        request.setVotingId(654321L);
        request.setElectionId(301L);
        request.setCandidateId(400L);
        updateElection();
        String token = authUtils.getToken("123451");
        mockMvc.perform(post("/api/v1/voter/cast-vote")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void testUpdateVotersDetail() throws Exception {
        Long votingId = 654321L;
        String jsonPatch = "[{\"op\":\"replace\",\"path\":\"/address\",\"value\":\"4 Afolabi street\"}]";
        UpdateVoterResponse response = new UpdateVoterResponse();
        response.setAddress("");
        String token = authUtils.getToken("123451");
        mockMvc.perform(patch("/api/v1/voter/update/{votingId}", votingId)
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json-patch+json")
                        .content(jsonPatch))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data..address").value("4 Afolabi street"))
                .andDo(print());


    }

    private void updateElection() {
        var election = electionRepository.findById(301L).orElseThrow();
        election.setStartDate(now().minusDays(1));
        election.setEndDate(now().plusDays(1));
        electionRepository.save(election);
    }
}