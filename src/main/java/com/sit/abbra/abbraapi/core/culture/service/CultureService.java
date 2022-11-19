package com.sit.abbra.abbraapi.core.culture.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.abbra.abbraapi.core.culture.domain.CultureModel;
import com.sit.core.common.service.CommonService;

import util.database.connection.CCTConnection;

public class CultureService extends CommonService {

	private CultureDAO dao;
	
	public CultureService(Logger logger) {
		super(logger);
		this.dao = new CultureDAO(logger, SQLPath.CULTURE_SQL.getSqlPath());
	}

	/**
	 * searchCulture
	 * @param conn
	 * @param announceType
	 * @param modelResponse
	 * @throws Exception
	 */
	protected void searchCulture(CCTConnection conn, String announceType, CultureModel modelResponse) throws Exception {
		dao.searchCulture(conn, announceType, modelResponse);
	}
	
}
