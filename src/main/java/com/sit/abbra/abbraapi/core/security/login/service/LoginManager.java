package com.sit.abbra.abbraapi.core.security.login.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.log.service.LogManager;
import com.sit.abbra.abbraapi.core.security.login.domain.AccessTokenRequest;
import com.sit.abbra.abbraapi.core.security.login.domain.AuthorizeRequest;
import com.sit.abbra.abbraapi.core.security.login.domain.ClientSystem;
import com.sit.abbra.abbraapi.core.security.login.domain.Login;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginPayload;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUserRequest;
import com.sit.abbra.abbraapi.core.security.login.domain.PibicsAuthenModel;
import com.sit.abbra.abbraapi.core.security.login.domain.Token;
import com.sit.abbra.abbraapi.core.security.login.domain.UserProfile;
import com.sit.abbra.abbraapi.enums.AuthenGrantType;
import com.sit.abbra.abbraapi.enums.MessageAlert;
import com.sit.abbra.abbraapi.enums.Split;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.abbra.abbraapi.util.date.DateUtil;
import com.sit.abbra.abbraapi.util.security.EExtensionApiSecurityUtil;
import com.sit.abbra.abbraapi.util.security.SecurityUtilConfig;
import com.sit.abbra.abbraapi.util.validate.InputValidateUtil;
import com.sit.authen.service.AuthenticationManager;
import com.sit.core.common.service.CommonManager;
import com.sit.exception.AuthorizationException;
import com.sit.exception.CustomException;

import util.cryptography.DecryptionUtil;
import util.cryptography.EncryptionUtil;
import util.cryptography.enums.DecType;
import util.cryptography.enums.EncType;
import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.json.JSONObjectMapperUtil;

public class LoginManager extends CommonManager {

	private LoginService service = null;

	public LoginManager(Logger logger) {
		super(logger);
		this.service = new LoginService(logger);
	}
	
	/**
	 * Get AuthorizeRequest by key
	 * @implNote
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public AuthorizeRequest getAuthorizeByKey(String key) throws Exception {
		AuthorizeRequest authorizeRequest = null;
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			AuthenticationManager authManager = new AuthenticationManager(getLogger());
			authorizeRequest = authManager.searchAuthorizeByKey(conn, key);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return authorizeRequest;
	}

	/**
	 * ดึงข้อมูล Client System ด้วย client id
	 * @implNote
	 * @param conn
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	public ClientSystem getClientSystemByClientId(String clientId) throws Exception{
		ClientSystem client = null;
		CCTConnection conn = null;
		try {			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			client = service.searchClientSystem(conn, clientId);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return client;
	}
	
	/**
	 * Process override
	 * @implNote
	 * @param loginRequest
	 * @param authId
	 * @return
	 * @throws Exception
	 */
	public AuthorizeRequest overrideLogin(LoginUserRequest loginUserRequest) throws Exception{
		getLogger().debug("");
		
		String authId = loginUserRequest.getAuthId();
		AuthorizeRequest authorizeRequest = null;
		CCTConnection conn = null;
		try {			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			// Get authorize request from authorize map	
			AuthenticationManager authManager = new AuthenticationManager(getLogger());
			authorizeRequest = authManager.searchAuthorizeByKey(conn, authId);
			if(authorizeRequest == null) {
				getLogger().error("Not found key in authorize");
				throw new CustomException(MessageAlert.INVALID_USER_OR_PASS.getVal());
			}
			
			// Check Expire
			Date currentDate = DateUtil.getCurrentDateDB(conn.getConn());
			Date expire = authorizeRequest.getExpired();
			if(currentDate.compareTo(expire) > 0) {
				getLogger().error("Authorize expired when override.");
				throw new CustomException(MessageAlert.INVALID_USER_OR_PASS.getVal());				
			}
			
			String encryptData = authorizeRequest.getEncryptData();
			if(encryptData == null || encryptData.isEmpty()) {
				getLogger().error("Authorize not found encrypt data.");
				throw new CustomException(MessageAlert.INVALID_USER_OR_PASS.getVal());				
			}
				
			// Decrypt data
			String loginUserOverrideId = DecryptionUtil.doDecrypt(encryptData, authId, DecType.AES256);
			getLogger().debug("loginUserOverrideId: {}", loginUserOverrideId);

			// Get user by id
			LoginUser loginUser = service.getLoginUserByUserId(conn, loginUserOverrideId);
			
			// Process authen code
			new AuthenticationManager(getLogger()).processAuthenCode(conn, authId, authorizeRequest, loginUser.getUserId());
			
			insertLoginEventLog(loginUserRequest, loginUser);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return authorizeRequest;
	}
	
	/**
	 * Process Enter Login
	 * @implNote
	 * @param authId 
	 * @param loginUser
	 * @param token
	 * @return
	 */
	public AuthorizeRequest login(LoginUserRequest loginUserRequest, HttpServletRequest request) throws Exception{
		getLogger().debug("");
		
		AuthorizeRequest authorizeRequest = null;
		CCTConnection conn = null;
		try {			
						
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			// Get Current date
			String currentDateTime = DateUtil.getCurrentDateStr(conn.getConn(), ParameterConfig.getDateFormat().getDbFormat());
						
			// Validate Input Login
			validateInputLogin(loginUserRequest);

			// Get authorize request from authorize map		
			AuthenticationManager authManager = new AuthenticationManager(getLogger());
			authorizeRequest = authManager.searchAuthorizeByKey(conn, loginUserRequest.getAuthId());
			if(authorizeRequest == null) {
				getLogger().error("Not found key in authorize");
				throw new CustomException(MessageAlert.INVALID_USER_OR_PASS.getVal());
			}
			
			// Check Expire
			Date currentDate = DateUtil.getCurrentDateDB(conn.getConn());
			Date expire = authorizeRequest.getExpired();
			if(currentDate.compareTo(expire) > 0) {
				getLogger().error("Authorize expired.");
				throw new CustomException(MessageAlert.INVALID_USER_OR_PASS.getVal());				
			}
			
			// Business
			LoginUser loginUser = checkLogin(conn, loginUserRequest, currentDateTime);
			if(loginUser == null) {
				getLogger().error("Not found user in DB");
				throw new CustomException(MessageAlert.INVALID_USER_OR_PASS.getVal());
			}
			
			// Check status Authenticate 
			checkStatusAuthen(conn, loginUser, loginUserRequest, authorizeRequest);
			
			// Process authen code
			new AuthenticationManager(getLogger()).processAuthenCode(conn, loginUserRequest.getAuthId(), authorizeRequest, loginUser.getUserId());
			
			//insertLoginEventLog(loginUserRequest, loginUser);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return authorizeRequest;
	}
	
	/**
	 * CLEAR_WRONG_LOGIN_SQL
	 * PDP-3 ตรวจสอบ Login ซ้ำ
	 * @implNote
	 * @param conn
	 * @param loginUser
	 * @param loginUserRequest
	 * @param authorizeRequest
	 * @param currentDateTime
	 * @throws Exception
	 */
	private void checkStatusAuthen(CCTConnection conn, LoginUser loginUser, LoginUserRequest loginUserRequest, AuthorizeRequest authorizeRequest) throws Exception {
		// PDP-7 ตรวจสอบ Login ซ้ำ 
		chkLoginDuplicate(conn, loginUser, loginUserRequest, authorizeRequest);
	}
	
	/**
	 * Validate Input Login
	 * @implNote
	 * @param loginUser
	 * @throws CustomException
	 */
	private void validateInputLogin(LoginUserRequest loginUser) throws CustomException {
		long usernameLength = 20;
		long passwordLength = 100;
		if (!InputValidateUtil.hasValue(loginUser)) {
			getLogger().error("LoginUser is Null");
			throw new CustomException(MessageAlert.INSUFFICIENT_DATA.getVal());
		}

		if (!InputValidateUtil.hasValue(loginUser.getUsername())) {
			getLogger().error("Username is Null");
			throw new CustomException(MessageAlert.INSUFFICIENT_DATA.getVal());
		}
		
		if (usernameLength < loginUser.getUsername().length()) {
			getLogger().error("Username length > {}", usernameLength);
			throw new CustomException(MessageAlert.INVALID_DATA.getVal());
		}

		if (!InputValidateUtil.hasValue(loginUser.getPassword())) {
			getLogger().error("Password is Null");
			throw new CustomException(MessageAlert.INSUFFICIENT_DATA.getVal());
		}
		
		if (passwordLength < loginUser.getPassword().length()) {
			getLogger().error("Password length > {}", passwordLength);
			throw new CustomException(MessageAlert.INVALID_DATA.getVal());
		}
	}
	
	/**
	 * Login PB
	 * @implNote
	 * @param conn
	 * @param loginUserRequest
	 * @param configSystem
	 * @param loginUser
	 * @param request
	 * @throws Exception
	 */
	private LoginUser checkLogin(CCTConnection conn, LoginUserRequest loginUserRequest, String currentDateTime) throws Exception {
		LoginUser loginUser = null;
		/*
		LoginUser loginUser = new LoginUser();
		String token = TokenUtil.getToken(conn, getLogger(), true);
		
		// Generate Login : Object
		LoginAuthModel loginAuthModel = new LoginAuthModel();
		loginAuthModel.setUserInput(loginUserRequest.getUsername());
		loginAuthModel.setPasswordInput(loginUserRequest.getPassword());
					
		// Check_Config_AuthenPB_SQL (ยกเลิก)
		loginAuthModel.setCheckPibicsAuthen("N");
				
		// Generate Login : Json
		String loginJson = JsonUtil.convertObject2Json(loginAuthModel);
		
		boolean chkExpire = true;
		while(chkExpire) {
			// Create Header: Authorization
			Map<String, String> headerPBMap = new HashMap<>();
			//Setting "Bearer " + token
			headerPBMap.put("Authorization", "Bearer " + token);
			
			getLogger().debug("-> check-auth pibics");
			try{
				chkExpire = false;
				CommonResponse respLogin = service.callAuthPBWS(ParameterConfig.getLoginWebService().getEndPointAuth(), loginJson, headerPBMap);
				
				// Check Response : LoginUser with PB.
				pbAuth = service.checkReponseLoginUser(respLogin, loginAuthModel.getCheckPibicsAuthen());
			}catch(TokenExpireException e) {
				chkExpire = true;
				token = TokenUtil.getToken(conn, getLogger(), false);
			}
		}
		 */
		
		String temp = EncryptionUtil.doEncrypt(loginUserRequest.getPassword(), EncType.SHA256);
		PibicsAuthenModel pbAuth = service.searchUserDB(conn, loginUserRequest.getUsername(), temp);
		if(pbAuth != null) {
			loginUser = new LoginUser();
			
			// Update User
			service.updateUserOfficer(conn, pbAuth);
			loginUser.setUserId(pbAuth.getUserId());
			
			// Search User Profile
			//String flagChangeLog = service.searchPopupChangeLog(conn, loginUser.getUserId());
			
			// ยังไม่ได้ change_log?
			//if(flagChangeLog.equals("N")){ 
				// update change_log
				//service.updatePopupChangeLog(conn, loginUser.getUserId());
			//}
						
		}
		
		return loginUser;
	}

	/**
	 * PDP-3 ตรวจสอบ Login ซ้ำ
	 * @implNote
	 * @param conn
	 * @param loginUser
	 * @throws Exception
	 * @throws CustomException
	 */
	private void chkLoginDuplicate(CCTConnection conn, LoginUser loginUser, LoginUserRequest loginRequest, AuthorizeRequest authorizeRequest) throws Exception {
		long loginDup = service.searchLoginDup(conn, loginUser.getUserId());
		getLogger().debug("loginDup: {}, isOverride: {}", loginDup, loginRequest.isOverride());
		if (loginDup > 0 && !loginRequest.isOverride()) {
			// ซ้ำ แต่มี Override มา
			String logoutTime = service.searchLoginDupCheckLogouDateTime(conn, loginUser.getUserId());
			String curDate = DateUtil.getCurrentDateStr(conn.getConn(), ParameterConfig.getDateFormat().getDbFormat());
			getLogger().debug("curDate: {} >, logoutTime: {}, ", curDate, logoutTime);
			if (!logoutTime.isEmpty() && EExtensionApiUtil.convertLongValue(curDate) <= EExtensionApiUtil.convertLongValue(logoutTime)) {
			
				// ถ้า logout ไปแล้วจะถือว่าไม่ซ้ำ
				List<String> listData = new ArrayList<>();
				listData.add(loginUser.getUserId());
				
				String data = StringUtils.join(listData, Split.PIPE.getValue());
				getLogger().warn("Duplicate login data [{}]", data);
				
				String encryptData = EncryptionUtil.doEncrypt(data, authorizeRequest.getKey(), EncType.AES256); 
				authorizeRequest.setEncryptData(encryptData);
				
				AuthenticationManager authManager = new AuthenticationManager(getLogger());
				authManager.addAuthorize(conn, authorizeRequest);					
				
				// แจ้ง Alert No. [40003] : มีการใช้งานรหัสผู้ใช้ นี้อยู่ ต้องการ Override สิทธิ์ หรือไม่?
				throw new CustomException(MessageAlert.OVERRIDE.getVal());
			}
		}
	}
		
		
	/**
	 * Process login with access code
	 * @implNote
	 * @param accessToken
	 * @param token
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public UserProfile login(AccessTokenRequest accessToken, Token token, String agentName) throws Exception {
		UserProfile userProfile = null;
		CCTConnection conn = null;
		try {			
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			String currentDateTime = DateUtil.getCurrentDateStr(conn.getConn(), ParameterConfig.getDateFormat().getDbFormat());

			// Check validate
			validateAccessTokenRequest(conn, accessToken);
						
			// Get Authorize request from oauth map
			AuthenticationManager authManager = new AuthenticationManager(getLogger());
			AuthorizeRequest authorizeRequest = authManager.searchAuthorizeByKey(conn, accessToken.getAuthCode());
			if(authorizeRequest == null) {
				getLogger().error("Not found key in authen code");
				throw new CustomException(MessageAlert.INSUFFICIENT_DATA.getVal());
			} else {
				authManager.deleteAuthorizeByKey(conn, accessToken.getAuthCode());
			}

			// Check Expire
			Date currentDate = DateUtil.getCurrentDateDB(conn.getConn());
			Date resetExpire = authorizeRequest.getExpired();
			if(currentDate.compareTo(resetExpire) > 0) {
				getLogger().error("Authorize expired.");
				throw new CustomException("10002");				
			}

			// 4. Check code challenge vs code verifier
			//String checkCode = EExtensionApiUtil.encodeByteToHex(EncryptionUtil.encryptSHA512(accessToken.getCodeVerifier()));
			String checkCode = EExtensionApiUtil.encodeByteToHex(accessToken.getCodeVerifier().getBytes(StandardCharsets.UTF_8));
			getLogger().debug("    CheckCode: {}" , checkCode);
			getLogger().debug("CodeChallenge: {}" , authorizeRequest.getCodeChallenge());
			if(!authorizeRequest.getCodeChallenge().equals(checkCode)) {
				getLogger().error("Invalid check code");
				throw new CustomException("10002");				
			}
			
			// Get UserId
			String decryptData = DecryptionUtil.doDecrypt(authorizeRequest.getEncryptData(), accessToken.getAuthCode(), DecType.AES256);
			String userId = decryptData;
			
			// Get loginUser by Id
			LoginUser loginUser =  service.getLoginUserByUserId(conn, userId);
			userProfile = new UserProfile(loginUser);
			
			// จัดการส่วน Token
			secureLoginData(token, userId, loginUser.getSalt());
			
			String loginDatetime = currentDateTime;
			String logoutDatetime = service.generateLogoutDateTime(currentDateTime); 
			String actionDatetime = currentDateTime;
			
			getLogger().info("Login: {}, Logout: {}, Action: {}", loginDatetime, logoutDatetime, actionDatetime);
			service.updateLastLogin(conn, loginUser, token, agentName, loginDatetime, logoutDatetime, actionDatetime);
			
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return userProfile;
	}

	/**
	 * Secure Login Data
	 * @implNote
	 * @param token
	 * @param userId
	 * @param salt
	 * @throws Exception
	 */
	private void secureLoginData(Token token, String userId, String salt) throws Exception {
		// Gen Secret
		String secret = EExtensionApiSecurityUtil.genSecret();
		
		//นำ Secret กับ Salt มาสร้าง TextEncryptor > นำ TextEncryptor มา Encrypt LoginId
		String userIdEnc = EExtensionApiSecurityUtil.encryptId(salt, secret, userId);
		
		LoginPayload payload = new LoginPayload();
		payload.setId(userIdEnc);
		
		token.setEncrypt(userIdEnc);
		token.setSecret(secret);
		token.setSalt(salt);
		
		long loginExpire = EExtensionApiSecurityUtil.getLoginExpireMillis(ParameterConfig.getSecurityUtilConfig().getLoginExpire());
		token.setLoginToken(EExtensionApiSecurityUtil.genLoginToken(secret, payload, loginExpire));

	}
	
	/**
	 * validateAccessTokenRequest
	 * @implNote
	 * @param conn
	 * @param accessToken
	 * @throws Exception
	 */
	private void validateAccessTokenRequest(CCTConnection conn, AccessTokenRequest accessToken) throws Exception {
		String clientId = accessToken.getClientId();
		String redirectUrl = accessToken.getRedirectUrl();
		String grantType = accessToken.getGrantType();
		String codeVerifier = accessToken.getCodeVerifier();
		String authCode = accessToken.getAuthCode();
		
		if(clientId == null || clientId.isEmpty() 
			|| redirectUrl == null || redirectUrl.isEmpty()
			|| grantType == null || grantType.isEmpty()
			|| codeVerifier == null || codeVerifier.isEmpty()
			|| authCode == null || authCode.isEmpty()) {
			throw new AuthorizationException("Invalid input");
		}

		// get client system 
		ClientSystem clientSystem = service.searchClientSystem(conn, clientId); 
		
		// 1. ตรวจสอบว่ามี Client Id นี้ในฐานข้อมูล
		if(clientSystem == null) {
			throw new AuthorizationException("No register.");
		}
		
		// 2. ตรวจสอบ redirect url ว่าตรงกับที่ลงทะเบียนไว้หรือไม่
		if(!redirectUrl.equals(clientSystem.getRedirectUrl())) {
			throw new AuthorizationException("Wrong redirect url.");
		}
		
		// 3. ดึง grant type มาตรวจสอบ response_type
		AuthenGrantType authenGrantType = AuthenGrantType.valueOf(clientSystem.getGrantType().toUpperCase());
		if(authenGrantType == null || !grantType.equalsIgnoreCase(authenGrantType.getGrantType())) {
			throw new AuthorizationException("Wrong grant type.");
		}
	}
	
	/**
	 * Request filter by access token
	 * @implNote
	 * @param conn
	 * @param util
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public LoginUser filterAccessToken(CCTConnection conn, ContainerRequestContext context) throws Exception {
		// check access token
		LoginUser user = checkAccessToken(conn, context);
		if (!context.getUriInfo().getPath().startsWith("api/authenticate/")) {
			// Get Security Config
			SecurityUtilConfig securityConfig = ParameterConfig.getSecurityUtilConfig();

			// Check cross check key
			String crossCheckKey = context.getHeaderString(securityConfig.getCrossCheckKey());
			
			String checkKey = EExtensionApiUtil.encodeByteToHex(EncryptionUtil.encryptSHA512(user.getToken()));
			if(!checkKey.equals(crossCheckKey)) {
				throw new CustomException("Invalid Authorization key");
			}				
		}
		return user;
	}
	
	/**
	 * Check access token
	 * @implNote
	 * @param conn
	 * @param util
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public LoginUser checkAccessToken(CCTConnection conn, ContainerRequestContext context) throws Exception {
		LoginUser user = null;		
		// Get access token
		String accessToken = EExtensionApiSecurityUtil.getLoginToken(context);
		getLogger().debug(accessToken);
		
		// Check and Get user login by access token
		AuthenticationManager authManager = new AuthenticationManager(getLogger());
		user = authManager.getLoginUserByAccessToken(conn, accessToken);
		
		getLogger().debug("userId(Encrypted): {}", user.getToken());
		getLogger().debug("userId: {}", user.getUserId());
		return user;
	}

	/**
	 * Logout
	 * @implNote
	 * @param user
	 * @throws Exception
	 */
	public void logout(LoginUser user) throws Exception{
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			insertLogoutEventLog(user);
			service.logout(conn, user.getUserId());
			
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}

	/**
	 * updateLogoutTime
	 * @implNote
	 * @param conn
	 * @param loginUser
	 * @param currentDateTime
	 * @throws Exception
	 */
	public void updateLogoutTime(CCTConnection conn, LoginUser loginUser, String currentDateTime) throws Exception {
		if (loginUser != null) {
			service.updateLogoutTime(conn, loginUser, currentDateTime);
		}
	}
	
	/**
	 * บันทึก Event log เมื่อ login
	 * @implNote
	 * @param loginUserRequest
	 * @param loginUser
	 */
	private void insertLoginEventLog(LoginUserRequest loginUserRequest, LoginUser loginUser) {
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			if (loginUserRequest.isOverride()) {
				loginUserRequest.setUsername(loginUser.getUserName().toLowerCase());
			}
			
			String operatorId = "10100290";
			String pathInfo = "signup";
			String logData = JSONObjectMapperUtil.convertObject2Json(loginUserRequest);
			
			LogManager logManger = new LogManager(getLogger());
			logManger.insertEvent(conn, operatorId, pathInfo, logData, loginUser);
		} catch (Exception e) {
			getLogger().catching(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	/**
	 * บันทึก Event log เมื่อ logout
	 * @implNote
	 * @param loginUser
	 */
	private void insertLogoutEventLog(LoginUser loginUser) {
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			String operatorId = "10100291";
			String pathInfo = "logout";
			loginUser.setUserName(loginUser.getUserName().toLowerCase());
			String logData = JSONObjectMapperUtil.convertObject2Json(loginUser);
			
			LogManager logManger = new LogManager(getLogger());
			logManger.insertEvent(conn, operatorId, pathInfo, logData, loginUser);
		} catch (Exception e) {
			getLogger().catching(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	
	/**
	 * Get Login by token
	 * @implNote
	 * @param conn
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public Login getLoginByToken(CCTConnection conn, String token) throws Exception {
		return service.getLoginByToken(conn, token);
	}
	
	/**
	 * Get Login by userId
	 * @implNote
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public LoginUser getLoginUserByUserId(CCTConnection conn, String userId) throws Exception {
		return service.getLoginUserByUserId(conn, userId);
	}
}
