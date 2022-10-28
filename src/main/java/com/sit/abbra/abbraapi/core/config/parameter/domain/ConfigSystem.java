package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;

public class ConfigSystem implements Serializable {

	private static final long serialVersionUID = 6884438933092503349L;

	private Long configSystemId;
	private long connectTimeOut;
	private String conditionRight;
	private String loginWrong;
	private long loginWrongTime;
	private long loginWrongIn;
	private String loginCaptcha;
	private long loginWrongCaptcha;
	private String methodUnlock;
	private long methodUnlockAuto;
	private long createUser;
	private String createDate;
	private long updateUser;
	private String updateDate;
	
	private long pwLength;
	
	public Long getConfigSystemId() {
		return configSystemId;
	}

	public void setConfigSystemId(Long configSystemId) {
		this.configSystemId = configSystemId;
	}

	public long getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(long connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	public String getConditionRight() {
		return conditionRight;
	}

	public void setConditionRight(String conditionRight) {
		this.conditionRight = conditionRight;
	}

	public String getLoginWrong() {
		return loginWrong;
	}

	public void setLoginWrong(String loginWrong) {
		this.loginWrong = loginWrong;
	}

	public long getLoginWrongTime() {
		return loginWrongTime;
	}

	public void setLoginWrongTime(long loginWrongTime) {
		this.loginWrongTime = loginWrongTime;
	}

	public long getLoginWrongIn() {
		return loginWrongIn;
	}

	public void setLoginWrongIn(long loginWrongIn) {
		this.loginWrongIn = loginWrongIn;
	}

	public String getLoginCaptcha() {
		return loginCaptcha;
	}

	public void setLoginCaptcha(String loginCaptcha) {
		this.loginCaptcha = loginCaptcha;
	}

	public long getLoginWrongCaptcha() {
		return loginWrongCaptcha;
	}

	public void setLoginWrongCaptcha(long loginWrongCaptcha) {
		this.loginWrongCaptcha = loginWrongCaptcha;
	}

	public String getMethodUnlock() {
		return methodUnlock;
	}

	public void setMethodUnlock(String methodUnlock) {
		this.methodUnlock = methodUnlock;
	}

	public long getMethodUnlockAuto() {
		return methodUnlockAuto;
	}

	public void setMethodUnlockAuto(long methodUnlockAuto) {
		this.methodUnlockAuto = methodUnlockAuto;
	}

	public long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(long createUser) {
		this.createUser = createUser;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(long updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public long getPwLength() {
		return pwLength;
	}

	public void setPwLength(long pwLength) {
		this.pwLength = pwLength;
	}

}