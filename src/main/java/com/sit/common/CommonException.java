package com.sit.common;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.exception.DefaultExceptionMessage;

public class CommonException extends Exception {

	private static final long serialVersionUID = 461924630018745907L;

	private String loggingCode;

	public CommonException() {
		super(getDefaultMessage());
	}

	public CommonException(String message) {
		super(message);
	}

	public CommonException(Throwable cause) {
		super(cause);
	}

	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getErrorCode() {
		return ErrorsCode.DEFAULT.getErrorCode();
	}

	public String getErrorDesc() {
		return ErrorsCode.DEFAULT.getErrorDesc();
	}

	public String getDisplayStatus() {
		return ErrorsCode.DEFAULT.getDisplayStatus();
	}

	public String getComponentType() {
		return ErrorsCode.DEFAULT.getComponentType();
	}

	public String getLoggingCode() {
		return loggingCode;
	}

	public void setLoggingCode(String loggingCode) {
		this.loggingCode = loggingCode;
	}

	public static String getDefaultMessage() {
		return DefaultExceptionMessage.DEFAULT;
	}
	
}
