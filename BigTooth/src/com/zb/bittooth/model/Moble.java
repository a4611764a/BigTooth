package com.zb.bittooth.model;

import java.io.Serializable;

import com.baidu.location.LocationClient;

public class Moble implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3493417200764552573L;
	private String longitude;
	private String latitude;
	private String city;
	private String address;
	private LocationClient location;

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public LocationClient getLocation() {
		return location;
	}

	public void setLocation(LocationClient location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
