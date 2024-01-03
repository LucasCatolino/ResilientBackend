package ar.edu.itba.cleancode.resilientbackend.exceptions;

public class ReactionException extends RuntimeException {

    public ReactionException (Long tweetId, Long userId) {
        super("Could not create reaction with tweetId " + tweetId + " and userId " + userId);
    }
    
}
