package com.sit.pibics.eextensionapi;

import java.util.Calendar;
import java.util.Locale;

import com.sit.abbra.abbraapi.util.date.DateUtil;

public class Test {
	public static void main(String[] args) {
		try {
			String str = "/promotion/2022/10/thumbnail.3.jpg";
			String[] strArr = str.split("/", -1);
			System.out.println(strArr.length);
			
			
			Calendar cal = Calendar.getInstance(Locale.ENGLISH);
			cal.set(Integer.valueOf(strArr[strArr.length-3]), Integer.valueOf(strArr[strArr.length-2])-1, 1);
			System.out.println(cal.getTime());
			
			System.out.println(strArr[strArr.length-1].substring(strArr[strArr.length-1].lastIndexOf(".")+1));
			
			System.out.println(cal.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.ENGLISH));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
