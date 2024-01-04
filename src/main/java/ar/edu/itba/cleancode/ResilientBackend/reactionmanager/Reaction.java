package ar.edu.itba.cleancode.resilientbackend.reactionmanager;

import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ar.edu.itba.cleancode.resilientbackend.tweetmanager.Tweet;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reaction {

    private static final String BACKEND_REACT = "backendusers";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reactionId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonIgnoreProperties("reactions") // Ignore serialization of the AppUser's reactions property
    private AppUser userId;

    @ManyToOne
    @JoinColumn(name = "tweetId", nullable = false)
    private Tweet tweetId;

    // True = Like & False = dislike
    private Boolean is_like;

    /*public Reaction(Boolean isLike, Long tweetId, Long userId) {
        this.setLike(isLike);
        this.setTweetId(tweetId);
        this.setUserId(userId); // TODO: da error porque dice que AppUser.getId() da null por this.userId null
                                /* ¿por qué con tweet funciona y con user no? *
    }*/

    @CircuitBreaker (name = BACKEND_REACT)
    @TimeLimiter(name = BACKEND_REACT)
    @Bulkhead(name = BACKEND_REACT)
    @Retry(name = BACKEND_REACT)
    public Long getId() {
        return this.reactionId;
    }

    @CircuitBreaker (name = BACKEND_REACT)
    @TimeLimiter(name = BACKEND_REACT)
    @Bulkhead(name = BACKEND_REACT)
    @Retry(name = BACKEND_REACT)
    public AppUser getUserId() {
        return this.userId;
    }

    @CircuitBreaker (name = BACKEND_REACT)
    @TimeLimiter(name = BACKEND_REACT)
    @Bulkhead(name = BACKEND_REACT)
    @Retry(name = BACKEND_REACT)
    public void setUserId(Long userId) {
        AppUser user = new AppUser();
        user.setId(userId);
        
        this.userId = user;
    }

    @CircuitBreaker (name = BACKEND_REACT)
    @TimeLimiter(name = BACKEND_REACT)
    @Bulkhead(name = BACKEND_REACT)
    @Retry(name = BACKEND_REACT)
    public Tweet getTweetId() {
        return this.tweetId;
    }

    @CircuitBreaker (name = BACKEND_REACT)
    @TimeLimiter(name = BACKEND_REACT)
    @Bulkhead(name = BACKEND_REACT)
    @Retry(name = BACKEND_REACT)
    public void setTweetId(Long tweetId) {
        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        
        this.tweetId = tweet;
    }
    
    @CircuitBreaker (name = BACKEND_REACT)
    @TimeLimiter(name = BACKEND_REACT)
    @Bulkhead(name = BACKEND_REACT)
    @Retry(name = BACKEND_REACT)
    public Boolean getLike() {
        return this.is_like;
    }

    @CircuitBreaker (name = BACKEND_REACT)
    @TimeLimiter(name = BACKEND_REACT)
    @Bulkhead(name = BACKEND_REACT)
    @Retry(name = BACKEND_REACT)
    public void setLike(Boolean newReaction) {
        this.is_like = newReaction;
    }

}