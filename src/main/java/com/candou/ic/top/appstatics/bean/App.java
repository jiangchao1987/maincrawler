package com.candou.ic.top.appstatics.bean;

public class App {

	private int id;
	private String today;
	private int tRank;
	private String yesterday;
	private int yRank;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public int gettRank() {
		return tRank;
	}

	public void settRank(int tRank) {
		this.tRank = tRank;
	}

	public String getYesterday() {
		return yesterday;
	}

	public void setYesterday(String yesterday) {
		this.yesterday = yesterday;
	}

	public int getyRank() {
		return yRank;
	}

	public void setyRank(int yRank) {
		this.yRank = yRank;
	}

}
