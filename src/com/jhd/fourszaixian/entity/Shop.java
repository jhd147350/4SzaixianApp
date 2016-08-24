package com.jhd.fourszaixian.entity;

public class Shop {

	private int id;
//	private String uid;
	private String brand;
	private String series;
	private String model;
	private String type;
	private String shopName;
	private String shopYuyue;
	private String shop4S;
	private String shopDistance;
	private String shopAddress;
	private String shopImg;

	public Shop() {
		super();
	}

	public Shop(int id, String brand, String series, String model, String type,
			String shopName, String shopYuyue, String shop4s,
			String shopDistance, String shopAssress, String shopImg) {
		super();
		this.id = id;
		
		this.brand = brand;
		this.series = series;
		this.model = model;
		this.type = type;
		this.shopName = shopName;
		this.shopYuyue = shopYuyue;
		this.shop4S = shop4s;
		this.shopDistance = shopDistance;
		this.shopAddress = shopAssress;
		this.shopImg = shopImg;
	}

//	public String getUid() {
//		return uid;
//	}
//	public void setUid(String uid) {
//		this.uid = uid;
//	}
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopYuyue() {
		return shopYuyue;
	}

	public void setShopYuyue(String shopYuyue) {
		this.shopYuyue = shopYuyue;
	}

	public String getShop4S() {
		return shop4S;
	}

	public void setShop4S(String shop4s) {
		shop4S = shop4s;
	}

	public String getShopDistance() {
		return shopDistance;
	}

	public void setShopDistance(String shopDistance) {
		this.shopDistance = shopDistance;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAssress) {
		this.shopAddress = shopAssress;
	}

	public String getShopImg() {
		return shopImg;
	}

	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}

	@Override
	public String toString() {
		return "Shop [id=" + id + ", brand=" + brand
				+ ", series=" + series + ", model=" + model + ", type=" + type
				+ ", shopName=" + shopName + ", shopYuyue=" + shopYuyue
				+ ", shop4S=" + shop4S + ", shopDistance=" + shopDistance
				+ ", shopAddress=" + shopAddress + ", shopImg=" + shopImg + "]";
	}

}
