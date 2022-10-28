package com.sit.abbra.abbraapi.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Base64;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;
import util.bundle.BundleUtil;
import util.log4j2.DefaultLogUtil;

public class EExtensionApiUtil {
	
	private EExtensionApiUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static ResourceBundle getBundleMessageAlert(Locale locale) throws Exception {
		return BundleUtil.getBundle("resources.bundle.common.MessageAlert", locale);
	}
	
	public static String getMessage(ResourceBundle bundle, String key) {
		return getMessage("", bundle, key);
	}
	
	public static String getMessage(String defaultMessage, ResourceBundle bundle, String key) {
		String result = defaultMessage;
		try {
			result = bundle.getString(key);
		} catch (Exception e) {
			getLogger().warn("Key not found = {}", key);
			getLogger().warn("User message default = {}", defaultMessage);
		}
		return result;
	}
	
	public static ResourceBundle getBundleMessageSecurity(Locale locale) throws Exception {
		return BundleUtil.getBundle("resources.bundle.security.MessageSecurity", locale);
	}
	
	public static String encodeByteToHex(byte[] bytes) {
	    return DatatypeConverter.printHexBinary(bytes).toLowerCase();
	}

	public static byte[] decodeHexToByte(String hexString) {
	    return DatatypeConverter.parseHexBinary(hexString);
	}
	
	
	public static String getRedirectLoginURLFromAPI(String fromURL) {
		String toURL = "";
		// [0]=DOMAIN_NAME_URL, [1]=REDIRECT_URL
		for (String urlConfig : ParameterConfig.getParameter().getCheckRedirectLogin().getRedirect()) {
			String[] urlArray = urlConfig.split(",");
			if (urlArray[1].equals(fromURL)) {
				toURL = urlArray[0];
			}
		}
		// ถ้าไม่ Match เลยจะให้ไปไหน
		return toURL;
	}
	
	/**
	 * ใช้สำหรับ login.jsp ที่ไม่ส่ง ID มาเข้ามา
	 * @param hostURL
	 * @return
	 */
	public static String getRedirectLoginURLFromHostURL(String hostURL) {
		String toURL = "";
		for (String urlConfig : ParameterConfig.getCheckRedirectLogin().getRedirect()) {
			// [0]=DOMAIN_NAME_URL, [1]=REDIRECT_URL
			String[] urlArray = urlConfig.split(",");
			if(urlArray[0].indexOf(hostURL) != -1) {
				toURL = urlArray[1];
			}
		}
		return toURL;
	}
	
	/**
	 * ใช้สำหรับแปลงค่า String of long ให้เป็น Long เพื่อใช้ในการ execute query
	 *
	 * @param value
	 * @return
	 */
	public static Long convertLongValue(String value) {
		if ((value == null) || value.trim().isEmpty()) {
			return null;
		} else {
			return Long.parseLong(value);
		}
	}

	/**
	 * ใช้สำหรับแปลงค่า String of double ให้เป็น Double เพื่อใช้ในการ execute query
	 *
	 * @param value
	 * @return
	 */
	public static Double convertDoubleValue(String value) {
		if ((value == null) || value.trim().isEmpty()) {
			return null;
		} else {
			return Double.parseDouble(value);
		}
	}
	
	/**
	 * Priority Style
	 * @param priority
	 * @param activeCode
	 * @return
	 */
	public static String getPriorityStyle(String priority, String activeCode) {
		String classRow = ""; /* ปกติ */
		
		  if(activeCode.equals("N")) {/* ยกเลิก */
		    classRow = "priority-inactive";

		  } else if(priority.equals("1")) {/* ด่วนพิเศษ */
		    classRow = "priority-sp-urgent";

		  } else if(priority.equals("2")) {/* ด่วน */
		    classRow = "priority-urgent";

		  }
		return classRow;
	}
	
	/**
	 * 
	 * @param pullPathFile = /opt/data/extension_ws/dev/20220921/2060-20220921141135/image-person_2060_20220921141135483.png
	 * @return
	 * @throws Exception
	 */
	public static String getThumbnailByScale(String pullPathFile, double scale, boolean isWatermark, String watermarkSource) {

		String fileBase64 = ""; 
		try {
			
			String pathFileThumb = appendPrefix(pullPathFile, "/thumbnail.");
			
			// 1. หาว่ามีการสร้าง thumbnail แล้วหรือยัง
			if(!new File(pathFileThumb).exists()) {
				// Create thumbnail
				File destinationDir = new File(pullPathFile.substring(0, pullPathFile.lastIndexOf("/")));
				
				if(isWatermark) {
					BufferedImage watermarkImage = ImageIO.read(new File(watermarkSource));
					Thumbnails.of(pullPathFile)
					.watermark(Positions.BOTTOM_RIGHT, watermarkImage, 0.5f)
					.scale(scale)
					.toFiles(destinationDir, Rename.PREFIX_DOT_THUMBNAIL);	
					
				} else {
					Thumbnails.of(pullPathFile)
					.scale(scale)
					.toFiles(destinationDir, Rename.PREFIX_DOT_THUMBNAIL);	
				}
			}
			
			byte[] fileContent = FileUtils.readFileToByteArray(new File(pathFileThumb));
			fileBase64 = Base64.getEncoder().encodeToString(fileContent);

		} catch (Exception e) {
			// ถ้าเกิด Exception ใดๆก็ตามที่ทำให้ไม่สามารถแสดงรูปได้ แต่ระบบยังต้องทำงานต่อ
			getLogger().error("", e);
		}
		return fileBase64;
	}
	
	public static String convertBase64(String pathFile) throws Exception {
		String fileBase64 = ""; 
		byte[] fileContent = FileUtils.readFileToByteArray(new File(pathFile));
		fileBase64 = Base64.getEncoder().encodeToString(fileContent);
		return fileBase64;
	}
	
	private static String appendPrefix(String fileName, String prefix) {
		StringBuilder myName = new StringBuilder(fileName);
		int idx = myName.toString().lastIndexOf("/");
		myName.replace(idx, idx+1, prefix);
		return myName.toString();
	}

	public static Logger getLogger() {
		return DefaultLogUtil.UTIL;
	}
}
