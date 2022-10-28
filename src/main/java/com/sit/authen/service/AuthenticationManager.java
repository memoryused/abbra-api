package com.sit.authen.service;

import java.util.Calendar;
import java.util.Locale;

import javax.ws.rs.container.ContainerRequestContext;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.AuthorizeRequest;
import com.sit.abbra.abbraapi.core.security.login.domain.ClientSystem;
import com.sit.abbra.abbraapi.core.security.login.domain.Login;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginPayload;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.security.login.domain.Token;
import com.sit.abbra.abbraapi.core.security.login.domain.UserProfile;
import com.sit.abbra.abbraapi.core.security.login.service.LoginManager;
import com.sit.abbra.abbraapi.enums.AuthenGrantType;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.abbra.abbraapi.util.date.DateUtil;
import com.sit.abbra.abbraapi.util.security.EExtensionApiSecurityUtil;
import com.sit.abbra.abbraapi.util.security.SecurityUtilConfig;
import com.sit.core.common.service.CommonManager;
import com.sit.exception.AuthenticateException;
import com.sit.exception.AuthorizationException;

import util.cryptography.EncryptionUtil;
import util.cryptography.enums.EncType;
import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;

public class AuthenticationManager  extends CommonManager{

	private AuthenticationService service;
	
	public AuthenticationManager(Logger logger) {
		super(logger);
		this.service = new AuthenticationService(logger);
	}
	
	/**
	 * checkAccessToken
	 * @implNote
	 * @param context
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public UserProfile checkAccessToken(ContainerRequestContext context, Token token) throws Exception {
		getLogger().debug("");
		
		UserProfile userProfile = null;
		CCTConnection conn = null;
		try {
			// Get ConfigSystem
			SecurityUtilConfig config = ParameterConfig.getSecurityUtilConfig();
						
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			// Get access token
			String accessToken = EExtensionApiSecurityUtil.getLoginToken(context);
			getLogger().debug(accessToken);
			
			LoginUser user = getLoginUserByAccessToken(conn, accessToken);
			userProfile = new UserProfile(user);

			// จัดการส่วน Token
			LoginPayload payload = new LoginPayload();
			payload.setId(user.getToken());
			
			long loginExpire = EExtensionApiSecurityUtil.getLoginExpireMillis(config.getLoginExpire());
			token.setLoginToken(EExtensionApiSecurityUtil.genLoginToken(user.getSecret(), payload, loginExpire));
			
			token.setEncrypt(user.getToken());
			token.setSecret(user.getSecret());
			token.setSalt(user.getSalt());
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return userProfile;
	}
	
	/**
	 * Client call authenticate 
	 * @implNote
	 * @param clientId
	 * @param redirectUrl
	 * @param responseType
	 * @param codeChallenge
	 * @param state
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	public void callAuthenticate(String clientId, String redirectUrl, String responseType
			, String codeChallenge, String state, String authId) throws Exception {
				
		CCTConnection conn = null;
		try {			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			String currentDateTime = DateUtil.getCurrentDateStr(conn.getConn(), ParameterConfig.getDateFormat().getDbFormat());
			
			// จัดการ Clear Data ในตาราง Authorize ที่ Expire
			service.deleteExpireAuthorize(conn, currentDateTime);
								
			// get client system 
			ClientSystem clientSystem = service.searchClientSystem(conn, clientId);
			
			// Check request parameter
			validateAuthenticateParameter(clientSystem, redirectUrl, responseType);
							
			// create authorize request
			SecurityUtilConfig config = ParameterConfig.getSecurityUtilConfig();
			Calendar expired = Calendar.getInstance(Locale.ENGLISH);
			expired.setTime(DateUtil.getCurrentDateDB(conn.getConn()));
			expired.add(Calendar.MINUTE, (int)config.getAuthenExpire());		
			
			AuthorizeRequest authorizeRequest = new AuthorizeRequest(clientSystem);
			authorizeRequest.setCodeChallenge(codeChallenge);
			authorizeRequest.setState(state);
			authorizeRequest.setKey(authId);
			authorizeRequest.setExpired(expired.getTime());
			authorizeRequest.setClientId(clientSystem.getClientId());
			// Keep to DB
			service.addAuthorize(conn, authorizeRequest);
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}

	/**
	 * validateAuthenticateParameter
	 * @implNote
	 * @param clientSystem
	 * @param redirectUrl
	 * @param responseType
	 * @throws AuthorizationException
	 */
	private void validateAuthenticateParameter(ClientSystem clientSystem, String redirectUrl, String responseType) throws AuthorizationException {
		// 1. ตรวจสอบว่ามี Client Id นี้ในฐานข้อมูล
		if(clientSystem == null) {
			throw new AuthorizationException("No register.");
		}
		
		// 2. ตรวจสอบ redirect url ว่าตรงกับที่ลงทะเบียนไว้หรือไม่
		if(!redirectUrl.equals(clientSystem.getRedirectUrl())) {
			throw new AuthorizationException("Wrong redirect url.");
		}
		
		// 3. ดึง grant type มาตรวจสอบ response_type
		AuthenGrantType grantType = AuthenGrantType.valueOf(clientSystem.getGrantType().toUpperCase());
		if(grantType == null || grantType.getResponseType() == null || !responseType.equals(grantType.getResponseType())) {
			throw new AuthorizationException("Wrong response type.");
		}
	}
	
	/**
	 * searchAuthorizeByKey
	 * @implNote
	 * @param conn
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public AuthorizeRequest searchAuthorizeByKey(CCTConnection conn, String key) throws Exception {
		return service.searchAuthorizeByKey(conn, key);
	}
	
	/**
	 * Insert/Update Authorization request
	 * @implNote
	 * @param conn
	 * @param authorizeRequest
	 * @throws Exception
	 */
	public void addAuthorize(CCTConnection conn, AuthorizeRequest authorizeRequest) throws Exception {
		service.addAuthorize(conn, authorizeRequest);
	}
		
	/**
	 * Process Authen Code
	 * @implNote
	 * @param conn
	 * @param authId
	 * @param authorizeRequest
	 * @param userId
	 * @throws Exception
	 */
	public void processAuthenCode(CCTConnection conn, String authId, AuthorizeRequest authorizeRequest, String userId) throws Exception {
		// Generate authen code
		String authenCode = EExtensionApiSecurityUtil.genAuthenticateCode();
		authorizeRequest.setKey(authenCode);
		getLogger().debug(" authenCode: {}", authorizeRequest.getKey());
		getLogger().debug("     userId: {}", userId);
		String encryptData = EncryptionUtil.doEncrypt(userId, authenCode, EncType.AES256);
		authorizeRequest.setEncryptData(encryptData);
		getLogger().debug("encryptData: {}", authorizeRequest.getEncryptData());
					
		// Insert into DB
		service.addAuthorize(conn, authorizeRequest);
		
		// Remove from old Map
		service.deleteAuthorizeByKey(conn, authId);
	}
	
	/**
	 * deleteAuthorizeByKey
	 * @implNote
	 * @param conn
	 * @param key
	 * @throws Exception
	 */
	public void deleteAuthorizeByKey(CCTConnection conn, String key) throws Exception {
		service.deleteAuthorizeByKey(conn, key);
	}
	
	/**
	 * getLoginUserByAccessToken
	 * @implNote
	 * @param conn
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public LoginUser getLoginUserByAccessToken(CCTConnection conn, String accessToken) throws Exception {		
		LoginUser user = null;
		
		// Get login payload from login token (JWT) 
		LoginPayload loginPayload = EExtensionApiSecurityUtil.getLoginPayloadFromToken(accessToken);
		getLogger().debug("         ID: {}", loginPayload.getId());
		getLogger().debug("        Exp: {}", loginPayload.getExp());

		// validate token (verify & check expired)
		String userIdEncrypted = loginPayload.getId();
		LoginManager loginManager = new LoginManager(getLogger());
		Login login = loginManager.getLoginByToken(conn, userIdEncrypted);		
		boolean isValid = EExtensionApiSecurityUtil.validateLoginToken(login, accessToken);
		getLogger().debug("Token Valid: {}", isValid);
		if (!isValid) {
			throw new AuthenticateException("Invalid Token!");
		}
					
		// search SEC_LOGIN by userId encrypted
		String userId = EExtensionApiSecurityUtil.decryptId(login.getSalt(), login.getSecret(), userIdEncrypted);
		
		user = loginManager.getLoginUserByUserId(conn, userId);
		user.setToken(userIdEncrypted);
		user.setSecret(login.getSecret());
		return user;
	}
}
