package ar.edu.itba.cleancode.resilientbackend.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ar.edu.itba.cleancode.resilientbackend.controllers.TweetController;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.Tweet;

@Component
public class TweetModelAssembler implements RepresentationModelAssembler<Tweet, EntityModel<Tweet>> {
    
    @Override
    public EntityModel<Tweet> toModel (Tweet tweet) {
        return EntityModel.of(tweet,
            linkTo(methodOn(TweetController.class).getTweetById(tweet.getId())).withSelfRel(),
            linkTo(methodOn(TweetController.class).getAllTweets()).withRel("tweets"));
    }
    
}
