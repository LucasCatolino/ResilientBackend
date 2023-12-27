package ar.edu.itba.cleancode.resilientbackend.usermanager;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AppUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String mail;
    private String birthday;
    private String password;

    public String getPassword() {
        return this.password;
    }

    @CircuitBreaker (name = "backendUsers")
    public Long getId() {
        return this.id;
    }

    @CircuitBreaker (name = "backendUsers")
    public void setId(Long id) {
        this.id = id;
    }

    @CircuitBreaker (name = "backendUsers")
    public String getName() {
        return this.name;
    }

    @CircuitBreaker (name = "backendUsers")
    public void setName(String name) {
        this.name = name;
    }

    @CircuitBreaker (name = "backendUsers")
    public String getMail() {
        return this.mail;
    }

    @CircuitBreaker (name = "backendUsers")
    public void setMail(String mail) {
        this.mail = mail;
    }

    @CircuitBreaker (name = "backendUsers")
    public String getBirthday() {
        return this.birthday;
    }

    @CircuitBreaker (name = "backendUsers")
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @CircuitBreaker (name = "backendUsers")
    public void setPassword(String password) {
        this.password = password;
    }
}
