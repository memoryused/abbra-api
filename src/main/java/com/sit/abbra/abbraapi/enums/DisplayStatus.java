package com.sit.abbra.abbraapi.enums;

public enum DisplayStatus {

	SUCCESS("S"), WARN("W"), ERROR("E"), INFO("I");

	private String value;

	private DisplayStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
