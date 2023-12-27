package ar.edu.itba.cleancode.resilientbackend.tweetmanager;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    // You can add custom query methods here if needed
}
