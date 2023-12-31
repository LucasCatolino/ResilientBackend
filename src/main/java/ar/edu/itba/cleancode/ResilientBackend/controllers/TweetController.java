package ar.edu.itba.cleancode.resilientbackend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.itba.cleancode.resilientbackend.DatabaseConnector;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.Tweet;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.TweetRepository;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.TweetRequest;
import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUser;

@RestController
@RequestMapping("/api")
public class TweetController {

    private final DatabaseConnector databaseConnector;
    private final TweetRepository tweetRepository;

    @Autowired
    public TweetController(DatabaseConnector databaseConnector, TweetRepository tweetRepository) {
        this.databaseConnector = databaseConnector;
        this.tweetRepository = tweetRepository;
    }

    @GetMapping("/tweets/health")
    public String hello() {
        return "Tweets up!";
    }

    @GetMapping("/tweets")
    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }

    @GetMapping("/tweets/{id}")
    public Optional<Tweet> getTweetById(@PathVariable Long id) {
        return tweetRepository.findById(id);
    }

    @PostMapping("/tweets")
    public Tweet addTweet(@RequestBody TweetRequest request) {
        Tweet tweet = new Tweet();
        AppUser user = new AppUser();
        user.setId(request.getUserId());
        tweet.setUserId(user);
        tweet.setTitle(request.getTitle());
        tweet.setContent(request.getContent());
        
        return tweetRepository.save(tweet);
    }

    @PutMapping("/tweets/{id}")
    public Tweet updateTweet(@PathVariable Long id, @RequestBody TweetRequest request) {
        Tweet updatedTweet = new Tweet();
        updatedTweet.setId(id);
        AppUser user = new AppUser();
        user.setId(request.getUserId());
        updatedTweet.setUserId(user);
        updatedTweet.setTitle(request.getTitle());
        updatedTweet.setContent(request.getContent());
        
        return tweetRepository.save(updatedTweet);
    }

    @DeleteMapping("/tweets/{id}")
    public void deleteTweet(@PathVariable Long id) {
        tweetRepository.deleteById(id);
    }

}
