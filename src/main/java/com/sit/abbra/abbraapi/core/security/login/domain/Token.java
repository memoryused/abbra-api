package com.sit.abbra.abbraapi.core.security.login.domain;

import java.io.Serializable;

/**
 * สำหรับเก็บข้อมูลการ encrypt ในการ request
 */
public class Token implements Serializable {
	
	private static final long serialVersionUID = 2728506609946671950L;

	private String loginToken;
	private String encrypt;
	private String salt;
	private String secret;

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}
	
	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
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
