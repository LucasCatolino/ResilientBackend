package ar.edu.itba.cleancode.resilientbackend.tweetmanager;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tweet {

    private static final String BACKEND_TWEETS = "backendusers";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String title;
    private String content;

    @CircuitBreaker (name = BACKEND_TWEETS)
    @TimeLimiter(name = BACKEND_TWEETS)
    @Bulkhead(name = BACKEND_TWEETS)
    @Retry(name = BACKEND_TWEETS)
    public Long getId() {
        return this.id;
    }

    @CircuitBreaker (name = BACKEND_TWEETS)
    @TimeLimiter(name = BACKEND_TWEETS)
    @Bulkhead(name = BACKEND_TWEETS)
    @Retry(name = BACKEND_TWEETS)
    public void setId(Long id) {
        this.id = id;
    }

    @CircuitBreaker (name = BACKEND_TWEETS)
    @TimeLimiter(name = BACKEND_TWEETS)
    @Bulkhead(name = BACKEND_TWEETS)
    @Retry(name = BACKEND_TWEETS)
    public Long getUserId() {
        return this.userId;
    }

    @CircuitBreaker (name = BACKEND_TWEETS)
    @TimeLimiter(name = BACKEND_TWEETS)
    @Bulkhead(name = BACKEND_TWEETS)
    @Retry(name = BACKEND_TWEETS)
    public void setUserId(Long userId) {
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