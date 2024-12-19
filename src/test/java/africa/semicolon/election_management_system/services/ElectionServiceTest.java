package africa.semicolon.election_management_system.services;

import africa.semicolon.election_management_system.data.models.Election;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static africa.semicolon.election_management_system.data.constants.Category.NATIONAL;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("/db/data.sql")
public class ElectionServiceTest {

    @Autowired
    private ElectionService electionService;

    @Test
    public void getElectionTest() {
        Election election = electionService.getElectionBy(302L);
        assertThat(election).isNotNull();
        assertThat(election.getCategory()).isEqualTo(NATIONAL);
    }

    @Test
    public void getAllElectionsTest() {
        List<Election> elections = electionService.getAllElections();
        assertThat(elections).isNotNull();
        assertThat(elections).hasSize(3);
    }

    @Test
    public void viewElectionResultsTest() {
        var response = electionService.viewElectionResults(302L);
        System.out.println(response);
        assertThat(response).isNotNull();
        assertThat(response.getTotalVotes()).isEqualTo(7);
        assertThat(response.getCandidateResultResponses()).hasSize(3);
    }
}