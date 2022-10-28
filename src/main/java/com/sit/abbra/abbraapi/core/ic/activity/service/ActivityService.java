package com.sit.abbra.abbraapi.core.ic.activity.service;

import java.util.List;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.common.AbbraCommonDomain;
import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.core.common.service.CommonService;

import util.database.connection.CCTConnection;

public class ActivityService extends CommonService {

	private ActivityDAO dao;
	
	public ActivityService(Logger logger) {
		super(logger);
		this.dao = new ActivityDAO(logger, SQLPath.ACTIVITY_SQL.getSqlPath());
	}

	protected List<AbbraCommonDomain> searchActivity(CCTConnection conn, int annType) throws Exception {
		return dao.searchActivity(conn, annType);
	}
	
	protected List<AbbraCommonDomain> searchAllActivity(CCTConnection conn, int annType) throws Exception {
		return dao.searchAllActivity(conn, annType);
	}
}
