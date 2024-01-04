package ar.edu.itba.cleancode.resilientbackend.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import ar.edu.itba.cleancode.resilientbackend.SingleLogger;
import ar.edu.itba.cleancode.resilientbackend.assemblers.TweetModelAssembler;
import ar.edu.itba.cleancode.resilientbackend.exceptions.CouldNotDeleteTweetException;
import ar.edu.itba.cleancode.resilientbackend.exceptions.TweetNotFoundException;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.Tweet;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.TweetRepository;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.TweetRequest;
import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUser;

@RestController
@RequestMapping("/api")
public class TweetController {

    private final TweetRepository tweetRepository;
    private final TweetModelAssembler tweetAssembler;
    private final Logger logger;

    @Autowired
    public TweetController(TweetRepository tweetRepository, TweetModelAssembler tweetAssembler) {
        this.tweetRepository = tweetRepository;
        this.tweetAssembler = tweetAssembler;
        this.logger = SingleLogger.getLogger();
    }

    @GetMapping("/tweets")
    public CollectionModel<EntityModel<Tweet>> getAllTweets() {
        List<EntityModel<Tweet>> tweets = tweetRepository.findAll().stream()
        .map(tweetAssembler::toModel).collect(Collectors.toList());

    return CollectionModel.of(tweets, linkTo(methodOn(TweetController.class).getAllTweets()).withSelfRel());
    }

    @GetMapping("/tweets/{id}")
    public EntityModel<Tweet> getTweetById(@PathVariable Long id) {
        Tweet tweet = new Tweet();
        tweet = tweetRepository.findById(id)
            .orElseThrow(() -> new TweetNotFoundException(id));

        return tweetAssembler.toModel(tweet);
    }

    @PostMapping("/tweets")
    public ResponseEntity<EntityModel<Tweet>> addTweet(@RequestBody TweetRequest request) {
        Tweet tweet = new Tweet();
        AppUser user = new AppUser();
        user.setId(request.getUserId());
        tweet.setUserId(user);
        tweet.setTitle(request.getTitle());
        tweet.setContent(request.getContent());
        Tweet newTweet = tweetRepository.save(tweet);

        return ResponseEntity
            .created(linkTo(methodOn(TweetController.class).getTweetById(newTweet.getId())).toUri())
            .body(tweetAssembler.toModel(newTweet));
    }

    @PutMapping("/tweets/{id}")
    public ResponseEntity<?> updateTweet(@PathVariable Long id, @RequestBody TweetRequest tweetToUpdate) {
        AppUser user = new AppUser();
        Tweet updatedTweet = tweetRepository.findById(id)
            .map(tweet -> {
                user.setId(tweetToUpdate.getUserId());
                tweet.setUserId(user);
                tweet.setTitle(tweetToUpdate.getTitle());
                tweet.setContent(tweetToUpdate.getContent());
                return tweetRepository.save(tweet);
            })
            .orElseThrow(() -> new TweetNotFoundException(id));
        
        EntityModel<Tweet> entityModel = tweetAssembler.toModel(updatedTweet);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<?> deleteTweet(@PathVariable Long id) {
        logger.info("Delete tweet with tweetId: " + id);
        tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException(id));
        try {
            tweetRepository.deleteById(id);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new CouldNotDeleteTweetException(id);
        }

        return ResponseEntity.noContent().build(); // return 204 No Content
    }

}
