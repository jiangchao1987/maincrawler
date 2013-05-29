package com.candou.ic.navigation.jcwx.bean;

/**
 * 一级分类，二级暂时只保留分类名，这里没有体现二级分类，App中保留。
 *
 * @author jiangchao
 */
public class Category {

    private int cid;
    private String cname;
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
