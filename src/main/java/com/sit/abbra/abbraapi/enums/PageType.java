package com.sit.abbra.abbraapi.enums;

public enum PageType {

	ADD("A"), EDIT("E"), VIEW("V");

	private String value;

	private PageType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}