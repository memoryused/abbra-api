package com.sit.abbra.abbraapi.core.security.login.domain;

import java.io.Serializable;
import java.util.List;

import com.sit.common.CommonSelectItem;

public class UserProfile implements Serializable {
	
	private static final long serialVersionUID = 2133643713430041962L;
	
	// Data ที่ต้องส่งไปหน้าจอ
	private String username;
	private String fullname;
	private String language; // ใช้ระบุ locale ที่ใช้ client ตัวอย่าง en, th, etc..
	private String changelogRead; 
	private String organizeName;
	private String mainPathURL; 
	private String loginDate; 
	private List<Operator> listOperator = null; // ใช้สำหรับวาดเมนู
	private List<CommonSelectItem> listContactUs = null;
	
	private transient Environment environment;
	
	public UserProfile(LoginUser loginUser) {
		this.username = loginUser.getUserId();
		this.fullname = loginUser.getUserName();
		this.language = loginUser.getLanguage().toUpperCase();
		this.changelogRead = loginUser.getChangeLog();
		this.organizeName = loginUser.getOrganizationName();
		this.loginDate = loginUser.getLoginDate();
	}

	public List<Operator> getListOperator() {
		return listOperator;
	}

	public void setListOperator(List<Operator> listOperator) {
		this.listOperator = listOperator;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getOrganizeName() {
		return organizeName;
	}

	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}

	public String getMainPathURL() {
		return mainPathURL;
	}

	public void setMainPathURL(String mainPathURL) {
		this.mainPathURL = mainPathURL;
	}

	public String getChangelogRead() {
		return changelogRead;
	}

	public void setChangelogRead(String changelogRead) {
		this.changelogRead = changelogRead;
	}

	public List<CommonSelectItem> getListContactUs() {
		return listContactUs;
	}

	public void setListContactUs(List<CommonSelectItem> listContactUs) {
		this.listContactUs = listContactUs;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
}
