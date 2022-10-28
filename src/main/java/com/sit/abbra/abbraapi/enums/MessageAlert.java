package com.sit.abbra.abbraapi.enums;

public enum MessageAlert {
	BUNDLE_FILE("resources.bundle.common.MessageAlert"),
	
	SUCCESS("00000"),
	UNSUCCESS("00001"),
	INSUFFICIENT_DATA("10002"),
	INVALID_DATA("10005"),
	SAVE_SUCCESS("30003"),
	SAVE_UNSUCCESS("30007"),
	DATA_NOT_FOUND("30009"),
	SERVICE_TIMEOUT("30015"),
	INVALID_USER_OR_PASS("30039"),
	OVERRIDE("40003"),
	
	
	
	
	FILE_NOY_FOUND("30013"),
	NO_DATA_FROM_PB("30023"),
	CALCELED_DATA("30022"),
	NOTED_DATA("30041"),
	DATA_LOCKED("30101"),
	VISA_ALREADY_USED("30106"),
	ALREADY_BEEN_SEND_VFS("30042"),
	COULD_NOT_SEND_VFS("30043")
	;
	private String val;

	private MessageAlert(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}
}
