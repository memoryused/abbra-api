package com.sit.domain;

import java.io.Serializable;

public class HeaderSorts implements Serializable {

	private static final long serialVersionUID = 2489867188624653141L;

	public static final String DESC = "DESC";
	public static final String ASC = "ASC";

	private String columnName;
	private String order;

	public HeaderSorts() {

	}

	public HeaderSorts(String columnName, String order) {
		this.columnName = columnName;
		this.order = order;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
