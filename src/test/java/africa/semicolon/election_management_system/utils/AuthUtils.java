package africa.semicolon.election_management_system.utils;

import africa.semicolon.election_management_system.dtos.requests.LoginRequest;
import africa.semicolon.election_management_system.services.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AuthUtils {

    @Autowired
    private ElectionService electionService;

    public String getToken(String uniqueIdentifier)  {
        LoginRequest request = new LoginRequest();
        request.setUniqueIdentifier(uniqueIdentifier);
        request.setPassword("password");
        var response = electionService.login(request);
        return response.getToken();
    }
}
