package ar.edu.itba.cleancode.resilientbackend.reactionmanager;

// DTO
public class ReactionResponse {
    private Long userId;
    private Long tweetId;
    private Boolean isLike;

    public ReactionResponse(Long userId, Long tweetId, Boolean isLike) {
        this.userId = userId;
        this.tweetId = tweetId;
        this.isLike = isLike;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public Boolean getIsLike() {
        return isLike;
    }
}
