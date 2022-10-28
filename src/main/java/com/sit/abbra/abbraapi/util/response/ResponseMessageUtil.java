package com.sit.abbra.abbraapi.util.response;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.enums.ActionType;
import com.sit.abbra.abbraapi.enums.ComponentType;
import com.sit.abbra.abbraapi.enums.DisplayStatus;
import com.sit.abbra.abbraapi.enums.MessageAlert;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.common.CommonError;
import com.sit.common.CommonException;
import com.sit.common.CommonResponse;
import com.sit.common.CommonSearchResult;
import com.sit.domain.ExceptionVariable;
import com.sit.exception.AuthenticateException;
import com.sit.exception.AuthorizationException;
import com.sit.exception.CustomException;
import com.sit.exception.DataNotFoundException;
import com.sit.exception.DuplicateException;
import com.sit.exception.GetLockException;
import com.sit.exception.InputValidateException;
import com.sit.exception.MaxExceedAlertException;
import com.sit.exception.MaxExceedException;
import com.sit.exception.MaxExceedReportException;
import com.sit.exception.ServerValidateException;
import com.sit.exception.TokenExpireException;

import util.json.JSONObjectMapperUtil;
import util.string.StringUtil;

public class ResponseMessageUtil {
	
	private static final String REPLACER = "xxx";
	private final Logger logger;
	private final LoginUser user;
	
	public ResponseMessageUtil(Logger logger) {
		this.logger = logger;
		this.user = null;
	}
	
	public ResponseMessageUtil(Logger logger, LoginUser user) {
		this.logger = logger;
		this.user = user;
	}
	
	private Logger getLogger() {
		return logger;
	}
	
	private Locale getUserLocaleByDefault() {
		Locale locale = ParameterConfig.getApplication().getApplicationLocale();
		if (user == null) {
			return locale;
		}
		
		if (user.getLocale() == null) {
			return locale;
		}
		return user.getLocale();
	}

	/**
	 * Convert {@link CommonResponse} to String
	 * @param response
	 * @return
	 */
	public String getMessage(CommonResponse response) {
		String message = "";
		try {
			message = JSONObjectMapperUtil.convertObject2Json(response);
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return message;
	}

	/**
	 * Create Default {@link CommonResponse}
	 * @param obj
	 * @return {@link Response}
	 */
	public Response manageResult(Object obj) {
		Response response = null;
		try {
			CommonResponse commonResponse = createSuccessResult(obj, ActionType.DEFAULT);
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.OK).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	/**
	 * Create Custom MessageCode {@link CommonResponse}
	 * @param obj
	 * @param messageCode
	 * @return {@link Response}
	 */
	public Response manageResult(Object obj, String messageCode) {
		Response response = null;
		try {
			ResourceBundle bundle = EExtensionApiUtil.getBundleMessageAlert(getUserLocaleByDefault());
			
			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setData(obj);
			commonResponse.setMessageCode(messageCode);
			commonResponse.setMessageDesc(bundle.getString(messageCode));
			
			manageSuccessDisplay(commonResponse);
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.OK).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	/**
	 * Create {@link CommonResponse} by {@link ActionType}
	 * @param obj
	 * @param actionType
	 * @return {@link Response}
	 */
	public Response manageResult(Object obj, ActionType actionType) {
		Response response = null;
		try {
			
			if (actionType == null) {
				actionType = ActionType.DEFAULT;
			}
			
			switch (actionType) {
			case SEARCH:
				response = manageSearchResult(obj);
				break;
				
			case ADD:
				response = manageAddResult(obj);
				break;
				
			case EDIT:
				response = manageEditResult(obj);
				break;
				
			case VIEW:
				response = manageViewResult(obj);
				break;
				
			case DELETE:
				response = manageDeleteResult(obj);
				break;
				
			default:
				response = manageResult(obj);
				break;
			}
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	/**
	 * Create {@link CommonResponse} by {@link ActionType}
	 * @param obj
	 * @param actionType
	 * @return
	 */
	public CommonResponse createSuccessResult(Object obj, ActionType actionType) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			ResourceBundle bundle = EExtensionApiUtil.getBundleMessageAlert(getUserLocaleByDefault());
			
			String messageCode = actionType.getMessageSuccessCode();
			String messageDesc = EExtensionApiUtil.getMessage(actionType.getMessageSuccessDesc(), bundle, messageCode);
			
			commonResponse.setData(obj);
			commonResponse.setMessageCode(messageCode);
			commonResponse.setMessageDesc(messageDesc);
			
			manageSuccessDisplay(commonResponse);
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return commonResponse;
	}
	
	private Response manageSearchResult(Object obj) {
		Response response = null;
		try {
			CommonResponse commonResponse = new CommonResponse();

			CommonSearchResult<?> result = (CommonSearchResult<?>) obj;
			if (result.getListResult() != null && !result.getListResult().isEmpty()) {
				commonResponse = createSuccessResult(obj, ActionType.SEARCH);

			} else {
				// Data cannot be found.
				ResourceBundle bundle = EExtensionApiUtil.getBundleMessageAlert(getUserLocaleByDefault());
				
				String messageCode = MessageAlert.DATA_NOT_FOUND.getVal();
				String messageDesc = EExtensionApiUtil.getMessage("Data cannot be found.", bundle, messageCode);
				
				commonResponse.setData(obj);
				commonResponse.setMessageCode(messageCode);
				commonResponse.setMessageDesc(messageDesc);
				
				manageSuccessDisplay(commonResponse);

			}
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.OK).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}

	private Response manageAddResult(Object obj) {
		Response response = null;
		try {
			CommonResponse commonResponse = createSuccessResult(obj, ActionType.ADD);
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.OK).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}

	private Response manageEditResult(Object obj) {
		Response response = null;
		try {
			CommonResponse commonResponse = createSuccessResult(obj, ActionType.EDIT);
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.OK).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	private Response manageViewResult(Object obj) {
		Response response = null;
		try {
			CommonResponse commonResponse = createSuccessResult(obj, ActionType.VIEW);
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.OK).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	private Response manageDeleteResult(Object obj) {
		Response response = null;
		try {
			CommonResponse commonResponse = createSuccessResult(obj, ActionType.DELETE);
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.OK).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	/**
	 * Manage Exception by Default
	 * @param obj
	 * @param exception
	 * @return {@link Response}
	 */
	public Response manageException(Object obj, Exception exception) {
		return manageException(obj, exception, ActionType.DEFAULT);
	}
	
	/**
	 * Manage Exception by {@link ActionType}
	 * @param obj
	 * @param exception
	 * @param actionType
	 * @return {@link Response}
	 */
	public Response manageException(Object obj, Exception exception, ActionType actionType) {
		return manageException(obj, exception, actionType, null);
	}
	
	/**
	 * Manage Exception by MessageCode
	 * @param obj
	 * @param exception
	 * @param actionType
	 * @param messageCode
	 * @return {@link Response}
	 */
	private Response manageException(Object obj, Exception exception, ActionType actionType, String messageCode) {
		/**
		 * getLogger().catching(exception); นำออกก่อนอาจไมไ่ด้ใช้
		 */
		Response response = null;
		try {
			if (exception != null) {
				if (exception instanceof DuplicateException 
						|| exception instanceof MaxExceedAlertException
						|| exception instanceof MaxExceedException
						|| exception instanceof MaxExceedReportException
						) {
					
					/** Return HttpStatus.OK (200) **/
					response = manageBusinessException(obj, exception, messageCode);
					
				} else if (exception instanceof AuthenticateException) {
					response = manageAuthenticateException(obj, exception, messageCode);
					
				} else if (exception instanceof AuthorizationException) {
					response = manageAuthorizationException(obj, exception, messageCode);
					
				} else if (exception instanceof ServerValidateException) {
					response = manageServerValidateException(obj, exception, messageCode);
					
				} else if (exception instanceof InputValidateException) {
					response = manageInputValidateException(obj, exception, messageCode);
					
				} else if (exception instanceof DataNotFoundException) {
					response = manageDataNotFoundException(obj, exception, messageCode);
					
				} else if (exception instanceof TokenExpireException) {
					response = manageTokenExpireException(obj, exception, messageCode);

				} else if (exception instanceof CustomException) {
					response = manageCustomException(obj, exception, actionType, messageCode);
					
				} else if (exception instanceof GetLockException) {
					response = manageGetLockException(obj, exception, actionType, messageCode);
					
				} else {
					response = manageOtherException(obj, exception, actionType, messageCode);
					
				}
				
			}
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	public Response manageException(Object obj, Exception exception, String messageCode) {
		return manageException(obj, exception, ActionType.DEFAULT, messageCode);
	}

	private Response manageBusinessException(Object obj, Exception exception, String messageCode) {
		Response response = null;
		try {
			CommonException ex = (CommonException) exception;
			loggingBusinessException(ex);	// Create Logging Code and Logging Exception
			
			CommonResponse commonResponse = createErrorResultForException(obj, ex, messageCode);
			
			if (exception instanceof DuplicateException) {
				DuplicateException dupEx = (DuplicateException) exception;
				if (dupEx.getInvalidInputs() != null && !dupEx.getInvalidInputs().isEmpty()) {
					commonResponse.setInvalid(dupEx.getInvalidInputs());
				}
			}
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.OK).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	private Response manageAuthenticateException(Object obj, Exception exception, String messageCode) {
		Response response = null;
		try {
			CommonException ex = (CommonException) exception;
			logging(ex);	// Create Logging Code and Logging Exception
			
			CommonResponse commonResponse = createErrorResultForException(obj, ex, messageCode);
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.FORBIDDEN).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	private Response manageAuthorizationException(Object obj, Exception exception, String messageCode) {
		Response response = null;
		try {
			CommonException ex = (CommonException) exception;
			logging(ex);	// Create Logging Code and Logging Exception
			
			CommonResponse commonResponse = createErrorResultForException(obj, ex, messageCode);
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.UNAUTHORIZED).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	private Response manageServerValidateException(Object obj, Exception exception, String messageCode) {
		Response response = null;
		try {
			CommonException ex = (CommonException) exception;
			logging(ex);	// Create Logging Code and Logging Exception
			
			CommonResponse commonResponse = createErrorResultForException(obj, ex, messageCode);
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.BAD_REQUEST).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	private Response manageInputValidateException(Object obj, Exception exception, String messageCode) {
		Response response = null;
		try {
			CommonException ex = (CommonException) exception;
			logging(ex);	// Create Logging Code and Logging Exception
			
			if(StringUtil.stringToNull(messageCode) == null) {
				messageCode = MessageAlert.INSUFFICIENT_DATA.getVal();
			}
			
			CommonResponse commonResponse = createErrorResultForException(obj, ex, messageCode);
			
			InputValidateException inputValidateEx = (InputValidateException)exception;
			if(inputValidateEx.getInvalidInputs() != null && !inputValidateEx.getInvalidInputs().isEmpty()) {
				commonResponse.setInvalid(inputValidateEx.getInvalidInputs());
			}
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.OK).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	private Response manageDataNotFoundException(Object obj, Exception exception, String messageCode) {
		Response response = null;
		try {
			CommonException ex = (CommonException) exception;
			logging(ex);	// Create Logging Code and Logging Exception
			
			CommonResponse commonResponse = createErrorResultForException(obj, ex, messageCode);
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.GONE).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	private Response manageTokenExpireException(Object obj, Exception exception, String messageCode) {
		Response response = null;
		try {
			CommonException ex = (CommonException) exception;
			logging(ex);	// Create Logging Code and Logging Exception
			
			CommonResponse commonResponse = createErrorResultForException(obj, ex, messageCode);
			
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.OK).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	private Response manageCustomException(Object obj, Exception exception, ActionType actionType, String messageCode) {
		Response response = null;
		try {
			CustomException ex = (CustomException) exception;
			logging(ex);	// Create Logging Code and Logging Exception
			
			ResourceBundle bundle = EExtensionApiUtil.getBundleMessageAlert(getUserLocaleByDefault());
			
			// Set Common Response
			String msgCode = messageCode;
			if (messageCode == null || messageCode.trim().isEmpty()) {
				msgCode = ex.getMessage();
			}
			String msgDesc = EExtensionApiUtil.getMessage(actionType.getMessageErrorDesc(), bundle, msgCode);
			// replace xxx
			msgDesc = replaceMessage(msgDesc, ex.getReplaces());
			
			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setData(obj);
			commonResponse.setMessageCode(msgCode);
			commonResponse.setMessageDesc(msgDesc);
			
			// phronphun.s
			if (ex.getInvalidInputs() != null && !ex.getInvalidInputs().isEmpty()) {
				commonResponse.setInvalid(ex.getInvalidInputs());
			}
			
			// Set Common Exception
			String loggingCode = ex.getErrorCode();
			if (ex.getLoggingCode() != null && !ex.getLoggingCode().isEmpty()) {
				loggingCode = ex.getLoggingCode();
			}
			
			CommonError commonError = new CommonError();
			commonError.setErrorCode(loggingCode);
			commonError.setErrorDesc(msgDesc);
			
			commonResponse.setComponentType(ex.getComponentType());
			commonResponse.setDisplayStatus(ex.getDisplayStatus());
			commonResponse.setError(commonError);
	
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.OK).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	private Response manageOtherException(Object obj, Exception exception, ActionType actionType, String messageCode) {
		Response response;
		CommonException ex = null;
		try {
			ex = (CommonException) exception;
		} catch (Exception e) {
			ex = new CommonException(exception);
		}
		
		logging(ex);	// Create Logging Code and Logging Exception
		
		CommonResponse commonResponse = createErrorResult(obj, actionType, ex, messageCode);
		
		String message = getMessage(commonResponse);
		response = Response.status(Response.Status.OK).entity(message).build();
		return response;
	}
	
	private CommonResponse createErrorResult(Object obj, ActionType actionType, Exception exception, String messageCode) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			// Create CommonResponse
			if (actionType == null) {
				actionType = ActionType.DEFAULT;
			}
			
			ResourceBundle bundle = EExtensionApiUtil.getBundleMessageAlert(getUserLocaleByDefault());
			
			if (messageCode == null || messageCode.trim().isEmpty()) {
				messageCode = actionType.getMessageErrorCode();
			}
			
			String messageDesc = EExtensionApiUtil.getMessage(actionType.getMessageErrorDesc(), bundle, messageCode);
			
			commonResponse.setData(obj);
			commonResponse.setMessageCode(messageCode);
			commonResponse.setMessageDesc(messageDesc);
			
			manageErrorDisplay(commonResponse, exception);

		} catch (Exception e) {
			getLogger().catching(e);
		}
		return commonResponse;
	}
	
	private CommonResponse createErrorResultForException(Object obj, Exception exception, String messageCode) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			CommonException ex = (CommonException) exception;
			
			// Create CommonResponse
			ResourceBundle bundle = EExtensionApiUtil.getBundleMessageAlert(getUserLocaleByDefault());
			
			String msgCode = messageCode;
			if (messageCode == null || messageCode.trim().equals("")) {
				msgCode = ex.getErrorCode().replace(ExceptionVariable.EXCEPTION_CODE_PREFIX, ExceptionVariable.EXCEPTION_CODE_REPLACE);
			}
			
			String msgDesc = EExtensionApiUtil.getMessage(bundle, msgCode);
			if(obj instanceof CommonSearchResult && exception instanceof MaxExceedException){
				msgDesc = msgDesc.replace("xxx", new DecimalFormat("#,##0").format(((CommonSearchResult<?>)obj).getTotalResult()));
			}
			
			commonResponse.setData(obj);
			commonResponse.setMessageCode(msgCode);
			commonResponse.setMessageDesc(msgDesc);
			
			// Set Common Exception
			String loggingCode = ex.getErrorCode();
			if (ex.getLoggingCode() != null && !ex.getLoggingCode().isEmpty()) {
				loggingCode = ex.getLoggingCode();
			}
			
			CommonError commonError = new CommonError();
			commonError.setErrorCode(loggingCode);
			commonError.setErrorDesc(msgDesc);
			
			commonResponse.setComponentType(ex.getComponentType());
			commonResponse.setDisplayStatus(ex.getDisplayStatus());
			commonResponse.setError(commonError);
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return commonResponse;
	}
	
	/**
	 * Manage Default Success Display Control
	 * @param response
	 */
	private void manageSuccessDisplay(CommonResponse response) {
		try {
			response.setDisplayStatus(DisplayStatus.SUCCESS.getValue());
			response.setComponentType(ComponentType.SNACK.getValue());
			
		} catch (Exception e) {
			getLogger().error(e);
		}
	}
	
	/**
	 * Manage 
	 * @param response
	 * @param status
	 * @param type
	 */
	private void manageErrorDisplay(CommonResponse response, Exception exception) {
		try {
			// Create CommonError
			if (exception != null) {
				CommonException ex = null;
				if (exception instanceof CommonException) {
					ex = (CommonException) exception;
				} else {
					ex = new CommonException();
				}
	
				String loggingCode = ex.getErrorCode();
				if (ex.getLoggingCode() != null && !ex.getLoggingCode().isEmpty()) {
					loggingCode = ex.getLoggingCode();
				}
				
				ResourceBundle bundle = EExtensionApiUtil.getBundleMessageAlert(getUserLocaleByDefault());

				String errorCode = ex.getErrorCode().replace(ExceptionVariable.EXCEPTION_CODE_PREFIX, ExceptionVariable.EXCEPTION_CODE_REPLACE);
				String errorDesc = EExtensionApiUtil.getMessage(bundle, errorCode);
				
				CommonError commonError = new CommonError();
				commonError.setErrorCode(loggingCode);
				commonError.setErrorDesc(errorDesc);
	
				response.setComponentType(ex.getComponentType());
				response.setDisplayStatus(ex.getDisplayStatus());
				response.setError(commonError);
	
			}
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
	}

	private void logging(CommonException exception) {
		try {
			String loggingCode = exception.getErrorCode() + "-" + getLoggingTime();
			exception.setLoggingCode(loggingCode);
			
			getLogger().error(loggingCode);
			getLogger().catching(exception);
		} catch (Exception e) {
			getLogger().catching(e);
		}
	}
	
	private void loggingBusinessException(CommonException exception) {
		try {
			String loggingCode = exception.getErrorCode() + "-" + getLoggingTime();
			exception.setLoggingCode(loggingCode);
			
			getLogger().warn(loggingCode);
			getLogger().warn(exception);
		} catch (Exception e) {
			getLogger().catching(e);
		}
	}
	
	private String getLoggingTime() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS", ParameterConfig.getApplication().getApplicationLocale());
		return LocalDateTime.now().format(format);
	}
	
	private String replaceMessage(String message, String[] replaces) {
		if (message != null && replaces != null) {
			for (String replace : replaces) {
				int index1 = message.indexOf(REPLACER.toLowerCase());
				int index2 = message.indexOf(REPLACER.toUpperCase());
				if (index1 < 0) {
					message = message.replaceFirst(REPLACER.toUpperCase(), replace);
					continue;
				}
				
				if (index2 < 0) {
					message = message.replaceFirst(REPLACER.toLowerCase(), replace);
					continue;
				}
				
				if (index1 < index2) {
					message = message.replaceFirst(REPLACER.toLowerCase(), replace);
				} else {
					message = message.replaceFirst(REPLACER.toUpperCase(), replace);
				}
			}
		}
		return message;
	}

	/**
	 * Manage Exception by {@link ActionType}
	 * @param obj
	 * @param exception
	 * @param actionType
	 * @return {@link Response}
	 */
	public Response manageDuplicateException(Object obj, Exception exception, String dupMsgDesc) {
		Response response = null;
		try {
			ResourceBundle bundle = EExtensionApiUtil.getBundleMessageAlert(getUserLocaleByDefault());
			
			CommonException ex = (CommonException) exception;
			
			String msgCode = ex.getErrorCode().replace(ExceptionVariable.EXCEPTION_CODE_PREFIX, ExceptionVariable.EXCEPTION_CODE_REPLACE);
			String msgDesc = bundle.getString(msgCode) + " " + dupMsgDesc;
			
			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setData(obj);
			commonResponse.setMessageCode(msgCode);
			commonResponse.setMessageDesc(msgDesc);
			
			// Set Common Exception
			String loggingCode = ex.getErrorCode();
			if (ex.getLoggingCode() != null && !ex.getLoggingCode().isEmpty()) {
				loggingCode = ex.getLoggingCode();
			}
						
			CommonError commonError = new CommonError();
			commonError.setErrorCode(loggingCode);
			commonError.setErrorDesc(msgDesc);
			
			commonResponse.setComponentType(ex.getComponentType());
			commonResponse.setDisplayStatus(ex.getDisplayStatus());
			commonResponse.setError(commonError);
					
			DuplicateException dupEx = (DuplicateException)exception;
			if (dupEx.getInvalidInputs() != null && !dupEx.getInvalidInputs().isEmpty()) {
				commonResponse.setInvalid(dupEx.getInvalidInputs());
			}
			
			String message = JSONObjectMapperUtil.convertObject2Json(commonResponse);
			response = Response.status(Response.Status.OK).entity(message).build();
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;
	}
	
	/**
	 * เพิ่ม GetLockException
	 * @param obj
	 * @param exception
	 * @param actionType
	 * @param messageCode
	 * @return
	 */
	private Response manageGetLockException(Object obj, Exception exception, ActionType actionType,
			String messageCode) {
		getLogger().info("manageGetLockException {}", actionType);
		
		Response response = null;
		try {
			CommonException ex = (CommonException) exception;
			logging(ex);	// Create Logging Code and Logging Exception
			
			CommonResponse commonResponse = new CommonResponse();
			String msgCode = "";
			String msgDesc = ex.getMessage();
			
			if (messageCode == null || messageCode.trim().equals("")) {
				msgCode = ex.getErrorCode().replace(ExceptionVariable.EXCEPTION_CODE_PREFIX, ExceptionVariable.EXCEPTION_CODE_REPLACE);
			} else {
				msgCode = messageCode;
			}
			
			commonResponse.setData(obj);
			commonResponse.setMessageCode(msgCode);
			commonResponse.setMessageDesc(msgDesc);
			
			// Set Common Exception
			String loggingCode = ex.getErrorCode();
			if (ex.getLoggingCode() != null && !ex.getLoggingCode().isEmpty()) {
				loggingCode = ex.getLoggingCode();
			}
			
			CommonError commonError = new CommonError();
			commonError.setErrorCode(loggingCode);
			commonError.setErrorDesc(msgDesc);
			
			commonResponse.setComponentType(ex.getComponentType());
			commonResponse.setDisplayStatus(ex.getDisplayStatus());
			commonResponse.setError(commonError);
	
			String message = getMessage(commonResponse);
			response = Response.status(Response.Status.OK).entity(message).build();
			
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return response;

	}
}
