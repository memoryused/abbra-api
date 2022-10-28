package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;
import java.util.Locale;

import javax.xml.bind.annotation.XmlTransient;

public class Application implements Serializable {

	private static final long serialVersionUID = -6791131125526807670L;

	private String applicationLocaleString;
	private Locale applicationLocale;
	
	private boolean enableSQLParameterizedDebug;
	private boolean enableSQLParameterizedParamDebug;
	
	private String[] versionProvide;
	private String securityUtilConfigPath;
	
	private Integer maxExceed;
	private Integer maxExceedReport;
	private String auditLogConfigPath;
	private Integer maxExceedDay;
	private String sharePath;

	public boolean isEnableSQLParameterizedDebug() {
		return enableSQLParameterizedDebug;
	}

	public void setEnableSQLParameterizedDebug(boolean enableSQLParameterizedDebug) {
		this.enableSQLParameterizedDebug = enableSQLParameterizedDebug;
	}

	public boolean isEnableSQLParameterizedParamDebug() {
		return enableSQLParameterizedParamDebug;
	}

	public void setEnableSQLParameterizedParamDebug(boolean enableSQLParameterizedParamDebug) {
		this.enableSQLParameterizedParamDebug = enableSQLParameterizedParamDebug;
	}
	
	@XmlTransient
	public Locale getApplicationLocale() {
		if (applicationLocale == null) {
			applicationLocale = new Locale(getApplicationLocaleString().toLowerCase(),
					getApplicationLocaleString().toUpperCase());
		}
		return applicationLocale;
	}

	public String getApplicationLocaleString() {
		return applicationLocaleString;
	}

	public void setApplicationLocaleString(String applicationLocaleString) {
		this.applicationLocaleString = applicationLocaleString;
	}

	public String[] getVersionProvide() {
		return versionProvide;
	}

	public void setVersionProvide(String[] versionProvide) {
		this.versionProvide = versionProvide;
	}

	public String getSecurityUtilConfigPath() {
		return securityUtilConfigPath;
	}

	public void setSecurityUtilConfigPath(String securityUtilConfigPath) {
		this.securityUtilConfigPath = securityUtilConfigPath;
	}
	
	public Integer getMaxExceed() {
		return maxExceed;
	}

	public void setMaxExceed(Integer maxExceed) {
		this.maxExceed = maxExceed;
	}

	public Integer getMaxExceedReport() {
		return maxExceedReport;
	}

	public void setMaxExceedReport(Integer maxExceedReport) {
		this.maxExceedReport = maxExceedReport;
	}

	public String getAuditLogConfigPath() {
		return auditLogConfigPath;
	}

	public void setAuditLogConfigPath(String auditLogConfigPath) {
		this.auditLogConfigPath = auditLogConfigPath;
	}

	public Integer getMaxExceedDay() {
		return maxExceedDay;
	}

	public void setMaxExceedDay(Integer maxExceedDay) {
		this.maxExceedDay = maxExceedDay;
	}
	
	public String getSharePath() {
		return sharePath;
	}

	public void setSharePath(String sharePath) {
		this.sharePath = sharePath;
	}

}
