package com.candou.ic.market.pp.bean;

public class Job {
	private int id;
	private String name;
	private float price = 0.0f;
	private String priceCurrency;
	private String url;
	private int categoryID;
	private String categoryName;
	private String createdAt;
	private String updatedAt;
	private String version;
	private String releaseDate;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}

	public String getUrl() {
		return url;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setPriceCurrency(String currency) {
		this.priceCurrency = currency;
	}

	public String getPriceCurrency() {
		return priceCurrency;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", name=" + name + ", price=" + price + ", priceCurrency=" + priceCurrency + ", url="
				+ url + ", categoryID=" + categoryID + ", categoryName=" + categoryName + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", version=" + version + ", releaseDate=" + releaseDate + "]";
	}
	
}
