package com.sit.exception;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.common.CommonException;

public class DeprecatedVersionException extends CommonException {

	private static final long serialVersionUID = 5324359581691805266L;

	public DeprecatedVersionException() {
		super(getDefaultMessage());
	}

	public DeprecatedVersionException(String message) {
		super(message);
	}
	
	public DeprecatedVersionException(Throwable cause) {
		super(cause);
	}
	
	public DeprecatedVersionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	@Override
	public String getErrorCode() {
		return ErrorsCode.DEPRECATED_VERSION.getErrorCode();
	}

	@Override
	public String getErrorDesc() {
		return ErrorsCode.DEPRECATED_VERSION.getErrorDesc();
	}
	
	@Override
	public String getDisplayStatus() {
		return ErrorsCode.DEPRECATED_VERSION.getDisplayStatus();
	}

	@Override
	public String getComponentType() {
		return ErrorsCode.DEPRECATED_VERSION.getComponentType();
	}
	
	public static String getDefaultMessage() {
		return DefaultExceptionMessage.DEPRECATED_VERSION;
	}
}
