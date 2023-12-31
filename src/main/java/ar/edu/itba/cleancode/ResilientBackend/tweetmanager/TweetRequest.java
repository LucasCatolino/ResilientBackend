package ar.edu.itba.cleancode.resilientbackend.tweetmanager;

// DTO
public class TweetRequest {
    
    private Long userId;
    private String title;
    private String content;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
