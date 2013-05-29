package com.candou.ac.rom.bean;

public class RomPhoto {
    
    private int appId;
    private String originalUrl;//URL地址

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    @Override
    public String toString() {
        return "RomPhoto [appId=" + appId + ", originalUrl=" + originalUrl + "]";
    }
}
