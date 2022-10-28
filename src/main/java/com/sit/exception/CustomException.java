package com.sit.exception;

import java.util.List;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.common.CommonException;
import com.sit.common.CommonInputValidate;

/**
 * กำหนดเอง
 */
public class CustomException extends CommonException {
	
	private static final long serialVersionUID = 1702538989138219791L;

	private final String[] replaces;
	private final List<CommonInputValidate> invalidInputs;
	private final String displayStatus;
	
	public CustomException() {
		super(getDefaultMessage());
		this.replaces = null;
		this.invalidInputs = null;
		this.displayStatus = ErrorsCode.CUSTOM.getDisplayStatus();
	}

	public CustomException(String message) {
		super(message);
		this.replaces = null;
		this.invalidInputs = null;
		this.displayStatus = ErrorsCode.CUSTOM.getDisplayStatus();
	}
	
	// fix(TGPNR2022-94): ปรับแก้ Message ให้เป็นแถบสีเขียว 20220620:nopparat.p
	public CustomException(String message, String displayStatus) {
		super(message);
		this.replaces = null;
		this.invalidInputs = null;
		this.displayStatus = displayStatus;
	}
	
	public CustomException(Throwable cause) {
		super(cause);
		this.replaces = null;
		this.invalidInputs = null;
		this.displayStatus = ErrorsCode.CUSTOM.getDisplayStatus();
	}
	
	public CustomException(String message, Throwable cause) {
		super(message, cause);
		this.replaces = null;
		this.invalidInputs = null;
		this.displayStatus = ErrorsCode.CUSTOM.getDisplayStatus();
	}
	
	public CustomException(String message, String[] replaces) {
		super(message);
		this.replaces = replaces;
		this.invalidInputs = null;
		this.displayStatus = ErrorsCode.CUSTOM.getDisplayStatus();
	}
	
	public CustomException(String message, String[] replaces, List<CommonInputValidate> invalidInputs) {
		super(message);
		this.replaces = replaces;
		this.invalidInputs = invalidInputs;
		this.displayStatus = ErrorsCode.CUSTOM.getDisplayStatus();
	}
	
	// fix(TGPNR2022-94): ปรับแก้ Message ให้เป็นแถบสีเขียว 20220620:nopparat.p
	public CustomException(String message, String[] replaces, List<CommonInputValidate> invalidInputs, String displayStatus) {
		super(message);
		this.replaces = replaces;
		this.invalidInputs = invalidInputs;
		this.displayStatus = displayStatus;
	}
	
	@Override
	public String getErrorCode() {
		return ErrorsCode.CUSTOM.getErrorCode();
	}

	@Override
	public String getErrorDesc() {
		return ErrorsCode.CUSTOM.getErrorDesc();
	}
	
	@Override
	public String getDisplayStatus() {
		return displayStatus;
	}

	@Override
	public String getComponentType() {
		return ErrorsCode.CUSTOM.getComponentType();
	}

	public String[] getReplaces() {
		return replaces;
	}

	public List<CommonInputValidate> getInvalidInputs() {
		return invalidInputs;
	}
}
