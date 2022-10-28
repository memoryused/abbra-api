package com.sit.abbra.abbraapi.core.security.login.domain;

import java.io.Serializable;

public class AuthenticateCriteria implements Serializable {
	private static final long serialVersionUID = -487969960573625210L;

	private String clientId;
	private String redirectUrl;
	private String responseType;
	private String codeChallenge;
	private String state;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getCodeChallenge() {
		return codeChallenge;
	}

	public void setCodeChallenge(String codeChallenge) {
		this.codeChallenge = codeChallenge;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
