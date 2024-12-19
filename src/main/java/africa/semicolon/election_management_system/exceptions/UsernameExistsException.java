package africa.semicolon.election_management_system.exceptions;

public class UsernameExistsException extends ElectionManagementSystemBaseException {
    public UsernameExistsException(String message) {
        super(message);
    }
}
