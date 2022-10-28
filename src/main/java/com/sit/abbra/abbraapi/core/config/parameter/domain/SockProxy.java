package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;

public class SockProxy implements Serializable {

	private static final long serialVersionUID = -8811121860424285607L;
	
	private boolean useProxy;
	private String proxyIP;
	private int proxyPort;
	private int proxyVersion;
	private String nonProxyHosts;
	
	public boolean isUseProxy() {
		return useProxy;
	}
	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}
	public String getProxyIP() {
		return proxyIP;
	}
	public void setProxyIP(String proxyIP) {
		this.proxyIP = proxyIP;
	}
	public int getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}
	public int getProxyVersion() {
		return proxyVersion;
	}
	public void setProxyVersion(int proxyVersion) {
		this.proxyVersion = proxyVersion;
	}
	public String getNonProxyHosts() {
		return nonProxyHosts;
	}
	public void setNonProxyHosts(String nonProxyHosts) {
		this.nonProxyHosts = nonProxyHosts;
	}

}
