package com.sit.common;

import java.io.Serializable;
import java.util.List;

public class CommonError implements Serializable {

	private static final long serialVersionUID = -4810898889964967443L;

	private String errorCode; // error code (exception code)
	private String errorDesc; // error description (exception description)
	private List<String> errors; // errors

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
