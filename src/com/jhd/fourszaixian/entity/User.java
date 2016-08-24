package com.jhd.fourszaixian.entity;

public class User {
	private long id;
	private String phonenum;
	private String password;
	private String nickname;

	 
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	private User (){}    
	public static User getInstance() {    
		return LazyHolder.INSTANCE;    
	}  
		
	private static class LazyHolder {    
		private static  User INSTANCE = new User();    
	}   
}
