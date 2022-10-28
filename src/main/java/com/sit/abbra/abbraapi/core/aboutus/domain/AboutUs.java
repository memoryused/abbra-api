package com.sit.abbra.abbraapi.core.aboutus.domain;

import java.io.Serializable;

public class AboutUs implements Serializable {

	private static final long serialVersionUID = 6869607246037005609L;
	
	private String srcVdo;
	private String srcPicOrganization;
	private String srcPicExecutive;
	
	public String getSrcVdo() {
		return srcVdo;
	}
	public void setSrcVdo(String srcVdo) {
		this.srcVdo = srcVdo;
	}
	public String getSrcPicOrganization() {
		return srcPicOrganization;
	}
	public void setSrcPicOrganization(String srcPicOrganization) {
		this.srcPicOrganization = srcPicOrganization;
	}
	public String getSrcPicExecutive() {
		return srcPicExecutive;
	}
	public void setSrcPicExecutive(String srcPicExecutive) {
		this.srcPicExecutive = srcPicExecutive;
	}

}
