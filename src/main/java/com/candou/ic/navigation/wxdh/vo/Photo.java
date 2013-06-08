package com.candou.ic.navigation.wxdh.vo;

public class Photo {
    private int wx_photo_id;
    private int wx_id;
    private String wx_screen;//高分辨率  大图
    private String wx_thumb;//缩略图  低分辨率

    // 辅助字段
    private String wx_screen_url;
    private String wx_thumb_url;

    public int getWx_photo_id() {
        return wx_photo_id;
    }

    public int getWx_id() {
        return wx_id;
    }

    public String getWx_screen() {
        return wx_screen;
    }

    public String getWx_thumb() {
        return wx_thumb;
    }

    public void setWx_photo_id(int wx_photo_id) {
        this.wx_photo_id = wx_photo_id;
    }

    public void setWx_id(int wx_id) {
        this.wx_id = wx_id;
    }

    public void setWx_screen(String wx_screen) {
        this.wx_screen = wx_screen;
    }

    public void setWx_thumb(String wx_thumb) {
        this.wx_thumb = wx_thumb;
    }

    public String getWx_screen_url() {
        return wx_screen_url;
    }

    public String getWx_thumb_url() {
        return wx_thumb_url;
    }

    public void setWx_screen_url(String wx_screen_url) {
        this.wx_screen_url = wx_screen_url;
    }

    public void setWx_thumb_url(String wx_thumb_url) {
        this.wx_thumb_url = wx_thumb_url;
    }

    @Override
    public String toString() {
        return "Photo [wx_photo_id=" + wx_photo_id + ", wx_id=" + wx_id + ", wx_screen=" + wx_screen + ", wx_thumb=" + wx_thumb
            + ", wx_screen_url=" + wx_screen_url + ", wx_thumb_url=" + wx_thumb_url + "]";
    }

}
