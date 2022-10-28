package com.sit.authen.ws;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.sit.abbra.abbraapi.common.EExtensionApiCommonWS;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.AccessTokenRequest;
import com.sit.abbra.abbraapi.core.security.login.domain.AuthenticateCriteria;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.security.login.domain.Token;
import com.sit.abbra.abbraapi.core.security.login.domain.UserProfile;
import com.sit.abbra.abbraapi.core.security.login.service.LoginManager;
import com.sit.abbra.abbraapi.enums.ActionType;
import com.sit.abbra.abbraapi.enums.ActiveStatus;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.abbra.abbraapi.util.response.ResponseMessageUtil;
import com.sit.abbra.abbraapi.util.security.EExtensionApiSecurityUtil;
import com.sit.abbra.abbraapi.util.security.SecurityUtilConfig;
import com.sit.authen.service.AuthenticationManager;
import com.sit.common.CommonResponse;
import com.sit.domain.GlobalVariable;
import com.sit.exception.AuthenticateException;
import com.sit.exception.AuthorizationException;

import util.cryptography.EncryptionUtil;
import util.json.JSONObjectMapperUtil;

@Path("/")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class AuthenticationWS extends EExtensionApiCommonWS {
	
	@GET
	@Path("/internal_authenticate")
	/**
	 * 2
	 * @param context
	 * @param request
	 * @param responseType
	 * @param clientId
	 * @param codeChallenge
	 * @param state
	 * @param redirectUrl
	 * @return
	 */
	public Response authenticateIn(@Context ContainerRequestContext context,
			@Context HttpServletRequest request, 
			@QueryParam("response_type") String responseType,
			@QueryParam("client_id") String clientId,
			@QueryParam("code_challenge") String codeChallenge,
			@QueryParam("state") String state,
			@QueryParam("redirect_url") String redirectUrl) {
		
		getLogger().debug("Call /internal_authenticate");
		getLogger().debug("response_type : {}", responseType);
		getLogger().debug("client_id : {} ", clientId);
		getLogger().debug("code_challenge : {}", codeChallenge);
		getLogger().debug("state : {}", state);
		getLogger().debug("redirect_url : {} ", redirectUrl);
				
		AuthenticateCriteria criteria = new AuthenticateCriteria();
		criteria.setClientId(clientId);
		criteria.setResponseType(responseType);
		criteria.setCodeChallenge(codeChallenge);
		criteria.setState(state);
		criteria.setRedirectUrl(redirectUrl);
		return authenticate(request, criteria);
	}
	
	private Response authenticate(HttpServletRequest request, AuthenticateCriteria criteria) {
		
		String clientId = criteria.getClientId();
		String redirectUrl = criteria.getRedirectUrl();
		String responseType = criteria.getResponseType();
		String codeChallenge = criteria.getCodeChallenge();
		String state = criteria.getState();
		
		Response response = null;
		SecurityUtilConfig config = null;
		try {		
			// create manager
			AuthenticationManager manager = new AuthenticationManager(getLogger());
			
			// Check validate
			if(clientId == null || clientId.isEmpty() 
				|| redirectUrl == null || redirectUrl.isEmpty()
				|| responseType == null || responseType.isEmpty()
				|| codeChallenge == null || codeChallenge.isEmpty()
				|| state == null || state.isEmpty()) {
				throw new AuthorizationException("Invalid input");
			}
			
			// Get config
			config = ParameterConfig.getSecurityUtilConfig();
			
			String authId = EExtensionApiSecurityUtil.genAuthId();
			
			// Call manage business manager
			manager.callAuthenticate(clientId, redirectUrl, responseType, codeChallenge, state, authId);
			getLogger().debug("Redirect URL: {}", redirectUrl);
			String url = EExtensionApiUtil.getRedirectLoginURLFromAPI(redirectUrl);
			url = url + request.getContextPath() + config.getLoginPage() + "?id="+authId;			
			getLogger().debug("   Login URL: {}", url);
			
			// Redirect to login page
			response = Response.seeOther(new URI(url)).build();
		} catch (Exception e) {
			response = getMessageUtil().manageException(null, e, ActionType.NO_LOGIN);
		}
		return response;
	}

	@POST
	@Path("/oauth/token")
	/**
	 *  ส่ง Auth Code + Code Verifier + redirect_uri
	 * @param request
	 * @param jsonRequest
	 * @return
	 * @throws Exception
	 */
	public Response getAccessToken(@Context HttpServletRequest request, String jsonRequest) throws Exception {
		getLogger().debug("Call /oauth/token");
						
		// Get Header user-agent
		String userAgent = request.getHeader(GlobalVariable.HEADER_USER_AGENT);
		getLogger().debug("Browser User Agents : {}", userAgent);

		Response response = null;
		UserProfile profile = null;
		
		Token token = new Token();
		try {			
			AccessTokenRequest req = (AccessTokenRequest) JSONObjectMapperUtil.convertJson2Object(jsonRequest, AccessTokenRequest.class);
			getLogger().debug("   grant_type: {}", req.getGrantType());
			getLogger().debug("    client_id: {}", req.getClientId());
			getLogger().debug("    auth_code: {}", req.getAuthCode());
			getLogger().debug("code_verifier: {}", req.getCodeVerifier());
			getLogger().debug(" redirect_url: {}", req.getRedirectUrl());
			
			// call manage business manager
			LoginManager manager = new LoginManager(getLogger());
			profile = manager.login(req, token, userAgent);
			
			// manage result
			response = createLoginSuccessResponse(token, profile);
		} catch (Exception e) {
			response = getMessageUtil().manageException(profile, e);
		}
		return response;
	}
	
	@GET
	@Path("/authenticate")
	/**
	 * 1
	 * @param context
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Response authenticate(@Context ContainerRequestContext context, @Context HttpServletRequest request) throws Exception {		
		getLogger().debug("Call /authenticate");
		
		Response response = null;
		try {		
			// create manager
			AuthenticationManager manager = new AuthenticationManager(getLogger());
			
			// Check had access token in cookie
			Token token = new Token();
			UserProfile profile = manager.checkAccessToken(context, token);
			if(profile != null) {
				// manage result
				response = createLoginSuccessResponse(token, profile);
			}
		} catch (AuthenticateException e) {
			getLogger().warn("Cann't check access token", e);
			response = getMessageUtil().manageResult(null);
		} catch (Exception e) {
			response = getMessageUtil().manageException(null, e, ActionType.NO_LOGIN);
		}
		return response;
	}
	
	/**
	 * create login success response
	 * 
	 * @param token:
	 * @param profile
	 * @param loginUser 
	 * @return
	 * @throws Exception
	 */
	private Response createLoginSuccessResponse(Token token, UserProfile profile) throws Exception {
		getLogger().debug("");
		Response response = null;
		SecurityUtilConfig securityConfig = ParameterConfig.getSecurityUtilConfig();
			
		Long searchExprire = securityConfig.getLoginExpire();
			
		// set login token to cookies
		int exp = searchExprire.intValue() * 60; // seconds
		NewCookie cookies = EExtensionApiSecurityUtil.createCookie(token.getLoginToken(), exp);
		getLogger().debug("MaxAge [{}]", cookies.getMaxAge());
		getLogger().debug("Expiry [{}]", cookies.getExpiry());
		getLogger().debug("Expiry [{}]", cookies.getPath());
								
		CommonResponse commonResponse = getMessageUtil().createSuccessResult(profile, ActionType.NO_LOGIN);

		String message = getMessageUtil().getMessage(commonResponse);
		response = Response.status(Response.Status.OK)
				.header("access-control-expose-headers", securityConfig.getCrossCheckKey())
				.header(securityConfig.getCrossCheckKey(), EExtensionApiUtil.encodeByteToHex(EncryptionUtil.encryptSHA512(token.getEncrypt())))
				.cookie(cookies)
				.entity(message)
				.build();
		
		return response;
	}
		
	@POST
	@Path("/api/authenticate/logout")
	public Response logout(@Context HttpServletRequest servletRequest, @Context HttpServletResponse servletResponse, String jsonReq) throws Exception {
		getLogger().debug("Call /api/authenticate/logout");

		LoginUser user = getLoginUser(servletRequest);
		ResponseMessageUtil messageUtil = getMessageUtil(user);
		
		Response response = null;
		try {
			// call manage business manager
			LoginManager manager = new LoginManager(getLogger());
			manager.logout(user);

			// set result 
			servletRequest.setAttribute(ParameterConfig.getSecurityUtilConfig().getReqKeyLogout(), ActiveStatus.ACTIVE.getValue());
			
			// manage result
			response = messageUtil.manageResult(null);
		} catch (Exception e) {
			response = messageUtil.manageException(null, e, ActionType.NO_LOGIN);			
		}
		return response;
	}
	
	@POST
	@Path("/api/authenticate/loadAll")
	public Response loadAllOperator(@Context HttpServletRequest request) {
		getLogger().debug("Call /api/authenticate/loadAll");
		
		Response response = null;
		UserProfile profile = null;
		try {
			
			// 1. Get User Login
			LoginUser loginUser = getLoginUser(request);

				
			//เก็บ login user
			profile = new UserProfile(loginUser);
			
			// manage result
			response = getMessageUtil().manageResult(profile);
			
		} catch (Exception e) {
			// call manage exception
			response =  getMessageUtil().manageException(profile, e);		
		}
		
		return response;
	}
	
}
