package com.sit.abbra.abbraapi.core.admin.domain;

import com.sit.common.CommonDomain;

public class Announce extends CommonDomain {

	private static final long serialVersionUID = 8707612272156243152L;
	
	private String announceType;
	private String title;
	private String detail;
	private String announceDate;
	private String coverPicPath;
	private String filePath;
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
	public String getCoverPicPath() {
		return coverPicPath;
	}
	public void setCoverPicPath(String coverPicPath) {
		this.coverPicPath = coverPicPath;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
