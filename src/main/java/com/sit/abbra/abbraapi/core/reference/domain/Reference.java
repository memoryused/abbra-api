package com.sit.abbra.abbraapi.core.reference.domain;

import java.io.Serializable;

public class Reference implements Serializable {

	private static final long serialVersionUID = -1159726823770881241L;

	private String label;
	private String extension;
	private String fn;
	private String fnPath;
	
	public String getFn() {
		return fn;
	}
	public void setFn(String fn) {
		this.fn = fn;
	}
	public String getFnPath() {
		return fnPath;
	}
	public void setFnPath(String fnPath) {
		this.fnPath = fnPath;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
}
