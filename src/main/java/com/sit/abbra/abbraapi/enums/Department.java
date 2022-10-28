package com.sit.abbra.abbraapi.enums;

public enum Department {

	WH_LG("WH&LG")
	, ICT("ICT")
	, CS("CS")
	, FN_AC("FN&AC")
	, HRA_ST("HRA&ST")
	, MKT("MKT")
	, MS("MS")
	, PD("PD")
	, PN("PN")
	, PS("PS")
	, QA("QA")
	, QS("QS")
	, RD("RD")
	, SA("SA")
	, SH("SH")
	;

	private String val;

	private Department(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}
}
