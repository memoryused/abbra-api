package com.sit.abbra.abbraapi.util.date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;

import com.sit.domain.GlobalVariable;

import util.database.connection.CCTConnectionUtil;
import util.log4j2.DefaultLogUtil;
import util.sql.SQLParameterizedUtil;

public class DateUtil {

	// REVIEW : Code Analysis by anusorn.l : Utility classes should not have public
	// constructors
	private DateUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final Logger logger = DefaultLogUtil.UTIL;

//////////////////////////////////////////FOR INSERT IN SQL /////////////////////////////////////////////////////////
	/**
	 * ทำหน้าที่ดึงข้อมูลวันที่ปัจจุบันเป็นรูปแบบ String เพื่อเตรียมไป Insert ตรงๆใน
	 * sql
	 * 
	 * @param format (yyyy-MM-dd HH:mm:ss.mmm)
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentDateStr(Connection conn, String formatInsert) throws Exception {
		String currentDateStr = null;
		try {
			Date date = getCurrentDateDB(conn);
			currentDateStr = convertUtcToDisplay(date, formatInsert);

		} catch (Exception e) {
			getLogger().catching(e);
			throw e;
		}
		return currentDateStr;
	}

////////////////////////////////////////// FOR DISPLAY /////////////////////////////////////////////////////////

	/**
	 * ทำหน้าที่แปลงข้อมูล Date เป็น String รูปแบบที่กำหนด เนื่องจากข้อมูลวันที่ใน
	 * db ถูกเก็บเป็น UTC ในโครงการจะให้แสดงผลบนหน้าจอเป็น UTC ด้วย
	 * 
	 * @param date
	 * @param dscFormat format ปลายทางที่ต้องการแสดงผล
	 * @return
	 */
	public static String convertUtcToDisplay(Date date, String dscFormat) { 
		// REVIEW : Code Analysis by anusorn.l : Generic exceptions should never be thrown
		String dateStr = null;
		try {
			if (date != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(dscFormat, Locale.ENGLISH);
				dateStr = dateFormat.format(date);
			}
		} catch (Exception e) {
			getLogger().catching(e);
			throw e;
		}
		return dateStr;
	}

	public static Date getCurrentDateDB(Connection conn) throws Exception {
		Date date = null;

		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			//stmt = SQLParameterizedUtil.createPrepareStatement(conn, "select CURRENT TIMESTAMP as cur from SYSIBM.SYSDUMMY1");
			stmt = SQLParameterizedUtil.createPrepareStatement(conn, "SELECT SYSDATE() AS cur");
			rst = stmt.executeQuery();
			if (rst.next()) {
				date = rst.getTimestamp("cur");
			}
		} catch (Exception e) {
			getLogger().catching(e);
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return date;
	}
	
	
	public static String convertFormatDate(String dateStr, String fromFormat, String toFormat) throws ParseException {
		String result = null;

		if (dateStr != null && !dateStr.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat(fromFormat, Locale.ENGLISH);
			Date d = sdf.parse(dateStr);
			sdf.applyPattern(toFormat);
			result = sdf.format(d);
		}

		return result;
	}
	
	/**
	 * หาจำนวนวันที่-เวลาต่างกัน
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getDiffDateTime(Date start, Date end) {
		
		Calendar calStart = Calendar.getInstance(Locale.ENGLISH);
		calStart.setTime(start);
		calStart.getTimeInMillis();
		
		Calendar calEnd = Calendar.getInstance(Locale.ENGLISH);
		calEnd.setTime(end);
		calEnd.getTimeInMillis();
		
	    long diff = calEnd.getTimeInMillis() - calStart.getTimeInMillis();
	    return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * หาจำนวนวันที่ต่างกันระหว่างวันที่ ไม่รวมเวลา
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getDiffDay(Date start, Date end) {
		
		Calendar calStart = Calendar.getInstance(Locale.ENGLISH);
		calStart.setTime(start);
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calStart.set(Calendar.MINUTE, 0);
		calStart.set(Calendar.SECOND, 0);
		calStart.set(Calendar.MILLISECOND, 0);
		calStart.getTimeInMillis();
		
		Calendar calEnd = Calendar.getInstance(Locale.ENGLISH);
		calEnd.setTime(end);
		calEnd.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.set(Calendar.MINUTE, 0);
		calEnd.set(Calendar.SECOND, 0);
		calEnd.set(Calendar.MILLISECOND, 0);
		calEnd.getTimeInMillis();
		
	    long diff = calEnd.getTimeInMillis() - calStart.getTimeInMillis();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * ทำหน้าที่แปลง date string เป็น date
	 * 
	 * @param dateStr
	 * @param dateFormat
	 * @param locale
	 * @return
	 * @throws ParseException 
	 * @throws Exception
	 */
	public static Date convertStrToDate(String dateStr, String dateFormat, Locale locale) throws ParseException {
		Date date = null;
		try {
			if (dateStr != null && dateFormat != null) {
				if (locale != null) {
					date = new SimpleDateFormat(dateFormat, locale).parse(dateStr);
				} else {
					date = new SimpleDateFormat(dateFormat, Locale.ENGLISH).parse(dateStr);
				}
			}

		} catch (ParseException e) {
			getLogger().catching(e);
			throw e;
		}
		return date;
	}

	/**
	 * เปลื่ยน String ให้เป็น Date
	 * @param input
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String input, String format) throws ParseException {
		if (input == null) {
			return null;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, new Locale("en", "EN"));
		return dateFormat.parse(input);
	}
	
	/**
	 * เปลื่ยน Date ให้ String
	 * @param date
	 * @param format
	 * @return
	 */
	public static String toString(Date date, String format) {
		if (date == null) {
			return null;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
		return dateFormat.format(date);
	}
	
	/**
	 * ดึง Date
	 * @param format
	 * @return
	 */
	public static String getDateTimeString(String format) {
		return getTime(format);
	}
	
	private static String getTime(String format) {
		SimpleDateFormat sdfLocal = new SimpleDateFormat(format, GlobalVariable.LOCALE_INSERT_LOG);
		return sdfLocal.format(new Date());
	}
	
	public static Logger getLogger() {
		return logger;
	}
}
