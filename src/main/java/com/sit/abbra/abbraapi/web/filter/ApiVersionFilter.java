package com.sit.abbra.abbraapi.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.common.CommonError;
import com.sit.common.CommonResponse;
import com.sit.domain.ExceptionVariable;
import com.sit.exception.DeprecatedVersionException;

import util.log4j2.DefaultLogUtil;

public class ApiVersionFilter implements Filter {
	
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		HttpServletResponse httpResponse = ((HttpServletResponse) response);
		
		
		getLogger().debug("ContextPath: {}", httpRequest.getContextPath());
		getLogger().debug("     METHOD: {}", httpRequest.getMethod());
		getLogger().debug("        URI: {}", httpRequest.getRequestURI());

		if (httpRequest.getRequestURI().equalsIgnoreCase(httpRequest.getContextPath() + "/botdetectcaptcha")
				|| httpRequest.getRequestURI().equalsIgnoreCase(httpRequest.getContextPath() + "/signup")
				|| httpRequest.getRequestURI().equalsIgnoreCase(httpRequest.getContextPath() + "/reset_password")
				|| httpRequest.getRequestURI().equalsIgnoreCase(httpRequest.getContextPath() + "/forgot_password")
				|| httpRequest.getRequestURI().equalsIgnoreCase(httpRequest.getContextPath() + "/globalDataServlet")
				|| httpRequest.getRequestURI().equalsIgnoreCase(httpRequest.getContextPath() + "/login.jsp")
				|| httpRequest.getRequestURI().equalsIgnoreCase(httpRequest.getContextPath() + "/errorPage.html")
				|| httpRequest.getRequestURI().equalsIgnoreCase(httpRequest.getContextPath() + "/index.html")
				|| httpRequest.getRequestURI().equalsIgnoreCase(httpRequest.getContextPath() + "/images/favicon.ico")
				) {
			chain.doFilter(request, response);
			return;
		}

		if (!checkPatternUri(httpRequest)) {
			chain.doFilter(request, response);
		} else {
			if (checkVersionProvide(httpRequest, httpResponse)) {
				chain.doFilter(request, response);
			}
		}
	}

	private boolean checkPatternUri(HttpServletRequest httpRequest) {
		boolean isPattern = false;

		try {
			getLogger().debug("URI : {}", httpRequest.getRequestURI());

			String[] versionProvide = httpRequest.getRequestURI().split("/");
			if (versionProvide[3].indexOf("v") == 0) {
				isPattern = true;
			}
		} catch (Exception e) {
			getLogger().catching(e);
		}
		
		return isPattern;
	}

	private boolean checkVersionProvide(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		boolean isProvide = false;

		try {
			getLogger().debug("URI : {}", httpRequest.getRequestURI());

			String[] versionProvide = httpRequest.getRequestURI().split("/");
			isProvide = Arrays.stream(ParameterConfig.getApplication().getVersionProvide()).anyMatch(versionProvide[3]::equals);
			if (!isProvide) {
				// Abort
				deprecatedVersionResponse(httpResponse);
			}
		} catch (Exception e) {
			getLogger().catching(e);
		}
		
		return isProvide;
	}

	private void deprecatedVersionResponse(HttpServletResponse httpResponse) {
		try {
			DeprecatedVersionException ex = new DeprecatedVersionException();

			String msgCode = ex.getErrorCode().replace(ExceptionVariable.EXCEPTION_CODE_PREFIX, ExceptionVariable.EXCEPTION_CODE_REPLACE);
			String msgDesc = ex.getErrorDesc();

			ResourceBundle bundle = EExtensionApiUtil.getBundleMessageAlert(ParameterConfig.getApplication().getApplicationLocale());
			if (bundle != null) {
				msgDesc = EExtensionApiUtil.getMessage(bundle, msgCode);
			}

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

			httpResponse.setContentType("application/json");
			httpResponse.setStatus(HttpServletResponse.SC_OK);
			httpResponse.setCharacterEncoding("UTF-8");

			PrintWriter out = httpResponse.getWriter();
			out.print(json);
			out.flush();

		} catch (Exception e) {
			getLogger().catching(e);
		}
	}

	public Logger getLogger() {
		return DefaultLogUtil.FILTER;
	}

	@Override
	public void destroy() {
		getLogger().info("");
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		getLogger().info("");
	}

}
