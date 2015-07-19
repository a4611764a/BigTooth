package com.zb.bittooth.model;

import java.io.Serializable;

public class Near implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8988875553942070939L;
	private String userName;
	private String id;
	private String distance;
	private String image_head;
	private String count;

	public Near() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getImage_head() {
		return image_head;
	}

	public void setImage_head(String image_head) {
		this.image_head = image_head;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

}
