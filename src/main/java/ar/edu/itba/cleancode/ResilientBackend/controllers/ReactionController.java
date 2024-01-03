package ar.edu.itba.cleancode.resilientbackend.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.itba.cleancode.resilientbackend.DatabaseConnector;
import ar.edu.itba.cleancode.resilientbackend.exceptions.AppUserNotFoundException;
import ar.edu.itba.cleancode.resilientbackend.exceptions.ReactionException;
import ar.edu.itba.cleancode.resilientbackend.exceptions.ReactionNotFoundException;
import ar.edu.itba.cleancode.resilientbackend.exceptions.TweetNotFoundException;
import ar.edu.itba.cleancode.resilientbackend.reactionmanager.Reaction;
import ar.edu.itba.cleancode.resilientbackend.reactionmanager.ReactionRepository;
import ar.edu.itba.cleancode.resilientbackend.reactionmanager.ReactionRequest;
import ar.edu.itba.cleancode.resilientbackend.reactionmanager.ReactionResponse;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.Tweet;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.TweetRepository;
import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUserRepository;


@RestController
@RequestMapping("/api")
public class ReactionController {
        
    private final DatabaseConnector databaseConnector;
    private final ReactionRepository reactionRepository;
    private final TweetRepository tweetRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public ReactionController(DatabaseConnector databaseConnector, ReactionRepository reactionRepository, TweetRepository tweetRepository, AppUserRepository appUserRepository) {
        this.databaseConnector = databaseConnector;
        this.reactionRepository = reactionRepository;
        this.tweetRepository = tweetRepository;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/reactions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EntityModel<ReactionResponse>> addReaction(@RequestBody ReactionRequest request) {
        Long tweetId = request.getTweetId();
        Long userId = request.getUserId();
        Boolean isLike = request.getLike();

        
        tweetRepository.findById(tweetId).orElseThrow(() -> new TweetNotFoundException(tweetId));
        appUserRepository.findById(userId).orElseThrow(() -> new AppUserNotFoundException(userId));

        Reaction reaction = new Reaction();
        reaction.setLike(isLike);
        reaction.setTweetId(tweetId);
        reaction.setUserId(userId);
        
        try {
            reactionRepository.save(reaction);

            ReactionResponse response = new ReactionResponse(
                    userId, tweetId, isLike);

            EntityModel<ReactionResponse> entityModel = EntityModel.of(response);

            Link selfLink = linkTo(methodOn(ReactionController.class).getAllReactionsForTweet(tweetId)).withSelfRel();
            entityModel.add(selfLink);

            return ResponseEntity.created(selfLink.toUri())
                .body(entityModel);
        } catch (Exception e) {
            throw new ReactionException(tweetId, userId);
        }
    }

    @GetMapping("/reactions/{tweetId}")
    public CollectionModel<EntityModel<ReactionResponse>> getAllReactionsForTweet(@PathVariable Long tweetId) {
        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        
        List<EntityModel<ReactionResponse>> reactions =
            reactionRepository.findReactionByTweetId(tweet).stream()
                    .map(reaction -> {
                        ReactionResponse response = new ReactionResponse(
                                reaction.getUserId().getId(),
                                reaction.getTweetId().getId(),
                                reaction.getLike()
                        );
                        return EntityModel.of(response,
                                linkTo(methodOn(ReactionController.class).getAllReactionsForTweet(tweetId)).withSelfRel());
                    })
                    .collect(Collectors.toList());
        if (reactions.isEmpty()){
            throw new ReactionNotFoundException(tweetId);
        }
        return CollectionModel.of(reactions, linkTo(methodOn(ReactionController.class).getAllReactionsForTweet(tweetId)).withSelfRel());
    }

}
