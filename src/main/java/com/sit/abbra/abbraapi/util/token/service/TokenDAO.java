package com.sit.abbra.abbraapi.util.token.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.Logger;

import com.sit.core.common.domain.CommonSQLPath;
import com.sit.core.common.service.CommonDAO;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.sql.SQLParameterizedDebugUtil;
import util.sql.SQLParameterizedUtil;
import util.string.StringUtil;

public class TokenDAO extends CommonDAO{

	public TokenDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}
	
	protected String searchTokenAuthor(CCTConnection conn, String clientId, String secret) throws Exception {
		String token = "";
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.stringToNull(clientId);
		params[paramIndex] = StringUtil.stringToNull(secret);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchTokenAuthor"
				, params
				);
		
		if(getLogger().isDebugEnabled()) {
			getLogger().debug("SQL : searchTokenAuthor \n {}", SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();

			if (rst.next()) {
				token = StringUtil.nullToString(rst.getString("TOKEN"));
			}

		} catch (Exception e) {
			getLogger().catching(e);
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return token;
	}

}
