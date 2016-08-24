package com.jhd.fourszaixian.entity;

public class Zjyitem {

	private int id;
	private String zjyContent;
	private String zjyTitle;
	private String zjyCfd;
	private String zjyTime;
	private String zjyAddress;
	private String zjyImg;
    private String zjyHttp;
	public Zjyitem() {
		super();
	}

	public Zjyitem(int id, String zjyContent, String zjyTitle, String zjyCfd,
			String zjytime, String zjyAddress, String zjyImg,String zjyHttp) {
		super();
		this.id = id;
		this.zjyContent = zjyContent;
		this.zjyTitle = zjyTitle;
		this.zjyCfd = zjyCfd;
		this.zjyTime = zjytime;
		this.zjyAddress = zjyAddress;
		this.zjyImg = zjyImg;
		this.zjyHttp = zjyHttp;
	}

	public String getZjyHttp() {
		return zjyHttp;
	}

	public void setZjyHttp(String zjyHttp) {
		this.zjyHttp = zjyHttp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getZjyContent() {
		return zjyContent;
	}

	public void setZjyContent(String zjyContent) {
		this.zjyContent = zjyContent;
	}

	public String getZjyTitle() {
		return zjyTitle;
	}

	public void setZjyTitle(String zjyTitle) {
		this.zjyTitle = zjyTitle;
	}

	public String getZjyCfd() {
		return zjyCfd;
	}

	public void setZjyCfd(String zjyCfd) {
		this.zjyCfd = zjyCfd;
	}

	public String getZjytime() {
		return zjyTime;
	}

	public void setZjytime(String zjytime) {
		this.zjyTime = zjytime;
	}

	public String getZjyAddress() {
		return zjyAddress;
	}

	public void setZjyAddress(String zjyAddress) {
		this.zjyAddress = zjyAddress;
	}

	public String getZjyImg() {
		return zjyImg;
	}

	public void setZjyImg(String zjyImg) {
		this.zjyImg = zjyImg;
	}

	@Override
	public String toString() {
		return "Zjyitem [id=" + id + ", zjyContent=" + zjyContent
				+ ", zjyTitle=" + zjyTitle + ", zjyCfd=" + zjyCfd
				+ ", zjytime=" + zjyTime + ", zjyAddress=" + zjyAddress
				+ ", zjyImg=" + zjyImg + ", zjyHttp=" + zjyHttp + "]";
	}

}
