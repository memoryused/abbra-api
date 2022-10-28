package com.sit.abbra.abbraapi.enums;

public enum Split {
	COMMA(","), COLON(":"), PIPE("|"), SLASH("/"), UNDER_SCORE("_"), DASH("-");

	private String value;

	private Split(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
