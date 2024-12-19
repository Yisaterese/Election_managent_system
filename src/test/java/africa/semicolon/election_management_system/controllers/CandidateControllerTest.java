package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.dtos.requests.DeleteCandidateRequest;
import africa.semicolon.election_management_system.dtos.requests.RegisterCandidateRequest;
import africa.semicolon.election_management_system.utils.AuthUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static africa.semicolon.election_management_system.utils.TestUtils.buildCandidateRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts ={"/db/data.sql"})
public class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthUtils authUtils;

    @Test
    void testRegisterCandidateTest() throws Exception {
        RegisterCandidateRequest registerCandidateRequest = buildCandidateRequest();
        mockMvc.perform(post("/api/v1/candidate/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerCandidateRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public  void deleteCandidateTest() throws Exception {
        DeleteCandidateRequest deleteCandidateRequest = new DeleteCandidateRequest();
        deleteCandidateRequest.setId(400L);
        String token = authUtils.getToken("123451");
        mockMvc.perform(delete("/api/v1/candidate/delete")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deleteCandidateRequest)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

}
