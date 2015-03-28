package com.hackathon.mobileapp.domain;

public class AppUser {
	
	private String UIN;
	private String firstName;
	private String lastName;
	private Address userAddress;
	public String getUIN() {
		return UIN;
	}
	public void setUIN(String uIN) {
		UIN = uIN;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Address getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(Address userAddress) {
		this.userAddress = userAddress;
	}
	
	
}
