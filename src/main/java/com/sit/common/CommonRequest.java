package com.sit.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.sit.abbra.abbraapi.core.security.login.domain.OperatorButton;
import com.sit.domain.Permission;

public class CommonRequest implements Serializable {

	private static final long serialVersionUID = -6049424025093348773L;

	private String hiddenToken;
	private Permission permission;
	private HashMap<String, OperatorButton> mapOperBtn = new LinkedHashMap<>();

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

	public HashMap<String, OperatorButton> getMapOperBtn() {
		return mapOperBtn;
	}

	public void setMapOperBtn(HashMap<String, OperatorButton> mapOperBtn) {
		this.mapOperBtn = mapOperBtn;
	}

}
