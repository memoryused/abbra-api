package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;

public class ConfigClient implements Serializable {

	private static final long serialVersionUID = 4138721562628314399L;

	private String clientId;
	private String secret;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
}
