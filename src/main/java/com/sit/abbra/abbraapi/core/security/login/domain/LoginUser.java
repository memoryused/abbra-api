package com.sit.abbra.abbraapi.core.security.login.domain;

import java.io.Serializable;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LoginUser implements Serializable {
	private static final long serialVersionUID = -8903362643632171821L;

	@JsonIgnore
	private String userId;
	private String userName;
	@JsonIgnore
	private String password;
	
	@JsonIgnore
	private String organizationId;
	private String organizationName;
	private String userFirstName;
	private String userLastName;
	private String email;
	@JsonIgnore
	private String lockStatus;
	@JsonIgnore
	private String lockDate;
	@JsonIgnore
	private String remark;
	@JsonIgnore
	private String active;
	@JsonIgnore
	private String createUser;
	@JsonIgnore
	private String createDate;
	@JsonIgnore
	private String updateUser;
	@JsonIgnore
	private String updateDate;
	
	@JsonIgnore
	private String secret;
	@JsonIgnore
	private String salt;
	private String language;
	private String changeLog;

	@JsonIgnore
	private String token;
	
	@JsonIgnore
	private String logoutDatetime;
	
	@JsonIgnore
	private String stationIp;
	
	@JsonIgnore
	private String loginDate;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getLockDate() {
		return lockDate;
	}

	public void setLockDate(String lockDate) {
		this.lockDate = lockDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@JsonIgnore
	public Locale getLocale() {
		return this.language != null && !this.language.trim().isEmpty()
				? new Locale(this.language.toLowerCase(), this.language.toUpperCase())
				: new Locale("en", "EN");
	}

	@JsonIgnore
	public String getLocaleStr() {
		return this.language != null && !this.language.trim().isEmpty() ? this.language.toUpperCase() : "EN";
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}

	public String getLogoutDatetime() {
		return logoutDatetime;
	}

	public void setLogoutDatetime(String logoutDatetime) {
		this.logoutDatetime = logoutDatetime;
	}

	public String getStationIp() {
		return stationIp;
	}

	public void setStationIp(String stationIp) {
		this.stationIp = stationIp;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
}
