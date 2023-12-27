package ar.edu.itba.cleancode.resilientbackend.usermanager;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AppUser {

    private static final String BACKENDUSERS = "backendusers";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String mail;
    private String birthday;
    private String password;

    @CircuitBreaker (name = BACKENDUSERS)
    @TimeLimiter(name = BACKENDUSERS)
    @Bulkhead(name = BACKENDUSERS)
    @Retry(name = BACKENDUSERS)
    public Long getId() {
        return this.id;
    }

    @CircuitBreaker (name = BACKENDUSERS)
    @TimeLimiter(name = BACKENDUSERS)
    @Bulkhead(name = BACKENDUSERS)
    @Retry(name = BACKENDUSERS)
    public void setId(Long id) {
        this.id = id;
    }

    @CircuitBreaker (name = BACKENDUSERS)
    @TimeLimiter(name = BACKENDUSERS)
    @Bulkhead(name = BACKENDUSERS)
    @Retry(name = BACKENDUSERS)
    public String getName() {
        return this.name;
    }

    @CircuitBreaker (name = BACKENDUSERS)
    @TimeLimiter(name = BACKENDUSERS)
    @Bulkhead(name = BACKENDUSERS)
    @Retry(name = BACKENDUSERS)
    public void setName(String name) {
        this.name = name;
    }

    @CircuitBreaker (name = BACKENDUSERS)
    @TimeLimiter(name = BACKENDUSERS)
    @Bulkhead(name = BACKENDUSERS)
    @Retry(name = BACKENDUSERS)
    public String getMail() {
        return this.mail;
    }

    @CircuitBreaker (name = BACKENDUSERS)
    @TimeLimiter(name = BACKENDUSERS)
    @Bulkhead(name = BACKENDUSERS)
    @Retry(name = BACKENDUSERS)
    public void setMail(String mail) {
        this.mail = mail;
    }

    @CircuitBreaker (name = BACKENDUSERS)
    @TimeLimiter(name = BACKENDUSERS)
    @Bulkhead(name = BACKENDUSERS)
    @Retry(name = BACKENDUSERS)
    public String getBirthday() {
        return this.birthday;
    }

    @CircuitBreaker (name = BACKENDUSERS)
    @TimeLimiter(name = BACKENDUSERS)
    @Bulkhead(name = BACKENDUSERS)
    @Retry(name = BACKENDUSERS)
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
    @CircuitBreaker (name = BACKENDUSERS)
    @TimeLimiter(name = BACKENDUSERS)
    @Bulkhead(name = BACKENDUSERS)
    @Retry(name = BACKENDUSERS)
    public String getPassword() {
        return this.password;
    }

    @CircuitBreaker (name = BACKENDUSERS)
    @TimeLimiter(name = BACKENDUSERS)
    @Bulkhead(name = BACKENDUSERS)
    @Retry(name = BACKENDUSERS)
    public void setPassword(String password) {
        this.password = password;
    }
}