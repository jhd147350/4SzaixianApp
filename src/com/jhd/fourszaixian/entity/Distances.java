package com.jhd.fourszaixian.entity;

public class Distances {

	private int id;
	private String uid;
	private String type;
	private double diatancess;

	public Distances() {
		super();
	}

	public Distances(int id, String uid, String type, double diatancess) {
		super();
		this.id = id;
		this.uid = uid;
		this.type = type;
		this.diatancess = diatancess;
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

	public double getDiatancess() {
		return diatancess;
	}

	public void setDiatancess(double diatancess) {
		this.diatancess = diatancess;
	}

	@Override
	public String toString() {
		return "Distances [id=" + id + ", uid=" + uid + ", type=" + type
				+ ", diatancess=" + diatancess + "]";
	}

}
