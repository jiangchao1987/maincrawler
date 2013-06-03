package com.candou.ic.navigation.wxdh.vo;

public class Photo {
    private int id;
    private int appid;
    private String photo_url = "";
    private String filename;
    private int type;

    private String created_at;
    private String updated_at;

    public int getAppid() {
        return appid;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setType(int type) {
        this.type = type;
    }



    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Photo [id=" + id + ", appid=" + appid + ", photo_url=" + photo_url + ", filename=" + filename + ", type=" + type
            + ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
    }





}
