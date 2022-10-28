package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;

public class DateFormat implements Serializable {
	private static final long serialVersionUID = -7829158200049419117L;

	private String timeZone;
	
	private String dbFormat;
	private String dbDate;
	private String dbDateTime;
	private String dbTime;
	private String dbTimestamp;
	private String dbInsert;

	private String uiFormat;
	private String uiDate;
	private String uiDateTime;
	private String uiTime;
	private String uiTimestamp;
	
	// 20/09/2022 thanat.s 2022EXTVFS-29 - เพิ่ม format ของ date สำหรับ print sticker
	private String stickerDateFormat;

	public String getDbFormat() {
		return dbFormat;
	}

	public void setDbFormat(String dbFormat) {
		this.dbFormat = dbFormat;
	}

	public String getDbDate() {
		return dbDate;
	}

	public void setDbDate(String dbDate) {
		this.dbDate = dbDate;
	}

	public String getDbDateTime() {
		return dbDateTime;
	}

	public void setDbDateTime(String dbDateTime) {
		this.dbDateTime = dbDateTime;
	}

	public String getDbTime() {
		return dbTime;
	}

	public void setDbTime(String dbTime) {
		this.dbTime = dbTime;
	}

	public String getUiFormat() {
		return uiFormat;
	}

	public void setUiFormat(String uiFormat) {
		this.uiFormat = uiFormat;
	}

	public String getUiDate() {
		return uiDate;
	}

	public void setUiDate(String uiDate) {
		this.uiDate = uiDate;
	}

	public String getUiDateTime() {
		return uiDateTime;
	}

	public void setUiDateTime(String uiDateTime) {
		this.uiDateTime = uiDateTime;
	}

	public String getUiTime() {
		return uiTime;
	}

	public void setUiTime(String uiTime) {
		this.uiTime = uiTime;
	}

	public String getDbTimestamp() {
		return dbTimestamp;
	}

	public void setDbTimestamp(String dbTimestamp) {
		this.dbTimestamp = dbTimestamp;
	}

	public String getUiTimestamp() {
		return uiTimestamp;
	}

	public void setUiTimestamp(String uiTimestamp) {
		this.uiTimestamp = uiTimestamp;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getDbInsert() {
		return dbInsert;
	}

	public void setDbInsert(String dbInsert) {
		this.dbInsert = dbInsert;
	}

	public String getStickerDateFormat() {
		return stickerDateFormat;
	}

	public void setStickerDateFormat(String stickerDateFormat) {
		this.stickerDateFormat = stickerDateFormat;
	}
}
