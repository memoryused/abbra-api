package com.sit.abbra.abbraapi.core.ic.activity.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.ic.activity.domain.ActivityModel;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.core.common.service.CommonManager;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;

public class ActivityManager extends CommonManager {

	private ActivityService service;
	
	public ActivityManager(Logger logger) {
		super(logger);
		this.service = new ActivityService(logger);
	}

	public void initData(ActivityModel modelResponse) throws Exception {
		getLogger().debug(" initData ");
		
		CCTConnection conn = null;
		
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			modelResponse.setListActivity(service.searchActivity(conn, 5));
			modelResponse.setListVdoActivity(service.searchActivity(conn, 6));
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	public void searchAllActivity(ActivityModel modelResponse) throws Exception {
		getLogger().debug(" searchAllActivity ");
		
		CCTConnection conn = null;
		
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			modelResponse.setListActivity(service.searchAllActivity(conn, 5));
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	public void searchAllVdoActivity(ActivityModel modelResponse) throws Exception {
		getLogger().debug(" searchAllVdoActivity ");
		
		CCTConnection conn = null;
		
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			modelResponse.setListVdoActivity(service.searchAllActivity(conn, 6));
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
}
