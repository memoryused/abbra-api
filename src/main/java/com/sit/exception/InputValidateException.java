package com.sit.exception;

import java.util.List;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.common.CommonException;
import com.sit.common.CommonInputValidate;

/**
 * ข้อมูลที่ได้รับไม่ถูกต้อง
 */
public class InputValidateException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2418835834743232665L;

	private final List<CommonInputValidate> invalidInputs;
	
	public InputValidateException() {
		super(getDefaultMessage());
		this.invalidInputs = null;
	}
	
	public InputValidateException(String message) {
		super(message);
		this.invalidInputs = null;
	}
	
	public InputValidateException(Throwable cause) {
		super(cause);
		this.invalidInputs = null;
	}
	
	public InputValidateException(String message, Throwable cause) {
		super(message, cause);
		this.invalidInputs = null;
	}
	
	public InputValidateException(List<CommonInputValidate> invalidInputs) {
		super(DefaultExceptionMessage.VALIDATOR_DEFAULT_MESSAGE);
		this.invalidInputs = invalidInputs;
	}
	
	@Override
	public String getErrorCode() {
		return ErrorsCode.INPUT_VALIDATE.getErrorCode();
	}

	@Override
	public String getErrorDesc() {
		return ErrorsCode.INPUT_VALIDATE.getErrorDesc();
	}

	@Override
	public String getDisplayStatus() {
		return ErrorsCode.INPUT_VALIDATE.getDisplayStatus();
	}

	@Override
	public String getComponentType() {
		return ErrorsCode.INPUT_VALIDATE.getComponentType();
	}
	
	public static String getDefaultMessage() {
		return DefaultExceptionMessage.INPUT_VALIDATE;
	}

	public List<CommonInputValidate> getInvalidInputs() {
		return invalidInputs;
	}
}
