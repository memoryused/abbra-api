package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;

public class AttachmentConfig implements Serializable {
	
	private static final long serialVersionUID = -2485334744959766636L;
	private long maxFilesize;
	private long maxVdosize;
	private String[] fileType;
	private String[] vdoType;
	private String[] imgType;
	
	public long getMaxFilesize() {
		return maxFilesize;
	}
	public void setMaxFilesize(long maxFilesize) {
		this.maxFilesize = maxFilesize;
	}
	public long getMaxVdosize() {
		return maxVdosize;
	}
	public void setMaxVdosize(long maxVdosize) {
		this.maxVdosize = maxVdosize;
	}
	public String[] getFileType() {
		return fileType;
	}
	public void setFileType(String[] fileType) {
		this.fileType = fileType;
	}
	public String[] getVdoType() {
		return vdoType;
	}
	public void setVdoType(String[] vdoType) {
		this.vdoType = vdoType;
	}
	public String[] getImgType() {
		return imgType;
	}
	public void setImgType(String[] imgType) {
		this.imgType = imgType;
	}

	
}
