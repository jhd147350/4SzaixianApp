package com.jhd.fourszaixian.ui.append;

import java.util.List;

public class NewsBean {
	
	private List<Lists> list;
	private String date;
	
	
	
	public List<Lists> getList() {
		return list;
	}



	public void setList(List<Lists> list) {
		this.list = list;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	@Override
	public String toString() {
		return "News [list=" + list + ", date=" + date + "]";
	}



	public class Lists{
		private String date;
		private String title;
		private String url;
		private String imageurl;
		private int type;
		private String content;
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getImageurl() {
			return imageurl;
		}
		public void setImageurl(String imageurl) {
			this.imageurl = imageurl;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		@Override
		public String toString() {
			return "Lists [date=" + date + ", title=" + title + ", url=" + url
					+ ", imageurl=" + imageurl + ", type=" + type
					+ ", content=" + content + "]";
		}
		
	}
	
}
