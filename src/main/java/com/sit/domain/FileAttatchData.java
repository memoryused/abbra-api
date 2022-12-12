package com.sit.domain;

import java.io.File;
import java.io.Serializable;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

public class FileAttatchData implements Serializable{

	private static final long serialVersionUID = 8657089822833606071L;

	private String jsonReq;
	private FormDataContentDisposition fileMeta;
	private File file;
	
	public FileAttatchData(String jsonReq, FormDataContentDisposition fileMeta, File file) {
		this.jsonReq = jsonReq;
		this.fileMeta = fileMeta;
		this.file = file;
	}
	
	public String getJsonReq() {
		return jsonReq;
	}
	public void setJsonReq(String jsonReq) {
		this.jsonReq = jsonReq;
	}
	public FormDataContentDisposition getFileMeta() {
		return fileMeta;
	}
	public void setFileMeta(FormDataContentDisposition fileMeta) {
		this.fileMeta = fileMeta;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
}
