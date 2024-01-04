package ar.edu.itba.cleancode.resilientbackend.exceptions;

public class CouldNotDeleteUserException extends RuntimeException {

    public CouldNotDeleteUserException (Long id) {
        super("Could not delete user with id: " + id);
    }
    
}