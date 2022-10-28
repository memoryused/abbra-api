package com.sit.abbra.abbraapi.util.rest.domain;

import java.io.Serializable;
import java.util.Map;

public class RestClientParams implements Serializable {

	private static final long serialVersionUID = 4205851561145411978L;

	private String url;
	private String path;
	private boolean isSecure;
	private Integer timeout;
	private String strJsonReq;
	private Class<?> clazz;
	private Map<String, String> headerMap;
	private int retry;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isSecure() {
		return isSecure;
	}
	public void setSecure(boolean isSecure) {
		this.isSecure = isSecure;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public String getStrJsonReq() {
		return strJsonReq;
	}
	public void setStrJsonReq(String strJsonReq) {
		this.strJsonReq = strJsonReq;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public Map<String, String> getHeaderMap() {
		return headerMap;
	}
	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}
	public int getRetry() {
		return retry;
	}
	public void setRetry(int retry) {
		this.retry = retry;
	}
}
