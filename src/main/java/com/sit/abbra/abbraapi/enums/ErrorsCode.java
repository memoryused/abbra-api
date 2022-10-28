package com.sit.abbra.abbraapi.enums;

/**
 * @author PG-Suraphong
 *
 */
public enum ErrorsCode {
	/** common exception **/
	DEFAULT("SIT-000000", "Undefined exception handle.", ComponentType.SNACK.getValue(), DisplayStatus.ERROR.getValue())
	, CUSTOM("SIT-000001", "Custom message exception.", ComponentType.SNACK.getValue(), DisplayStatus.ERROR.getValue())
	
	/** SIT exception **/
	, AUTHENTICATE("SIT-10019", "Please login, User has not logged in to use the system.", ComponentType.SNACK.getValue(), DisplayStatus.ERROR.getValue())
	, AUTHORIZATION("SIT-20004", "You donn't have permission to use this function.", ComponentType.REDIRECT.getValue(), DisplayStatus.WARN.getValue())
	, MISSING_PERMISSION_CONFIGURATION("SIT-20004", "You donn't have permission to use this function.", ComponentType.SNACK.getValue(), DisplayStatus.ERROR.getValue())
	, UPDATE_LOCK_EXCEPTION("SIT-10041", "Unable to process. Other user is in progress.", ComponentType.SNACK.getValue(), DisplayStatus.WARN.getValue())
	, SERVER_VALIDATE("SIT-10018", "Invalid XML/JSON format.", ComponentType.SNACK.getValue(), DisplayStatus.ERROR.getValue())
	, INPUT_VALIDATE("SIT-10018", "Invalid XML/JSON format.", ComponentType.SNACK.getValue(), DisplayStatus.ERROR.getValue())
	, MAX_EXCEED("SIT-30018", "xxx record(s) found. Do you want to view record(s) ?", ComponentType.CONFIRM.getValue(), DisplayStatus.INFO.getValue())
	, MAX_EXCEED_ALERT("SIT-30016", "Too many records were found. Please redefine the terms.", ComponentType.ALERT.getValue(), DisplayStatus.INFO.getValue())
	, MAX_EXCEED_REPORT("SIT-30016", "Too many records were found. Please redefine the terms.", ComponentType.ALERT.getValue(), DisplayStatus.INFO.getValue())
	, DUPLICATE("SIT-10003", "Duplicate data.", ComponentType.SNACK.getValue(), DisplayStatus.WARN.getValue())
	, DATA_NOT_FOUND("SIT-30011", "Data cannot be found.", ComponentType.REDIRECT.getValue(), DisplayStatus.INFO.getValue())
	, TOKEN_EXPIRE("SIT-30300", "Expired Token.", ComponentType.SNACK.getValue(), DisplayStatus.ERROR.getValue())
	, LOGIN_OVERRIDED("SIT-30300", "Expired Token.", ComponentType.SNACK.getValue(), DisplayStatus.ERROR.getValue())
	, DEPRECATED_VERSION("SIT-13000", "Deprecated version. Please update latest version.", ComponentType.SNACK.getValue(), DisplayStatus.ERROR.getValue())
	, GET_LOCK_EXCEPTION("SIT-30101", "Unable to edit the record. The record is being locked by another user", ComponentType.SNACK.getValue(), DisplayStatus.WARN.getValue())
	;
	
	private String errorCode;
	private String errorDesc;
	private String displayStatus;
	private String componentType;

	private ErrorsCode(String errorCode, String errorDesc, String componentType, String displayStatus) {
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.componentType = componentType;
		this.displayStatus = displayStatus;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public String getDisplayStatus() {
		return displayStatus;
	}

	public String getComponentType() {
		return componentType;
	}

}
