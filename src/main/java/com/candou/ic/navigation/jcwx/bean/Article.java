package com.candou.ic.navigation.jcwx.bean;

public class Article {

    private int articleId;
    private String title;
    private int views;
    private int like;
    private String wxh;
    private String wxqr;
    private String content;
    private String thumbnail;
    private String createdAt;
    private String updatedAt;
    private int firstCid;
    private String firstCname;
    private String secondCname;

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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getWxh() {
        return wxh;
    }

    public void setWxh(String wxh) {
        this.wxh = wxh;
    }

    public String getWxqr() {
        return wxqr;
    }

    public void setWxqr(String wxqr) {
        this.wxqr = wxqr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getFirstCid() {
        return firstCid;
    }

    public void setFirstCid(int firstCid) {
        this.firstCid = firstCid;
    }

    public String getFirstCname() {
        return firstCname;
    }

    public void setFirstCname(String firstCname) {
        this.firstCname = firstCname;
    }

    public String getSecondCname() {
        return secondCname;
    }

    public void setSecondCname(String secondCname) {
        this.secondCname = secondCname;
    }

    @Override
    public String toString() {
        return "Article [articleId=" + articleId + ", title=" + title + ", views=" + views
            + ", like=" + like + ", wxh=" + wxh + ", wxqr=" + wxqr + ", content=" + content
            + ", thumbnail=" + thumbnail + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
            + ", firstCid=" + firstCid + ", firstCname=" + firstCname + ", secondCname="
            + secondCname + "]";
    }

}
