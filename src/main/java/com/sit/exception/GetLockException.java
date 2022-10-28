package com.sit.exception;

import java.util.ResourceBundle;

import com.sit.abbra.abbraapi.enums.ErrorsCode;
import com.sit.abbra.abbraapi.enums.MessageAlert;
import com.sit.common.CommonException;
import com.sit.domain.GlobalVariable;

import util.bundle.BundleUtil;

public class GetLockException extends CommonException {

	/**
	 * IMMMA-254 : เพิ่ม Exception ในส่วนของการทำงาน lock process 20220121
	 */
	private static final long serialVersionUID = 5324359581691805266L;

	public GetLockException() {
		super(getDefaultMessage());
	}

	public GetLockException(String message) {
		super(message);
	}
	
	public GetLockException(Throwable cause) {
		super(cause);
	}
	
	public GetLockException(String message, Throwable cause) {
		super(message, cause);
	}
	
	@Override
	public String getErrorCode() {
		
		return ErrorsCode.GET_LOCK_EXCEPTION.getErrorCode();
	}

	@Override
	public String getErrorDesc() {
		return ErrorsCode.GET_LOCK_EXCEPTION.getErrorDesc();
	}
	
	@Override
	public String getDisplayStatus() {
		return  ErrorsCode.GET_LOCK_EXCEPTION.getDisplayStatus();
	}

	@Override
	public String getComponentType() {
		return ErrorsCode.GET_LOCK_EXCEPTION.getComponentType();
	}
	
	public static String getDefaultMessage() {
		/**IMMMA-329:ปรับแก้ เปลี่ยนกำหนดข้อความจาก bundle แทน**/
		ResourceBundle bundle = null;
		try {
			bundle = BundleUtil.getBundle(MessageAlert.BUNDLE_FILE.getVal(), GlobalVariable.LOCALE_OFFICER);
		} catch (Exception e) {
			bundle = null;
		}
		
		return bundle!=null?bundle.getString("30103"):"";
	}
	
}
