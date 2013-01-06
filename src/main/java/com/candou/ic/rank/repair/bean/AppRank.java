package com.candou.ic.rank.repair.bean;

public class AppRank {
    private Integer id;
    private int rank;
    private int categoryId;
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AppRank [id=" + id + ", rank=" + rank + ", categoryId=" + categoryId + ", createdAt=" + createdAt + "]";
    }
}
