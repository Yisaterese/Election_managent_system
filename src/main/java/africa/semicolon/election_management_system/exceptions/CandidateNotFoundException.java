package africa.semicolon.election_management_system.exceptions;

public class CandidateNotFoundException extends RuntimeException{
    public CandidateNotFoundException(String message) {
        super(message);
    }
}
