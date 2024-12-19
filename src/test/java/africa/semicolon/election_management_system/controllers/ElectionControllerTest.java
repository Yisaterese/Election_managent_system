package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.dtos.requests.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/db/data.sql")
public class ElectionControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void whenAuthenticated_thenStatusIsOk() throws Exception {
        mvc.perform(get("/api/v1/election/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void loginTest() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUniqueIdentifier("123451");
        request.setPassword("password");
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post("/api/v1/election/login")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void viewElectionResults() throws Exception {
        mvc.perform(get("/api/v1/election/view-results/302"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalVotes").value(7))
                .andDo(print());

    }

    @Test
    public void getElectionTest() throws Exception {
        mvc.perform(get("/api/v1/election/302"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.category").value("NATIONAL"))
                .andDo(print());
    }

    @Test
    public void getAllElectionsTest() throws Exception {
        mvc.perform(get("/api/v1/election/all"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}