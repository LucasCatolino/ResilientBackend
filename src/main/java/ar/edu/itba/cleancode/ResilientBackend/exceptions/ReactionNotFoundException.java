package ar.edu.itba.cleancode.resilientbackend.exceptions;

public class ReactionNotFoundException extends RuntimeException {

    public ReactionNotFoundException (Long tweetId) {
        super("Reaction not found for tweet id: " + tweetId);
    }

}
