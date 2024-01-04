package ar.edu.itba.cleancode.resilientbackend.commentmanager;

// DTO
public class CommentResponse {

    private Long userId;
    private Long tweetId;
    private String comment;

    public CommentResponse(Long userId, Long tweetId, String comment) {
        this.userId = userId;
        this.tweetId = tweetId;
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public String getComment() {
        return comment;
    }
}
