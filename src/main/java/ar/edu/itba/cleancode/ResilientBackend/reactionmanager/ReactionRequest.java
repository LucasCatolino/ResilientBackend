package ar.edu.itba.cleancode.resilientbackend.reactionmanager;

// DTO
public class ReactionRequest {

    private String isLike;
    private Long tweetId;
    private Long userId;

    public void setLike(String isLike){
        this.isLike = isLike;
    }

    public Boolean getLike(){
        return Boolean.valueOf(isLike);
    }

    public void setTweetId(Long tweetId){
        this.tweetId = tweetId;
    }

    public Long getTweetId(){
        return tweetId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public Long getUserId(){
        return userId;
    }
}
