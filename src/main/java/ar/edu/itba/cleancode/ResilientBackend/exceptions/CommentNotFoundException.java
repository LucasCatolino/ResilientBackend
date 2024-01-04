package ar.edu.itba.cleancode.resilientbackend.exceptions;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException (Long tweetId) {
        super("Comment not found for tweet id: " + tweetId);
    }

}
