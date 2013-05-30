package com.candou.ic.navigation.wxdh.vo;

import java.util.List;

public class App {
    private int id;
    private String name = ""; // 名称
    private String intro = "";// 简介
    private String url = ""; // 地址
    private int f;
    private String oc = "";
    private String wsu;
    private String detail = "";
    private int dts;
    private int cid;
    private String cname = "";// 分类名称
    private String icon = "";// icon地址
    private String icon_name;
    private List<Photo> photos;// icon地址
    private String imc = "";// 二维码地址
    private String imc_name;//二维码名称
    private String sc = "";
    private String direct_number = "";// 关注人数



    public String getIcon_name() {
        return icon_name;
    }

    public void setIcon_name(String icon_name) {
        this.icon_name = icon_name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIntro() {
        return intro;
    }

    public String getUrl() {
        return url;
    }

    public int getF() {
        return f;
    }

    public String getImc_name() {
        return imc_name;
    }

    public void setImc_name(String imc_name) {
        this.imc_name = imc_name;
    }

    public String getOc() {
        return oc;
    }

    public String getDetail() {
        return detail;
    }

    public int getCid() {
        return cid;
    }

    public String getCname() {
        return cname;
    }

    public String getImc() {
        return imc;
    }


    public String getSc() {
        return sc;
    }

    public String getDirect_number() {
        return direct_number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setOc(String oc) {
        this.oc = oc;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }


    public void setSc(String sc) {
        this.sc = sc;
    }

    public void setDirect_number(String direct_number) {
        this.direct_number = direct_number;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getDts() {
        return dts;
    }

    public void setDts(int dts) {
        this.dts = dts;
    }

    public String getWsu() {
        return wsu;
    }

    public void setWsu(String wsu) {
        this.wsu = wsu;
    }

    @Override
    public String toString() {
        return "App [id=" + id + ", name=" + name + ", intro=" + intro + ", url=" + url + ", f=" + f + ", oc=" + oc + ", wsu="
            + wsu + ", detail=" + detail + ", dts=" + dts + ", cid=" + cid + ", cname=" + cname + ", icon=" + icon
            + ", icon_name=" + icon_name + ", photos=" + photos + ", imc=" + imc + ", imc_name=" + imc_name + ", sc=" + sc
            + ", direct_number=" + direct_number + "]";
    }





}
