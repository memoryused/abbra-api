package com.sit.abbra.abbraapi.util.attachment;

import com.sit.abbra.abbraapi.core.attachment.service.AttachMentManager.FilterType;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;

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
}
