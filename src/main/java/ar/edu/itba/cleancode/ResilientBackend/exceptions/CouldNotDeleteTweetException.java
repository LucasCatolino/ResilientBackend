package ar.edu.itba.cleancode.resilientbackend.exceptions;

public class CouldNotDeleteTweetException extends RuntimeException {

    public CouldNotDeleteTweetException (Long id) {
        super("Could not delete tweet with id: " + id);
    }
    
}
