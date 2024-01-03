package ar.edu.itba.cleancode.resilientbackend.controllers;

import java.util.List;
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

import ar.edu.itba.cleancode.resilientbackend.DatabaseConnector;
import ar.edu.itba.cleancode.resilientbackend.assemblers.AppUserModelAssembler;
import ar.edu.itba.cleancode.resilientbackend.exceptions.AppUserNotFoundException;
import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUser;
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

    @GetMapping("/users")
    public CollectionModel<EntityModel<AppUser>> getAllUsers() {
        List<EntityModel<AppUser>> users = appUserRepository.findAll().stream()
        .map(appUserAssembler::toModel).collect(Collectors.toList());

    return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @GetMapping("/users/{id}")
    public EntityModel<AppUser> getUserById(@PathVariable Long id) {
        AppUser user = new AppUser();
            user = appUserRepository.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException(id));

        return appUserAssembler.toModel(user);
    }

    @PostMapping("/users")
    public ResponseEntity<EntityModel<AppUser>> addUser(@RequestBody AppUser user) {
        AppUser newUser = appUserRepository.save(user);

        return ResponseEntity
            .created(linkTo(methodOn(UserController.class).getUserById(newUser.getId())).toUri())
            .body(appUserAssembler.toModel(newUser));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody AppUser userToUpdate) {
        AppUser updatedUser = appUserRepository.findById(id)
            .map(user -> {
                user.setName(userToUpdate.getName());
                user.setMail(userToUpdate.getMail());
                user.setBirthday(userToUpdate.getBirthday());
                user.setPassword(userToUpdate.getPassword());
                return appUserRepository.save(user);
            })
            .orElseThrow(() -> new AppUserNotFoundException(id));
        
        EntityModel<AppUser> entityModel = appUserAssembler.toModel(updatedUser);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        appUserRepository.findById(id).orElseThrow(() -> new AppUserNotFoundException(id));
        appUserRepository.deleteById(id);

        return ResponseEntity.noContent().build(); // return 204 No Content
    }

}
