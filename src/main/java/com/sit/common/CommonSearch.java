package com.sit.common;

import java.io.Serializable;

public class CommonSearch implements Serializable {
	
	private static final long serialVersionUID = 7900491670579462828L;
	
	private String id;
	private String hiddenToken;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHiddenToken() {
		return hiddenToken;
	}

	public void setHiddenToken(String hiddenToken) {
		this.hiddenToken = hiddenToken;
	}
}
