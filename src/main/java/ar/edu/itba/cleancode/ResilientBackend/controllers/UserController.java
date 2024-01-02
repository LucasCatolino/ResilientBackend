package ar.edu.itba.cleancode.resilientbackend.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import ar.edu.itba.cleancode.resilientbackend.DatabaseConnector;
import ar.edu.itba.cleancode.resilientbackend.advicers.AppUserNotFoundAdvice;
import ar.edu.itba.cleancode.resilientbackend.exceptions.AppUserNotFoundException;
import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUser;
import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUserModelAssembler;
import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUserRepository;


@RestController
@RequestMapping("/api")
public class UserController {

    private final DatabaseConnector databaseConnector;
    private final AppUserRepository appUserRepository;
    private final AppUserModelAssembler appUserAssembler;

    @Autowired
    public UserController(DatabaseConnector databaseConnector, AppUserRepository appUserRepository, AppUserModelAssembler appUserAssembler) {
        this.databaseConnector = databaseConnector;
        this.appUserRepository = appUserRepository;
        this.appUserAssembler = appUserAssembler;
    }

    @GetMapping("/users/health")
    public String hello() {
        return "Users up!";
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<AppUser>> getAllUsers() {
        List<EntityModel<AppUser>> users = appUserRepository.findAll().stream()
        .map(appUserAssembler::toModel).collect(Collectors.toList());

    return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }
/*
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }
*/
/*
    private static EntityModel<AppUser> emptyEntityModel() {
        Link selfLink = linkTo(UserController.class).withSelfRel();
        
        return EntityModel.of(new AppUser(), selfLink);
    }
*/
    @GetMapping("/users/{id}")
    public EntityModel<AppUser> getUserById(@PathVariable Long id) {
        AppUser user = new AppUser();
        try {
            user = appUserRepository.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException(id));
        } catch (Exception e) {
            System.err.println("User not found with id: " + id);
        }
        return appUserAssembler.toModel(user);
        /*AppUser user;
        try {
            user = appUserRepository.findById(id).orElseThrow(() -> new Exception("" + id));
            return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
        } catch (Exception e) {
            System.err.println(e); // TODO: handlear este error
            return emptyEntityModel();
        }*/
      }
/*
    public Optional<AppUser> getUserById(@PathVariable Long id) {
        return appUserRepository.findById(id);
    }
*/

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
