package com.candou.ac.rom.bean;

public class RomJob {
    
    private int jobId;
    private String name;
    private String url;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "RomJob [jobId=" + jobId + ", name=" + name + ", url=" + url + "]";
    }
}
