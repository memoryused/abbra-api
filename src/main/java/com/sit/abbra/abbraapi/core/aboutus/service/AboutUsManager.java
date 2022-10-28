package com.sit.abbra.abbraapi.core.aboutus.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.aboutus.domain.AboutUsModel;
import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.core.common.service.CommonManager;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;

public class AboutUsManager extends CommonManager {

	private AboutUsService service;
	
	public AboutUsManager(Logger logger) {
		super(logger);
		this.service = new AboutUsService(logger);
	}

	public void initData(AboutUsModel modelResponse) throws Exception {
		getLogger().debug(" initData ");
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			modelResponse.getAboutUs().setSrcVdo(service.searchAboutUs(conn, 7));
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	public void gotoExecutive(AboutUsModel modelResponse) throws Exception {
		getLogger().debug(" gotoExecutive ");
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			modelResponse.getAboutUs().setSrcPicExecutive(service.searchAboutUs(conn, 8));
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	public void gotoOrganization(AboutUsModel modelResponse) throws Exception {
		getLogger().debug(" gotoOrganization ");
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			modelResponse.getAboutUs().setSrcPicOrganization(service.searchAboutUs(conn, 9));
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}

}
