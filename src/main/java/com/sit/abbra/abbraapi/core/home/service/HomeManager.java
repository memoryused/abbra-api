package com.sit.abbra.abbraapi.core.home.service;

import java.util.List;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.common.AbbraCommonDomain;
import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.home.domain.HomeModel;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.core.common.service.CommonManager;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;

public class HomeManager extends CommonManager {

	private HomeService service;
	
	public HomeManager(Logger logger) {
		super(logger);
		this.service = new HomeService(logger);
	}

	public void initData(HomeModel modelResponse) throws Exception {
		getLogger().debug(" initData ");
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			modelResponse.setLstPromotion(service.searchPromotion(conn));
			modelResponse.setListPressRelease(service.searchPressRelease(conn));
			service.searchVdoAndMarquee(conn, modelResponse);
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	public List<AbbraCommonDomain> searchPressRelease(CCTConnection conn) throws Exception {
		return service.searchPressRelease(conn);
	}
}
