package com.sit.exception;

import java.util.List;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.common.CommonException;
import com.sit.common.CommonInputValidate;

/**
 * บันทึก-แก้ไขข้อมูลซ้ำ
 */
public class DuplicateException extends CommonException {

	private static final long serialVersionUID = -2698980325595941519L;
	
	private final List<CommonInputValidate> invalidInputs;

	public DuplicateException() {
		super(getDefaultMessage());
		this.invalidInputs = null;
	}

	public DuplicateException(String message) {
		super(message);
		this.invalidInputs = null;
	}
	
	public DuplicateException(Throwable cause) {
		super(cause);
		this.invalidInputs = null;
	}
	
	public DuplicateException(String message, Throwable cause) {
		super(message, cause);
		this.invalidInputs = null;
	}
	
	public DuplicateException(List<CommonInputValidate> invalidInputs) {
		super(DefaultExceptionMessage.DUPLICATE);
		this.invalidInputs = invalidInputs;
	}
	
	@Override
	public String getErrorCode() {
		return ErrorsCode.DUPLICATE.getErrorCode();
	}

	@Override
	public String getErrorDesc() {
		return ErrorsCode.DUPLICATE.getErrorDesc();
	}
	
	@Override
	public String getDisplayStatus() {
		return ErrorsCode.DUPLICATE.getDisplayStatus();
	}

	@Override
	public String getComponentType() {
		return ErrorsCode.DUPLICATE.getComponentType();
	}
	
	public static String getDefaultMessage() {
		return DefaultExceptionMessage.DUPLICATE;
	}

	public List<CommonInputValidate> getInvalidInputs() {
		return invalidInputs;
	}
}
