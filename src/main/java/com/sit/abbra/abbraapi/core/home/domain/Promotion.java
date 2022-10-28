package com.sit.abbra.abbraapi.core.home.domain;

import java.io.Serializable;

public class Promotion implements Serializable {
	private static final long serialVersionUID = -5159596532250090746L;
	
	private String src;
	private String thumb;
	
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

}
