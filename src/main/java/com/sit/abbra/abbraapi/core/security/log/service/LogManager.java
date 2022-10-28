package com.sit.abbra.abbraapi.core.security.log.service;

import java.sql.SQLException;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.security.login.service.LoginManager;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.abbra.abbraapi.util.date.DateUtil;
import com.sit.core.common.service.CommonManager;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;

public class LogManager extends CommonManager {

	private LogService service = null;
	
	public LogManager(Logger logger) {
		super(logger);
		this.service = new LogService(getLogger());
	}

	public Long insertEvent(CCTConnection conn, String operatorId, String pathInfo, String logData, LoginUser loginUser) throws Exception {
		String currentTimestamp = DateUtil.getDateTimeString(ParameterConfig.getDateFormat().getDbTimestamp());
		String currentDateTime = currentTimestamp.substring(0, currentTimestamp.length() - 3);
		Long eventId = null;
		try {
			commit(conn);
			
			eventId = insertEvent(conn, operatorId, pathInfo, logData, loginUser, null);
			
			String logoutDatetime = service.generateLogoutDateTime(currentDateTime); 
			updateLogoutTime(conn, loginUser, logoutDatetime);
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return eventId;
	}
	
	private Long insertEvent(CCTConnection conn, String operatorId, String pathInfo, String logData, LoginUser loginUser, String currentDateTime) throws Exception {
		Long eventId = null;
		try {
			eventId = service.insetEvent(conn, operatorId, pathInfo, logData, loginUser);
			commit(conn);
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return eventId;
	}
	
	private void updateLogoutTime(CCTConnection conn, LoginUser loginUser, String currentDateTime) {
		try {
			LoginManager loginManager = new LoginManager(getLogger());
			loginManager.updateLogoutTime(conn, loginUser, currentDateTime);
			commit(conn);
		} catch (Exception e) {
			getLogger().catching(e);
		}
	}
	
	public Long insertEvent(String operatorId, String pathInfo, String logData, LoginUser loginUser) throws Exception {
		CCTConnection conn = null;
		Long eventId = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			eventId = insertEvent(conn, operatorId, pathInfo, logData, loginUser);
		} catch (Exception e) {
			getLogger().catching(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return eventId;
	}
	
	private void commit(CCTConnection conn) throws SQLException {
		if (!conn.getAutoCommit()) {
			conn.commit();
		}
	}
}
