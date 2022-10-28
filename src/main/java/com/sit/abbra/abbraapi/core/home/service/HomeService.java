package com.sit.abbra.abbraapi.core.home.service;

import java.util.List;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.common.AbbraCommonDomain;
import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.abbra.abbraapi.core.home.domain.HomeModel;
import com.sit.abbra.abbraapi.core.home.domain.Promotion;
import com.sit.core.common.service.CommonService;

import util.database.connection.CCTConnection;

public class HomeService extends CommonService {

	private HomeDAO dao;
	
	public HomeService(Logger logger) {
		super(logger);
		this.dao = new HomeDAO(logger, SQLPath.HOME_SQL.getSqlPath());
	}

	protected List<Promotion> searchPromotion(CCTConnection conn) throws Exception {
		return dao.searchPromotion(conn);
	}
	
	protected List<AbbraCommonDomain> searchPressRelease(CCTConnection conn) throws Exception {
		return dao.searchPressRelease(conn);
	}
	
	protected void searchVdoAndMarquee(CCTConnection conn, HomeModel modelResponse) throws Exception {
		dao.searchVdoAndMarquee(conn, modelResponse);
	}
}
