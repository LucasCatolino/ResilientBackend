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

    private static final String BACKEND_USERS = "backendusers";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String mail;
    private String birthday;
    private String password;

    @CircuitBreaker (name = BACKEND_USERS)
    @TimeLimiter(name = BACKEND_USERS)
    @Bulkhead(name = BACKEND_USERS)
    @Retry(name = BACKEND_USERS)
    public Long getId() {
        return this.userId; // TODO: ver por qué da null
    }

    @CircuitBreaker (name = BACKEND_USERS)
    @TimeLimiter(name = BACKEND_USERS)
    @Bulkhead(name = BACKEND_USERS)
    @Retry(name = BACKEND_USERS)
    public void setId(Long id) {
        this.userId = id;
    }

    @CircuitBreaker (name = BACKEND_USERS)
    @TimeLimiter(name = BACKEND_USERS)
    @Bulkhead(name = BACKEND_USERS)
    @Retry(name = BACKEND_USERS)
    public String getName() {
        return this.name;
    }

    @CircuitBreaker (name = BACKEND_USERS)
    @TimeLimiter(name = BACKEND_USERS)
    @Bulkhead(name = BACKEND_USERS)
    @Retry(name = BACKEND_USERS)
    public void setName(String name) {
        this.name = name;
    }

    @CircuitBreaker (name = BACKEND_USERS)
    @TimeLimiter(name = BACKEND_USERS)
    @Bulkhead(name = BACKEND_USERS)
    @Retry(name = BACKEND_USERS)
    public String getMail() {
        return this.mail;
    }

    @CircuitBreaker (name = BACKEND_USERS)
    @TimeLimiter(name = BACKEND_USERS)
    @Bulkhead(name = BACKEND_USERS)
    @Retry(name = BACKEND_USERS)
    public void setMail(String mail) {
        this.mail = mail;
    }

    @CircuitBreaker (name = BACKEND_USERS)
    @TimeLimiter(name = BACKEND_USERS)
    @Bulkhead(name = BACKEND_USERS)
    @Retry(name = BACKEND_USERS)
    public String getBirthday() {
        return this.birthday;
    }

    @CircuitBreaker (name = BACKEND_USERS)
    @TimeLimiter(name = BACKEND_USERS)
    @Bulkhead(name = BACKEND_USERS)
    @Retry(name = BACKEND_USERS)
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
    @CircuitBreaker (name = BACKEND_USERS)
    @TimeLimiter(name = BACKEND_USERS)
    @Bulkhead(name = BACKEND_USERS)
    @Retry(name = BACKEND_USERS)
    public String getPassword() {
        return this.password;
    }

    @CircuitBreaker (name = BACKEND_USERS)
    @TimeLimiter(name = BACKEND_USERS)
    @Bulkhead(name = BACKEND_USERS)
    @Retry(name = BACKEND_USERS)
    public void setPassword(String password) {
        this.password = password;
    }
}