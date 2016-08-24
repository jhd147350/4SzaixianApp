package com.jhd.fourszaixian.entity;

public class Info {

	private int id;
	private String infoTitle;
	private String infoTime;
	private String infoContent;
	private String infoImg;
	private String infoType;

	public Info() {
		super();
	}

	public Info(int id, String infoTitle, String infoTime, String infoContent,
			String infoImg, String infoType) {
		super();
		this.id = id;
		this.infoTitle = infoTitle;
		this.infoTime = infoTime;
		this.infoContent = infoContent;
		this.infoImg = infoImg;
		this.infoType = infoType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getInfoTime() {
		return infoTime;
	}

	public void setInfoTime(String infoTime) {
		this.infoTime = infoTime;
	}

	public String getInfoContent() {
		return infoContent;
	}

	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}

	public String getInfoImg() {
		return infoImg;
	}

	public void setInfoImg(String infoImg) {
		this.infoImg = infoImg;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	@Override
	public String toString() {
		return "Info [id=" + id + ", infoTitle=" + infoTitle + ", infoTime="
				+ infoTime + ", infoContent=" + infoContent + ", infoImg="
				+ infoImg + ", infoType=" + infoType + "]";
	}

}
