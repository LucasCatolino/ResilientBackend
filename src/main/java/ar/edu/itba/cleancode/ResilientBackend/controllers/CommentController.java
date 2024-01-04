package ar.edu.itba.cleancode.resilientbackend.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;

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

import ar.edu.itba.cleancode.resilientbackend.commentmanager.Comment;
import ar.edu.itba.cleancode.resilientbackend.commentmanager.CommentRepository;
import ar.edu.itba.cleancode.resilientbackend.commentmanager.CommentRequest;
import ar.edu.itba.cleancode.resilientbackend.commentmanager.CommentResponse;
import ar.edu.itba.cleancode.resilientbackend.exceptions.AppUserNotFoundException;
import ar.edu.itba.cleancode.resilientbackend.exceptions.CommentException;
import ar.edu.itba.cleancode.resilientbackend.exceptions.CommentNotFoundException;
import ar.edu.itba.cleancode.resilientbackend.exceptions.TweetNotFoundException;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.Tweet;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.TweetRepository;
import ar.edu.itba.cleancode.resilientbackend.usermanager.AppUserRepository;
import ar.edu.itba.cleancode.resilientbackend.SingleLogger;



@RestController
@RequestMapping("/api")
public class CommentController {
        
    private final CommentRepository commentRepository;
    private final TweetRepository tweetRepository;
    private final AppUserRepository appUserRepository;
    private final Logger logger;


    @Autowired
    public CommentController(CommentRepository commentRepository, TweetRepository tweetRepository, AppUserRepository appUserRepository) {
        this.commentRepository = commentRepository;
        this.tweetRepository = tweetRepository;
        this.appUserRepository = appUserRepository;
        this.logger = SingleLogger.getLogger();  
    }

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EntityModel<CommentResponse>> addComment(@RequestBody CommentRequest request) {
        Long tweetId = request.getTweetId();
        Long userId = request.getUserId();
        String commentContent = request.getComment();

        tweetRepository.findById(tweetId).orElseThrow(() -> new TweetNotFoundException(tweetId));
        appUserRepository.findById(userId).orElseThrow(() -> new AppUserNotFoundException(userId));

        Comment comment = new Comment();
        comment.setTweetId(tweetId);
        comment.setUserId(userId);
        comment.setComment(commentContent);
        logger.info("Add comment for tweet with id: " + Long.toString(tweetId));

        try {
            commentRepository.save(comment);

            CommentResponse response = new CommentResponse(
                    userId, tweetId, commentContent);

            EntityModel<CommentResponse> entityModel = EntityModel.of(response);

            Link selfLink = linkTo(methodOn(CommentController.class).getAllCommentsForTweet(tweetId)).withSelfRel();
            entityModel.add(selfLink);

            return ResponseEntity.created(selfLink.toUri())
                .body(entityModel);
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new CommentException(tweetId, userId);
        }
    }


    @GetMapping("/comments/{tweetId}")
    public CollectionModel<EntityModel<CommentResponse>> getAllCommentsForTweet(@PathVariable Long tweetId) {
        logger.info("Get comments for tweet with id: " + Long.toString(tweetId));

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        
        List<EntityModel<CommentResponse>> comments =
            commentRepository.findCommentByTweetId(tweet).stream()
                    .map(comment -> {
                        CommentResponse response = new CommentResponse(
                                comment.getTweetId().getId(),
                                comment.getUserId().getId(),
                                comment.getComment()
                        );
                        return EntityModel.of(response,
                                linkTo(methodOn(CommentController.class).getAllCommentsForTweet(tweetId)).withSelfRel());
                    })
                    .collect(Collectors.toList());
        if (comments.isEmpty()){
            logger.severe("No comments were found for tweet with id: " + Long.toString(tweetId));

            throw new CommentNotFoundException(tweetId);
        }
        return CollectionModel.of(comments, linkTo(methodOn(CommentController.class).getAllCommentsForTweet(tweetId)).withSelfRel());
    }

}
