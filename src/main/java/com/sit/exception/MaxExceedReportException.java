package com.sit.exception;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.common.CommonException;

/**
 * ใช้เมื่อพบข้อมูลเป็นจำนวนมาก แล้ว alert ให้ใส่เงือนไขค้นหาเพิ่ม
 */
public class MaxExceedReportException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8947876313112980822L;

	public MaxExceedReportException() {
		super(getDefaultMessage());
	}
	
	
	public MaxExceedReportException(String message) {
		super(message);
	}

	public MaxExceedReportException(Throwable cause) {
		super(cause);
	}
	
	public MaxExceedReportException(String message, Throwable cause) {
		super(message, cause);
	}
	
	@Override
	public String getErrorCode() {
		return ErrorsCode.MAX_EXCEED_REPORT.getErrorCode();
	}

	@Override
	public String getErrorDesc() {
		return ErrorsCode.MAX_EXCEED_REPORT.getErrorDesc();
	}

	@Override
	public String getDisplayStatus() {
		return ErrorsCode.MAX_EXCEED_REPORT.getDisplayStatus();
	}

	@Override
	public String getComponentType() {
		return ErrorsCode.MAX_EXCEED_REPORT.getComponentType();
	}
	
	public static String getDefaultMessage() {
		return DefaultExceptionMessage.MAX_EXCEED_REPORT;
	}
	
}
