package com.jhd.fourszaixian.entity;

public class Model {
	private long id;
	private String name;
	private String img;
	private String sortkey;
	private long subs_id;
	public Model() {
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getSortkey() {
		return sortkey;
	}
	public void setSortkey(String sortkey) {
		this.sortkey = sortkey;
	}
	public long getSubs_id() {
		return subs_id;
	}
	public void setSubs_id(long subs_id) {
		this.subs_id = subs_id;
	}
	@Override
	public String toString() {
		return "Model [id=" + id + ", name=" + name + ", img=" + img
				+ ", sortkey=" + sortkey + ", subs_id=" + subs_id + "]";
	}

}
