package com.sit.exception;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.common.CommonException;

public class TokenExpireException extends CommonException {

	private static final long serialVersionUID = 5324359581691805266L;

	public TokenExpireException() {
		super(getDefaultMessage());
	}

	public TokenExpireException(String message) {
		super(message);
	}
	
	public TokenExpireException(Throwable cause) {
		super(cause);
	}
	
	public TokenExpireException(String message, Throwable cause) {
		super(message, cause);
	}
	
	@Override
	public String getErrorCode() {
		return ErrorsCode.TOKEN_EXPIRE.getErrorCode();
	}

	@Override
	public String getErrorDesc() {
		return ErrorsCode.TOKEN_EXPIRE.getErrorDesc();
	}
	
	@Override
	public String getDisplayStatus() {
		return ErrorsCode.TOKEN_EXPIRE.getDisplayStatus();
	}

	@Override
	public String getComponentType() {
		return ErrorsCode.TOKEN_EXPIRE.getComponentType();
	}
	
	public static String getDefaultMessage() {
		return DefaultExceptionMessage.TOKEN_EXPIRE;
	}
}
