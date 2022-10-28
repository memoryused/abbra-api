package com.sit.pibics.eextensionapi;

import com.sit.abbra.abbraapi.util.date.DateUtil;

public class Test {
	public static void main(String[] args) {
		try {
			System.out.println(DateUtil.toDate("Thu Aug 18 11:35:19 ICT 2022", "yyyyMMddHHmmss"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
