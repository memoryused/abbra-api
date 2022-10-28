package com.sit.abbra.abbraapi.core.security.login.domain;

import java.io.Serializable;

public class ZendeskData implements Serializable{

	private static final long serialVersionUID = 8027331769677270053L;
	
	private String email;
	private String phone;
	private String organizationName;
	private String username;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
