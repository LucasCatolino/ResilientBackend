package ar.edu.itba.cleancode.resilientbackend.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ar.edu.itba.cleancode.resilientbackend.controllers.ReactionController;
import ar.edu.itba.cleancode.resilientbackend.reactionmanager.Reaction;

@Component
public class ReactionModelAssembler implements RepresentationModelAssembler<Reaction, EntityModel<Reaction>> {
    
    @Override
    public EntityModel<Reaction> toModel (Reaction reaction) {
        return EntityModel.of(reaction,
            linkTo(methodOn(ReactionController.class).getAllReactionsForTweet(reaction.getTweetId().getId())).withSelfRel());
    }
    
}
