package africa.semicolon.election_management_system.controllers;

import africa.semicolon.election_management_system.dtos.requests.RegisterAdminRequest;
import africa.semicolon.election_management_system.dtos.requests.ScheduleElectionRequest;
import africa.semicolon.election_management_system.utils.AuthUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static africa.semicolon.election_management_system.data.constants.Category.LGA;
import static java.time.Month.SEPTEMBER;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts ={"/db/data.sql"})
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthUtils authUtils;


    @Test
    void testRegisterAdmin() throws Exception {
        RegisterAdminRequest registerAdminRequest = new RegisterAdminRequest();
        registerAdminRequest.setUsername("Testing@One");
        registerAdminRequest.setPassword("password");
        mockMvc.perform(post("/api/v1/admin/register")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerAdminRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void testSchedulingElectionEndPoint() throws Exception {
        ScheduleElectionRequest scheduleElectionRequest = new ScheduleElectionRequest();
        scheduleElectionRequest.setTitle("LGA Election 3");
        scheduleElectionRequest.setCategory(LGA);
        scheduleElectionRequest.setStartDate(LocalDateTime.of(2024, SEPTEMBER, 19, 12, 0));
        scheduleElectionRequest.setEndDate(LocalDateTime.of(2024, SEPTEMBER, 21, 12, 0));
        String token = authUtils.getToken("username");
//        String token = authUtils.getToken("123451");
        mockMvc.perform(post("/api/v1/admin/schedule-election")
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(scheduleElectionRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}