package com.jhd.fourszaixian.entity;

public class Car {
	private int id;
	private String uid;
	private String carBrand;
	private String carSeries;
	private String carModel;
	private String carYear;
	private String carImg;

	public Car() {
		super();
	}

	public Car(int id, String uid, String carBrand, String carSeries,
			String carModel, String carYear, String carImg) {
		super();
		this.id = id;
		this.uid = uid;
		this.carBrand = carBrand;
		this.carSeries = carSeries;
		this.carModel = carModel;
		this.carYear = carYear;
		this.carImg = carImg;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getCarSeries() {
		return carSeries;
	}

	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getCarYear() {
		return carYear;
	}

	public void setCarYear(String carYear) {
		this.carYear = carYear;
	}

	public String getCarImg() {
		return carImg;
	}

	public void setCarImg(String carImg) {
		this.carImg = carImg;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", uid=" + uid + ", carBrand=" + carBrand
				+ ", carSeries=" + carSeries + ", carModel=" + carModel
				+ ", carYear=" + carYear + ", carImg=" + carImg + "]";
	}

}
