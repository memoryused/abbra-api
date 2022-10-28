package com.sit.abbra.abbraapi.util.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.util.EExtensionApiUtil;

import util.log4j2.DefaultLogUtil;

public class SecurityUtilConfig {
	
	private Logger logger = DefaultLogUtil.UTIL;
	
	public static final String DEFAULT_PATH = "com/sit/pibics/eextensionapi/util/security/security_util_config.properties";
	
	private String reqKeyUser;
	private String reqKeyLogout;
	private String crossCheckKey;
	private String cookiesKeyAuth;
	private String contextPath;
	private String loginPage;

	private int genSaltStrength;

	private long loginExpire;
	private long searchExpire;
	private long authenExpire;

	private boolean secureFlag;
	private boolean httpOnly;

	private String dateFormatForDisplayHHMMSS;
	private String dateFormatForCompareYYYYMMDDHHMMSS;
	
	public SecurityUtilConfig(String resourcePath) {
		if (!initialOverride(resourcePath)) {
			initialDefault();
		}
	}
	
	private boolean initialDefault() {
		boolean isSuccess = false;
		try {
			initial(DEFAULT_PATH);
			isSuccess = true;
			
			getLogger().debug("Initial Default Config Success.");
			
		} catch (Exception e) {
			getLogger().error("Initial Default Config Error!");
			getLogger().catching(e);
		}
		return isSuccess;
	}
	
	private boolean initialOverride(String overridePath) {
		boolean isSuccess = false;
		try {
			initial(overridePath);
			isSuccess = true;
			
			getLogger().debug("Initial Override Config Success.");
			
		} catch (Exception e) {
			getLogger().error("Initial Override Config Error!");
			getLogger().catching(e);
		}
		return isSuccess;
	}

	private void initial(String resourcePath) throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			// Load resource from path
			ClassLoader classLoader = (SecurityUtilConfig.class).getClassLoader();
			input = classLoader.getResourceAsStream(resourcePath);

			// Load properties from input stream
			prop.load(input);

			this.reqKeyUser = prop.getProperty("reqKeyUser");
			this.reqKeyLogout = prop.getProperty("reqKeyLogout");
			this.crossCheckKey = prop.getProperty("crossCheckKey");
			this.cookiesKeyAuth = prop.getProperty("cookiesKeyAuth");
			this.contextPath = prop.getProperty("contextPath");
			this.loginPage = prop.getProperty("loginPage");
			
			this.genSaltStrength = getIntProperty(prop, "genSaltStrength");
			
			this.loginExpire = getLongProperty(prop, "loginExpire");
			this.searchExpire = getLongProperty(prop, "searchExpire");
			this.authenExpire = getLongProperty(prop, "authenExpire");
			
			this.secureFlag = getBooleanProperty(prop, "secureFlag");
			this.httpOnly = getBooleanProperty(prop, "httpOnly");
			
			this.dateFormatForDisplayHHMMSS = prop.getProperty("forDisplayHHMMSS");
			this.dateFormatForCompareYYYYMMDDHHMMSS = prop.getProperty("forCompareYYYYMMDDHHMMSS");
			
		} catch (IOException e) {
			getLogger().catching(e);
			throw e;
			
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (Exception e2) {
				getLogger().catching(e2);
			}
		}
	}

	private Logger getLogger() {
		return logger;
	}

	private int getIntProperty(Properties prop, String propName) {
		String propVal = prop.getProperty(propName);
		return Integer.parseInt(propVal);
	}
	
	private long getLongProperty(Properties prop, String propName) {
		String propVal = prop.getProperty(propName);
		return EExtensionApiUtil.convertLongValue(propVal);
	}
	
	private boolean getBooleanProperty(Properties prop, String propName) {
		String propVal = prop.getProperty(propName);
		return Boolean.valueOf(propVal);
	}
	
	public String getReqKeyUser() {
		return reqKeyUser;
	}

	public String getReqKeyLogout() {
		return reqKeyLogout;
	}

	public String getCrossCheckKey() {
		return crossCheckKey;
	}
	
	public String getCookiesKeyAuth() {
		return cookiesKeyAuth;
	}

	public String getContextPath() {
		return contextPath;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public int getGenSaltStrength() {
		return genSaltStrength;
	}

	public long getLoginExpire() {
		return loginExpire;
	}

	public long getSearchExpire() {
		return searchExpire;
	}
	
	public long getAuthenExpire() {
		return authenExpire;
	}

	public boolean isSecureFlag() {
		return secureFlag;
	}

	public boolean isHttpOnly() {
		return httpOnly;
	}

	public String getDateFormatForDisplayHHMMSS() {
		return dateFormatForDisplayHHMMSS;
	}

	public String getDateFormatForCompareYYYYMMDDHHMMSS() {
		return dateFormatForCompareYYYYMMDDHHMMSS;
	}

	public long getLoginExpireMillis() {
		return (loginExpire * 60 * 1000);
	}
	
	public long getSearchExpireMillis() {
		return (searchExpire * 60 * 1000);
	}

}
