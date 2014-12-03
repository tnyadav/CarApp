package com.carapp.bean;

import com.google.gson.annotations.Expose;

public class Fleet {

	@Expose
	private String fleet_title;
	@Expose
	private String email;
	@Expose
	private String address;

	/**
	*
	* @return
	* The fleet_title
	*/
	public String getFleet_title() {
	return fleet_title;
	}

	/**
	*
	* @param fleet_title
	* The fleet_title
	*/
	public void setFleet_title(String fleet_title) {
	this.fleet_title = fleet_title;
	}

	/**
	*
	* @return
	* The email
	*/
	public String getEmail() {
	return email;
	}

	/**
	*
	* @param email
	* The email
	*/
	public void setEmail(String email) {
	this.email = email;
	}

	/**
	*
	* @return
	* The address
	*/
	public String getAddress() {
	return address;
	}

	@Override
	public String toString() {
		return fleet_title ;
	}

	/**
	*
	* @param address
	* The address
	*/
	public void setAddress(String address) {
	this.address = address;
	}

	

}
