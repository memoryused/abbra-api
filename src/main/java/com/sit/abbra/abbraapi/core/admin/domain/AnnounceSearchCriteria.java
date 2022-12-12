package com.sit.abbra.abbraapi.core.admin.domain;

import java.io.Serializable;

import com.sit.common.CommonSearchCriteria;

public class AnnounceSearchCriteria extends CommonSearchCriteria {

	private static final long serialVersionUID = -5443368675843655503L;
	
	private String announceType;
	private String title;
	private String detail;
	private String announceDate;

	public String getAnnounceType() {
		return announceType;
	}
	public void setAnnounceType(String announceType) {
		this.announceType = announceType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getAnnounceDate() {
		return announceDate;
	}
	public void setAnnounceDate(String announceDate) {
		this.announceDate = announceDate;
	}
	
}
