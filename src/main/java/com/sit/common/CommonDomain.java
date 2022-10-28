package com.sit.common;

import java.io.Serializable;

public class CommonDomain implements Serializable {
	
	private static final long serialVersionUID = 5412962298655430942L;
	
	private String hiddenToken;

	public String getHiddenToken() {
		return hiddenToken;
	}

	public void setHiddenToken(String hiddenToken) {
		this.hiddenToken = hiddenToken;
	}
}
