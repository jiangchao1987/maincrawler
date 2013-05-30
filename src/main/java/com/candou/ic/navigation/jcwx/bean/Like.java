package com.candou.ic.navigation.jcwx.bean;

public class Like {
    private int likeId;
    private int appId;
    private String name;
    private String thumbnail;
    public int getLikeId() {
        return likeId;
    }
    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }
    public int getAppId() {
        return appId;
    }
    public void setAppId(int appId) {
        this.appId = appId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    @Override
    public String toString() {
        return "Like [likeId=" + likeId + ", appId=" + appId + ", name=" + name + ", thumbnail="
            + thumbnail + "]";
    }




}
