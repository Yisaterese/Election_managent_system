package africa.semicolon.election_management_system.exceptions;

public class FailedVerificationException extends ElectionManagementSystemBaseException {
    public FailedVerificationException(String message){
        super(message);
    }
}
