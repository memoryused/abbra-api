package com.sit.abbra.abbraapi.core.security.login.domain;

import com.sit.common.CommonRequest;

public class LoginAuthModel extends CommonRequest {

	private static final long serialVersionUID = 5879190423793065272L;
	
	String userInput;
	String passwordInput;
	String checkPibicsAuthen;

	public String getUserInput() {
		return userInput;
	}

	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}

	public String getPasswordInput() {
		return passwordInput;
	}

	public void setPasswordInput(String passwordInput) {
		this.passwordInput = passwordInput;
	}

	public String getCheckPibicsAuthen() {
		return checkPibicsAuthen;
	}

	public void setCheckPibicsAuthen(String checkPibicsAuthen) {
		this.checkPibicsAuthen = checkPibicsAuthen;
	}

}
