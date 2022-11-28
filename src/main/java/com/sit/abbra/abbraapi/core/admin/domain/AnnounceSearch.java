package com.sit.abbra.abbraapi.core.admin.domain;


import com.sit.common.CommonSearch;

public class AnnounceSearch extends CommonSearch {

	private static final long serialVersionUID = -2816679747719737716L;
	
	private String announceType;
	private String title;
	private String detail;
	private String announceDate;
	private String status;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
