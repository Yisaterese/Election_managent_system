package africa.semicolon.election_management_system.exceptions;

public class IneligibleToVoteException extends ElectionManagementSystemBaseException {
    public IneligibleToVoteException(String message){
        super(message);
    }

}
