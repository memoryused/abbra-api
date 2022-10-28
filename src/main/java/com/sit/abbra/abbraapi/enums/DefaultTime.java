package com.sit.abbra.abbraapi.enums;

public enum DefaultTime {

	UI_FROM_HHMM("00:00"), UI_TO_HHMM("23:59")
	, UI_FROM_HHMMSS("00:00:00"), UI_TO_HHMMSS("23:59:59")
	, FROM_SS("00"), TO_SS("59")
	, FROM_NN("00"), TO_NN("99")
	, FROM_NNN("000"), TO_NNN("999");

	private String val;

	private DefaultTime(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}
}
