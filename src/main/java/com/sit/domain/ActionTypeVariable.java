package com.sit.domain;

public class ActionTypeVariable {	
	
	private ActionTypeVariable() {
		
	}
	
	public static final String ACTION_TYPE_DEFAULT_XSUCCESS_CODE = "00000";
	public static final String ACTION_TYPE_DEFAULT_XSUCCESS_DESC = "Success";
	
	public static final String ACTION_TYPE_SAVE_SUCCESS_CODE = "30003";
	public static final String ACTION_TYPE_SAVE_SUCCESS_DESC = "The data have been stored successfully.";
	
	public static final String ACTION_TYPE_SAVE_FAIL_CODE = "30007";
	public static final String ACTION_TYPE_SAVE_FAIL_DESC = "Failed to save data.";
	
	public static final String ACTION_TYPE_DEFAULT_PROCESS_NOT_COMPLETE_CODE = "30014";
	public static final String ACTION_TYPE_DEFAULT_PROCESS_NOT_COMPLETE_DESC = "Invalid Data";
	
	public static final String ACTION_TYPE_DEFAULT_FILE_NOT_FOUND_CODE = "30013";
	public static final String ACTION_TYPE_DEFAULT_FILE_NOT_FOUND_DESC = "File not found.";
}
