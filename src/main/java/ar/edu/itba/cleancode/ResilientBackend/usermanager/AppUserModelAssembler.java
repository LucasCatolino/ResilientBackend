package ar.edu.itba.cleancode.resilientbackend.usermanager;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.apache.catalina.startup.UserConfig;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ar.edu.itba.cleancode.resilientbackend.controllers.UserController;

@Component
public class AppUserModelAssembler implements RepresentationModelAssembler<AppUser, EntityModel<AppUser>> {
    
    @Override
    public EntityModel<AppUser> toModel (AppUser user) {
        return EntityModel.of(user,
            linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel(),
            linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
    }
    
}
