package com.sit.authen.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.AuthorizeRequest;
import com.sit.abbra.abbraapi.core.security.login.domain.ClientSystem;
import com.sit.abbra.abbraapi.util.date.DateUtil;
import com.sit.core.common.domain.CommonSQLPath;
import com.sit.core.common.service.CommonDAO;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.sql.SQLParameterizedDebugUtil;
import util.sql.SQLParameterizedUtil;
import util.string.StringUtil;

public class AuthenticationDAO extends CommonDAO {

	public AuthenticationDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	/**
	 * SQL : Delete Expire SEC_AUTHORIZE
	 * 
	 * @param conn
	 * @param currentDateTime
	 * @throws Exception
	 */
	protected void deleteExpireAuthorize(CCTConnection conn, String currentDateTime) throws Exception {
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = currentDateTime;

		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(),
				getSqlPath().getPath(), "deleteExpireAuthorize", params);

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
	 * Get Client System
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected ClientSystem searchClientSystem(CCTConnection conn, String clientId) throws Exception {
		ClientSystem result = null;

		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.nullToString(clientId);

		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(),
				getSqlPath().getPath(), "searchClientSystem", params);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}

		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
			rst = stmt.executeQuery();
			if (rst.next()) {
				result = new ClientSystem();
				result.setClientSystemId(StringUtil.nullToString(rst.getString("CLIENT_SYSTEM_ID")));
				result.setClientId(StringUtil.nullToString(rst.getString("CLIENT_ID")));
				result.setGrantType(StringUtil.nullToString(rst.getString("GRANT_TYPE")));
				result.setRedirectUrl(StringUtil.nullToString(rst.getString("REDIRECT_URL")));
				result.setMainUrl(StringUtil.nullToString(rst.getString("MAIN_URL")));
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}

	/**
	 * SQL : Get SEC_AUTHORIZE by AUTHORIZE_KEY
	 * 
	 * @param conn
	 * @param key
	 * @return
	 * @throws Exception
	 */
	protected AuthorizeRequest searchAuthorizeByKey(CCTConnection conn, String key) throws Exception {
		AuthorizeRequest result = null;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.nullToString(key);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchAuthorizeByKey"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}

		PreparedStatement stmt = null;
	    ResultSet rst = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
	        rst = stmt.executeQuery();
			
			if (rst.next()) {
				result = new AuthorizeRequest();
				result.setKey(rst.getString("AUTHORIZE_KEY"));
				result.setState(rst.getString("STATE"));
				result.setCodeChallenge(rst.getString("CODE_CHALLENGE"));
				result.setEncryptData(rst.getString("ENCRYPT_DATA"));
				result.setClientId(rst.getString("CLIENT_ID"));
				result.setExpired(DateUtil.toDate(rst.getString("AUTHORIZE_EXPIRED"), ParameterConfig.getDateFormat().getDbFormat()));
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * SQL : Insert SEC_AUTHORIZE
	 * 
	 * @param conn
	 * @param authorizeRequest
	 * @throws Exception
	 */
	protected void insertAuthorize(CCTConnection conn, AuthorizeRequest authorizeRequest) throws Exception {
		getLogger().debug("");
		
		int paramIndex = 0;
		Object[] params = new Object[6];
		params[paramIndex++] = StringUtil.stringToNull(authorizeRequest.getKey());
		params[paramIndex++] = StringUtil.stringToNull(authorizeRequest.getState());
		params[paramIndex++] = StringUtil.stringToNull(authorizeRequest.getCodeChallenge());
		params[paramIndex++] = StringUtil.stringToNull(authorizeRequest.getEncryptData());
		params[paramIndex++] = StringUtil.stringToNull(authorizeRequest.getClientId());
		params[paramIndex] = DateUtil.toString(authorizeRequest.getExpired(), ParameterConfig.getDateFormat().getDbFormat());	

		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertAuthorize"
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
	 * SQL : Update SEC_AUTHORIZE
	 * 
	 * @param conn
	 * @param authorizeRequest
	 * @throws Exception
	 */
	protected void updateAuthorize(CCTConnection conn, AuthorizeRequest authorizeRequest) throws Exception {
		int paramIndex = 0;
		Object[] params = new Object[6];
		params[paramIndex++] = StringUtil.stringToNull(authorizeRequest.getState());
		params[paramIndex++] = StringUtil.stringToNull(authorizeRequest.getCodeChallenge());
		params[paramIndex++] = StringUtil.stringToNull(authorizeRequest.getEncryptData());
		params[paramIndex++] = StringUtil.stringToNull(authorizeRequest.getClientId());
		params[paramIndex++] = DateUtil.toString(authorizeRequest.getExpired(), ParameterConfig.getDateFormat().getDbFormat());
		params[paramIndex] = StringUtil.nullToString(authorizeRequest.getKey());
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateAuthorize"
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
	 * SQL : Delete SEC_AUTHORIZE by key
	 * 
	 * @param conn
	 * @param key
	 * @throws Exception
	 */
	protected void deleteAuthorizeByKey(CCTConnection conn, String key) throws Exception {
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = key;
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "deleteAuthorizeByKey"
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
	
}
