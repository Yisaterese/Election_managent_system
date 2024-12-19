package africa.semicolon.election_management_system.exceptions;

public class IdentificationNumberAlreadyExistsException extends ElectionManagementSystemBaseException{
    public IdentificationNumberAlreadyExistsException(String message){
        super(message);
    }
}
