package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import util.database.Database;

@XmlRootElement
public class Parameter implements Serializable {

	private static final long serialVersionUID = -578325849007499211L;

	// @XmlTransient
	// @XmlAttribute
	// @XmlElement
	private Application application;
	private Database[] database;
	private Map<String, Database> databaseMap;
	private DateFormat dateFormat;
	private LockProcess lockProcess;
	private CheckRedirectLogin checkRedirectLogin;
	
	private AuthWebService authWebService;	// AuthWebService
	private LoginWebService loginWebService;
	private ConfigClient clientRegenerate;	// ConfigClient regenerate
	private UpdateExtToVfsWebService updateExtToVfsWebService;
	private SockProxy sockProxy;
	private ThumbnailatorConfig thumbnailConfig;
	private AttachmentConfig attachmentConfig;
	
	public Application getApplication() {
		return application;
	}

	@XmlElement
	public void setApplication(Application application) {
		this.application = application;
	}
	
	@XmlTransient
	public Map<String, Database> getDatabaseMap() {
		if (getDatabase() == null) {
			return databaseMap;
		}

		if (databaseMap == null) {
			databaseMap = new LinkedHashMap<>();
			for (Database db : getDatabase()) {
				databaseMap.put(db.getKey(), db);
			}
		}
		return databaseMap;
	}

	public Database[] getDatabase() {
		return database;
	}

	@XmlElement
	public void setDatabase(Database[] database) {
		if (this.databaseMap != null) {
			this.databaseMap.clear();
			this.databaseMap = null;
		}
		this.database = database;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public CheckRedirectLogin getCheckRedirectLogin() {
		return checkRedirectLogin;
	}

	public void setCheckRedirectLogin(CheckRedirectLogin checkRedirectLogin) {
		this.checkRedirectLogin = checkRedirectLogin;
	}

	public AuthWebService getAuthWebService() {
		return authWebService;
	}

	@XmlElement
	public void setAuthWebService(AuthWebService authWebService) {
		this.authWebService = authWebService;
	}

	public ConfigClient getClientRegenerate() {
		return clientRegenerate;
	}

	@XmlElement
	public void setClientRegenerate(ConfigClient clientRegenerate) {
		this.clientRegenerate = clientRegenerate;
	}
	
	public void setDatabaseMap(Map<String, Database> databaseMap) {
		this.databaseMap = databaseMap;
	}

	public LoginWebService getLoginWebService() {
		return loginWebService;
	}

	public void setLoginWebService(LoginWebService loginWebService) {
		this.loginWebService = loginWebService;
	}

	public LockProcess getLockProcess() {
		return lockProcess;
	}

	public void setLockProcess(LockProcess lockProcess) {
		this.lockProcess = lockProcess;
	}

	public UpdateExtToVfsWebService getUpdateExtToVfsWebService() {
		return updateExtToVfsWebService;
	}

	public void setUpdateExtToVfsWebService(UpdateExtToVfsWebService updateExtToVfsWebService) {
		this.updateExtToVfsWebService = updateExtToVfsWebService;
	}

	public SockProxy getSockProxy() {
		return sockProxy;
	}

	public void setSockProxy(SockProxy sockProxy) {
		this.sockProxy = sockProxy;
	}

	public ThumbnailatorConfig getThumbnailConfig() {
		return thumbnailConfig;
	}

	public void setThumbnailConfig(ThumbnailatorConfig thumbnailConfig) {
		this.thumbnailConfig = thumbnailConfig;
	}

	public AttachmentConfig getAttachmentConfig() {
		return attachmentConfig;
	}

	public void setAttachmentConfig(AttachmentConfig attachmentConfig) {
		this.attachmentConfig = attachmentConfig;
	}
}
