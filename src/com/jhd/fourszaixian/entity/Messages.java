package com.jhd.fourszaixian.entity;

public class Messages {

	private int id;
	private String uid;
	private String time;
	private String content;

	public Messages(int id, String uid, String time, String content) {
		super();
		this.id = id;
		this.uid = uid;
		this.time = time;
		this.content = content;
	}

	public Messages() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "Messages [id=" + id + ", uid=" + uid + ", time=" + time
				+ ", content=" + content + "]";
	}

}
