package com.sit.exception;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.common.CommonException;

/**
 * ใช้เมื่อพบข้อมูลเป็นจำนวนมาก แล้ว Confirm ต้องการค้นหาไหมต่อไหม?
 */
public class MaxExceedException extends CommonException {

	private static final long serialVersionUID = 5525853572322946843L;

	public MaxExceedException() {
		super(getDefaultMessage());
	}

	public MaxExceedException(String message) {
		super(message);
	}
	
	public MaxExceedException(Throwable cause) {
		super(cause);
	}
	
	public MaxExceedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	@Override
	public String getErrorCode() {
		return ErrorsCode.MAX_EXCEED.getErrorCode();
	}

	@Override
	public String getErrorDesc() {
		return ErrorsCode.MAX_EXCEED.getErrorDesc();
	}
	
	@Override
	public String getDisplayStatus() {
		return ErrorsCode.MAX_EXCEED.getDisplayStatus();
	}

	@Override
	public String getComponentType() {
		return ErrorsCode.MAX_EXCEED.getComponentType();
	}
	
	public static String getDefaultMessage() {
		return DefaultExceptionMessage.MAX_EXCEED;
	}
	
}
