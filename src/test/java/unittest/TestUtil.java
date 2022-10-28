package unittest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientProperties;

import com.sit.abbra.abbraapi.core.security.login.domain.AccessTokenRequest;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginPayload;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.abbra.abbraapi.util.security.EExtensionApiSecurityUtil;
import com.sit.domain.Payload;

import sit.util.http.HTTPParameterUtil;
import sit.util.http.domain.HTTPConfig;
import sit.util.http.secure.SecureHostnameVerifier;
import sit.util.http.secure.SecureTrustManager;
import util.cryptography.EncryptionUtil;
import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.database.enums.DbType;
import util.string.StringUtil;

public class TestUtil {
	
	private static final String REST_URI = TestVariable.REST_URI;
	private static final String REST_URI_PATTERN = TestVariable.REST_URI_PATTERN;
	
	private static final String CLIENT_RESPONSE_TYPE = TestVariable.CLIENT_RESPONSE_TYPE;
	private static final String CLIENT_URI_MAIN = TestVariable.CLIENT_URI_MAIN;
	private static final String CLIENT_HEADERS_ORIGIN = TestVariable.CLIENT_HEADERS_ORIGIN;
	private static final String CLIENT_HEADERS_HOST = TestVariable.CLIENT_HEADERS_HOST;
	private static final String CLIENT_CODE_VERIFIER = generateCodeVerifier();
	
	private static final String HEADER_ORIGIN = TestVariable.HEADER_ORIGIN;
	
	private static Client client = initClient();
	private static NewCookie cookie = null;
	private static String authorizationHeader = null;
	
	public static void main(String[] args) {
		String clientId = TestVariable.CLIENT_ID;
		String username = TestVariable.USERNAME1;
		String password = TestVariable.PASSWORD1;
		
		// Login
		login(clientId, username, password);
		
		// Cookie
		getLogger().debug("cookie: {}", getCookie());
		getLogger().debug("accessToken: {}", getAccessToken().getId());

		// Authorization
		getLogger().debug("authorizationHeader: {}", getAuthorizationHeader());
		
		// Logout
		logout();
		
		getLogger().debug("Logout");
	}
	
	public static void login1() {
		String clientId = TestVariable.CLIENT_ID;
		String user = TestVariable.USERNAME1;
		String password = TestVariable.PASSWORD1; 
		
		WebTarget authenIDTarget = getAuthenIDTarget(clientId);
		String authenId = getAuthenID(authenIDTarget);
		getLogger().debug("authenId: {}", authenId);
		
		String authenCode = signup(authenId, user, password);
		getLogger().debug("authenCode: {}", authenCode);
		
		WebTarget accessTokenTarget = getAccessTokenTarget();
		getAccessToken(clientId, accessTokenTarget, authenCode);
	}
	
	public static void login2() {
		String clientId = TestVariable.CLIENT_ID;
		String user = TestVariable.USERNAME2;
		String password = TestVariable.PASSWORD2; 
		
		WebTarget authenIDTarget = getAuthenIDTarget(clientId);
		String authenId = getAuthenID(authenIDTarget);
		getLogger().debug("authenId: {}", authenId);
		
		String authenCode = signup(authenId, user, password);
		getLogger().debug("authenCode: {}", authenCode);
		
		WebTarget accessTokenTarget = getAccessTokenTarget();
		getAccessToken(clientId, accessTokenTarget, authenCode);
	}
	
	public static void login3() {
		String clientId = TestVariable.CLIENT_ID;
		String user = TestVariable.USERNAME3;
		String password = TestVariable.PASSWORD3; 
		
		WebTarget authenIDTarget = getAuthenIDTarget(clientId);
		String authenId = getAuthenID(authenIDTarget);
		getLogger().debug("authenId: {}", authenId);
		
		String authenCode = signup(authenId, user, password);
		getLogger().debug("authenCode: {}", authenCode);
		
		WebTarget accessTokenTarget = getAccessTokenTarget();
		getAccessToken(clientId, accessTokenTarget, authenCode);
	}
	
	public static void login(String clientId, String user, String password) {
		WebTarget authenIDTarget = getAuthenIDTarget(clientId);
		String authenId = getAuthenID(authenIDTarget);
		getLogger().debug("authenId: {}", authenId);
		
		String authenCode = signup(authenId, user, password);
		getLogger().debug("authenCode: {}", authenCode);
		
		WebTarget accessTokenTarget = getAccessTokenTarget();
		getAccessToken(clientId, accessTokenTarget, authenCode);
	}
	
	public static void logout() {
		WebTarget logoutTarget = logoutTarget();
		logout(logoutTarget);
	}
	
	public static Client initClient() {
		Client client = null;
		if (REST_URI.startsWith("https")) {
			HTTPConfig httpConfig = new HTTPConfig();
			httpConfig.setHttps(true);
			httpConfig.setSslCheckCerExpire(false);
			httpConfig.setSslCheckHostname(false);
			httpConfig.setSslProtocolName("TLSv1.3");
			httpConfig.setConnectionTimeout(30000);
			httpConfig.setReadingTimeout(30000);
			
			SSLContext sslContext = null;
			try {
				// Create a trust manager that does not validate certificate chains
				TrustManager[] trustAllCerts = new TrustManager[] { new SecureTrustManager(httpConfig) };
				
				// Install the all-trusting trust manager
				HttpsURLConnection.setDefaultSSLSocketFactory(initSSLSocketFactory(httpConfig, trustAllCerts));
				
				// Install the all-trusting host verifier
				HttpsURLConnection.setDefaultHostnameVerifier(new SecureHostnameVerifier(httpConfig));
				
				sslContext = SSLContext.getInstance(httpConfig.getSslProtocolName());
				sslContext.init(null, trustAllCerts, null);
				
				client = ClientBuilder.newBuilder().sslContext(sslContext).build();
			} catch (Exception e) {
				getLogger().catching(e);
			}
			
			
		} else {
			client = ClientBuilder.newClient();
		}
		System.setProperty ( "sun.net.http.allowRestrictedHeaders" , "true" );
		return client;
	}
	
	private static WebTarget getAuthenIDTarget(String clientId) {
		return client.target(REST_URI)
				.path(REST_URI_PATTERN)
				.path("internal_authenticate")
				.queryParam("response_type", getResponseType())
				.queryParam("client_id", clientId)
				.queryParam("code_challenge", generateCodeChallenge())
				.queryParam("state", generateState())
				.queryParam("redirect_url", getMainURL());
	}
	
	private static String getAuthenID(WebTarget target) {
		Response response = target
				.request(MediaType.APPLICATION_JSON)
				.header(HEADER_ORIGIN, CLIENT_HEADERS_ORIGIN)
				.header(HttpHeaders.HOST, CLIENT_HEADERS_HOST)
				.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE)
				.get();
		return response.getLocation().getQuery().substring(3);
	}
	
	private static WebTarget logoutTarget() {
		return client.target(REST_URI)
				.path(REST_URI_PATTERN)
				.path("/api/authenticate/logout");
	}
	
	private static void logout(WebTarget target) {
			target.request(MediaType.APPLICATION_JSON)
				.header(HEADER_ORIGIN, CLIENT_HEADERS_ORIGIN)
				.header(HttpHeaders.HOST, CLIENT_HEADERS_HOST)
				.header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader())
				.cookie(getCookie())
				.post(null);
	}
	
	private static String signup(String authenId, String user, String password) {
		String authenCode = "";
		HTTPConfig httpConfig = new HTTPConfig();
		httpConfig.setContentType("text/html");
		httpConfig.setAcceptType("text/html");
		httpConfig.setConnectionTimeout(30000);
		httpConfig.setReadingTimeout(30000);

		String url = REST_URI + "signup";
		Map<String, String> httpHeader = null;
		Map<String, String> queryParam = new LinkedHashMap<>();
		queryParam.put("authId", authenId);
		queryParam.put("username", user);
		queryParam.put("password", password);
		
		String postData = null;
		try {
		    authenCode = getAuthenCode(url, httpConfig, httpHeader, queryParam, postData);
		    if (authenCode.startsWith("<!DOCTYPE ")) {
		    	getLogger().debug("Found login and override Yes.");
		    	queryParam.put("isOverride", "true");
		    	authenCode = getAuthenCode(url, httpConfig, httpHeader, queryParam, postData);
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return authenCode;
	}
	
	private static WebTarget getAccessTokenTarget() {
		return client.target(REST_URI)
				.path(REST_URI_PATTERN)
				.path("/oauth/token");
	}
	
	private static void getAccessToken(String clientId, WebTarget target, String authenCode) {
		AccessTokenRequest accessTokenRequest = new AccessTokenRequest();
		accessTokenRequest.setGrantType("AUTHORIZATION_CODE");
		accessTokenRequest.setClientId(clientId);
		accessTokenRequest.setAuthCode(authenCode);
		accessTokenRequest.setCodeVerifier(CLIENT_CODE_VERIFIER);
		accessTokenRequest.setRedirectUrl(CLIENT_URI_MAIN);
		
		Response response = target
				.request(MediaType.APPLICATION_JSON)
				.header(HEADER_ORIGIN, CLIENT_HEADERS_ORIGIN)
				.header(HttpHeaders.HOST, CLIENT_HEADERS_HOST)
				.post(Entity.entity(accessTokenRequest, MediaType.APPLICATION_JSON));
		cookie = response.getCookies().get("AUTHORIZATION");
		authorizationHeader = response.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).toString();
	}
	
	private static String getResponseType() {
		return CLIENT_RESPONSE_TYPE;
	}
	
	private static String getMainURL() {
		return CLIENT_URI_MAIN;
	}
	
	private static String generateCodeVerifier() {
		return EExtensionApiUtil.encodeByteToHex(SecureRandom.getSeed(8));
	}
	
	private static String getCodeVerifier() {
		return CLIENT_CODE_VERIFIER;
	}
	
	private static String generateCodeChallenge() {
		String result = null;
		try {
			result = EExtensionApiUtil.encodeByteToHex(EncryptionUtil.encryptSHA512(getCodeVerifier()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static String generateState() {
		return EExtensionApiUtil.encodeByteToHex(SecureRandom.getSeed(8));
	}
	
	private static String getAuthenCode(String url, HTTPConfig httpConfig, Map<String, String> httpHeader, Map<String, String> queryParam, String postData) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		if (httpConfig.isHttps()) {
			return getAuthenCodeHttps(url, httpConfig, httpHeader, queryParam, postData);
		} else {
			return getAuthenCodeHttp(url, httpConfig, httpHeader, queryParam, postData);
		}
	}

	/**
	 * เชื่อมต่อ HTTPS
	 * @param url
	 * @param reqData
	 * @param httpHeader
	 * @param httpConfig
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * @throws Exception
	 */
	private static String getAuthenCodeHttps(String urlString, HTTPConfig httpConfig, Map<String, String> httpHeader, Map<String, String> queryParam, String postData) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		String response = null;
		
		verifySSL(httpConfig);
		
		HttpURLConnection conn = null;
		BufferedReader br = null;
		try {
			// Adding Request Parameters
			if ((queryParam != null) &&
					!queryParam.isEmpty()) {
				urlString += "?" + HTTPParameterUtil.getParamsString(queryParam, httpConfig.getCharsetEncoder());
			}
			
			URL url = new URL(urlString);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(httpConfig.getHttpMethod());
			conn.setConnectTimeout(httpConfig.getConnectionTimeout());
			conn.setReadTimeout(httpConfig.getReadingTimeout());
			conn.setRequestProperty("Content-type", httpConfig.getContentType());
			conn.setRequestProperty("Accept", httpConfig.getAcceptType());
			if (httpHeader != null) {
				for (Entry<String,String> entry : httpHeader.entrySet()) { 
					conn.setRequestProperty(entry.getKey(), entry.getValue());		
				}
			}
			
			if ("POST".equals(httpConfig.getHttpMethod())
					&& postData != null) {
				write(conn, postData, httpConfig.getCharsetEncoder());
			}
			
			// Reading the Response
			response = read(conn, httpConfig.getCharsetEncoder());
		} finally {
			close(br);
			close(conn);
		}
		return response;
	}
	
	/**
	 * เชื่อมต่อ HTTP
	 * @param url
	 * @param reqData
	 * @param httpHeader
	 * @param httpConfig
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	private static String getAuthenCodeHttp(String urlString, HTTPConfig httpConfig, Map<String, String> httpHeader, Map<String, String> queryParam, String postData) throws IOException {
		String response = "";
		
		HttpURLConnection conn = null;
		BufferedReader br = null;
		try {
			// Adding Request Parameters
			if ((queryParam != null) &&
					!queryParam.isEmpty()) {
				urlString += "?" + HTTPParameterUtil.getParamsString(queryParam, httpConfig.getCharsetEncoder());
			}
			
			URL url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(httpConfig.getHttpMethod());
			conn.setConnectTimeout(httpConfig.getConnectionTimeout());
			conn.setReadTimeout(httpConfig.getReadingTimeout());
			conn.setRequestProperty("Content-type", httpConfig.getContentType());
			conn.setRequestProperty("Accept", httpConfig.getAcceptType());
			conn.setInstanceFollowRedirects(false);
			if (httpHeader != null) {
				for (Entry<String,String> entry : httpHeader.entrySet()) { 
					conn.setRequestProperty(entry.getKey(), entry.getValue());		
				}
			}
			
			if ("POST".equals(httpConfig.getHttpMethod())
					&& postData != null) {
				write(conn, postData, httpConfig.getCharsetEncoder());
			}
			
			getLogger().debug("httpStatus: {}", conn.getResponseCode());
			// Reading the Response
			if (conn.getResponseCode() == 200) { 
				response = read(conn, httpConfig.getCharsetEncoder());
			} else if (conn.getResponseCode() == 302){
				String redirectLocation = conn.getHeaderField("Location");
				getLogger().debug("redirectLocation: {}", redirectLocation);
				
				response = getQueryParam(redirectLocation, CLIENT_RESPONSE_TYPE);
			}
		}catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close(br);
			close(conn);
		}
		return response;
	}
	
	private static String getQueryParam(String url, String keyParam) {
		// http://localhost:4200/home?code=ae15df8b534186f95c5174491e9ad7e82adabacea331148a&state=6192c498b1bec4ff
		String queryValue = "";
		int indexOfQuery = url.indexOf("?");
		String[] queryParamArray = url.substring(indexOfQuery + 1).split("&");
		for (String queryParamData : queryParamArray) {
			if (queryParamData.startsWith(keyParam)) {
				queryValue = queryParamData.substring(keyParam.length() + 1);
				break;
			}
		} 
		return queryValue;
	}
	
	private static void write(URLConnection conn, String postData, String charsetEncoder) throws IOException {
		try (OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream(), Charset.forName(charsetEncoder).newEncoder()); ){
			os.write(postData);
			os.flush();
		}
	}
	
	private static String read(URLConnection conn, String charsetEncoder) throws IOException {
		StringBuilder responseBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName(charsetEncoder).newDecoder())); ){
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				responseBuilder.append(StringEscapeUtils.unescapeJava(inputLine));
			}
		}
		return responseBuilder.toString();
	}
	
	private static void verifySSL(HTTPConfig httpConfig) throws NoSuchAlgorithmException, KeyManagementException {
		
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new SecureTrustManager(httpConfig) };
		
		// Install the all-trusting trust manager
		HttpsURLConnection.setDefaultSSLSocketFactory(initSSLSocketFactory(httpConfig, trustAllCerts));
		
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(new SecureHostnameVerifier(httpConfig));
	}

	private static SSLSocketFactory initSSLSocketFactory(HTTPConfig httpConfig, TrustManager[] trustAllCerts) throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance(httpConfig.getSslProtocolName());
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		return sc.getSocketFactory();
	}
	
	public static String decodeUTF8(byte[] bytes, String charsetEncoder) throws UnsupportedEncodingException {
	    return new String(bytes, charsetEncoder);
	}
	
	public static byte[] encodeUTF8(String string, String charsetEncoder) throws UnsupportedEncodingException {
	    return string.getBytes(charsetEncoder);
	}
	
	public static void close(DataOutputStream os) {
		try {
			if (os != null) {
				os.close();
			}
		} catch (Exception e) {
			getLogger().catching(e);
		}
	}
	
	
	public static void close(OutputStreamWriter os) {
		try {
			if (os != null) {
				os.close();
			}
		} catch (Exception e) {
			getLogger().catching(e);
		}
	}
	
	public static void close(BufferedReader br) {
		try {
			if (br != null) {
				br.close();
			}
		} catch (Exception e) {
			getLogger().catching(e);
		}
	}
	
	public static void close(HttpURLConnection conn) {
		try {
			if (conn != null) {
				conn.disconnect();
			}
		} catch (Exception e) {
			getLogger().catching(e);
		}
	}
	
	public static void close(HttpsURLConnection conn) {
		try {
			if (conn != null) {
				conn.disconnect();
			}
		} catch (Exception e) {
			getLogger().catching(e);
		}
	}
	
	public static Client getClient() {
		return client;
	}
	
	public static NewCookie getCookie() {
		return cookie;
	}
	
	public static String getAuthorizationHeader() {
		return authorizationHeader;
	}
	
	public static Logger getLogger() {
		return LogManager.getLogger("UTIL");
	}
	
	public static String callAPI(String endpoint, String json) {
		return getClient().target(REST_URI)
				.path(REST_URI_PATTERN)
				.path(endpoint)
				.request(MediaType.APPLICATION_JSON)
				.header(HEADER_ORIGIN, CLIENT_HEADERS_ORIGIN)
				.header(HttpHeaders.HOST, CLIENT_HEADERS_HOST)
				.header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader())
				.cookie(getCookie())
				.post(Entity.entity(json, MediaType.APPLICATION_JSON), String.class);
	}
	
	public static String callAPIGET(String endpoint, String json) {
		return getClient().target(REST_URI)
				.path(REST_URI_PATTERN)
				.path(endpoint)
				.request(MediaType.APPLICATION_JSON)
				.header(HEADER_ORIGIN, CLIENT_HEADERS_ORIGIN)
				.header(HttpHeaders.HOST, CLIENT_HEADERS_HOST)
				.header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader())
				.cookie(getCookie())
				.get(String.class);
	}
	
	public static Response callAPIResp(String endpoint, String json) {
		return getClient().target(REST_URI)
				.path(REST_URI_PATTERN)
				.path(endpoint)
				.request(MediaType.APPLICATION_JSON)
				.header(HEADER_ORIGIN, CLIENT_HEADERS_ORIGIN)
				.header(HttpHeaders.HOST, CLIENT_HEADERS_HOST)
				.header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader())
				.cookie(getCookie())
				.post(Entity.entity(json, MediaType.APPLICATION_JSON));
	}
	
	public static LoginPayload getAccessToken() {
		LoginPayload loginPayload = new LoginPayload();
		try {
			String accessToken = EExtensionApiSecurityUtil.getTokenFromCookie(getCookie());
			loginPayload = EExtensionApiSecurityUtil.getLoginPayloadFromToken(accessToken);
			getLogger().debug("         ID: {}", loginPayload.getId());
			getLogger().debug("        Exp: {}", loginPayload.getExp());
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return loginPayload;
	}
	
	public static String generateHiddenToken(LoginUser loginUser, String id) throws Exception {
		Payload payload = new Payload();
		payload.setId(EExtensionApiSecurityUtil.encryptId(loginUser.getSalt(), loginUser.getSecret(), id));
		
		return EExtensionApiSecurityUtil.genSearchToken(loginUser.getSecret(), payload);
	}
	
	public static LoginUser getLoginUser(CCTConnection conn) throws Exception {
		getLogger().debug("");
		
		Statement stmt = null;
		ResultSet rst = null;
		
		LoginUser modelResult = null;
		try {
			String token = getAccessToken().getId();
			getLogger().debug("token: {}", token);
			
			stmt = conn.createStatement();
			
			String sql = "SELECT USER_ID, TOKEN, SALT, SECRET \r\n"
					+ "FROM SEC_LOGIN \r\n"
					+ "WHERE TOKEN = '" + token + "'";
			
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("SQL : {}", sql);
			}
			
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				modelResult = new LoginUser();
				
				modelResult.setUserId(StringUtil.nullToString(rst.getString("USER_ID")));
				modelResult.setToken(StringUtil.nullToString(rst.getString("TOKEN")));
				modelResult.setSalt(StringUtil.nullToString(rst.getString("SALT")));
				modelResult.setSecret(StringUtil.nullToString(rst.getString("SECRET")));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return modelResult;
	}
	
	public static Connection getConnection() throws SQLException {
		String uri = TestVariable.DATABASE_URI;
		String username = TestVariable.DATABASE_USERNAME;
		String password = TestVariable.DATABASE_PASSWORD;
		return DriverManager.getConnection(uri, username, password); 
	}
	
	public static CCTConnection getCCTConnection() throws SQLException {
		CCTConnection cctConn = new CCTConnection();
		Connection conn = getConnection();
		
		cctConn.setConn(conn);
		cctConn.setDbType(DbType.MARIADB);
		cctConn.setSchemas(new LinkedHashMap<>());
		return cctConn;
	}
	
	public void setAutoCommit(Connection conn, boolean flag) {
		try {
			conn.setAutoCommit(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(ResultSet rst) {
		try {
			if (rst != null) {
				rst.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
