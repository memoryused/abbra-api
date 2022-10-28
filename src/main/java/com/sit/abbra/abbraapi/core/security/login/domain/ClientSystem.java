package com.sit.abbra.abbraapi.core.security.login.domain;

import java.io.Serializable;

public class ClientSystem implements Serializable {
	
	private static final long serialVersionUID = -6281124176289638019L;

	private String clientSystemId;
	private String clientId;
	private String redirectUrl;
	private String grantType;
	private String mainUrl;
	
	public String getClientSystemId() {
		return clientSystemId;
	}

	public void setClientSystemId(String clientSystemId) {
		this.clientSystemId = clientSystemId;
	}

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

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

}
