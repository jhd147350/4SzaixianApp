package com.jhd.fourszaixian.entity;
public class Series {
	private long id;
	private String name;
	private long brand_id;
	public Series() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(long brand_id) {
		this.brand_id = brand_id;
	}
	@Override
	public String toString() {
		return "Series [id=" + id + ", name=" + name + ", brand_id=" + brand_id
				+ "]";
	}

}
