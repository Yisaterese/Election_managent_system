package africa.semicolon.election_management_system.exceptions;

public class InvalidVoteException extends RuntimeException {
    public InvalidVoteException(String message){
        super(message);
    }

}
