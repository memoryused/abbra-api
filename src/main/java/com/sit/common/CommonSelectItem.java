package com.sit.common;

import java.io.Serializable;

/**
 * 
 * สำหรับส่งข้อมูล select item กลับไปยัง Client แบบปกติ คือ มี key กับ value
 * ด้วย Json<br>
 * แต่เวลาใช้งานต้องสร้าง JSON Type ดังนี้<br>
 * exp. new TypeToken<ArrayList<CommonSelectItem>>(){}.getType();
 * 
 * @author sittipol.m
 *
 */
public class CommonSelectItem implements Serializable {

	private static final long serialVersionUID = 6771221109764211702L;

	private String key;
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}