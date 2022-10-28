package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;

public class LoginWebService implements Serializable{

	private static final long serialVersionUID = 1851662938782959302L;
	
	private String url;
	private String endPointToken;
	private String endPointAuth;
	private boolean secure;
	private int timeout;
	private int round;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEndPointToken() {
		return endPointToken;
	}
	public void setEndPointToken(String endPointToken) {
		this.endPointToken = endPointToken;
	}
	public String getEndPointAuth() {
		return endPointAuth;
	}
	public void setEndPointAuth(String endPointAuth) {
		this.endPointAuth = endPointAuth;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public boolean isSecure() {
		return secure;
	}
	public void setSecure(boolean secure) {
		this.secure = secure;
	}

}
