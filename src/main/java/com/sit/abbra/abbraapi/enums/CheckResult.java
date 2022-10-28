package com.sit.abbra.abbraapi.enums;

public enum CheckResult {
	YES("Y"), NO("N");
	
	private String value;
	
	private CheckResult(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
