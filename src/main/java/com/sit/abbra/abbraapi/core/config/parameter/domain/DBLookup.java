package com.sit.abbra.abbraapi.core.config.parameter.domain;

public enum DBLookup {
	E_EXT_API("eextapi");

	private String lookup;

	private DBLookup(String lookup) {
		this.lookup = lookup;
	}

	public String getLookup() {
		return lookup;
	}
}