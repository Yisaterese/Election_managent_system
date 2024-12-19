package africa.semicolon.election_management_system;

import africa.semicolon.election_management_system.security.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class ElectionManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectionManagementSystemApplication.class, args);
	}

}
