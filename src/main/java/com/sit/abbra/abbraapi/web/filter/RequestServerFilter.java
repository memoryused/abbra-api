package com.sit.abbra.abbraapi.web.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.security.login.service.LoginManager;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.common.CommonError;
import com.sit.common.CommonException;
import com.sit.common.CommonResponse;
import com.sit.domain.ExceptionVariable;
import com.sit.exception.AuthenticateException;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.log4j2.DefaultLogUtil;

@Provider
public class RequestServerFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		getLogger().debug("METHOD : {}", context.getMethod());
		getLogger().debug("URI : {}", context.getUriInfo().getPath());
		
		if (context.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS)) {
			getLogger().debug("SKIP: OPTION : {}", context.getUriInfo().getPath());
			return;
		}

		String[] skipFilter = {
				"authenticate", "internal_authenticate", "external_authenticate", "oauth/token" 
				, "botdetectcaptcha", "api/reloadConfig", "images/favicon.ico"
				};

		boolean isContains = Arrays.stream(skipFilter).anyMatch(context.getUriInfo().getPath()::equals);
		if (isContains) {
			getLogger().debug("SKIP: URI : {}", context.getUriInfo().getPath());
			return;
		}

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());

			// call manage business manager
			LoginManager manager = new LoginManager(getLogger());
			LoginUser user = manager.filterAccessToken(conn, context);
			getLogger().debug(user.getUserName());

			// Set user to request
			context.setProperty(ParameterConfig.getSecurityUtilConfig().getReqKeyUser(), user);

		} catch (Exception e) {
			createErrorResponse(context, e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		getLogger().debug("After doFilter had body [{}]", context.hasEntity());
	}

	private void createErrorResponse(ContainerRequestContext context, Exception exception) {
		try {

			AuthenticateException ex = new AuthenticateException(exception);
			
			logging(ex);
			
			String msgCode = ex.getErrorCode().replace(ExceptionVariable.EXCEPTION_CODE_PREFIX, ExceptionVariable.EXCEPTION_CODE_REPLACE);
			String msgDesc = ex.getErrorDesc();
			
			// Get Message Description from bundle
			msgDesc = getMsgDesc(msgCode, msgDesc);

			// Set Common Exception
			String loggingCode = ex.getErrorCode();
			if (ex.getLoggingCode() != null && !ex.getLoggingCode().isEmpty()) {
				loggingCode = ex.getLoggingCode();
			}
			
			CommonError commonError = new CommonError();
			commonError.setErrorCode(loggingCode);
			commonError.setErrorDesc(msgDesc);

			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setData(null);
			commonResponse.setMessageCode(msgCode);
			commonResponse.setMessageDesc(msgDesc);
			commonResponse.setComponentType(ex.getComponentType());
			commonResponse.setDisplayStatus(ex.getDisplayStatus());
			commonResponse.setError(commonError);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commonResponse);

			context.abortWith(Response.status(Response.Status.FORBIDDEN)
					.entity(json)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
					.build());

		} catch (Exception e) {
			getLogger().catching(e);
		}
	}

	private String getMsgDesc(String msgCode, String msgDesc) throws Exception {
		ResourceBundle bundle = EExtensionApiUtil.getBundleMessageAlert(ParameterConfig.getApplication().getApplicationLocale());
		if (bundle != null) {
			msgDesc = EExtensionApiUtil.getMessage(bundle, msgCode);
		}
		return msgDesc;
	}
	
	private void logging(CommonException exception) {
		try {
			String loggingCode = exception.getErrorCode() + "-" + getLoggingTime();
			exception.setLoggingCode(loggingCode);
			
			getLogger().error(loggingCode);
			getLogger().catching(exception);
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
	}
	
	private String getLoggingTime() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
		return LocalDateTime.now().format(format);
	}

	public Logger getLogger() {
		return DefaultLogUtil.FILTER;
	}
}