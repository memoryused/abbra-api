package com.sit.exception;

/**
 * ค่า Default Exception Message ต่างๆ ที่จัดเตรียมไว้ให้
 */
public class DefaultExceptionMessage {

	private DefaultExceptionMessage() {
		throw new IllegalStateException("Utility class");
	}
	 
	public static final String DEFAULT = "Internal Server Error.";
	public static final String AUTHENTICATE = "Please login, User has not logged in to use the system.";
	public static final String AUTHORIZATION = "You donn't have permission to use this function.";
	public static final String GET_LOCK_EXCEPTION = "Unable to edit the record. The record is being locked by another user";
	public static final String GET_LOCK_EXCEPTION_CUSTOM = "Unable to edit the record. The record is being locked by %s";
	public static final String UPDATE_LOCK_EXCEPTION = "Unable to process. Other user is in progress.";
	public static final String INPUT_VALIDATE = "Invalid XML/JSON";
	public static final String SERVER_VALIDATE = "Invalid XML/JSON";
	public static final String MAX_EXCEED = "Number of search result = xxx information. Too many records were found. Do you want to display information?";
	public static final String MAX_EXCEED_ALERT = "Too many records were found. Please redefine the terms.";
	public static final String MAX_EXCEED_REPORT = "Too many records were found. Please redefine the terms.";
	public static final String DUPLICATE = "Duplicate data, Please check again.";
	public static final String SESSION_EXPIRED = "Session expired.";
	public static final String DATA_NOT_FOUND = "Data cannot be found.";
	public static final String SEND_MAIL_ERROR = "Send e-mail unsuccessfull.";
	
	public static final String VALIDATOR_DEFAULT_MESSAGE = "Invalid Data. Please check again.";
	public static final String VALIDATOR_FORMAT_MESSAGE = "xxx is invalid.";
	public static final String TOKEN_EXPIRE = "Expired Token.";
	public static final String LOGIN_OVERRIDED = "Expired Token.";
	public static final String MAIL_AUTHEN_FAIL = "Send e-mail unsuccessfull.";
	public static final String MAIL_CONNECTION_FAIL = "Send e-mail unsuccessfull.";
	public static final String DEPRECATED_VERSION = "Deprecated version. Please update latest version.";
	
}
