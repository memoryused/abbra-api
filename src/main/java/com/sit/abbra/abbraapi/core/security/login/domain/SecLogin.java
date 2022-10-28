package com.sit.abbra.abbraapi.core.security.login.domain;

public class SecLogin {

	private Long loginId;
	private LoginUser loginUser;
	private Token token;
	private String agentName;
	private String loginDatetime;
	private String logoutDatetime;
	private String actionDatetime;

	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getLoginDatetime() {
		return loginDatetime;
	}

	public void setLoginDatetime(String loginDatetime) {
		this.loginDatetime = loginDatetime;
	}

	public String getLogoutDatetime() {
		return logoutDatetime;
	}

	public void setLogoutDatetime(String logoutDatetime) {
		this.logoutDatetime = logoutDatetime;
	}

	public String getActionDatetime() {
		return actionDatetime;
	}

	public void setActionDatetime(String actionDatetime) {
		this.actionDatetime = actionDatetime;
	}

	public LoginUser getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}

}
