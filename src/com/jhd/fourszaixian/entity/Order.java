package com.jhd.fourszaixian.entity;

import java.util.Date;



public class Order {

	private int id;
	private String uid;
	private String type;
	private String state;
	private String name;
	private String features;
	private String money;
	private String time;

	public Order() {
		super();
	}

	public Order(int id, String uid,String type, String state, String name,
			String features, String money, String time) {
		super();
		this.id = id;
		this.uid = uid;
		this.type = type;
		this.state = state;
		this.name = name;
		this.features = features;
		this.money = money;
		this.time = time;
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
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", uid=" + uid + ", type=" + type
				+ ", state=" + state + ", name=" + name + ", features="
				+ features + ", money=" + money + ", time=" + time + "]";
	}

}
