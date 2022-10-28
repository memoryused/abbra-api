package com.sit.abbra.abbraapi.enums;

public enum PFCode {
	
	PRE_APPROVE("1",  PFGroup.PRE_APPROVE)
	, APPROVE("2",  PFGroup.APPROVE)
	;
	
	private String programCode;
	private PFGroup groupOperator;
	
	private PFCode(String programCode, PFGroup groupOperator) {
		this.programCode = programCode;
		this.groupOperator = groupOperator;
	}
	
	public String getProgramCode() {
		return programCode;
	}
	
	public PFGroup getGroupOperator() {
		return groupOperator;
	}
	
	
	
}

