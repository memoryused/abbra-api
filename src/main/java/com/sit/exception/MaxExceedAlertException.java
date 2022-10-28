package com.sit.exception;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.common.CommonException;

/**
 * ใช้เมื่อพบข้อมูลเป็นจำนวนมาก แล้ว alert ให้ใส่เงือนไขค้นหาเพิ่ม
 */
public class MaxExceedAlertException extends CommonException {

	private static final long serialVersionUID = 71618177900411552L;

	public MaxExceedAlertException() {
		super(getDefaultMessage());
	}
	
	public MaxExceedAlertException(String message) {
		super(message);
	}
	
	public MaxExceedAlertException(Throwable cause) {
		super(cause);
	}
	
	public MaxExceedAlertException(String message, Throwable cause) {
		super(message, cause);
	}

	@Override
	public String getErrorCode() {
		return ErrorsCode.MAX_EXCEED_ALERT.getErrorCode();
	}

	@Override
	public String getErrorDesc() {
		return ErrorsCode.MAX_EXCEED_ALERT.getErrorDesc();
	}
	
	@Override
	public String getDisplayStatus() {
		return ErrorsCode.MAX_EXCEED_ALERT.getDisplayStatus();
	}

	@Override
	public String getComponentType() {
		return ErrorsCode.MAX_EXCEED_ALERT.getComponentType();
	}
	
	public static String getDefaultMessage() {
		return DefaultExceptionMessage.MAX_EXCEED_ALERT;
	}
	
}
