package com.sit.abbra.abbraapi.enums;

import com.sit.domain.FunctionType;

public enum PFOperator {
	
	PRE_APPROVE_SEARCH(FunctionType.SEARCH, ""),
	PRE_APPROVE_ADD(FunctionType.SEARCH, ""),
	PRE_APPROVE_EDIT(FunctionType.SEARCH, ""),
	PRE_APPROVE_VIEW(FunctionType.VIEW, ""),
	PRE_APPROVE_EXPORT(FunctionType.EXPORT, ""),
	
	APPROVE_SEARCH(FunctionType.SEARCH, ""),
	APPROVE_ADD(FunctionType.SEARCH, ""),
	APPROVE_EDIT(FunctionType.SEARCH, ""),
	APPROVE_VIEW(FunctionType.VIEW, ""),
	APPROVE_EXPORT(FunctionType.EXPORT, ""),
	;
	
	private String function;
	private String operator;
	
	private PFOperator(String function, String operator) {
		this.function = function;
		this.operator = operator;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public String getFunction() {
		return function;
	}
}
