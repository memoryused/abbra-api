package com.sit.abbra.abbraapi.core.security.login.domain;

import java.io.Serializable;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ConfigSystem;

public class LoginPage implements Serializable {

	private static final long serialVersionUID = 5966761539452853208L;

	private ConfigSystem config;
	private String footer;
	private boolean ldapStatus;

	public ConfigSystem getConfig() {
		return config;
	}

	public void setConfig(ConfigSystem config) {
		this.config = config;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public boolean getLdapStatus() {
		return ldapStatus;
	}

	public void setLdapStatus(boolean ldapStatus) {
		this.ldapStatus = ldapStatus;
	}
}
