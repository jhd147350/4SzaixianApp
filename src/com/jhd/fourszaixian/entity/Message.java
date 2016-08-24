package com.jhd.fourszaixian.entity;

public class Message {

	
		private int id;
		private String time;
		private String content;

		public Message(int id, String time, String content) {
			super();
			this.id = id;
			this.time = time;
			this.content = content;
		}

		public Message() {
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

		@Override
		public String toString() {
			return "message [id=" + id + ", time=" + time + ", content="
					+ content + "]";
		}

	
}
