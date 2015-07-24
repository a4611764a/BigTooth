package com.zb.bittooth.model;

public class Jokes {
	private String userName;
	private String imgHead;
	private String content;
	private String id;
	private String image;
	private String tag;
	private String userId;
	private String isShouCang; //1已经收藏，0为收藏
	public String getIsShouCang() {
		return isShouCang;
	}

	public void setIsShouCang(String isShouCang) {
		this.isShouCang = isShouCang;
	}

	public Jokes() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getImgHead() {
		return imgHead;
	}

	public void setImgHead(String imgHead) {
		this.imgHead = imgHead;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
