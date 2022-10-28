package com.sit.abbra.abbraapi.core.security.login.domain;

import java.io.Serializable;

public class Login implements Serializable {
	
	private static final long serialVersionUID = -7382069788889424930L;

	private String userId;
	private String lastLoginDate;
	private String token; // userId encrypted
	private String salt;
	private String secret;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
}
