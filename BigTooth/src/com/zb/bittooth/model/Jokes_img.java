package com.zb.bittooth.model;

import java.io.Serializable;

public class Jokes_img implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5975627161209961402L;
	private String title;
	private String id;
	private String content;
	private String img;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
}
