package com.sit.exception;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.common.CommonException;

/**
 * ใช้เมื่อมีการตรวจสอบข้อมูลฝั่ง Server แล้วไม่ผ่าน
 */
public class ServerValidateException extends CommonException {

	private static final long serialVersionUID = 8063417434820899233L;

	public ServerValidateException() {
		super(getDefaultMessage());
	}
	
	public ServerValidateException(String message) {
		super(message);
	}
	
	public ServerValidateException(Throwable cause) {
		super(cause);
	}
	
	public ServerValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String getErrorCode() {
		return ErrorsCode.SERVER_VALIDATE.getErrorCode();
	}

	@Override
	public String getErrorDesc() {
		return ErrorsCode.SERVER_VALIDATE.getErrorDesc();
	}

	@Override
	public String getDisplayStatus() {
		return ErrorsCode.SERVER_VALIDATE.getDisplayStatus();
	}

	@Override
	public String getComponentType() {
		return ErrorsCode.SERVER_VALIDATE.getComponentType();
	}
	
	public static String getDefaultMessage() {
		return DefaultExceptionMessage.SERVER_VALIDATE;
	}
	
}