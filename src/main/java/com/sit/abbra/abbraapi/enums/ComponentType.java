package com.sit.abbra.abbraapi.enums;

public enum ComponentType {

	SNACK("S"), ALERT("A"), CONFIRM("C"), REDIRECT("R");

	private String value;

	private ComponentType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}