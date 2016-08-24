package com.jhd.fourszaixian.entity;

public class Quan {
	private int id;
	private String uid;
	private String state;
	private String money;
	private String condition;
	private String deadline;

	public Quan() {
		super();
	}

	public Quan(int id, String uid, String state, String quane,
			String condition, String deadline) {
		super();
		this.id = id;
		this.uid = uid;
		this.state = state;
		this.money = quane;
		this.condition = condition;
		this.deadline = deadline;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String quane) {
		this.money = quane;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		return "Quan [id=" + id + ", uid=" + uid + ", state=" + state
				+ ", money=" + money + ", condition=" + condition
				+ ", deadline=" + deadline + "]";
	}

}
