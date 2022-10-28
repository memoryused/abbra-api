package com.sit.abbra.abbraapi.core.config.parameter.service;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.logging.log4j.Logger;

import com.sit.core.common.domain.CommonSQLPath;
import com.sit.core.common.service.CommonDAO;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.sql.SQLParameterizedUtil;

public class ParameterDAO extends CommonDAO {

	public ParameterDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}
	
	/**
	 * ทดสอบ SQL
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected String testSQL(CCTConnection conn) throws Exception {
		
		String result = null;
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "testSQL");
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			getLogger().info("conn.getConn().isOpen: {}", !conn.getConn().isClosed());
			stmt = createStatement(conn);
			rst = executeQuery(stmt, sql);
			if (rst.next()) {
				result = rst.getString(1);
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}

}
