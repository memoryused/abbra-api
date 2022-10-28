package com.sit.abbra.abbraapi.core.ic.announce.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.home.service.HomeManager;
import com.sit.abbra.abbraapi.core.home.service.HomeService;
import com.sit.abbra.abbraapi.core.ic.announce.domain.AnnounceModel;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.core.common.service.CommonManager;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;

public class AnnounceManager extends CommonManager {

	private AnnounceService service;
	
	public AnnounceManager(Logger logger) {
		super(logger);
		this.service = new AnnounceService(logger);
	}

	public void initData(AnnounceModel modelResponse) throws Exception {
		getLogger().debug(" initData ");
		
		CCTConnection conn = null;
		
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			HomeManager homeManager = new HomeManager(getLogger());
			
			modelResponse.setListAnnounce(homeManager.searchPressRelease(conn));
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
}
