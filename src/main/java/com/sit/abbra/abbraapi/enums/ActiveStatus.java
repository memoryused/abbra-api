package com.sit.abbra.abbraapi.enums;

public enum ActiveStatus {

	ACTIVE("Y"), INACTIVE("N");

	private String value;

	private ActiveStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}