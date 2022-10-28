package com.sit.abbra.abbraapi.web.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginPayload;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.enums.ActiveStatus;
import com.sit.abbra.abbraapi.util.security.EExtensionApiSecurityUtil;
import com.sit.abbra.abbraapi.util.security.SecurityUtilConfig;

import util.log4j2.DefaultLogUtil;

@Provider
public class ResponseServerFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		String userName = "";
		String messageError = "";
		try {
			// Get system config
			SecurityUtilConfig config = ParameterConfig.getSecurityUtilConfig();

			LoginUser loginUser = (LoginUser) requestContext.getProperty(ParameterConfig.getSecurityUtilConfig().getReqKeyUser());
			Cookie reqCookie = requestContext.getCookies().get(ParameterConfig.getSecurityUtilConfig().getCookiesKeyAuth());

			if (loginUser != null && reqCookie != null) {
				getLogger().debug(loginUser.getUserId());
				getLogger().debug(loginUser.getUserName());
				userName = loginUser.getUserName();
				
				String[] skipFilter = { "api/authenticate/logout", "/api/reloadConfig", "images/favicon.ico" };

				boolean isContains = Arrays.stream(skipFilter).anyMatch(requestContext.getUriInfo().getPath()::equals);
				if (isContains) {
					getLogger().debug("SKIP: URI : {}", requestContext.getUriInfo().getPath());
					
					String isLogout = (String) requestContext.getProperty(ParameterConfig.getSecurityUtilConfig().getReqKeyLogout());
					if (isLogout != null && isLogout.equals(ActiveStatus.ACTIVE.getValue())) {
						Long expForCookies = 0L; // seconds
						
						NewCookie newCookie = EExtensionApiSecurityUtil.createCookie(null, expForCookies.intValue());
						responseContext.getHeaders().add("Set-Cookie", newCookie);
						
						requestContext.removeProperty(ParameterConfig.getSecurityUtilConfig().getReqKeyLogout());
						return;
						
					}
				}
				
				/** Update Expired **/
				LoginPayload newLoginPayload = new LoginPayload();
				newLoginPayload.setId(loginUser.getToken());
				
				Long searchExprire = EExtensionApiSecurityUtil.getSearchExpireMillis(config.getLoginExpire());
				int exp = searchExprire.intValue();	// milliseconds

				String newLoginToken = EExtensionApiSecurityUtil.genLoginToken(loginUser.getSecret(), newLoginPayload, exp);
				
				NewCookie newCookie = EExtensionApiSecurityUtil.createCookie(newLoginToken, (exp/1000));
				responseContext.getHeaders().add("Set-Cookie", newCookie);
				
			}

		} catch (Exception e) {
			getLogger().catching(e);
		} finally {

		}
	}

	public Logger getLogger() {
		return DefaultLogUtil.FILTER;
	}
}