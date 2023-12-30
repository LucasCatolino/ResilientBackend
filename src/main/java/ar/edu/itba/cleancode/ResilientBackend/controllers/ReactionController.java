package ar.edu.itba.cleancode.resilientbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.itba.cleancode.resilientbackend.DatabaseConnector;
import ar.edu.itba.cleancode.resilientbackend.reactionmanager.Reaction;
import ar.edu.itba.cleancode.resilientbackend.reactionmanager.ReactionRepository;
import ar.edu.itba.cleancode.resilientbackend.reactionmanager.ReactionRequest;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.Tweet;
import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUser;


@RestController
@RequestMapping("/api")
public class ReactionController {
        
    private final DatabaseConnector databaseConnector;
    private final ReactionRepository reactionRepository;

    @Autowired
    public ReactionController(DatabaseConnector databaseConnector, ReactionRepository reactionRepository) {
        this.databaseConnector = databaseConnector;
        this.reactionRepository = reactionRepository;
    }

    @GetMapping("/reactions/health")
    public String hello() {
        return "Reactions up!";
    }

    @PostMapping("/reactions")
    public Reaction addReaction(@RequestBody ReactionRequest request) {
        Reaction reaction = new Reaction(request.getLike(), request.getTweetId(), request.getUserId());
        /*reaction.setLike(request.getLike());
        reaction.setTweetId(request.getTweetId());
        reaction.setUserId(request.getUserId());*/
        System.out.println(reaction);
        return reactionRepository.save(reaction);
    }


    @GetMapping("/reactions/{tweetId}")
    public List<Reaction> getAllReactionsForTweet(@PathVariable Long tweetId) {
        Tweet tweet = new Tweet();
        tweet.setId(tweetId);

        return reactionRepository.findReactionByTweetId(tweet);
    }

}
