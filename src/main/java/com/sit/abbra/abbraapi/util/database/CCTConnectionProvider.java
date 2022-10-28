package com.sit.abbra.abbraapi.util.database;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;

import util.database.connection.WebCommonConnectionProvider;

public class CCTConnectionProvider extends WebCommonConnectionProvider {

	public CCTConnectionProvider() throws Exception {
		super(ParameterConfig.getDatabase());
	}
}
