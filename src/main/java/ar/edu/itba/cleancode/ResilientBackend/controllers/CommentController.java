package ar.edu.itba.cleancode.resilientbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.itba.cleancode.resilientbackend.DatabaseConnector;
import ar.edu.itba.cleancode.resilientbackend.commentmanager.Comment;
import ar.edu.itba.cleancode.resilientbackend.commentmanager.CommentRepository;
import ar.edu.itba.cleancode.resilientbackend.commentmanager.CommentRequest;
import ar.edu.itba.cleancode.resilientbackend.tweetmanager.Tweet;


@RestController
@RequestMapping("/api")
public class CommentController {
        
    private final DatabaseConnector databaseConnector;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(DatabaseConnector databaseConnector, CommentRepository commentRepository) {
        this.databaseConnector = databaseConnector;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/comments/health")
    public String hello() {
        return "Comments up!";
    }

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    public String addReaction(@RequestBody CommentRequest request) {
        Comment comment = new Comment();
        comment.setComment(request.getComment());
        comment.setTweetId(request.getTweetId());
        comment.setUserId(request.getUserId());
        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new Error("Error al guardar");
        }
        return "Comment saved";
    }


    @GetMapping("/comments/{tweetId}")
    public List<Comment> getAllCommentsForTweet(@PathVariable Long tweetId) {
        Tweet tweet = new Tweet();
        tweet.setId(tweetId);

        return commentRepository.findCommentByTweetId(tweet); // TODO: cambiar la respuesta, que sea solo el tweet y los comentarios con id de usuario y talv ez nombre de usuario, pero no el usuario completo
    }

}
