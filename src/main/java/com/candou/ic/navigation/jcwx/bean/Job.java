package com.candou.ic.navigation.jcwx.bean;

public class Job {

    private int id;
    private String title;
    private String wxh; // 微信号
    private String cname;
    private int views;
    private String content;
    private String thumbnail;
    private String createdAt;
    private String updatedAt;
    private int cid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWxh() {
        return wxh;
    }

    public void setWxh(String wxh) {
        this.wxh = wxh;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
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

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Job [id=" + id + ", title=" + title + ", wxh=" + wxh + ", cname=" + cname
            + ", views=" + views + ", content=" + content + ", thumbnail=" + thumbnail
            + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", cid=" + cid + "]";
    }

}
