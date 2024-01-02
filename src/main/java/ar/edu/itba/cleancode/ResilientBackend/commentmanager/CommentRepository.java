package ar.edu.itba.cleancode.resilientbackend.commentmanager;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.itba.cleancode.resilientbackend.tweetmanager.Tweet;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findCommentByTweetId(Tweet tweet);
}
