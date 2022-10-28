package com.sit.abbra.abbraapi.enums;

public enum MessageCode {

	SUCCESS("00000");

	private String value;

	private MessageCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
