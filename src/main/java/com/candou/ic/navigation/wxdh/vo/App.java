package com.candou.ic.navigation.wxdh.vo;

import java.util.List;

public class App {
    private String wx_id;
    private String wx_displayname; // 显示名称
    private String wx_intro;// 简介
    private String wx_url; // 关注微信跳转地址
    private String wx_name;// 微信名称
    private String wx_detail;// 详细
    private String wx_category_name;// 分类名称
    private int wx_views;// 关注人数
    private String wx_icon;// icon头像本地保存地址
    private String wx_date; // 微信加入列表时间
    private String wx_status;// 状态
    private String wx_qrcode;// imc二维码本地保存地址

    // 辅助字段

    private String wx_icon_url; // icon远程地址
    private String wx_qrcode_url;// imc远程地址

    private List<Photo> photos;// 缩略图

    public String getWx_id() {
        return wx_id;
    }

    public String getWx_displayname() {
        return wx_displayname;
    }

    public String getWx_intro() {
        return wx_intro;
    }

    public String getWx_url() {
        return wx_url;
    }

    public String getWx_name() {
        return wx_name;
    }

    public String getWx_detail() {
        return wx_detail;
    }

    public String getWx_category_name() {
        return wx_category_name;
    }

    public int getWx_views() {
        return wx_views;
    }

    public String getWx_icon() {
        return wx_icon;
    }

    public String getWx_date() {
        return wx_date;
    }

    public String getWx_status() {
        return wx_status;
    }

    public String getWx_qrcode() {
        return wx_qrcode;
    }

    public String getWx_icon_url() {
        return wx_icon_url;
    }

    public String getWx_qrcode_url() {
        return wx_qrcode_url;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setWx_id(String wx_id) {
        this.wx_id = wx_id;
    }

    public void setWx_displayname(String wx_displayname) {
        this.wx_displayname = wx_displayname;
    }

    public void setWx_intro(String wx_intro) {
        this.wx_intro = wx_intro;
    }

    public void setWx_url(String wx_url) {
        this.wx_url = wx_url;
    }

    public void setWx_name(String wx_name) {
        this.wx_name = wx_name;
    }

    public void setWx_detail(String wx_detail) {
        this.wx_detail = wx_detail;
    }

    public void setWx_category_name(String wx_category_name) {
        this.wx_category_name = wx_category_name;
    }

    public void setWx_views(int wx_views) {
        this.wx_views = wx_views;
    }

    public void setWx_icon(String wx_icon) {
        this.wx_icon = wx_icon;
    }

    public void setWx_date(String wx_date) {
        this.wx_date = wx_date;
    }

    public void setWx_status(String wx_status) {
        this.wx_status = wx_status;
    }

    public void setWx_qrcode(String wx_qrcode) {
        this.wx_qrcode = wx_qrcode;
    }

    public void setWx_icon_url(String wx_icon_url) {
        this.wx_icon_url = wx_icon_url;
    }

    public void setWx_qrcode_url(String wx_qrcode_url) {
        this.wx_qrcode_url = wx_qrcode_url;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "App [wx_id=" + wx_id + ", wx_displayname=" + wx_displayname + ", wx_intro=" + wx_intro + ", wx_url=" + wx_url
            + ", wx_name=" + wx_name + ", wx_detail=" + wx_detail + ", wx_category_name=" + wx_category_name + ", wx_views="
            + wx_views + ", wx_icon=" + wx_icon + ", wx_date=" + wx_date + ", wx_status=" + wx_status + ", wx_qrcode="
            + wx_qrcode + ", wx_icon_url=" + wx_icon_url + ", wx_qrcode_url=" + wx_qrcode_url + ", photos=" + photos + "]";
    }

}
