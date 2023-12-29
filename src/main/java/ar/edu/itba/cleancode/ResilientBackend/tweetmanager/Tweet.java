package ar.edu.itba.cleancode.resilientbackend.tweetmanager;

import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUser;
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
public class Tweet {

    private static final String BACKEND_TWEETS = "backendusers";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tweetId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private AppUser userId;
    private String title;
    private String content;

    @CircuitBreaker (name = BACKEND_TWEETS)
    @TimeLimiter(name = BACKEND_TWEETS)
    @Bulkhead(name = BACKEND_TWEETS)
    @Retry(name = BACKEND_TWEETS)
    public Long getId() {
        return this.tweetId;
    }

    @CircuitBreaker (name = BACKEND_TWEETS)
    @TimeLimiter(name = BACKEND_TWEETS)
    @Bulkhead(name = BACKEND_TWEETS)
    @Retry(name = BACKEND_TWEETS)
    public void setId(Long id) {
        this.tweetId = id;
    }

    @CircuitBreaker (name = BACKEND_TWEETS)
    @TimeLimiter(name = BACKEND_TWEETS)
    @Bulkhead(name = BACKEND_TWEETS)
    @Retry(name = BACKEND_TWEETS)
    public Long getUserId() {
        return userId.getId();
    }

    @CircuitBreaker (name = BACKEND_TWEETS)
    @TimeLimiter(name = BACKEND_TWEETS)
    @Bulkhead(name = BACKEND_TWEETS)
    @Retry(name = BACKEND_TWEETS)
    public void setUserId(AppUser userId) {
        this.userId = userId;
    }

    @CircuitBreaker (name = BACKEND_TWEETS)
    @TimeLimiter(name = BACKEND_TWEETS)
    @Bulkhead(name = BACKEND_TWEETS)
    @Retry(name = BACKEND_TWEETS)
    public String getTitle() {
        return this.title;
    }

    @CircuitBreaker (name = BACKEND_TWEETS)
    @TimeLimiter(name = BACKEND_TWEETS)
    @Bulkhead(name = BACKEND_TWEETS)
    @Retry(name = BACKEND_TWEETS)
    public void setTitle(String title) {
        this.title = title;
    }
    
    @CircuitBreaker (name = BACKEND_TWEETS)
    @TimeLimiter(name = BACKEND_TWEETS)
    @Bulkhead(name = BACKEND_TWEETS)
    @Retry(name = BACKEND_TWEETS)
    public String getContent() {
        return this.content;
    }

    @CircuitBreaker (name = BACKEND_TWEETS)
    @TimeLimiter(name = BACKEND_TWEETS)
    @Bulkhead(name = BACKEND_TWEETS)
    @Retry(name = BACKEND_TWEETS)
    public void setContent(String content) {
        this.content = content;
    }
}