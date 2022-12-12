package com.sit.abbra.abbraapi.util.attachment;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.sit.abbra.abbraapi.core.attachment.domain.Attachment;
import com.sit.abbra.abbraapi.core.attachment.service.AttachMentManager.FilterType;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.enums.ActionType;
import com.sit.abbra.abbraapi.util.date.DateUtil;
import com.sit.abbra.abbraapi.util.response.ResponseMessageUtil;
import com.sit.abbra.abbraapi.util.security.EExtensionApiSecurityUtil;
import com.sit.domain.FileAttatchData;
import com.sit.domain.GlobalVariable;
import com.sit.exception.CustomException;

import util.database.connection.CCTConnection;
import util.file.FileManagerUtil;
import util.string.StringUtil;

public class AttachmentUtil {

	// REVIEW : Code Analysis by Anusorn.l : Utility classes should not have public constructors
	private AttachmentUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	/**
	 * Check max file size
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static boolean checkMaxSizing(String fileTmpPath, String filename, String[] replaces, FilterType filterType) throws Exception {
		long fileSize = ParameterConfig.getAttachment().getMaxFilesize();
		
		// VDO
		if(FilterType.ALL.equals(filterType) || FilterType.VDO.equals(filterType) ) {
			for (String ft : ParameterConfig.getAttachment().getVdoType()) {
				if (filename.toUpperCase().endsWith(ft.toUpperCase())) {
					// VDO file
					fileSize = ParameterConfig.getAttachment().getMaxVdosize();
					break;
				}
			}
			
		}
		
		replaces[0] = String.valueOf(fileSize).concat(" MB");
		// REVIEW-21 : Code Analysis by Anusorn.l : Collapsible "if" statements 
		return StringUtil.stringToNull(fileTmpPath) != null && FileManagerUtil.getSizeOfFile(fileTmpPath) <= (fileSize * 1024 * 1024);
	}
	
	
	
	/**
	 * Check file img,pdf extension
	 * @param filename
	 * @param filterType
	 * @return
	 * @throws Exception
	 */
	public static boolean checkFileType(String filename, FilterType filterType, String[] replaces) throws Exception {
		boolean valid = false;

		// Image,pdf,...
		if(StringUtil.stringToNull(filename) != null ) {
			switch (filterType) {
			  case ALL:
				  valid = validateFileAll(filename);
				  replaces[0] = String.join(",", ParameterConfig.getAttachment().getImgType())
						  +","+String.join(",", ParameterConfig.getAttachment().getVdoType())
						  +","+String.join(",", ParameterConfig.getAttachment().getFileType());
				  break;
			  case DOC:
				  valid = validateFileDoc(filename);
				  replaces[0] = String.join(",", ParameterConfig.getAttachment().getFileType());
				  break;
			  case IMG:
				  valid = validateFileImg(filename);
				  replaces[0] = String.join(",", ParameterConfig.getAttachment().getImgType());
				  break;
			  case VDO:
				  valid = validateFileVdo(filename);
				  replaces[0] = String.join(",", ParameterConfig.getAttachment().getVdoType());
				  break;
			  case IMG_DOC:
				  valid = validateFileImgDoc(filename);
				  replaces[0] = String.join(",", ParameterConfig.getAttachment().getImgType())
						  +","+String.join(",", ParameterConfig.getAttachment().getFileType());
				  break;
			}
		}
		
		return valid;
	}
	
	/**
	 * Validate file extension
	 * 
	 * @param filename
	 * @param filterType
	 * @return
	 * @throws Exception 
	 */
	public static boolean validateFileAll(String filename) throws Exception {
		boolean valid = false;

		// Image,pdf, vdo, ...
		if(validateFileImg(filename) || validateFileVdo(filename) || validateFileDoc(filename) ) {
			valid = true;
		}
		
		return valid;
	}
	
	/**
	 * Validate file image extension
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static boolean validateFileImg(String filename) throws Exception {
		boolean valid = false;
		// Image
		if(StringUtil.stringToNull(filename) != null ) {
			for (String ft : ParameterConfig.getAttachment().getImgType()) {
				if (filename.toUpperCase().endsWith(ft.toUpperCase())) {
					valid = true;
					break;
				}
			}
		}
		return valid;
				
	}
	
	/**
	 * Validate file document extension
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static boolean validateFileDoc(String filename) throws Exception {
		boolean valid = false;
		// Docuemnt
		if(StringUtil.stringToNull(filename) != null ) {
			for (String ft : ParameterConfig.getAttachment().getFileType()) {
				if (filename.toUpperCase().endsWith(ft.toUpperCase())) {
					valid = true;
					break;
				}
			}
		}
		return valid;
				
	}
	
	/**
	 * Validate file vdo extension
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static boolean validateFileVdo(String filename) throws Exception {
		boolean valid = false;
		// VDO
		if(StringUtil.stringToNull(filename) != null ) {
			for (String ft : ParameterConfig.getAttachment().getVdoType()) {
				if (filename.toUpperCase().endsWith(ft.toUpperCase())) {
					valid = true;
					break;
				}
			}
		}
		return valid;
				
	}
	
	/**
	 * Validate file image and document extension
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static boolean validateFileImgDoc(String filename) throws Exception {
		boolean valid = false;
		
		if(StringUtil.stringToNull(filename) != null ) {
			// img
			for (String ft : ParameterConfig.getAttachment().getImgType()) {
				if (filename.toUpperCase().endsWith(ft.toUpperCase())) {
					return true;
				}
			}
			// document
			for (String ft : ParameterConfig.getAttachment().getFileType()) {
				if (filename.toUpperCase().endsWith(ft.toUpperCase())) {
					return true;
				}
			}
		}
		return valid;
	}
	
	/**
	 * ใช้กับระบบที่ระบุ fileType
	 * REVIEW : Code Analysis by Anusorn.l : Methods should not have too many parameters
	 * @param request
	 * @param jsonReq
	 * @param fileMeta
	 * @param file
	 * @param operatorId
	 * @param lstFilesType
	 * @param className
	 * @param logger
	 * @return
	 * @throws Exception
	 */
	public static Response upload(HttpServletRequest request, FileAttatchData fileAttatchData, String operatorId, String[] lstFilesType, Object className, Logger logger) throws Exception {
		List<String> listOperator = new ArrayList<>();
		listOperator.add(operatorId);
		return upload(request, fileAttatchData, listOperator, operatorId, lstFilesType, className, logger);
	}
	
	/**
	 * ใช้กับระบบที่ส่ง listOperator มา
	 * REVIEW : Code Analysis by Anusorn.l : Methods should not have too many parameters
	 * @param request
	 * @param jsonReq
	 * @param fileMeta
	 * @param file
	 * @param listOperator
	 * @param operatorId
	 * @param className
	 * @param logger
	 * @return
	 * @throws Exception
	 */
	public static Response upload(HttpServletRequest request, FileAttatchData fileAttatchData, 
			List<String> listOperator, String operatorId, Object className, Logger logger) throws Exception {
		
		List<String> list = new ArrayList<String>(Arrays.asList(ParameterConfig.getAttachment().getFileType()));
		list.addAll(Arrays.asList(ParameterConfig.getAttachment().getImgType()));
		list.addAll(Arrays.asList(ParameterConfig.getAttachment().getVdoType()));
		
		String[] lstFilesType = list.toArray(new String[0]);
		return upload(request, fileAttatchData, listOperator, operatorId, lstFilesType, className, logger);
	}
	
	private static Response upload(HttpServletRequest request, FileAttatchData fileAttatchData, 
			List<String> listOperator, String operatorId, String[] lstFilesType, Object className, Logger logger) throws Exception {

		logger.info("##### UPLOAD FILE #####");

		Response response = null;
		ResponseMessageUtil messageUtil = null;
		Attachment attResult = new Attachment();

		try {
			// #1 Get User Login
			LoginUser loginUser = (LoginUser) request.getAttribute(ParameterConfig.getSecurityUtilConfig().getReqKeyUser());

			// #2 initial message util
			messageUtil = new ResponseMessageUtil(logger, loginUser);

			// #3 Validate permission by call common service
			//AuthorizeManager authorizeManager = new AuthorizeManager(logger);
			//authorizeManager.checkAuthorizeMethod(loginUser, listOperator, operatorId, fileAttatchData.getJsonReq(), request.getPathInfo());

			// #5 Call manage Business
			String tempfile = writeToFile(fileAttatchData.getFile(), fileAttatchData.getFileMeta(), lstFilesType, loginUser, logger);
			attResult.setRep(EExtensionApiSecurityUtil.encryptId(loginUser.getSalt(), loginUser.getSecret(), tempfile));

			// #6 call manage result
			response = messageUtil.manageResult(attResult, ActionType.UPLOAD);

		} catch (CustomException e) {
			// call CustomException
			response = messageUtil.manageException(attResult, e);

		} catch (Exception e) {
			logger.catching(e);
			
			// call manage exception
			if (messageUtil != null) {
				response = manageException(messageUtil, attResult, e, ActionType.UPLOAD);
			}
			
			// Add Log event Error
			//LogManager logManager = new LogManager(logger);
			//logManager.addError(Operator.AGR_EDIT.getOperator(), request, className, e);
		}

		return response;
	}
	

	/**
	 * save uploaded file to new location
	 * 
	 * @param file
	 * @param fileMeta
	 * @param lstFilesType 
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	private static String writeToFile(File file, FormDataContentDisposition fileMeta, String[] lstFilesType, LoginUser loginUser, Logger logger) throws Exception {
		String uploadedFileLocation = "";
		String tmpPath = ParameterConfig.getApplication().getSharePath() + "/temp/" + loginUser.getToken(); // userId ไม่เพียงพอ
																								// ต้องใช้ actionToken
		// REVIEW : Code Analysis by Anusorn.l : "StandardCharsets" constants should be preferred
		String originalName = new String(fileMeta.getFileName().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
		String tmpName = "/" + Calendar.getInstance(Locale.ENGLISH).getTimeInMillis()
				+ GlobalVariable.SPLITER_FILE_UPLOAD_KEYWORD + originalName;

		try {
			// 1.Validate Windows filename 
			if (!AttachmentUtil.isValidFileName(originalName)) {
				String[] replaces = { "file name" };
				String invalidFormat = "10500";
				throw new CustomException(invalidFormat, replaces);
			}
			
			// 2.Validate file size **Maximum allowed size for upload is xxx
			if ( !AttachmentUtil.checkMaxSizing(file.getAbsolutePath(), ParameterConfig.getAttachment().getMaxFilesize()) ) {
				String[] replaces = { String.valueOf(ParameterConfig.getAttachment().getMaxFilesize()).concat(" MB") };
				String maximumFileSize = "10054";
				throw new CustomException(maximumFileSize, replaces);
			}
			
			// 3.Validate file extension
			if (!AttachmentUtil.checkFileType(originalName, lstFilesType)) {
				String fileType = "10055";
				throw new CustomException(fileType, lstFilesType);
			}
			
			uploadedFileLocation = tmpPath + tmpName;
			FileManagerUtil.crateDirectoryWithoutOverwrite(tmpPath);
			FileManagerUtil.copy(file.getAbsolutePath(), uploadedFileLocation);

			// 4.Post check after copy (check size 0)
			if ( AttachmentUtil.checkMaxSizing(uploadedFileLocation, 0) ) {
				String failToUpload = "30111";
				throw new CustomException(failToUpload, "");
			}
			
		} catch (Exception e) {
			// REVIEW-14 : Code Analysis by Anusorn.l : Rethrow the exception
			logger.catching(e);
			throw e;
		}

		return uploadedFileLocation;
	}
	

	/**
	 * Check max file size
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static boolean checkMaxSizing(String fileTmpPath, long fileSize) throws Exception {
		// REVIEW-21 : Code Analysis by Anusorn.l : Collapsible "if" statements 
		return StringUtil.stringToNull(fileTmpPath) != null && FileManagerUtil.getSizeOfFile(fileTmpPath) <= (fileSize * 1024 * 1024);
	}

	/**
	 * Validate Windows filename 
	 * {@link} https://docs.microsoft.com/en-us/windows/win32/fileio/naming-a-file?redirectedfrom=MSDN#file_and_directory_names
	 * {@link} https://stackoverflow.com/questions/6730009/validate-a-file-name-on-windows
	 * @param text
	 * @return
	 */
	public static boolean isValidFileName(String text) {
		Pattern pattern = Pattern.compile(
				"# Match a valid Windows filename (unspecified file system).          \n"
						+ "^                                # Anchor to start of string.        \n"
						+ "(?!                              # Assert filename is not: CON, PRN, \n"
						+ "  (?:                            # AUX, NUL, COM1, COM2, COM3, COM4, \n"
						+ "    CON|PRN|AUX|NUL|             # COM5, COM6, COM7, COM8, COM9,     \n"
						+ "    COM[1-9]|LPT[1-9]            # LPT1, LPT2, LPT3, LPT4, LPT5,     \n"
						+ "  )                              # LPT6, LPT7, LPT8, and LPT9...     \n"
						+ "  (?:\\.[^.]*)?                  # followed by optional extension    \n"
						+ "  $                              # and end of string                 \n"
						+ ")                                # End negative lookahead assertion. \n"
						+ "[^<>:\"/\\\\|?*\\x00-\\x1F]*     # Zero or more valid filename chars.\n"
						+ "[^<>:\"/\\\\|?*\\x00-\\x1F\\ .]  # Last char is not a space or dot.  \n"
						+ "$                                # Anchor to end of string.            ",
				Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.COMMENTS);
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}
	

	/**
	 * Check file extension : return TRUE when file extension.
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static boolean checkFileType(String filename, String[] fileType) throws Exception {
		boolean valid = false;

		// REVIEW : Code Analysis by Anusorn.l : Generic exceptions should never be thrown
		if (StringUtil.stringToNull(filename) != null) {
			for (String ft : fileType) {
				if (filename.toUpperCase().endsWith(ft.toUpperCase())) {
					valid = true;
					break;
				}
			}
		}
		return valid;
	}
	
	// REVIEW-7 : Code Analysis by Sittipol.m
	private static Response manageException(ResponseMessageUtil messageUtil, Object object, Exception e, ActionType actionType) {
		if (messageUtil == null) {
			return null;
		}
		
		return messageUtil.manageException(object, e, actionType);
	}

	/**
	 * method for save file (server)
	 * 
	 * @param encrypte
	 * @param sysName
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public static String saveFileServerPath(CCTConnection conn, String encrypte, String sysName, LoginUser loginUser) throws Exception {

		// decrypt get file-name
		String tmpFile = EExtensionApiSecurityUtil.decryptId(loginUser.getSalt(), loginUser.getSecret(), encrypte);
		
		StringBuilder savePath = new StringBuilder();
		savePath.append(sysName);
		savePath.append(File.separator);	// REVIEW : Code Analysis by Anusorn.l : URIs should not be hardcoded
		savePath.append(DateUtil.getCurrentDateStr(conn.getConn(), "yyyyMMdd"));
		savePath.append(File.separator);
		FileManagerUtil.crateDirectoryWithoutOverwrite(ParameterConfig.getApplication().getSharePath() + savePath.toString());

		String ftemp = java.nio.file.Paths.get(tmpFile).getFileName().toString();
		String orgFileNameArr = ftemp.split(GlobalVariable.SPLITER_FILE_UPLOAD_KEYWORD)[1];
		
		StringBuilder saveFileLocation = new StringBuilder();
		saveFileLocation.append(savePath.toString());
		saveFileLocation.append(orgFileNameArr);

		FileManagerUtil.copy(tmpFile, ParameterConfig.getApplication().getSharePath() + saveFileLocation.toString());
		
		return saveFileLocation.toString();
	}
}
