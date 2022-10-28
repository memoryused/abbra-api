package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;

public class AuthWebService implements Serializable {

	private static final long serialVersionUID = 3284895809837256750L;

	private String url;
	private String endPointRequestToken;
	private boolean secure;
	private int timeout;
	private int round;
	private String protocol;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEndPointRequestToken() {
		return endPointRequestToken;
	}
	public void setEndPointRequestToken(String endPointRequestToken) {
		this.endPointRequestToken = endPointRequestToken;
	}
	public boolean isSecure() {
		return secure;
	}
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	
}
