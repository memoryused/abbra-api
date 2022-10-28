package com.sit.exception;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.common.CommonException;

/**
 * ใช้สำหรับยืนยันตัวตน
 */
public class AuthenticateException extends CommonException {

	private static final long serialVersionUID = -7351163217961819178L;

	public AuthenticateException() {
		super(getDefaultMessage());
	}

	public AuthenticateException(String message) {
		super(message);
	}
	
	public AuthenticateException(Throwable cause) {
		super(cause);
	}

	public AuthenticateException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String getErrorCode() {
		return ErrorsCode.AUTHENTICATE.getErrorCode();
	}

	@Override
	public String getErrorDesc() {
		return ErrorsCode.AUTHENTICATE.getErrorDesc();
	}
	
	@Override
	public String getDisplayStatus() {
		return ErrorsCode.AUTHENTICATE.getDisplayStatus();
	}

	@Override
	public String getComponentType() {
		return ErrorsCode.AUTHENTICATE.getComponentType();
	}
	
	public static String getDefaultMessage() {
		return DefaultExceptionMessage.AUTHENTICATE;
	}

}