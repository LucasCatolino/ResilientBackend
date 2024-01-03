package ar.edu.itba.cleancode.resilientbackend.exceptions;

public class TweetNotFoundException extends RuntimeException {

    public TweetNotFoundException (Long id) {
        super("Tweet not found with id: " + id);
    }
    
}
