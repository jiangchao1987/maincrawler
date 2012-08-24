package com.candou.ac.rom.bean;

import java.util.List;

public class RomApp {

	private int appId;
	private String appName;
	private String author;
	private String fitType;
	private float size;
	private String releaseDate;
	private String romType;
	private float star;
	private String description;
	private String iconUrl;
	private List<RomPhoto> photos;
	private String url;
	private String downloadUrl;
	private String filename;
	private int categoryId;
	private String categoryName;
	private String company;

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getAppId() {
		return appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getFitType() {
		return fitType;
	}

	public void setFitType(String fitType) {
		this.fitType = fitType;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getRomType() {
		return romType;
	}

	public void setRomType(String romType) {
		this.romType = romType;
	}

	public float getStar() {
		return star;
	}

	public void setStar(float star) {
		this.star = star;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public List<RomPhoto> getPhotos() {
		return photos;
	}

	public void setPhotos(List<RomPhoto> photos) {
		this.photos = photos;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "RomApp [appId=" + appId + ", appName=" + appName + ", author=" + author + ", fitType=" + fitType + ", size=" + size + ", releaseDate=" + releaseDate + ", romType=" + romType
				+ ", star=" + star + ", description=" + description + ", iconUrl=" + iconUrl + ", photos=" + photos + ", url=" + url + ", downloadUrl=" + downloadUrl + ", filename=" + filename
				+ ", categoryId=" + categoryId + ", categoryName=" + categoryName + ", company=" + company + "]";
	}

}
