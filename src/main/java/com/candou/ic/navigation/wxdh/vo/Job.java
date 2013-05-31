package com.candou.ic.navigation.wxdh.vo;


public class Job {


    private int id;
    private  String name = ""; // 名称
    private  String intro = "";// 简介
    private  String url = ""; // 地址
    private int f;
    private  String oc = "";
    private int cid;
    private  String cname = "";// 分类名称
    private  String direct_number = "";// 关注人数
    private  int is_matched ;

    private String created_at;
    private String updated_at;
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
    public String getOc() {
        return oc;
    }
    public int getCid() {
        return cid;
    }
    public String getCname() {
        return cname;
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
    public void setCid(int cid) {
        this.cid = cid;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public void setDirect_number(String direct_number) {
        this.direct_number = direct_number;
    }
    public int getIs_matched() {
        return is_matched;
    }
    public void setIs_matched(int is_matched) {
        this.is_matched = is_matched;
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
        return "Job [id=" + id + ", name=" + name + ", intro=" + intro + ", url=" + url + ", f=" + f + ", oc=" + oc + ", cid="
            + cid + ", cname=" + cname + ", direct_number=" + direct_number + ", is_matched=" + is_matched + ", created_at="
            + created_at + ", updated_at=" + updated_at + "]";
    }







}
