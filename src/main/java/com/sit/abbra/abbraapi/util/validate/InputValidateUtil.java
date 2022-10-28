package com.sit.abbra.abbraapi.util.validate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.common.CommonInputValidate;

import util.log4j2.DefaultLogUtil;


public class InputValidateUtil {
	
	/**
	 * @param inputValue
	 * @return <br>
	 * true เมื่อมีค่าเช่น A, B <br>
	 * false เมื่อเป็น null หรือ empty 
	 */
	public static boolean hasValue(Object inputValue) {
		if (isNull(inputValue)) {
			return false;
		} else {
			return !String.valueOf(inputValue).trim().isEmpty();
		}
	}

	public static boolean hasValue(String inputValue, String... checkValues) {
		boolean hasValue = false;
		if (checkValues == null) {
			return hasValue;
		} else if (inputValue == null) {
			return hasValue;
		} else {
			for (int i = 0; i < checkValues.length; ++i) {
				if (inputValue.equals(checkValues[i])) {
					hasValue = true;
					break;
				}
			}

			return hasValue;
		}
	}

	/**
	 * 
	 * @param object
	 * @return <br>
	 * true เมื่อเป็น null <br>
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

	
	/**
	 * 
	 * @param inputDateStr
	 * @param format
	 * @param logger
	 * @return <br>
	 * true เมื่อคือวันที่
	 */
	public static boolean isDate(String inputDateStr, String format, Logger logger) {
		boolean isValid = false;

		try {
			if (format != null && !format.equals("")) {
				SimpleDateFormat e = new SimpleDateFormat(format, new Locale("en", "EN"));
				Date date = e.parse(inputDateStr);
				String outputDateStr = e.format(date);
				if (inputDateStr.equals(outputDateStr)) {
					isValid = true;
				}
			}
		} catch (Exception arg6) {
			logger.catching(arg6);
		}

		return isValid;
	}
	
	/**
	 * ตรวจสอบ Format เวลา HH:mm
	 * @param value
	 * @param logger
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง, เป็น null หรือ empty 
	 */
    public static boolean isTime24Hours(String value, Logger logger) 
    { 
    	boolean isValid = false;
        try {
	        if (isNull(value)) {
				return isValid;
			}
	  
	        // Regex to check valid time in 24-hour format. 
	        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]"; 
	  
	        // Compile the ReGex 
	        Pattern p = Pattern.compile(regex); 
	        
	        Matcher m = p.matcher(value); 
	        return m.matches(); 
			
			
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
        return isValid;
    } 
	
    /**
	 * ตรวจสอบ Format ตัวเลข
	 * @param value
	 * @param logger
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง, เป็น null หรือ empty 
	 */
	public static boolean isNumber(String value, Logger logger) {
		boolean isValid = false;
		try {
			if (isNull(value)) {
				return isValid;
			}
			return value.matches("\\d+");
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
		return isValid;
	}

	/**
	 * ตรวจสอบ Format ตัวเลขทศนิยม
	 * @param value
	 * @param logger
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง, เป็น null หรือ empty 
	 */
	public static boolean isDecimalNumber(String value, Logger logger) {
		boolean isValid = false;
		try {
			if (isNull(value)) {
				return isValid;
			}
			return value.matches("^(\\d*\\.)?\\d+$");
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
		return isValid;
	}
	
	// แก้ไข Validate Email ตาม Sonarqube เรื่อง Make sure the regex used here cannot lead to denial of service.
	public static boolean isEmail(String value, Logger logger) {
		boolean isValid = false;
		try {
			if (isNull(value)) {
				return isValid;
			}
			
			// https://howtodoinjava.com/java/regex/java-regex-validate-email-address/
			String regexStandard = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+[a-zA-Z]{2,6}$"; 
	        Pattern p = Pattern.compile(regexStandard); 
	        Matcher m = p.matcher(value.trim()); 
	        
	        if (m.matches()) {
	        	// หาเพิ่มเติมคือ .., .a, @., .123
	        	String regexExtra = "(\\.{2})|(\\.[\\w]{1}$)|(@\\.)|(\\.[\\d]+$)|(\\.@)"; 
		        p = Pattern.compile(regexExtra); 
		        m = p.matcher(value.trim());
		        if (!m.find()) {
		        	isValid = true;
		        }
	        }
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
		return isValid;
	}
	
	/**
	 * ตรวจสอบ Format อักษร ENG, ตัวใหญ่+ตัวเล็ก, ตัวเลข, space, .,-/()'_*
	 * @param value
	 * @param logger
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง, เป็น null หรือ empty 
	 */
	public static boolean isEngNumberSpecial(String value, Logger logger) {
		boolean isValid = false;
		try {
			if (isNull(value)) {
				return isValid;
			}
			return value.matches("^[a-zA-Z0-9.,-/()'_*\\s]+$");
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
		return isValid;
	}

	/**
	 * ตรวจสอบ Format อักษร ENG, ตัวใหญ่, ตัวเลข*
	 * @param value
	 * @param logger
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง, เป็น null หรือ empty 
	 */
	public static boolean isEngUpperNumber(String value, Logger logger) {
		boolean isValid = false;
		try {
			if (isNull(value)) {
				return isValid;
			}
			return value.matches("^[A-Z0-9\\s]+$");
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
		return isValid;
	}
	
	public static boolean isEngUpperNumberWithoutSpace(String value, Logger logger) {
		boolean isValid = false;
		try {
			if (isNull(value)) {
				return isValid;
			}
			return value.matches("^[A-Z0-9]+$");
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
		return isValid;
	}

	/**
	 * ตรวจสอบ Format อักษร ENG, ตัวใหญ่+ตัวเล็ก, ตัวเลข, -
	 * @param value
	 * @param logger
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง, เป็น null หรือ empty
	 */
	public static boolean isEngNumberHyphen(String value, Logger logger) {
		boolean isValid = false;
		try {
			if (isNull(value)) {
				return isValid;
			}
			return value.matches("^[a-zA-Z0-9-]+$");
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
		return isValid;
	}
	
	/**
	 * ตรวจสอบ Format อักษร ENG ตัวหใญ่+เล็ก  และ Space
	 * @param value
	 * @param logger
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง, เป็น null หรือ empty 
	 */
	public static boolean isEngSpace(String value, Logger logger) {
		boolean isValid = false;
		try {
			return value.matches("^[A-Za-z\\s]+$");
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
		return isValid;
	}
	

	/**
	 * ตรวจสอบ Format อักษร ไทย+ENG, ตัวใหญ่+ตัวเล็ก, ตัวเลข, space, .,-/()'_*
	 * @param value
	 * @param logger
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง, เป็น null หรือ empty 
	 */
	public static boolean isThaEngNumberSpecial(String value, Logger logger) {
		boolean isValid = false;
		try {
			if (isNull(value)) {
				return isValid;
			}
			return value.matches("^[A-Za-zก-๛0-9.,-/()'_*\\s]+$");
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
		return isValid;
	}
	
	/**
	 * ตรวจสอบ Format Phone
	 * ตัวเลขและ คอมม่า
	 * @param value
	 * @param logger
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง, เป็น null หรือ empty 
	 */
	public static boolean isPhone(String value, Logger logger) {
		boolean isValid = false;
		try {
			if (isNull(value)) {
				return isValid;
			}
			return value.matches("^-?[0-9]+(\\\\,[0-9]*){0,64}$");
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
		return isValid;
	}
	
	/**
	 * ตรวจสอบการกรอกค่า A-Z, a-z, Number,Special char, Space
	 * @param value
	 * @param logger
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง, เป็น null หรือ empty 
	 */
	public static boolean isNotThai(String value) {
		boolean isValid = false;
		
		if (isNull(value)) {
			return isValid;
		}

	    boolean result = value.matches("^[A-Za-z0-9`!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~฿\\s]+$");
	    if(result) {
	    	isValid = true;
	    }
		return isValid;
	}
	
	/**
	 * ตรวจสอบ Numeric ต้องตรงตามที่ระบุใน DB เช่น numeric(11,2) 
	 * @param data
	 * @param numeric
	 * @param digit
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง
	 */
	public static boolean isFormatNumeric(String data, int numeric, int digit){
		boolean isValid = false;
		
		if(data.length() <= (numeric+1)) {	//+1 เนื่องจากรวม . ด้วย
			String[] d = data.split("\\.", -1);
			
			if(d.length > 1) {
				isValid = (d[d.length-1].length() > 0 && d[d.length-1].length() <= digit) && (d[0].length() > 0 && d[0].length() <= (numeric-digit));
			} else {
				isValid = (d[0].length() <= (numeric-digit));
			}
		} 
		
		return isValid;
	}
	
	public static void main(String[] args) {
		try {
			/**
//			DefaultLogUtil.UTIL.debug(hasValue(""));
//			DefaultLogUtil.UTIL.debug(hasValue(" "));
//			DefaultLogUtil.UTIL.debug(hasValue("2"));
//			DefaultLogUtil.UTIL.debug(hasValue(null));
			**/
			/**
//			DefaultLogUtil.UTIL.debug(isTime24Hours("", DefaultLogUtil.UTIL));
//			DefaultLogUtil.UTIL.debug(isTime24Hours(null, DefaultLogUtil.UTIL));
//			DefaultLogUtil.UTIL.debug(isTime24Hours("25:00", DefaultLogUtil.UTIL));
//			DefaultLogUtil.UTIL.debug(isTime24Hours("24:00", DefaultLogUtil.UTIL));
//			DefaultLogUtil.UTIL.debug(isTime24Hours("23:00", DefaultLogUtil.UTIL));
			DefaultLogUtil.UTIL.debug(isNumber(" ", DefaultLogUtil.UTIL));
			DefaultLogUtil.UTIL.debug(isNumber(null, DefaultLogUtil.UTIL));
			DefaultLogUtil.UTIL.debug(isNumber("AA", DefaultLogUtil.UTIL));
			DefaultLogUtil.UTIL.debug(isNumber("23", DefaultLogUtil.UTIL));
			**/
			
			/**
			DefaultLogUtil.UTIL.debug(isEmail("sititpo.m@gmail.com", DefaultLogUtil.UTIL));
			DefaultLogUtil.UTIL.debug(isFormatNumeric("123022", 11, 2));
			DefaultLogUtil.UTIL.debug(isFormatNumeric("12345.00", 11, 2));
			DefaultLogUtil.UTIL.debug(isFormatNumeric("123.22", 11, 2));
//			DefaultLogUtil.UTIL.debug(isFormatNumeric("0", 11, 2));
//			DefaultLogUtil.UTIL.debug(isFormatNumeric("123456789", 11, 2));
//			DefaultLogUtil.UTIL.debug(isFormatNumeric("123456789.98", 11, 2));
//			DefaultLogUtil.UTIL.debug("");
//			DefaultLogUtil.UTIL.debug(isFormatNumeric(".12", 11, 2));
//			DefaultLogUtil.UTIL.debug(isFormatNumeric("1.", 11, 2));
//			DefaultLogUtil.UTIL.debug(isFormatNumeric("1.098", 11, 2));
//			DefaultLogUtil.UTIL.debug(isFormatNumeric("12345678.098", 11, 2));
//			DefaultLogUtil.UTIL.debug(isFormatNumeric("123456789.098", 11, 2));
//			DefaultLogUtil.UTIL.debug(isFormatNumeric("1234567890.09", 11, 2));
//			DefaultLogUtil.UTIL.debug(isFormatNumeric("123456789009", 11, 2));
//			DefaultLogUtil.UTIL.debug(isFormatNumeric("-123456789", 11, 2));
			 */

		} catch (Exception e) {
			// REVIEW-6 : Code Analysis by Sittipol.m
			DefaultLogUtil.UTIL.catching(e);
		}
		
		
		/**
		System.out.println(isNotThai("013536"));
		System.out.println(isNotThai("sdgfdf"));
		System.out.println(isNotThai("TSWYJKDGHS"));
		System.out.println(isNotThai("หฟดา่าสกหดาส"));
		System.out.println(isNotThai("๑๒๓๔๕๖๗๘๐"));
		System.out.println(isNotThai("฿!@#$%^&*()_+{}:<>?"));
		System.out.println(isNotThai("ิื์ิใ้้เโ็่๋ๆไำะัํี๊ฯ"));
		System.out.println(isNotThai("!@#$%^&*()_+{}:<>?"));
		System.out.println(isNotThai("กดาดjskfljsdfkl"));
		System.out.println(isNotThai("Only 2 days left to our upcoming webinar! Be the first to witness the power of QA Orchestration into your mobile testing process and have Katalon and Kobiton’s specialists resolve your inquiries."));
		
		System.out.println(isNotThai("!@#$%^&*()_+-=[]{}?~฿"));
		System.out.println(isNotThai(";':"));
		System.out.println(isNotThai("\""));
		System.out.println(isNotThai("\\"));
		System.out.println(isNotThai("/"));
		System.out.println(isNotThai(""));
		System.out.println(isNotThai(" "));
		
		System.out.println(isNotThai("ສະບາຍດີ"));// lao
		System.out.println(isNotThai("ㅇㅈㅁㅎ"));// korean
		System.out.println(isNotThai("こんにちは"));// Japanese
		System.out.println(isNotThai("你好"));// Chinese
		System.out.println(isNotThai("Привет"));// Russian
		System.out.println(isNotThai("Helló"));// Hungarian
		System.out.println(isNotThai("أهلا"));// Arabic
		*/
	}
	
	/**
	 * ตรวจสอบ Format อักษร ENG(พิมพ์ใหญ่/พิมพ์เล็ก), ตัวเลข, (space)
	 * @param value
	 * @param logger
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง, เป็น null หรือ empty 
	 */
	public static boolean isEngNumber(String value, Logger logger) {
		boolean isValid = false;
		
		try {
			if (isNull(value)) {
				return isValid;
			}
			
			isValid = value.matches("^[A-Za-z0-9\\s]+$");	// A-Z a-z 0-9 (space)
			
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
		
		return isValid;
	}
	
	/**
	 * ตรวจสอบ Format อักษร ENG(พิมพ์ใหญ่/พิมพ์เล็ก)
	 * 
	 * @param value
	 * @param logger
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง, เป็น null หรือ empty 
	 */
	public static boolean isEngOnly(String value, Logger logger) {
		boolean isValid = false;
		
		try {
			if (isNull(value)) {
				return isValid;
			}
			
			isValid = value.matches("^[A-Za-z]+$");	// A-Z a-z
			
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
		
		return isValid;
	}
	
	/**
	 * ตรวจสอบ Format อักษร ENG(พิมพ์ใหญ่/พิมพ์เล็ก), ตัวเลข
	 * 
	 * @param value
	 * @param logger
	 * @return <br>
	 * true เมื่อรุปแบบถูกต้อง <br>
	 * false เมื่อรูปแบบไม่ถูกต้อง, เป็น null หรือ empty 
	 */
	public static boolean isEngNumberOnly(String value, Logger logger) {
		boolean isValid = false;
		
		try {
			if (isNull(value)) {
				return isValid;
			}
			
			isValid = value.matches("^[A-Za-z0-9]+$");	// A-Z a-z 0-9
			
		} catch (Exception arg6) {
			logger.catching(arg6);
		}
		
		return isValid;
	}
	
	/**
	 * หาจำนวนวันที่ต่างกันระหว่างวันที่ ไม่รวมเวลา
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getDiffDay(Date start, Date end) {
		
		Calendar calStart = Calendar.getInstance(ParameterConfig.getApplication().getApplicationLocale());
		calStart.setTime(start);
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calStart.set(Calendar.MINUTE, 0);
		calStart.set(Calendar.SECOND, 0);
		calStart.set(Calendar.MILLISECOND, 0);
		calStart.getTimeInMillis();
		
		Calendar calEnd = Calendar.getInstance(ParameterConfig.getApplication().getApplicationLocale());
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
	 * 
	 * @param invalidInputs
	 * @param element
	 * @param msg
	 * @param value
	 */
	public static void addInvalidInput(List<CommonInputValidate> invalidInputs, String element, String msg, String value, Logger logger) {
		CommonInputValidate input = new CommonInputValidate();
		input.setElement(element);
		input.setMsg(msg);
		invalidInputs.add(input);
		logger.error("[{}] : [{}] -> [{}]", element, value, msg);
	}
}
