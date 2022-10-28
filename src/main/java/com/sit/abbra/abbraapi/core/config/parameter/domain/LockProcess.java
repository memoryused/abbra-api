package com.sit.abbra.abbraapi.core.config.parameter.domain;

import java.io.Serializable;
import java.util.Locale;

public class LockProcess implements Serializable {
	
	private static final long serialVersionUID = -9103458462038074083L;
	
	private String dateFormatCompare;
	private String dateLocaleString;
	private Locale dateLocale;
	
	public String getDateFormatCompare() {
		return dateFormatCompare;
	}
	
	public void setDateFormatCompare(String dateFormatCompare) {
		this.dateFormatCompare = dateFormatCompare;
	}
	
	public Locale getDateLocale() {
		if(dateLocale == null) {
			dateLocale = new Locale(getDateLocaleString().toLowerCase(), getDateLocaleString().toUpperCase());
		}
		return dateLocale;
	}
	
	public String getDateLocaleString() {
		return dateLocaleString;
	}
	public void setDateLocaleString(String dateLocaleString) {
		this.dateLocaleString = dateLocaleString;
	}
}
