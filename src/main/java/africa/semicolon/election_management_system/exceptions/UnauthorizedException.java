package africa.semicolon.election_management_system.exceptions;

public class UnauthorizedException extends ElectionManagementSystemBaseException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
