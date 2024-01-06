package ar.edu.itba.cleancode.resilientbackend;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUser;
import ar.edu.itba.cleancode.resilientbackend.DatabaseConnector;
import ar.edu.itba.cleancode.resilientbackend.controllers.UserController;

import ar.edu.itba.cleancode.resilientbackend.controllers.TweetController;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.Tweet;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.TweetRequest;

import ar.edu.itba.cleancode.resilientbackend.controllers.CommentController;
import ar.edu.itba.cleancode.resilientbackend.commentmanager.Comment;
import ar.edu.itba.cleancode.resilientbackend.commentmanager.CommentRequest;
import ar.edu.itba.cleancode.resilientbackend.commentmanager.CommentResponse;

import ar.edu.itba.cleancode.resilientbackend.controllers.ReactionController;
import ar.edu.itba.cleancode.resilientbackend.reactionmanager.Reaction;
import ar.edu.itba.cleancode.resilientbackend.reactionmanager.ReactionRequest;
import ar.edu.itba.cleancode.resilientbackend.reactionmanager.ReactionResponse;


@SpringBootTest(properties = "spring.config.name=application-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResilientBackendApplicationTests{

    private final String NAME = "test";
    private final String MAIL = "test@test.test";
    private final String PASS = "password";
    private final String BIRTHDAY = "1/2/2022";

    private final String TITLE = "test";
    private final String CONTENT = "test content";

    private AppUser AppUser;
    
    @Autowired
    private UserController userController;

    @Autowired
    private TweetController tweetController;

    @Autowired
    private CommentController commentController;

    @Autowired
    private ReactionController reactionController;


    @Test
    @Order(1)
    public void testCreateAndRetrieveUser(){
        AppUser user = new AppUser();
        user.setName(NAME);
        user.setMail(MAIL);
        user.setPassword(PASS);
        user.setBirthday(BIRTHDAY);

        userController.addUser(user);

        CollectionModel<EntityModel<AppUser>> retrievedUsers = userController.getAllUsers();
        EntityModel<AppUser> retUser =  retrievedUsers.getContent().stream().collect(Collectors.toList()).get(0);
        assertEquals(NAME, retUser.getContent().getName());
    }

    @Test
    @Order(2)
    public void testDeleteUser(){
        AppUser user = new AppUser();
        user.setName(NAME);
        user.setMail(MAIL);
        user.setPassword(PASS);
        user.setBirthday(BIRTHDAY);

        userController.addUser(user);
        userController.deleteUser(2L);

        CollectionModel<EntityModel<AppUser>> retrievedUsers = userController.getAllUsers();
        assertEquals(1, retrievedUsers.getContent().stream().collect(Collectors.toList()).size());
    }

    @Test
    @Order(3)
    public void testGetUserById(){
        AppUser user = new AppUser();
        user.setName(NAME);
        user.setMail(MAIL);
        user.setPassword(PASS);
        user.setBirthday(BIRTHDAY);

        userController.addUser(user);
        EntityModel<AppUser> retrievedUser = userController.getUserById(3L);

        assertEquals(NAME, retrievedUser.getContent().getName());
        assertEquals(3L, retrievedUser.getContent().getId());

    }

    @Test
    @Order(4)
    public void testUpdateUser(){
        AppUser user = new AppUser();
        user.setName(NAME);
        user.setMail(MAIL);
        user.setPassword(PASS);
        user.setBirthday(BIRTHDAY);

        userController.addUser(user);

        user.setName("new Name");
        userController.updateUser(4L, user);

        EntityModel<AppUser> retrievedUser = userController.getUserById(4L);
        assertEquals("new Name", retrievedUser.getContent().getName());
        assertEquals(4L, retrievedUser.getContent().getId());
    }

    
    @Test
    @Order(5)
    public void testAddAndRetrieveTweet(){
        TweetRequest tweet = new TweetRequest();
        tweet.setContent(CONTENT);
        tweet.setTitle(TITLE);
        tweet.setUserId(4L);

        tweetController.addTweet(tweet);
        
        EntityModel<Tweet> retrievedTweet = tweetController.getTweetById(1L);
        assertEquals(TITLE, retrievedTweet.getContent().getTitle());
        
    }

    @Test
    @Order(6)
    public void testUpdateTweet(){
        EntityModel<Tweet> retrievedTweet = tweetController.getTweetById(1L);
        assertEquals(TITLE, retrievedTweet.getContent().getTitle());

        TweetRequest updatedTweet = new TweetRequest();
        Long userId = retrievedTweet.getContent().getUserId();
        updatedTweet.setUserId(userId);
        updatedTweet.setContent("new content");
        updatedTweet.setTitle("new title");

        tweetController.updateTweet(1L, updatedTweet);

        EntityModel<Tweet> retTweet = tweetController.getTweetById(1L);
        assertEquals("new title", retTweet.getContent().getTitle());
        assertEquals("new content", retTweet.getContent().getContent()); 
    }

    @Test
    @Order(7)
    public void testGetAllTweets(){
        TweetRequest tweet = new TweetRequest();
        tweet.setContent(CONTENT);
        tweet.setTitle(TITLE);
        tweet.setUserId(4L);

        tweetController.addTweet(tweet);
        
        CollectionModel<EntityModel<Tweet>> retrievedTweets = tweetController.getAllTweets();
        assertEquals(2, retrievedTweets.getContent().stream().collect(Collectors.toList()).size());
        
    }

    @Test
    @Order(8)
    public void testDeleteTweet(){
        tweetController.deleteTweet(2L);
        CollectionModel<EntityModel<Tweet>> retrievedTweets = tweetController.getAllTweets();
        assertEquals(1, retrievedTweets.getContent().stream().collect(Collectors.toList()).size());
    }

    @Test
    @Order(9)
    public void testAddandRetrieveReaction(){
        ReactionRequest reaction = new ReactionRequest();
        reaction.setLike("true");
        reaction.setTweetId(1L);
        reaction.setUserId(1L);

        reactionController.addReaction(reaction);
        
        CollectionModel<EntityModel<ReactionResponse>> reactions = reactionController.getAllReactionsForTweet(1L);
        assertEquals(1, reactions.getContent().stream().collect(Collectors.toList()).size());
        
    }

    @Test
    @Order(10)
    public void testAddandRetrieveComment(){
        CommentRequest comment = new CommentRequest();
        comment.setComment("comment");
        comment.setTweetId(1L);
        comment.setUserId(1L);

        commentController.addComment(comment);
        
        CollectionModel<EntityModel<CommentResponse>> comments = commentController.getAllCommentsForTweet(1L);
        assertEquals(1, comments.getContent().stream().collect(Collectors.toList()).size());
        
    }
}