package ar.edu.itba.cleancode.resilientbackend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.itba.cleancode.resilientbackend.DatabaseConnector;
import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUser;
import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUserRepository;


@RestController
@RequestMapping("/api")
public class UserController {

    private final DatabaseConnector databaseConnector;
    private final AppUserRepository appUserRepository;

    @Autowired
    public UserController(DatabaseConnector databaseConnector, AppUserRepository appUserRepository) {
        this.databaseConnector = databaseConnector;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/users/health")
    public String hello() {
        return "Users up!";
    }

    @GetMapping("/users")
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public Optional<AppUser> getUserById(@PathVariable Long id) {
        return appUserRepository.findById(id);
    }

    @PostMapping("/users")
    public AppUser addUser(@RequestBody AppUser user) {
        return appUserRepository.save(user);
    }

    @PutMapping("/users/{id}")
    public AppUser updateUser(@PathVariable Long id, @RequestBody AppUser updatedUser) {
        updatedUser.setId(id);
        return appUserRepository.save(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        appUserRepository.deleteById(id);
    }

}
