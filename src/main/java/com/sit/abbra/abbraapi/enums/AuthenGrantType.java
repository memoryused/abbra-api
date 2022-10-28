package com.sit.abbra.abbraapi.enums;

public enum AuthenGrantType {
	AUTHORIZATION_CODE("authorization_code", "code")
	, PASSWORD("password", "form")
	, CLIENT_CREDENTIALS("client_credentials", null)
	, IMPLICIT("implicit", null);

	private String grantType;
	private String responseType;
	
	AuthenGrantType(String grantType, String responseType) {
		this.grantType = grantType;
		this.responseType = responseType;
	}

	public String getGrantType() {
		return grantType;
	}

	public String getResponseType() {
		return responseType;
	}
	
}
