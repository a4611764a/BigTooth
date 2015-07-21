package com.zb.bittooth.model;

import java.io.Serializable;

public class UserInfor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7401106391232290572L;
	private String isLogin;
	private String head_img;
	private String name;
	private String level;
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}
	
}
