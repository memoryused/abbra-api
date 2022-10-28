package com.sit.common;

import java.io.Serializable;

import com.sit.domain.Permission;

public class CommonRequest implements Serializable {

	private static final long serialVersionUID = -6049424025093348773L;

	private String hiddenToken;
	private Permission permission;

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public String getHiddenToken() {
		return hiddenToken;
	}

	public void setHiddenToken(String hiddenToken) {
		this.hiddenToken = hiddenToken;
	}

}
