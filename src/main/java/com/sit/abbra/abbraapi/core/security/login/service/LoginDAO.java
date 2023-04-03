package com.sit.abbra.abbraapi.core.security.login.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.ClientSystem;
import com.sit.abbra.abbraapi.core.security.login.domain.Login;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.security.login.domain.OperatorButton;
import com.sit.abbra.abbraapi.core.security.login.domain.PibicsAuthenModel;
import com.sit.abbra.abbraapi.core.security.login.domain.SecLogin;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.core.common.domain.CommonSQLPath;
import com.sit.core.common.service.CommonDAO;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.sql.SQLParameterizedDebugUtil;
import util.sql.SQLParameterizedUtil;
import util.string.StringUtil;

public class LoginDAO extends CommonDAO {
	
	public static final String COL_USER_ID = "USER_ID";
	public static final String COL_USERNAME = "TG_USER_ID";
	public static final String COL_PASSWORD = "PASSWORD";
	public static final String COL_EMAIL = "EMAIL";
	public static final String COL_OPERATOR_ID = "OPERATOR_ID";

	public LoginDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}
		
	/**
	 * Get Client System
	 * @implNote
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected ClientSystem searchClientSystem(CCTConnection conn, String clientId) throws Exception {
		ClientSystem result = null;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.nullToString(clientId);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchClientSystem"
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
	 * SQL :  Insert SEC_LOGIN_SQL
	 * @implNote
	 * @param conn
	 * @param secLogin
	 * @throws Exception
	 */
	protected void insertSecLogin(CCTConnection conn, SecLogin secLogin) throws Exception {
		int paramIndex = 0;
		Object[] params = new Object[8];
		params[paramIndex++] = secLogin.getLoginUser().getUserId();
		params[paramIndex++] = secLogin.getLoginDatetime();
		params[paramIndex++] = secLogin.getLogoutDatetime();
		params[paramIndex++] = StringUtil.stringToNull(secLogin.getToken().getSecret());
		params[paramIndex++] = StringUtil.stringToNull(secLogin.getToken().getEncrypt());
		params[paramIndex++] = StringUtil.stringToNull(secLogin.getToken().getSalt());
		params[paramIndex++] = secLogin.getActionDatetime();
		params[paramIndex] = StringUtil.stringToNull(secLogin.getAgentName());
		
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertSecLogin"
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
	 * SQL :  UPDATE_SEC_LOGIN_SQL
	 * @implNote
	 * @param conn
	 * @param secLogin
	 * @return
	 * @throws Exception
	 */
	protected int updateSecLogin(CCTConnection conn, SecLogin secLogin) throws Exception {
		
		int rowCount = 0;
		
		int paramIndex = 0;
		Object[] params = new Object[8];
		params[paramIndex++] = secLogin.getLoginDatetime();
		params[paramIndex++] = secLogin.getLogoutDatetime();
		params[paramIndex++] = StringUtil.stringToNull(secLogin.getToken().getSecret());
		params[paramIndex++] = StringUtil.stringToNull(secLogin.getToken().getEncrypt());
		params[paramIndex++] = StringUtil.stringToNull(secLogin.getToken().getSalt());
		params[paramIndex++] = secLogin.getActionDatetime();
		params[paramIndex++] = StringUtil.stringToNull(secLogin.getAgentName());
		params[paramIndex] = secLogin.getLoginUser().getUserId();
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateSecLogin"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
	    try {
	        stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
	        rowCount = stmt.executeUpdate();
	    } finally {
	        CCTConnectionUtil.closeStatement(stmt);
	    }
	    return rowCount;
	}
	
	/**
	 * SQL :  tot=ค้นหาข้อมูลการ Login_SQL
	 * @implNote
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	protected long searchLoginDup(CCTConnection conn, String userId) throws Exception {
		long loginDup = 0;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.stringToNull(userId);

		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchLoginDup"
				, params);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
				
		PreparedStatement stmt = null;
	    ResultSet rst = null;	    
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
	        rst = stmt.executeQuery();

			if (rst.next()){
				loginDup = rst.getLong("tot");
			}			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return loginDup;
	}
	
	/**
	 * SQL :  chkLogin=ตรวจสอบการ Login_SQL
	 * @implNote
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	protected String searchLoginDupCheckLogouDateTime(CCTConnection conn, String userId) throws Exception {
		String result = "";
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = userId;

		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchLoginDupCheckLogouDateTime"
				, params);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
				
		PreparedStatement stmt = null;
	    ResultSet rst = null;	    
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
	        rst = stmt.executeQuery();

			if (rst.next()){
				result = StringUtil.nullToString(rst.getString("LOGOUT_DATETIME"));
			}			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * Search SEC_LOGIN by Token
	 * @implNote
	 * @param conn
	 * @param token (User Id Encrypted)
	 * @return
	 * @throws Exception
	 */
	protected Login searchLoginByToken(CCTConnection conn, String token) throws Exception {
		Login login = null;

		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.nullToString(token);

		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchLoginByToken"
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
				login = new Login();
				login.setToken(StringUtil.nullToString(rst.getString("TOKEN")));
				login.setSalt(StringUtil.nullToString(rst.getString("SALT")));
				login.setSecret(StringUtil.nullToString(rst.getString("SECRET")));
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return login;
	}
	
	/**
	 * Get Login user by user Id
	 * @implNote
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	protected LoginUser searchLoginUserByUserId(CCTConnection conn, String userId) throws Exception {
		LoginUser result = null;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = userId;
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchLoginUserByUserId"
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
	    		result = initLoginUser(rst);
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	private LoginUser initLoginUser(ResultSet rst) throws Exception {
		LoginUser result = new LoginUser();
		
		// USER_ID, ACTIVE, CHANGE_LOG, LOCK_STATUS
		result.setUserId(rst.getString(COL_USER_ID));
		result.setActive("Y");	
		result.setLockStatus("1");
		result.setStationIp(StringUtil.nullToString(rst.getString("STATION_IP")));
		result.setChangeLog("Y");
		
		// , USER_LANG, TG_USER_ID, FUNCTION_CODE
		result.setLanguage(ParameterConfig.getApplication().getApplicationLocaleString());
		result.setUserName(StringUtil.nullToString(rst.getString("USERNAME")));
		result.setOrganizationName("");
		result.setOrganizationId(StringUtil.nullToString(rst.getString("ORGANIZATION_ID")));
		
		result.setLoginDate(rst.getString("LAST_LOGIN_DATETIME"));
		
		// , SALT
		result.setSalt(StringUtil.nullToString(rst.getString("SALT")));
		String picture =  StringUtil.nullToString(rst.getString("PICTURE"));
		if(!picture.isEmpty()) {
			result.setPicture(
				EExtensionApiUtil.getThumbnailByScale(
						ParameterConfig.getApplication().getSharePath() + picture, 
						ParameterConfig.getThumbnailConfig().getScale(), 
						ParameterConfig.getThumbnailConfig().isWatermark(), 
						ParameterConfig.getThumbnailConfig().getWatermarkImage())
				);
		}
		
		return result;
	}
	
	/**
	 * SQL : LOGOUT_SQL
	 * @implNote
	 * @param conn
	 * @param userId
	 * @throws Exception
	 */
	protected void updateLogout(CCTConnection conn, String userId, String logoutDatetime) throws Exception{
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = logoutDatetime;
		params[paramIndex] = userId;
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateLogout"
				, params);

		PreparedStatement stmt = null;
	    try {
	        stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
	        stmt.executeUpdate();
	    } finally {
	        CCTConnectionUtil.closeStatement(stmt);
	    }	    
	}
	
	/**
	 * SQL :  UPDATE Connection timeout_SQL
	 * @implNote
	 * @param conn
	 * @param userId
	 * @param logoutDatetime
	 * @throws Exception
	 */
	protected void updateLogoutTime(CCTConnection conn, String userId, String logoutDatetime) throws Exception {
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = logoutDatetime;
		params[paramIndex++] = logoutDatetime;
		params[paramIndex] = userId;
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateLogoutTime"
				, params);

		PreparedStatement stmt = null;
	    try {
	        stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
	        stmt.executeUpdate();
	    } finally {
	        CCTConnectionUtil.closeStatement(stmt);
	    }	    
	}
	
	/**
	 * SQL : Check_Config_AuthenPB_SQL 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected String searchConfigAuthenPB(CCTConnection conn) throws Exception {
		getLogger().debug("");
		
		String authPB = "";
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchConfigAuthenPB"
				);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, null));
		}

		PreparedStatement stmt = null;
	    ResultSet rst = null;	    
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql);
	        rst = stmt.executeQuery();
	        
			if (rst.next()){
				authPB = StringUtil.nullToString(rst.getString("AUTHPB_FG"));
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return authPB;
	}
	
	/**
	 * SQL : Check_SEC_USER_OFFICER_SQL #1 -> CNT_USER
	 * @implNote
	 * @Create 2022-01-18 [-] sitthinarong.j
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	protected int countSecUserOfficer(CCTConnection conn, String userId) throws Exception {
		getLogger().debug("");
		
		int count = 0;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = userId;
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "countSecUserOfficer"
				, params
				);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}

		PreparedStatement stmt = null;
	    ResultSet rst = null;	    
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
	        rst = stmt.executeQuery();
	        
			if (rst.next()){
				count = rst.getInt("CNT_USER");
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return count;
	}
	
	/**
	 * SQL : Insert_SEC_USER_OFFICER_SQL 
	 * @implNote
	 * @Create 2022-01-18 [-] sitthinarong.j
	 * @param conn
	 * @param pbAuth
	 * @throws Exception
	 */
	protected void insertSecUserOfficer(CCTConnection conn, PibicsAuthenModel pbAuth) throws Exception {
		getLogger().debug("");
		
		int paramIndex = 0;
		Object[] params = new Object[11];
		params[paramIndex++] = pbAuth.getUserId();
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getRankNm());
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getName());
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getSurname());
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getSex());

		params[paramIndex++] = pbAuth.getDeptSeqNo();
		params[paramIndex++] = pbAuth.getPvSeqNo();
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getDeptAbbFmt1());
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getDeptAbbFmt3());
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getDeptTnm());
		
		params[paramIndex] = StringUtil.stringToNull(pbAuth.getPositionName());
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertSecUserOfficer"
				, params
				);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			stmt.executeUpdate();
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
	}
	
	/**
	 * SQL : Check_SEC_USER_OFFICER_SQL #2 -> CNT_DUP
	 * @implNote
	 * @Create 2022-01-18 [-] sitthinarong.j
	 * @param conn
	 * @param pbAuth
	 * @return
	 * @throws Exception
	 */
	protected int countDupUserOfficer(CCTConnection conn, PibicsAuthenModel pbAuth) throws Exception {
		getLogger().debug("");
		
		int count = 0;
		
		int paramIndex = 0;
		Object[] params = new Object[4];
		params[paramIndex++] = pbAuth.getUserId();
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getRankNm());
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getDeptTnm());
		params[paramIndex] = StringUtil.stringToNull(pbAuth.getPositionName());
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "countDupUserOfficer"
				, params
				);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}

		PreparedStatement stmt = null;
	    ResultSet rst = null;	    
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
	        rst = stmt.executeQuery();
	        
			if (rst.next()){
				count = rst.getInt("CNT_DUP");
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return count;
	}
	
	/**
	 * SQL : Update_SEC_USER_OFFICER_SQL 
	 * @implNote
	 * @Create 2022-01-18 [-] sitthinarong.j
	 * @param conn
	 * @param pbAuth
	 * @return
	 * @throws Exception
	 */
	protected void updateSecUserOfficer(CCTConnection conn, PibicsAuthenModel pbAuth) throws Exception {
		getLogger().debug("");
		
		int paramIndex = 0;
		Object[] params = new Object[11];
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getRankNm());
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getName());
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getSurname());
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getSex());
		params[paramIndex++] = pbAuth.getDeptSeqNo();
		
		params[paramIndex++] = pbAuth.getPvSeqNo();
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getDeptAbbFmt1());
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getDeptAbbFmt3());
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getDeptTnm());
		params[paramIndex++] = StringUtil.stringToNull(pbAuth.getPositionName());
		
		params[paramIndex] = pbAuth.getUserId();

		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateSecUserOfficer"
				, params
				);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			stmt.executeUpdate();
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
	}
	
	/**
	 * searchSecLoginSeq
	 * @implNote
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected Long searchSecLoginSeq(CCTConnection conn) throws Exception{
		Long seq = 0l;
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchSecLoginSeq"
				);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debug(sql));
		}

		PreparedStatement stmt = null;
	    ResultSet rst = null;	    
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql);
	        rst = stmt.executeQuery();
			if (rst.next()){
				seq = rst.getLong("SEC_LOGIN_SEQ");
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return seq;
	}
	
	/**
	 * SQL : Check_PopupChange_SQL
	 * @implNote
	 * @param conn
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	protected String searchPopupChangeLog(CCTConnection conn, String userName) throws Exception {
		getLogger().debug("");
		
		String result = "";
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.nullToString(userName);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchPopupChangeLog"
				, params
				);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}

		PreparedStatement stmt = null;
	    ResultSet rst = null;	    
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
	        rst = stmt.executeQuery();
	        
			if (rst.next()){
				result = StringUtil.nullToString(rst.getString("CHANGE_LOG"));
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * SQL : Update_PopupChangeLog_SQL
	 * @implNote
	 * @param conn
	 * @param userName
	 * @throws Exception
	 */
	protected void updatePopupChangeLog(CCTConnection conn, String userName) throws Exception {
		getLogger().debug("");
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.nullToString(userName);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updatePopupChangeLog"
				, params
				);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			stmt.executeUpdate();
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
	}
	
	protected PibicsAuthenModel searchUserDB(CCTConnection conn, String userName, String pass) throws Exception {
		getLogger().debug("");
		
		PibicsAuthenModel pbAuth = null;
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.nullToString(userName);
		params[paramIndex] = StringUtil.nullToString(pass);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchUserDB"
				, params
				);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}

		PreparedStatement stmt = null;
	    ResultSet rst = null;	    
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
	        rst = stmt.executeQuery();
	        
			if (rst.next()){
				pbAuth = new PibicsAuthenModel();
				pbAuth.setUserId(StringUtil.nullToString(rst.getString("USER_ID")));
				pbAuth.setResetPasswordStatus(StringUtil.nullToString(rst.getString("RESET_PASSWORD_STATUS")));
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return pbAuth;
	}
	
	protected boolean searchCountIsAdminGroup(CCTConnection conn, String loginId) throws Exception {
		getLogger().debug("");
		
		boolean isAdminGroup = false;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.nullToString(loginId);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchIsAdminGroup"
				, params
				);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}

		PreparedStatement stmt = null;
	    ResultSet rst = null;	    
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
	        rst = stmt.executeQuery();
	        
			if (rst.next()){
				isAdminGroup = rst.getInt("CNT") > 0 ? true : false;
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return isAdminGroup;
	}
	
	protected void updateChangePassword(CCTConnection conn, String userId, String newPass) throws Exception {
		getLogger().debug("");
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.stringToNull(newPass);
		params[paramIndex++] = StringUtil.stringToNull(userId);
		params[paramIndex++] = StringUtil.stringToNull(userId);
		

		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateChangePassword"
				, params
				);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			stmt.executeUpdate();
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
	}
	
	/**
	 * หาสิทธิ์ตามปุ่มที่กำหนดเอาไว้
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	protected HashMap<String, OperatorButton> searchOperBtnByUserId(CCTConnection conn, String userId) throws Exception {
		HashMap<String, OperatorButton> mapOper = new LinkedHashMap<>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = userId;
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchOperBtnByUserId"
				, params);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
	    ResultSet rst = null;	    
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
	        rst = stmt.executeQuery();

	        while (rst.next()) {
	        	OperatorButton obj = new OperatorButton();
	        	obj.setSecAbbr(StringUtil.nullToString(rst.getString("sec_abbr")));
	        	obj.setB1(convertToBoolean(rst.getString("b1")));
	        	obj.setB2(convertToBoolean(rst.getString("b2")));
	        	obj.setB3(convertToBoolean(rst.getString("b3")));
	        	obj.setB4(convertToBoolean(rst.getString("b4")));
	        	obj.setB5(convertToBoolean(rst.getString("b5")));
	        	obj.setB6(convertToBoolean(rst.getString("b6")));
	        	obj.setB7(convertToBoolean(rst.getString("b7")));
	        	obj.setB8(convertToBoolean(rst.getString("b8")));
	        	obj.setB9(convertToBoolean(rst.getString("b9")));
	        	obj.setB10(convertToBoolean(rst.getString("b10")));
	        	obj.setB11(convertToBoolean(rst.getString("b11")));
	        	obj.setB12(convertToBoolean(rst.getString("b12")));
	        	
	        	mapOper.put(obj.getSecAbbr(), obj);
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return mapOper;
	}
	
	private boolean convertToBoolean(String data) throws Exception {
		return (StringUtil.stringToNull(data) != null && data.equals("Y"));
	}
}
	
	
	

	
	

