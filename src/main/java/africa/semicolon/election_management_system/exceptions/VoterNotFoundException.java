package africa.semicolon.election_management_system.exceptions;

public class VoterNotFoundException extends ElectionManagementSystemBaseException{
    public VoterNotFoundException(String message) {
        super(message);
    }
}
