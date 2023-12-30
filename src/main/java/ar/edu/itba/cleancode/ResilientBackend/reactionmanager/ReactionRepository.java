package ar.edu.itba.cleancode.resilientbackend.reactionmanager;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.itba.cleancode.resilientbackend.tweetmanager.Tweet;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    
    List<Reaction> findReactionByTweetId(Tweet tweet);
}
