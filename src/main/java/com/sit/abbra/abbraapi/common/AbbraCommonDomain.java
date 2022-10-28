package com.sit.abbra.abbraapi.common;

import java.io.Serializable;

public class AbbraCommonDomain implements Serializable {

	private static final long serialVersionUID = 4313274996009515869L;
	private String src;
	private String thumb;
	private String subject;
	private String detail;
	
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
