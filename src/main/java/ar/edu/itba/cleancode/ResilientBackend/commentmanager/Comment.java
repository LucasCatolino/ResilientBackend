package ar.edu.itba.cleancode.resilientbackend.commentmanager;

import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUser;

import ar.edu.itba.cleancode.resilientbackend.tweetmanager.Tweet;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {

    private static final String BACKEND_COMMENT= "backendusers";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private AppUser userId;

    @ManyToOne
    @JoinColumn(name = "tweetId", nullable = false)
    private Tweet tweetId;

    private String comment;

    @CircuitBreaker (name = BACKEND_COMMENT)
    @TimeLimiter(name = BACKEND_COMMENT)
    @Bulkhead(name = BACKEND_COMMENT)
    @Retry(name = BACKEND_COMMENT)
    public Long getCommentId() {
        return this.commentId;
    }

    @CircuitBreaker (name = BACKEND_COMMENT)
    @TimeLimiter(name = BACKEND_COMMENT)
    @Bulkhead(name = BACKEND_COMMENT)
    @Retry(name = BACKEND_COMMENT)
    public AppUser getUserId() {
        return this.userId;
    }

    @CircuitBreaker (name = BACKEND_COMMENT)
    @TimeLimiter(name = BACKEND_COMMENT)
    @Bulkhead(name = BACKEND_COMMENT)
    @Retry(name = BACKEND_COMMENT)
    public void setUserId(Long userId) {
        AppUser user = new AppUser();
        user.setId(userId);
        
        this.userId = user;
    }

    @CircuitBreaker (name = BACKEND_COMMENT)
    @TimeLimiter(name = BACKEND_COMMENT)
    @Bulkhead(name = BACKEND_COMMENT)
    @Retry(name = BACKEND_COMMENT)
    public Tweet getTweetId() {
        return this.tweetId;
    }

    @CircuitBreaker (name = BACKEND_COMMENT)
    @TimeLimiter(name = BACKEND_COMMENT)
    @Bulkhead(name = BACKEND_COMMENT)
    @Retry(name = BACKEND_COMMENT)
    public void setTweetId(Long tweetId) {
        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        
        this.tweetId = tweet;
    }
    
    @CircuitBreaker (name = BACKEND_COMMENT)
    @TimeLimiter(name = BACKEND_COMMENT)
    @Bulkhead(name = BACKEND_COMMENT)
    @Retry(name = BACKEND_COMMENT)
    public String getComment() {
        return this.comment;
    }

    @CircuitBreaker (name = BACKEND_COMMENT)
    @TimeLimiter(name = BACKEND_COMMENT)
    @Bulkhead(name = BACKEND_COMMENT)
    @Retry(name = BACKEND_COMMENT)
    public void setComment(String newComment) {
        this.comment = newComment;
    }

}