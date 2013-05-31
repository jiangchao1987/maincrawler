package com.candou.ic.navigation.wxdh.vo;

public class Category {

    private int cid;
    private String cname="";//分类名称
    private String curl;
    public int getCid() {
        return cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }


    public String getCurl() {
        return curl;
    }
    public void setCurl(String curl) {
        this.curl = curl;
    }
    @Override
    public String toString() {
        return "Category [cid=" + cid + ", cname=" + cname + ", curl=" + curl + "]";
    }




}
