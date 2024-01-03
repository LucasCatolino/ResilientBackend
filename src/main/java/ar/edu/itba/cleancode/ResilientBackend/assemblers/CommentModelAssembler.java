package ar.edu.itba.cleancode.resilientbackend.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ar.edu.itba.cleancode.resilientbackend.controllers.CommentController;
import ar.edu.itba.cleancode.resilientbackend.commentmanager.Comment;

@Component
public class CommentModelAssembler implements RepresentationModelAssembler<Comment, EntityModel<Comment>> {
    
    @Override
    public EntityModel<Comment> toModel (Comment comment) {
        return EntityModel.of(comment,
            linkTo(methodOn(CommentController.class).getAllCommentsForTweet(comment.getTweetId().getId())).withSelfRel());
    }
    
}
