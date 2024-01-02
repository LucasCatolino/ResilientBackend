package ar.edu.itba.cleancode.resilientbackend.commentmanager;

// DTO
public class CommentRequest {

    private String comment;
    private Long tweetId;
    private Long userId;

    public void setComment(String newComment){
        this.comment = newComment;
    }

    public String getComment(){
        return this.comment;
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
