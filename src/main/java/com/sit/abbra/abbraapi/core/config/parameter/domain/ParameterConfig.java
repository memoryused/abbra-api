package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.service.ParameterManager;
import com.sit.abbra.abbraapi.util.security.SecurityUtilConfig;

import util.database.Database;
import util.log4j2.DefaultLogUtil;
import util.sql.SQLParameterizedDebugUtil;

public class ParameterConfig implements Serializable {

	private static final long serialVersionUID = -2187894195556282622L;
	
	private static Parameter parameter;
	private static SecureConfig secure;
	private static ConfigSystem configSystem;
	private static SecurityUtilConfig securityUtilConfig;
	
	public static void initParameter(String parameterFile) {
		try {
			getLogger().info("Parameter path :- {}", parameterFile);
			parameter = new ParameterManager(getLogger()).loadParameter(parameterFile);
			
			// เปิดใช้งาน feature แทน parameter ที่ sql
			if (ParameterConfig.getApplication().isEnableSQLParameterizedDebug()) {
				System.setProperty(SQLParameterizedDebugUtil.PARAMETERZIZED_DEBUG, "Y");
			}
			
			// เพิ่ม config ให้ print แต่ parameter ที่ส่งเข้าไปใน sql ได้
			if (ParameterConfig.getApplication().isEnableSQLParameterizedParamDebug()) {
				System.setProperty(SQLParameterizedDebugUtil.PARAMETERZIZED_PARAMS_DEBUG, "Y");
			}
			getLogger().info("OK.");
		} catch (Exception e) {
			getLogger().error("Can't load Parameter!!!", e);
		}
	}
	
	public static void initSecure(String securefile) {
		try {
			getLogger().debug("Secure path :- {}", securefile);
			secure = new ParameterManager(getLogger()).loadSecre(securefile);
			getLogger().info("OK.");
		} catch (Exception e) {
			getLogger().error("Can't load Secure!!!", e);
		}
	}

	public static void initSecurityUtil() {
		// Load Security config from file
		try {
			getLogger().info("Initial security util");
			securityUtilConfig = new SecurityUtilConfig(ParameterConfig.getApplication().getSecurityUtilConfigPath());
			getLogger().info("Initial security util completed.");
			
		} catch (Exception e) {
			getLogger().error("Can't load security util!!!", e);
		}
	}
	
	public static Application getApplication() {
		return parameter.getApplication();
	}
	
	public static Database[] getDatabase() {
		return parameter.getDatabase();
	}
	
	public static Parameter getParameter() {
		return parameter;
	}
	
	public static SecureConfig getSecure() {
		return secure;
	}
	
	public static DateFormat getDateFormat() {
		return parameter.getDateFormat();
	}
	
	public static ConfigSystem getConfigSystem() {
		return configSystem;
	}
	
	public static SecurityUtilConfig getSecurityUtilConfig() {
		return securityUtilConfig;
	}
	
	public static CheckRedirectLogin getCheckRedirectLogin() {
		return parameter.getCheckRedirectLogin();
	}

	public static AuthWebService getAuthWebService() {
		return parameter.getAuthWebService();
	}

	public static LoginWebService getLoginWebService() {
		return parameter.getLoginWebService();
	}

	public static UpdateExtToVfsWebService getUpdateExtToVfsWebService() {
		return parameter.getUpdateExtToVfsWebService();
	}

	public static SockProxy getSockProxy() {
		return parameter.getSockProxy();
	}

	public static ThumbnailatorConfig getThumbnailConfig() {
		return parameter.getThumbnailConfig();
	}
	
	public static AttachmentConfig getAttachment() {
		return parameter.getAttachmentConfig();
	}
	
	public static Logger getLogger() {
		return DefaultLogUtil.INITIAL;
	}
}
