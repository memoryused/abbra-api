package com.sit.abbra.abbraapi.enums;

public enum LoginCaptcha {
	NO_CHECK("1"), ALWAYS("2"), FAIL_COUNT("3");

	private String value;

	private LoginCaptcha(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
