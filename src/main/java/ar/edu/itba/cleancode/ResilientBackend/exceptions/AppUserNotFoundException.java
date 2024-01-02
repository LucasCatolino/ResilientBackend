package ar.edu.itba.cleancode.resilientbackend.exceptions;

public class AppUserNotFoundException extends RuntimeException {

    public AppUserNotFoundException (Long id) {
        super("User not found with id: " + id);
    }
    
}
