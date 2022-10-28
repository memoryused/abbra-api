package com.sit.abbra.abbraapi.core.security.log.domain;

import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.util.validate.InputValidateUtil;

public class LogEvent {

	private Long eventId;
	private Long operatorId;

	private String eventTime;
	private String logData;

	private String logDisplay;
	private LoginUser loginUser;
	private String methodClass;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getLogStatus() {
		if (InputValidateUtil.hasValue(logData)) {
			return "1";
		} else {
			return null;
		}
	}

	public String getLogData() {
		return logData;
	}

	public void setLogData(String logData) {
		this.logData = logData;
	}

	public LoginUser getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}

	public String getMethodClass() {
		return methodClass;
	}

	public void setMethodClass(String methodClass) {
		this.methodClass = methodClass;
	}

	public String getLogDisplay() {
		return logDisplay;
	}

	public void setLogDisplay(String logDisplay) {
		this.logDisplay = logDisplay;
	}
}
