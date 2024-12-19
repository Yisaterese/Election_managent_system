package africa.semicolon.election_management_system;

import africa.semicolon.election_management_system.data.models.Admin;
import africa.semicolon.election_management_system.data.models.Voter;
import africa.semicolon.election_management_system.data.repositories.AdminRepository;
import africa.semicolon.election_management_system.data.repositories.VoterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class ScriptTest {
    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Test
    @DisplayName("testing dummy data")
    public void testScript() {
        List<Voter> voters = voterRepository.findAll();
        assertThat(voters.size()).isGreaterThanOrEqualTo(7);
        System.out.println(voters);
    }

    @Test
    @DisplayName("testing dummy data for admin entity")
    public void testScript2() {
        List<Admin> admins = adminRepository.findAll();
        assertThat(admins).hasSize(1);
        System.out.println(admins);
    }

}
