package ar.edu.itba.cleancode.resilientbackend.exceptions;

public class CommentException extends RuntimeException {

    public CommentException (Long tweetId, Long userId) {
        super("Could not create comment with tweetId " + tweetId + " and userId " + userId);
    }
    
}
