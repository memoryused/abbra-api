package com.sit.abbra.abbraapi.enums;

public enum LockStatusType {

	READY("1"), LOCKED("2");

	private String value;

	private LockStatusType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}