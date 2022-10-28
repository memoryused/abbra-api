package com.sit.abbra.abbraapi.core.selectitem.enums;

public enum SelectItemMapper {
	
	/** Selectitem Mapper <Key, SQL Name> **/
	PREFIX("searchPrefixSelectItem"),
	FUNCTION_CODE("searchFunctionCodeSelectItem"),
	;
	
	private String sqlName;

	private SelectItemMapper(String sqlName) {
		this.sqlName = sqlName;
	}

	public String getSqlName() {
		return sqlName;
	}
}
