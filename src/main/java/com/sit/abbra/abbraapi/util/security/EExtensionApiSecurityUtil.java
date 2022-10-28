package com.sit.abbra.abbraapi.util.security;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.encrypt.BouncyCastleAesCbcBytesEncryptor;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.Login;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginPayload;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.enums.Split;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.abbra.abbraapi.util.validate.InputValidateUtil;
import com.sit.abbra.abbraapi.web.filter.CorsFilter;
import com.sit.domain.Payload;
import com.sit.exception.AuthenticateException;
import com.sit.exception.ServerValidateException;

import util.cryptography.DecryptionUtil;
import util.cryptography.EncryptionUtil;
import util.cryptography.enums.DecType;
import util.cryptography.enums.EncType;
import util.json.JSONObjectMapperUtil;
import util.log4j2.DefaultLogUtil;

public class EExtensionApiSecurityUtil {
	
	private static final String KEYTOOL = "$2a$08$";
	
	public EExtensionApiSecurityUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static Logger getLogger() {
		return DefaultLogUtil.UTIL;
	}
	
	private static SecurityUtilConfig getSecurityUtilConfig() {
		return ParameterConfig.getSecurityUtilConfig();
	}
	
	/**
	 * Get LoginToken from {@link ContainerRequestContext}
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getLoginToken(ContainerRequestContext context) throws AuthenticateException {
		Cookie jwtCookie = getJwtCookie(context);
		return getTokenFromCookie(jwtCookie);
	}
	
	/**
	 * Get LoginPayload from Token and search salt & secret by userIdEncrypted (no verify)
	 * 
	 * @param loginToken
	 * @return
	 * @throws Exception
	 */
	public static LoginPayload getLoginPayloadFromToken(String loginToken) throws Exception {
		LoginPayload loginPayload = null;
		
		if(loginToken != null && !loginToken.isEmpty()) {
			JWT jwt = JWTParser.parse(loginToken);
			String content = jwt.getJWTClaimsSet().toString();		
			loginPayload = (LoginPayload) JSONObjectMapperUtil.convertJson2Object(content, LoginPayload.class);
		}
		return loginPayload;
	}	

	/**
	 * Verify login token
	 * 
	 * @param conn
	 * @param token
	 * @param userId (Encrypted)
	 * @return
	 * @throws Exception
	 */
	public static boolean validateLoginToken(Login login, String token) {
		boolean isValid = false;
		// search secret by userId (encrypted)
		String secret = "";
		if (login != null) {
			secret = login.getSecret();
		}
		getLogger().debug("Secret: {}", secret);

		// verify token
		if (secret != null && !secret.isEmpty()) {
			boolean isTrust = verifyToken(secret, token);
			getLogger().debug("verifyToken: {}", isTrust);
			if (isTrust) {
				// validate token expire
				isValid = checkJwtExpired(token, LoginPayload.class);
			}
		}
		return isValid;
	}
		
	/**
	 * Create JWT
	 * 
	 * @param content
	 * @param secret
	 * @return
	 * @throws JOSEException 
	 * @throws ParseException 
	 * @throws Exception
	 */
	private static JWT createJwt(String secret, String content) throws JOSEException, ParseException {
		JWT jwt = null;
		if (secret != null && !secret.isEmpty() && content != null && !content.isEmpty()) {
			// create jwt
			MACSigner signer = new MACSigner(secret);
			JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new com.nimbusds.jose.Payload(content));

			// Apply the HMAC
			jwsObject.sign(signer);

			// To serialize to compact form, produces something like
			// eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
			String s = jwsObject.serialize();

			jwt = JWTParser.parse(s);
		}
		return jwt;
	}
	
	/**
	 * Get {@link Cookie} from {@link ContainerRequestContext}
	 * 
	 * @param request
	 * @return
	 * @throws AuthenticateException
	 */
	private static Cookie getJwtCookie(ContainerRequestContext context) throws AuthenticateException {
		Cookie jwtCookie = context.getCookies().get(ParameterConfig.getSecurityUtilConfig().getCookiesKeyAuth());
		if (jwtCookie == null) {
			throw new AuthenticateException("Invalid Token JWT is null");
		}

		// check empty cookie
		if (jwtCookie.getValue() == null || jwtCookie.getValue().trim().isEmpty()) {
			throw new AuthenticateException("Invalid Token");
		}
		return jwtCookie;
	}

	/**
	 * Get JWT Token from Cookies
	 * 
	 * @param jwtCookie
	 * @return
	 * @throws AuthenticateException
	 */
	public static String getTokenFromCookie(Cookie jwtCookie) throws AuthenticateException {
		String token = jwtCookie.getValue();
		if (token == null || token.isEmpty()) {
			throw new AuthenticateException("Invalid Token");
		}
		return token;
	}
	
	/**
	 * verify JWT token
	 * 
	 * @param secret
	 * @param token
	 * @return
	 */
	private static boolean verifyToken(String secret, String token) {
		boolean isTrust = false;
		try {
			JWSObject jwsObject = JWSObject.parse(token);
			JWSVerifier verifier = new MACVerifier(secret);
			isTrust = jwsObject.verify(verifier);
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return isTrust;
	}
	
	/**
	 * validate JWT Token expired (Login Token, Action Token และ Hidden Token)
	 * 
	 * @param token
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	private static boolean checkJwtExpired(String token, Class<?> clazz) {
		boolean isValid = false;
		try {
			JWT jwt = JWTParser.parse(token);
			Payload payload = (Payload) JSONObjectMapperUtil.convertJson2Object(jwt.getJWTClaimsSet().toString(), clazz);

			Calendar calendar = Calendar.getInstance(ParameterConfig.getApplication().getApplicationLocale());			
			long current = calendar.getTimeInMillis();
			long exp = payload.getExp();

			if (exp >= current) {
				isValid = true;
			}

		} catch (Exception e) {
			getLogger().error(e.getMessage());
		}
		
		return isValid;
	}
	
	/**
	 * create JWT Access Token
	 * 
	 * @param secret
	 * @param payload
	 * @return
	 * @throws Exception
	 */
	public static String genLoginToken(String secret, Payload payload, long loginExpire) throws Exception {
		String loginToken = null;
		if (payload != null && secret != null && !secret.isEmpty()) {
			Calendar calendar = Calendar.getInstance(ParameterConfig.getApplication().getApplicationLocale());			
			long current = calendar.getTimeInMillis();
			long exp = current + loginExpire;
			payload.setExp(exp);

			// convert obj to json
			String content = JSONObjectMapperUtil.convertObject2Json(payload); 

			JWT jwt = createJwt(secret, content);
			if(jwt != null) {
				loginToken = jwt.getParsedString();
			}
		}
		return loginToken;
	}

	/**
	 * create JWT Hidden Token 
	 * 
	 * @param secret
	 * @param payload
	 * @param sessionExpire
	 * @return
	 * @throws Exception
	 */
	public static String genHiddenToken(String secret, Payload payload) throws Exception {
		String hiddenToken = null;
		if (payload != null && secret != null && !secret.isEmpty()) {
			// convert obj to json
			String content = JSONObjectMapperUtil.convertObject2Json(payload);

			JWT jwt = createJwt(secret, content);
			if(jwt != null) {
				hiddenToken = jwt.getParsedString();
			}
		}
		return hiddenToken;
	}
	
	/**
	 * Encrypt password
	 * 
	 * @param salt
	 * @param password
	 * @return
	 */
	public static String encodePassword(String salt, String password) {
		salt = KEYTOOL + salt;
		String raw = BCrypt.hashpw(password, salt); 
		return raw.substring(salt.length(), raw.length());
	}
	
	/**
	 * generate salt for encrypt id
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String genSalt() {
		SecureRandom random = new SecureRandom();
		String salt = BCrypt.gensalt(getSecurityUtilConfig().getGenSaltStrength(), random);
		return salt.substring(KEYTOOL.length(), salt.length());
	}
		
	/**
	 * generate secret
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String genSecret() {
		return EExtensionApiUtil.encodeByteToHex(SecureRandom.getSeed(32));
	}
	
	/**
	 * generate authenticate code
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String genAuthenticateCode() {
		return EExtensionApiUtil.encodeByteToHex(SecureRandom.getSeed(24));
	}
	
	/**
	 * generate auth id
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String genAuthId() {
		return EExtensionApiUtil.encodeByteToHex(SecureRandom.getSeed(16));
	}
	
	/**
	 * generate reset code
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String genResetCode(String clientId, String key) throws Exception {
		String value = EncryptionUtil.doEncrypt(clientId, key, EncType.AES256);
		return EExtensionApiUtil.encodeByteToHex(value.getBytes());
	}
	
	/**
	 * Get client id by reset code
	 * 
	 * @param renewCode
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getClientIdByResetCode(String resetCode, String key) throws Exception {
		String renew = new String(EExtensionApiUtil.decodeHexToByte(resetCode), StandardCharsets.UTF_8);
		return DecryptionUtil.doDecrypt(renew, key, DecType.AES256);
	}
		
	/**
	 * encrypt id or pk id for search and user id
	 * 
	 * @param salt
	 * @param secret
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static String encryptId(String salt, String secret, String id) {
		String encryptedId = null;
		if(id != null && !id.isEmpty()) {
			
			String secretHex = EExtensionApiUtil.encodeByteToHex(secret.getBytes());
			String saltHex = EExtensionApiUtil.encodeByteToHex(salt.getBytes());
			TextEncryptor encryptor = new EExtensionTextEncryptor(new BouncyCastleAesCbcBytesEncryptor(secretHex, saltHex));
			/** 
			https://github.com/spring-projects/spring-security/issues/4049
			fix Unable to initialize due to invalid secret key for jdk 1.8
			TextEncryptor encryptor = Encryptors.text(secretHex, saltHex);
			**/
			
			encryptedId = encryptor.encrypt(id);
		}

		return encryptedId;
	}
	
	/**
	 * decrypt id & pk for search
	 * 
	 * @param salt
	 * @param secret
	 * @param encryptedId
	 * @return
	 * @throws Exception
	 */
	public static String decryptId(String salt, String secret, String encryptedId) {
		String id = null;
		
		if(encryptedId != null && !encryptedId.isEmpty()) {
			
			String secretHex = EExtensionApiUtil.encodeByteToHex(secret.getBytes());
			String saltHex = EExtensionApiUtil.encodeByteToHex(salt.getBytes());
			TextEncryptor encryptor = new EExtensionTextEncryptor(new BouncyCastleAesCbcBytesEncryptor(secretHex, saltHex));
			/** 
			https://github.com/spring-projects/spring-security/issues/4049
			fix Unable to initialize due to invalid secret key for jdk 1.8
			TextEncryptor encryptor = Encryptors.text(secretHex, saltHex);
			**/
			
			id = encryptor.decrypt(encryptedId);
		}
		return id;
	}
	
	/**
	 * Get login expire millisecond
	 * 
	 * @param loginExpire
	 * @return
	 */
	public static long getLoginExpireMillis(long loginExpire) {
		return loginExpire * 60L * 1000L;
	}
	
	/**
	 * Get search expire millisecond
	 * 
	 * @param searchExpire
	 * @return
	 */
	public static long getSearchExpireMillis(long searchExpire) {
		return searchExpire * 60L * 1000L;
	}
	
	/**
	 * get user from request {@link HttpServletRequest}
	 * 
	 * @param request
	 * @return
	 */
	public LoginUser getLoginUser(HttpServletRequest request) {
		return (LoginUser) request.getAttribute(getSecurityUtilConfig().getReqKeyUser());
	}

	/**
	 * Insert JWT to Cookies
	 * 
	 * @param token
	 * @param request
	 * @param response
	 * @param cookie
	 * @param expiry
	 */
	public NewCookie createCookie(String token, HttpServletRequest request, int expiry) {
		return createCookie(token, request.getContextPath(), expiry);
	}
	
	/**
	 * Insert JWT to cookies
	 * 
	 * @param token
	 * @param request
	 * @param response
	 * @param cookie
	 * @param expiry
	 */
	public static NewCookie createCookie(String token, int expiry) {		
		return createCookie(token, getSecurityUtilConfig().getContextPath(), expiry);
	}
	
	private static NewCookie createCookie(String token, String contextPath, int expiry) {
		NewCookie cookie = null;
		
		String domain = null;
		String comment = null;
		boolean secure = getSecurityUtilConfig().isSecureFlag();
		boolean httpOnly = getSecurityUtilConfig().isHttpOnly();
		/** ตัด SameSite ไปก่อนนะ ไม่งั้นแล้ว jboss จะติด double quotes มาด้วยตอนสร้าง cookies
		cookie = new NewCookie(getSecurityUtilConfig().getCookiesKeyAuth()
				, token+";SameSite=Strict;"
				, contextPath
				, domain
                , Cookie.DEFAULT_VERSION
				, comment
				, expiry
                , null
				, secure
				, httpOnly
				);
		**/
		cookie = new NewCookie(getSecurityUtilConfig().getCookiesKeyAuth()
				, token
				, contextPath
				, domain
                , Cookie.DEFAULT_VERSION
				, comment
				, expiry
                , null
				, secure
				, httpOnly
				);

		return cookie;
	}
	
	/**
	 * create search token
	 * 
	 * @param secret
	 * @param payload
	 * @return
	 * @throws Exception
	 */
	public static String genSearchToken(String secret, Payload payload) throws Exception {
		String hiddenToken = null;
		if (payload != null && secret != null && !secret.isEmpty()) {
			Calendar calendar = Calendar.getInstance(ParameterConfig.getApplication().getApplicationLocale());			
			long current = calendar.getTimeInMillis();
			long exp = current + getSecurityUtilConfig().getSearchExpireMillis();
			payload.setExp(exp);
			
			// convert obj to json
			String content = JSONObjectMapperUtil.convertObject2Json(payload);
			
			JWT jwt = createJwt(secret, content);
			if(jwt != null) {
				hiddenToken = jwt.getParsedString();
			}
		}			
		return hiddenToken;
	}
	
	/**
	 * remove JWT Token from Cookies when logout
	 * 
	 * @param requestContext {@link ContainerRequestContext}
	 * @param responseContext {@link ContainerResponseContext}
	 * @throws Exception
	 */
	public void removeTokenFromCookie(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
		try {
			Cookie reqCookie = requestContext.getCookies().remove(getSecurityUtilConfig().getCookiesKeyAuth());
			if (reqCookie != null) {
				getLogger().debug("Remove Token in Cookies Success.");
			} else {
				getLogger().debug("No Login Token in Cookies.");
			}
			
		} catch (Exception e) {
			getLogger().error("");
			throw e;
		}
	}
	
	/**
	 * remove JWT Token from cookies when logout
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 */
	public void removeTokenFromCookie(HttpServletRequest request, HttpServletResponse response) {
		javax.servlet.http.Cookie[] requestCookies = request.getCookies();
		if (requestCookies != null) {
			for (javax.servlet.http.Cookie c : requestCookies) {
				// delete cookie
				if (c.getName().equals(getSecurityUtilConfig().getCookiesKeyAuth())) {
					c.setMaxAge(0);
					response.addCookie(c);
				}
			}
		}
	}
	
	/**
	 * validate token and return object (inside payload)
	 * 
	 * @param token
	 * @param userId
	 * @param clazz
	 * @param string 
	 * @return
	 * @throws Exception
	 */
	public static Object getHiddenToken(String token, Class<?> clazz, String secret) throws Exception {
		Object object = null;
		// verify token
		if ((token != null && !token.isEmpty()) && (secret != null && !secret.isEmpty())) {
			JWSObject jwsObject = JWSObject.parse(token);
			// verify
			JWSVerifier verifier = new MACVerifier(secret);
			if (jwsObject.verify(verifier)) {
				// check expire
				object = JSONObjectMapperUtil.convertJson2Object(jwsObject.getPayload().toString(), clazz);
			} else {
				getLogger().error("Token verify couldn't be verified.!!!");
			}
		}
		return object;
	}
	
	/**
	 * Find UI context
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getContext(HttpServletRequest request) {
		String context = "";
		String origin = CorsFilter.enumAsList(request.getHeaders("Origin")).get(0);
		String referer = CorsFilter.enumAsList(request.getHeaders("Referer")).get(0);
		String[] token = StringUtils.split(referer.substring(origin.length()), Split.SLASH.getValue());
		if(token.length > 0) {
			context = token[0];
		}
		return context;
	}
	
	/**
	 * Find UI context
	 * 
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	public static String getContext(ContainerRequestContext requestContext) {
		String context = "";
		String origin = requestContext.getHeaderString("Origin");
		String referer = requestContext.getHeaderString("Referer");
		String[] token = StringUtils.split(referer.substring(origin.length()), Split.SLASH.getValue());
		if(token.length > 0) {
			context = token[0];
		}
		return context;
	}
	
	public static Payload validateHiddenToken(String hiddenToken, LoginUser loginUser) throws Exception {
		Payload payload = (Payload) EExtensionApiSecurityUtil.getHiddenToken(hiddenToken, Payload.class, loginUser.getSecret());
		
		if(!InputValidateUtil.hasValue(payload) || !InputValidateUtil.hasValue(payload.getId())) {
			throw new ServerValidateException();
		}
		
		return payload;
	}
}
