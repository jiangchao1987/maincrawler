package com.candou.ic.market.pp.bean;

public class Price {
	private int applicationId;
	private float currentPrice;//现在的价格
	private float lastPrice;
	private String priceTrend;//价格趋势

	public Price(int applicationId, float currentPrice, float lastPrice,
			String priceTrend) {
		super();
		this.applicationId = applicationId;
		this.currentPrice = currentPrice;
		this.lastPrice = lastPrice;
		this.priceTrend = priceTrend;
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public float getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}

	public float getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(float lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getPriceTrend() {
		return priceTrend;
	}

	public void setPriceTrend(String priceTrend) {
		this.priceTrend = priceTrend;
	}

}
