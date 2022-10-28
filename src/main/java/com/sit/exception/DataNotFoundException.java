package com.sit.exception;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.common.CommonException;

/**
 * ไม่พบข้อมูล
 */
public class DataNotFoundException extends CommonException {

	private static final long serialVersionUID = 2458261092508847647L;

	public DataNotFoundException() {
		super(getDefaultMessage());
	}

	public DataNotFoundException(String message) {
		super(message);
	}
	
	public DataNotFoundException(Throwable cause) {
		super(cause);
	}

	
	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	@Override
	public String getErrorCode() {
		return ErrorsCode.DATA_NOT_FOUND.getErrorCode();
	}

	@Override
	public String getErrorDesc() {
		return ErrorsCode.DATA_NOT_FOUND.getErrorDesc();
	}
	
	@Override
	public String getDisplayStatus() {
		return ErrorsCode.DATA_NOT_FOUND.getDisplayStatus();
	}

	@Override
	public String getComponentType() {
		return ErrorsCode.DATA_NOT_FOUND.getComponentType();
	}
	
	public static String getDefaultMessage() {
		return DefaultExceptionMessage.DATA_NOT_FOUND;
	}

}
