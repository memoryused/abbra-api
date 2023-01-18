package com.sit.abbra.abbraapi.core.security.login.domain;

import java.io.Serializable;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LoginUserRequest implements Serializable {
	
	private static final long serialVersionUID = 8820951633578249860L;

	private String username;
	@JsonIgnore
	private String password;
	private String captchaId;
	private String captchaCode;
	private String language;
	private boolean isOverride;
	private String agentName;
	private String authId;
	private Locale locale;
	
	private boolean isChangePWD;
	private String usernameModal;
	private String passwordModal;
	private String newpasswordModal;
	private String cfnewpasswordModal;

	public boolean isChangePWD() {
		return isChangePWD;
	}

	public void setChangePWD(boolean isChangePWD) {
		this.isChangePWD = isChangePWD;
	}

	public String getUsernameModal() {
		return usernameModal;
	}

	public void setUsernameModal(String usernameModal) {
		this.usernameModal = usernameModal;
	}

	public String getPasswordModal() {
		return passwordModal;
	}

	public void setPasswordModal(String passwordModal) {
		this.passwordModal = passwordModal;
	}

	public String getNewpasswordModal() {
		return newpasswordModal;
	}

	public void setNewpasswordModal(String newpasswordModal) {
		this.newpasswordModal = newpasswordModal;
	}

	public String getCfnewpasswordModal() {
		return cfnewpasswordModal;
	}

	public void setCfnewpasswordModal(String cfnewpasswordModal) {
		this.cfnewpasswordModal = cfnewpasswordModal;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptchaId() {
		return captchaId;
	}

	public void setCaptchaId(String captchaId) {
		this.captchaId = captchaId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCaptchaCode() {
		return captchaCode;
	}

	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isOverride() {
		return isOverride;
	}

	public void setOverride(boolean isOverride) {
		this.isOverride = isOverride;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
