package com.sit.abbra.abbraapi.core.config.parameter.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.abbra.abbraapi.core.config.parameter.domain.Parameter;
import com.sit.core.common.service.CommonService;

import util.database.connection.CCTConnection;
import util.xml.XMLUtil;

public class ParameterService extends CommonService {

	private ParameterDAO dao = null;
	
	public ParameterService(Logger logger) {
		super(logger);
		this.dao = new ParameterDAO(getLogger(), SQLPath.TEST_SQL.getSqlPath());
	}

	protected Parameter loadParameter(String filePath) throws Exception {
		return (Parameter) XMLUtil.xmlToObject(filePath, new Parameter());
	}
	
	protected String testSQL(CCTConnection conn) throws Exception {
		return getDao().testSQL(conn);
	}
	
	public ParameterDAO getDao() {
		return dao;
	}
}
