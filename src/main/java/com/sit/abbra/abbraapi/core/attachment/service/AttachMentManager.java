package com.sit.abbra.abbraapi.core.attachment.service;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.util.attachment.AttachmentUtil;
import com.sit.core.common.service.CommonManager;
import com.sit.domain.GlobalVariable;
import com.sit.exception.CustomException;

import util.file.FileManagerUtil;


public class AttachMentManager extends CommonManager {

	public enum FilterType {
		ALL("All"), VDO("VDO"), IMG("Image"), DOC("Document"), IMG_DOC("Image,Document");

		private String value;

		private FilterType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	public AttachMentManager(Logger logger) {
		super(logger);
	}

	public String manageFile(File file, FormDataContentDisposition fileMeta,String announceType, String dateStr, FilterType filterType ) throws Exception {
		String uploadedFileLocation = "";
		String[] arrDate = dateStr.split("/");
		String tmpPath = ParameterConfig.getApplication().getSharePath();
		// Encode fileName to UTF-8
		String originalName = new String(fileMeta.getFileName().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
		// /announceType/year/month/fileName
		String tmpSubPath = announceType + GlobalVariable.SLASH + arrDate[2]+ GlobalVariable.SLASH + arrDate[1];
		String tmpName = "/"+ originalName;
		
		try {

			//Validate file size **Maximum allowed size for upload is xxx
			String[] replaces = new String[1];
			getLogger().debug("Validate file size {}",filterType.value);
			if (!AttachmentUtil.checkMaxSizing(file.getAbsolutePath(), originalName, replaces, filterType)) {
				String maximumFileSize = "10054";
				throw new CustomException(maximumFileSize, replaces);
			}
			
			//Validate file extension
			// ใช้ originalName แทน fileMeta
			getLogger().debug("Validate file extension {}",filterType.value);
			if(!AttachmentUtil.checkFileType(originalName, filterType, replaces)) {	
				String fileType = "10055";
				throw new CustomException(fileType, replaces);
			}
			
			uploadedFileLocation = tmpSubPath + tmpName;
			FileManagerUtil.crateDirectoryWithoutOverwrite(tmpPath+tmpSubPath);
		
		} catch (Exception e) {
			getLogger().catching(e);
			throw e;
		}
		
		return uploadedFileLocation;
	}
	
}
