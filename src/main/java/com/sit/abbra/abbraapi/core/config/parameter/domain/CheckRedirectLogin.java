package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;

public class CheckRedirectLogin implements Serializable {
	
	private static final long serialVersionUID = 8774164140466400548L;
	
	private String[] redirect;
	
	public String[] getRedirect() {
		return redirect;
	}
	public void setRedirect(String[] redirect) {
		this.redirect = redirect;
	}
}
