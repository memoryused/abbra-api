package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;

public class PushWebService implements Serializable {

	private static final long serialVersionUID = 8669342311691827846L;

	private String url;
	private String endPointPushMsg;
	private boolean secure;
	private int timeout;
	private String protocol;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEndPointPushMsg() {
		return endPointPushMsg;
	}
	public void setEndPointPushMsg(String endPointPushMsg) {
		this.endPointPushMsg = endPointPushMsg;
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
}
