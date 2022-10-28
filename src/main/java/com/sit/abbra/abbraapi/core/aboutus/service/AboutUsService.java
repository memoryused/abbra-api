package com.sit.abbra.abbraapi.core.aboutus.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.core.common.service.CommonService;

import util.database.connection.CCTConnection;

public class AboutUsService extends CommonService {

	private AboutUsDAO dao;
	
	public AboutUsService(Logger logger) {
		super(logger);
		this.dao = new AboutUsDAO(logger, SQLPath.ABOUT_US_SQL.getSqlPath());
	}

	protected String searchAboutUs(CCTConnection conn, int annType) throws Exception {
		return dao.searchAboutUs(conn, annType);
	}
}
