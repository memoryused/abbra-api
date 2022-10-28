package com.sit.abbra.abbraapi.core.security.log.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.security.log.domain.LogEvent;
import com.sit.core.common.domain.CommonSQLPath;
import com.sit.core.common.service.CommonDAO;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.sql.SQLParameterizedDebugUtil;
import util.sql.SQLParameterizedUtil;

public class LogDAO extends CommonDAO {

	public LogDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	protected void insertEvent(CCTConnection conn, LogEvent event) throws Exception {
		int paramIndex = 0;
		Object[] params = new Object[7];
		params[paramIndex++] = event.getEventId();
		params[paramIndex++] = event.getLoginUser().getUserId();
		params[paramIndex++] = event.getLogStatus();
		params[paramIndex++] = event.getOperatorId();
		params[paramIndex++] = null;
		params[paramIndex++] = event.getMethodClass();
		params[paramIndex] = event.getLogData();
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertLogEvent"
				, params);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
	    try {
	        stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
	        stmt.executeUpdate();
	    } finally {
	        CCTConnectionUtil.closeStatement(stmt);
	    }	    
	}
	
	/**
	 * ค้นหา SEQ_NO สำหรับ insertLog
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected Long searchSeqNo(CCTConnection conn) throws Exception {
		Long seqNo = null;
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchSeqNo"
				);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, null));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
	    try {
	        stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql);
	        rst = stmt.executeQuery();
	        
	        if(rst.next()) {
	        	seqNo = rst.getLong("SEQ_NO");
	        }
	    } finally {
	        CCTConnectionUtil.closeAll(rst, stmt);
	    }
	    
	    return seqNo;
	}
}
