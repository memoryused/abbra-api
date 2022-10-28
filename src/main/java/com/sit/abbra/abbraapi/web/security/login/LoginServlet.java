package com.sit.abbra.abbraapi.web.security.login;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.AuthorizeRequest;
import com.sit.abbra.abbraapi.core.security.login.domain.ClientSystem;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUserRequest;
import com.sit.abbra.abbraapi.core.security.login.service.LoginManager;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.domain.GlobalVariable;

import util.string.StringUtil;

public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = -506035889484590249L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginUserRequest loginUserRequest = getUserRequest(request);
		try {
			AuthorizeRequest authorizeRequest = null;
			LoginManager manager = new LoginManager(getLogger());
			if(loginUserRequest.isOverride()) {
				// call manage business manager
				authorizeRequest = manager.overrideLogin(loginUserRequest);
			} else {
				// call manage business manager
				authorizeRequest = manager.login(loginUserRequest, request);				
			}
			
			// Callback to Client
			ClientSystem clientSystem = manager.getClientSystemByClientId(authorizeRequest.getClientId());
			String url = clientSystem.getRedirectUrl() + "?code=" + authorizeRequest.getKey() + "&state=" + authorizeRequest.getState();
			getLogger().debug("Redirect: {}", url);
			response.sendRedirect(url);
			
		} catch (Exception e) {
			execptionHandling(loginUserRequest, request, response, e);
		}
	}
	
	private void execptionHandling(LoginUserRequest loginUserRequest, HttpServletRequest request, HttpServletResponse response, Exception exception) {
		getLogger().catching(exception);
		
		String messageCode = exception.getMessage();
		getLogger().error("Message Code [{}]", messageCode);
		
		// Default to login.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		try {
			LoginManager manager = new LoginManager(getLogger());
			
			AuthorizeRequest authorizeRequest = manager.getAuthorizeByKey(loginUserRequest.getAuthId());
			ResourceBundle bundle = EExtensionApiUtil.getBundleMessageAlert(loginUserRequest.getLocale());
			String defaultMessage = EExtensionApiUtil.getMessage(bundle, "30010");
			String errorDescription = EExtensionApiUtil.getMessage(defaultMessage, bundle, messageCode);
			String userName = (loginUserRequest.getUsername() == null) ? "" : loginUserRequest.getUsername();
			request.setAttribute("username", userName);
			request.setAttribute("msgError", errorDescription);
			request.setAttribute("msgType", "40003".equals(messageCode) ? "C" : "A");
			
			if(authorizeRequest == null) {
				dispatcher = request.getRequestDispatcher("/login.jsp");
			} else {
				dispatcher = request.getRequestDispatcher("/login.jsp?id=" + loginUserRequest.getAuthId());
			}
		} catch (Exception ex) {
			getLogger().catching(ex);
		}
		
        try {
	        dispatcher.forward(request, response);
		} catch (ServletException | IOException ex) {
			getLogger().catching(ex);			
		}	  
	}
	
	private LoginUserRequest getUserRequest(HttpServletRequest request) {
		String userAgent = request.getHeader(GlobalVariable.HEADER_USER_AGENT);
		getLogger().debug("Browser User Agents : {}", userAgent);

		LoginUserRequest loginUserRequest = new LoginUserRequest();
		try {
			loginUserRequest.setUsername(StringUtil.stringToNull(request.getParameter("username")));
			loginUserRequest.setPassword(StringUtil.stringToNull(request.getParameter("password")));
			loginUserRequest.setCaptchaCode(StringUtil.stringToNull(request.getParameter("captchaCode")));
			loginUserRequest.setCaptchaId(StringUtil.stringToNull(request.getParameter("captchaId")));
			loginUserRequest.setOverride("true".equals(request.getParameter("isOverride")));
			loginUserRequest.setAuthId(StringUtil.stringToNull(request.getParameter("authId")));
			loginUserRequest.setLanguage(StringUtil.stringToNull(request.getParameter("language")));
			loginUserRequest.setAgentName(StringUtil.stringToNull(userAgent));
			if (loginUserRequest.getLanguage() == null) {
				loginUserRequest.setLocale(ParameterConfig.getApplication().getApplicationLocale());
			} else {
				loginUserRequest.setLocale(new Locale(loginUserRequest.getLanguage().toLowerCase(), loginUserRequest.getLanguage().toUpperCase()));
			}
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return loginUserRequest;
	}
	
	public Logger getLogger() {
		return LogManager.getLogger(this.getClass().getSimpleName());
	}
}
