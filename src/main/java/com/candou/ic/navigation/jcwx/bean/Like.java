package com.candou.ic.navigation.jcwx.bean;

public class Like {

    private int likeId;
    private int articleId;
    private String title;
    private String thumbnail;
    private String createdAt;
    private String updatedAt;

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Like [likeId=" + likeId + ", articleId=" + articleId + ", title=" + title
            + ", thumbnail=" + thumbnail + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
            + "]";
    }

}
