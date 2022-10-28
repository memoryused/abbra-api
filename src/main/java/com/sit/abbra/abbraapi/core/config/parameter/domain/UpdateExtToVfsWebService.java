package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;

public class UpdateExtToVfsWebService implements Serializable {

	private static final long serialVersionUID = -969491339771905305L;
	
	private String url;
	private String endPointReceiveUpd;
	private String authorization;
	private boolean secure;
	private int timeout;
	private int round;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEndPointReceiveUpd() {
		return endPointReceiveUpd;
	}
	public void setEndPointReceiveUpd(String endPointReceiveUpd) {
		this.endPointReceiveUpd = endPointReceiveUpd;
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
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
}
