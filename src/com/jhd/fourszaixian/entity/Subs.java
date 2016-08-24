package com.jhd.fourszaixian.entity;

public class Subs {
	private long id;
	private String name;
	private long series_id;
	public Subs() {
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
	public long getSeries_id() {
		return series_id;
	}
	public void setSeries_id(long series_id) {
		this.series_id = series_id;
	}
	@Override
	public String toString() {
		return "Subs [id=" + id + ", name=" + name + ", series_id=" + series_id
				+ "]";
	}

}
