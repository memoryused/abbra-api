package com.sit.domain;

public class FunctionType {
	
	private FunctionType() {
		throw new IllegalStateException("Utility class");
	}
	
	public static final String NONE = "";
	public static final String SEARCH = "search";
	public static final String ADD = "add";
	public static final String EDIT = "edit";
	public static final String VIEW = "view";
	public static final String DELETE = "delete";
	public static final String EXPORT = "export";
	public static final String VIEW_PNRGOV_MESSAGE = "viewPNRGOV";
	public static final String VIEW_RECORD_LOCATOR = "viewRecLocator";
	public static final String REGENERATE_PNRGOV = "regenerate";
	public static final String RETRANSMIT_PNRGOV = "retransmit";
}
