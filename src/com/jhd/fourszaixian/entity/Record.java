package com.jhd.fourszaixian.entity;

import java.sql.Date;
import java.util.Calendar;

public class Record {

	private int id;
	private String uid;
	private String xcType;
	private double xcMoney;
	private String xcMonth;
	private String xcTime;
	private String xcYear;

	public Record() {
		super();
	}

	public String getXcMonth() {
		return xcMonth;
	}

	public void setXcMonth(String xcMonth) {
		this.xcMonth = xcMonth;
	}

	public Record(int id, String uid, String xcType, double xcMoney,
			String xcMonth, String xcTime, String xcYear) {
		super();
		this.id = id;
		this.uid = uid;
		this.xcType = xcType;
		this.xcMoney = xcMoney;
		this.xcMonth = xcMonth;
		this.xcTime = xcTime;
		this.xcYear = xcYear;
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

	public String getXcTime() {
		return xcTime;
	}

	public void setXcTime(String xcTime) {
		this.xcTime = xcTime;
	}

	public String getXcType() {
		return xcType;
	}

	public void setXcType(String xcType) {
		this.xcType = xcType;
	}

	public double getXcMoney() {
		return xcMoney;
	}

	public void setXcMoney(double xcMoney) {
		this.xcMoney = xcMoney;
	}

	public String getXcYear() {
		return xcYear;
	}

	public void setXcYear(String xcYear) {
		this.xcYear = xcYear;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", uid=" + uid + ", xcType=" + xcType
				+ ", xcMoney=" + xcMoney + ", xcMonth=" + xcMonth + ", xcTime="
				+ xcTime + ", xcYear=" + xcYear + "]";
	}

}
