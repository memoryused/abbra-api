package com.sit.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.util.response.ResponseMessageUtil;

public abstract class CommonWS {
	private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

	public LoginUser getLoginUser(HttpServletRequest request) {
		return (LoginUser) request.getAttribute(ParameterConfig.getSecurityUtilConfig().getReqKeyUser());
	}
	
	public ResponseMessageUtil getMessageUtil() {
		return new ResponseMessageUtil(logger);
	}
	
	public ResponseMessageUtil getMessageUtil(LoginUser loginUser) {
		return new ResponseMessageUtil(logger, loginUser);
	}
	
	public Logger getLogger() {
		return logger;
	}

}