package com.sit.abbra.abbraapi.core.security.login.domain;

import java.io.Serializable;
import java.util.Date;

public class AuthorizeRequest implements Serializable {
	
	private static final long serialVersionUID = 9177961099406018361L;

	// key : Session Id / Authen Code
	private String key;
	private String state;
	private String codeChallenge;
	private String encryptData;
	private Date expired;
	private String clientId;	
	private ClientSystem clientSystem;

	public AuthorizeRequest(ClientSystem clientSystem) {
		this.clientSystem = clientSystem;
	}
	
	public AuthorizeRequest() {
		
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCodeChallenge() {
		return codeChallenge;
	}

	public void setCodeChallenge(String codeChallenge) {
		this.codeChallenge = codeChallenge;
	}

	public String getEncryptData() {
		return encryptData;
	}

	public void setEncryptData(String encryptData) {
		this.encryptData = encryptData;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public ClientSystem getClientSystem() {
		return clientSystem;
	}

}
