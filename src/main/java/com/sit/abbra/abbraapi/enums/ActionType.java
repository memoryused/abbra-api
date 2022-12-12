package com.sit.abbra.abbraapi.enums;

import com.sit.domain.ActionTypeVariable;

public enum ActionType {

	DEFAULT(ActionTypeVariable.ACTION_TYPE_DEFAULT_XSUCCESS_CODE, ActionTypeVariable.ACTION_TYPE_DEFAULT_XSUCCESS_DESC, ActionTypeVariable.ACTION_TYPE_DEFAULT_PROCESS_NOT_COMPLETE_CODE, ActionTypeVariable.ACTION_TYPE_DEFAULT_PROCESS_NOT_COMPLETE_DESC)
	, SEARCH(ActionTypeVariable.ACTION_TYPE_DEFAULT_XSUCCESS_CODE, ActionTypeVariable.ACTION_TYPE_DEFAULT_XSUCCESS_DESC, ActionTypeVariable.ACTION_TYPE_DEFAULT_PROCESS_NOT_COMPLETE_CODE, ActionTypeVariable.ACTION_TYPE_DEFAULT_PROCESS_NOT_COMPLETE_DESC)
	, ADD(ActionTypeVariable.ACTION_TYPE_SAVE_SUCCESS_CODE, ActionTypeVariable.ACTION_TYPE_SAVE_SUCCESS_DESC, ActionTypeVariable.ACTION_TYPE_SAVE_FAIL_CODE, ActionTypeVariable.ACTION_TYPE_SAVE_FAIL_DESC)
	, EDIT(ActionTypeVariable.ACTION_TYPE_SAVE_SUCCESS_CODE, ActionTypeVariable.ACTION_TYPE_SAVE_SUCCESS_DESC, ActionTypeVariable.ACTION_TYPE_SAVE_FAIL_CODE, ActionTypeVariable.ACTION_TYPE_SAVE_FAIL_DESC)
	, VIEW(ActionTypeVariable.ACTION_TYPE_DEFAULT_XSUCCESS_CODE, ActionTypeVariable.ACTION_TYPE_DEFAULT_XSUCCESS_DESC, ActionTypeVariable.ACTION_TYPE_DEFAULT_PROCESS_NOT_COMPLETE_CODE, ActionTypeVariable.ACTION_TYPE_DEFAULT_PROCESS_NOT_COMPLETE_DESC)
	, DOWNLOAD(ActionTypeVariable.ACTION_TYPE_DEFAULT_XSUCCESS_CODE, ActionTypeVariable.ACTION_TYPE_DEFAULT_XSUCCESS_DESC, ActionTypeVariable.ACTION_TYPE_DEFAULT_FILE_NOT_FOUND_CODE, ActionTypeVariable.ACTION_TYPE_DEFAULT_FILE_NOT_FOUND_DESC)
	, DELETE(ActionTypeVariable.ACTION_TYPE_SAVE_SUCCESS_CODE, ActionTypeVariable.ACTION_TYPE_SAVE_SUCCESS_DESC, ActionTypeVariable.ACTION_TYPE_SAVE_FAIL_CODE, ActionTypeVariable.ACTION_TYPE_SAVE_FAIL_DESC)
	, NO_LOGIN("00000", "Success", ActionTypeVariable.ACTION_TYPE_DEFAULT_PROCESS_NOT_COMPLETE_CODE, ActionTypeVariable.ACTION_TYPE_DEFAULT_PROCESS_NOT_COMPLETE_DESC)
	, UPLOAD("X00000", "Success", "00001", "unsuccess")
	;

	private String messageSuccessCode;
	private String messageSuccessDesc;
	private String messageErrorCode;
	private String messageErrorDesc;

	private ActionType(String messageSuccessCode, String messageSuccessDesc
			, String messageErrorCode, String messageErrorDesc) {
		
		this.messageSuccessCode = messageSuccessCode;
		this.messageSuccessDesc = messageSuccessDesc;
		this.messageErrorCode = messageErrorCode;
		this.messageErrorDesc = messageErrorDesc;
	
	}
	
	public String getMessageErrorCode() {
		return messageErrorCode;
	}

	public String getMessageErrorDesc() {
		return messageErrorDesc;
	}
	
	public String getMessageSuccessCode() {
		return messageSuccessCode;
	}

	public String getMessageSuccessDesc() {
		return messageSuccessDesc;
	}

}
