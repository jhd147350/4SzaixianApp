package com.jhd.fourszaixian.ui.append;

import android.R.integer;

public class SelfDriveTourBean {
	private int travelId;
	private String title;
	private String content;
	private String url;
	private String cityName;
	private long createTime;
	private String picUrl;
	private String activityTime;
	private String departureLocation;
	private String activityLocation;
	public int getTravelId() {
		return travelId;
	}
	public void setTravelId(int travelId) {
		this.travelId = travelId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}
	public String getDepartureLocation() {
		return departureLocation;
	}
	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}
	public String getActivityLocation() {
		return activityLocation;
	}
	public void setActivityLocation(String activityLocation) {
		this.activityLocation = activityLocation;
	}
	@Override
	public String toString() {
		return "SelfDriveTourBean [travelId=" + travelId + ", title=" + title
				+ ", content=" + content + ", url=" + url + ", cityName="
				+ cityName + ", createTime=" + createTime + ", picUrl="
				+ picUrl + ", activityTime=" + activityTime
				+ ", departureLocation=" + departureLocation
				+ ", activityLocation=" + activityLocation + "]";
	}
	

}
