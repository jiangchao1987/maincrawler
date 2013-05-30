package com.candou.ic.navigation.jcwx.bean;

public class App {
    private int app_id;
    private String name;
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
    public int getApp_id() {
        return app_id;
    }
    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
        return "App [app_id=" + app_id + ", name=" + name + ", views=" + views + ", like=" + like
            + ", wxh=" + wxh + ", wxqr=" + wxqr + ", content=" + content + ", thumbnail="
            + thumbnail + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", firstCid="
            + firstCid + ", firstCname=" + firstCname + ", secondCname=" + secondCname + "]";
    }

}
