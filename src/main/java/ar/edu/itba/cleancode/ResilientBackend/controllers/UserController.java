package ar.edu.itba.cleancode.resilientbackend.controllers;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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

import ar.edu.itba.cleancode.resilientbackend.SingleLogger;
import ar.edu.itba.cleancode.resilientbackend.assemblers.AppUserModelAssembler;
import ar.edu.itba.cleancode.resilientbackend.exceptions.AppUserNotFoundException;
import ar.edu.itba.cleancode.resilientbackend.exceptions.CouldNotDeleteUserException;
import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUser;
import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUserRepository;


@RestController
@RequestMapping("/api")
public class UserController {

    private final AppUserRepository appUserRepository;
    private final AppUserModelAssembler appUserAssembler;
    private final Logger logger;

    @Autowired
    public UserController(AppUserRepository appUserRepository, AppUserModelAssembler appUserAssembler) {
        this.appUserRepository = appUserRepository;
        this.appUserAssembler = appUserAssembler;
        this.logger = SingleLogger.getLogger();
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<AppUser>> getAllUsers() {
        logger.info("Showing all users");

        List<EntityModel<AppUser>> users = appUserRepository.findAll().stream()
        .map(appUserAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @GetMapping("/users/{id}")
    public EntityModel<AppUser> getUserById(@PathVariable Long id) {
        logger.info("Showing user with id: " + Long.toString(id));
        
        AppUser user = new AppUser();
            user = appUserRepository.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException(id));
        
        return appUserAssembler.toModel(user);
    }

    @PostMapping("/users")
    public ResponseEntity<EntityModel<AppUser>> addUser(@RequestBody AppUser user) {
        AppUser newUser = null;

        try {
            newUser = appUserRepository.save(user);
            logger.info("Creating user with name: " + user.getName());
        } catch (Exception e) {
            logger.severe("Error creating user with name: " + user.getName());
        }

        return ResponseEntity
            .created(linkTo(methodOn(UserController.class).getUserById(newUser.getId())).toUri())
            .body(appUserAssembler.toModel(newUser));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody AppUser userToUpdate) {
        AppUser updatedUser = null;
        logger.info("Update user with userId: " + Long.toString(id));
        try {
        updatedUser = appUserRepository.findById(id)
            .map(user -> {
                user.setName(userToUpdate.getName());
                user.setMail(userToUpdate.getMail());
                user.setBirthday(userToUpdate.getBirthday());
                user.setPassword(userToUpdate.getPassword());
                return appUserRepository.save(user);
            })
            .orElseThrow(() -> new AppUserNotFoundException(id));
        } catch(Exception e) {
            logger.severe("Error updating user with id: " + id);
        }
        
        EntityModel<AppUser> entityModel = appUserAssembler.toModel(updatedUser);
        
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        logger.info("Delete user with userId: " + Long.toString(id));
        appUserRepository.findById(id).orElseThrow(() -> new AppUserNotFoundException(id));
        try {
            appUserRepository.deleteById(id);
        } catch (Exception e) {
            logger.severe("Could not delete user with id: " + Long.toString(id));
            throw new CouldNotDeleteUserException(id);
        }

        return ResponseEntity.noContent().build(); // return 204 No Content
    }

}
