package com.sit.abbra.abbraapi.util.zpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.Logger;

import util.log4j2.DefaultLogUtil;

public class ZPLUtil {

	public static final String DEFAULT_PATH = "com/sit/pibics/eextensionapi/util/zpl/ZPL_Template.txt";

	private static Logger logger = DefaultLogUtil.UTIL;
	private static String SPACE = "_20";

	private ZPLUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String getZPL(String ackNo, String visaNo, String issueDate, String permitedUpTo, String category, String passportNo) {

		String zpl = null;
		try {
			String zplTemplate = getZPLTemplate();

			if (zplTemplate != null) {
				zpl = String.format(zplTemplate, ackNo.replace(" ", SPACE), visaNo.replace(" ", SPACE), issueDate.replace(" ", SPACE), permitedUpTo.replace(" ", SPACE), category.replace(" ", SPACE), passportNo.replace(" ", SPACE));
			} else {
				getLogger().error("ZPL Template is null...");
			}
		} catch (Exception e) {
			getLogger().catching(e);
		}

		return zpl;
	}

	private static String getZPLTemplate() throws IOException {

		String zplTemplate = null;
		InputStream inputStream = null;
		try {
			// Load resource from path
			ClassLoader classLoader = (ZPLUtil.class).getClassLoader();
			inputStream = classLoader.getResourceAsStream(DEFAULT_PATH);

			zplTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
		} catch (Exception e) {
			getLogger().catching(e);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		return zplTemplate;
	}

	private static Logger getLogger() {
		return logger;
	}

}
