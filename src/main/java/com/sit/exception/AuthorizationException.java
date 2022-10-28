package com.sit.exception;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.common.CommonException;

/**
 * ใช้ยืนยันสิทธิ์การเข้าใช้ใช้งาน
 */
public class AuthorizationException extends CommonException {

	private static final long serialVersionUID = 3457404600878463484L;

	public AuthorizationException() {
		super(getDefaultMessage());
	}

	public AuthorizationException(String message) {
		super(message);
	}
	
	public AuthorizationException(Throwable cause) {
		super(cause);
	}
	
	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	@Override
	public String getErrorCode() {
		return ErrorsCode.AUTHORIZATION.getErrorCode();
	}

	@Override
	public String getErrorDesc() {
		return ErrorsCode.AUTHORIZATION.getErrorDesc();
	}
	
	@Override
	public String getDisplayStatus() {
		return ErrorsCode.AUTHORIZATION.getDisplayStatus();
	}

	@Override
	public String getComponentType() {
		return ErrorsCode.AUTHORIZATION.getComponentType();
	}

	public static String getDefaultMessage() {
		return DefaultExceptionMessage.AUTHORIZATION;
	}
	
}
