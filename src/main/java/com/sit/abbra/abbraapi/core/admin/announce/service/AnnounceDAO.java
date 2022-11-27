package com.sit.abbra.abbraapi.core.admin.announce.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.admin.domain.Announce;
import com.sit.abbra.abbraapi.core.admin.domain.AnnounceSearch;
import com.sit.abbra.abbraapi.core.admin.domain.AnnounceSearchCriteria;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.util.security.EExtensionApiSecurityUtil;
import com.sit.core.common.domain.CommonSQLPath;
import com.sit.core.common.service.CommonDAO;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.sql.SQLExtraParam;
import util.sql.SQLParameterizedDebugUtil;
import util.sql.SQLParameterizedUtil;
import util.sql.enums.SQLParamType;
import util.string.StringUtil;

public class AnnounceDAO extends CommonDAO {

	public static final String TITLE = "Title";
	public static final String DETAIL = "Detail";
	public static final String FILE = "File";
	
	public AnnounceDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	/**
	 * searchAnnounce
	 * @param conn
	 * @param criteria
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected List<AnnounceSearch> searchAnnounce(CCTConnection conn, AnnounceSearchCriteria criteria, LoginUser loginUser) throws Exception {
		getLogger().debug(" Admin searchAnnounce ");
		
		List<AnnounceSearch> listAnnounceSearch = new ArrayList<>();
		
		int paramIndex = 0;
		Object[] params = new Object[4];
		params[paramIndex++] = StringUtil.stringToNull(criteria.getAnnounceType());
		params[paramIndex++] = StringUtil.stringToNull(criteria.getTitle());
		params[paramIndex++] = StringUtil.stringToNull(criteria.getDetail());
		params[paramIndex] = StringUtil.stringToNull(criteria.getAnnounceDate());
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"searchAnnounce",
				params
				);
		
		if(getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			while (rst.next()) {
				AnnounceSearch result = new AnnounceSearch();
				
				result.setHiddenToken(EExtensionApiSecurityUtil.encryptId(
							loginUser.getSalt(), 
							loginUser.getSecret(), 
							rst.getString("AnnounceId")
							));
				result.setAnnounceType(StringUtil.nullToString(rst.getString("Category")));
				result.setTitle(StringUtil.nullToString(rst.getString(TITLE)));
				result.setDetail(StringUtil.nullToString(rst.getString(DETAIL)));
				result.setAnnounceDate(StringUtil.nullToString(rst.getString("Fm_AnnounceDate")));
				result.setStatus(StringUtil.nullToString(rst.getString("Status")));
				
				listAnnounceSearch.add(result);
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		getLogger().debug("Found data {}", listAnnounceSearch.size());
		return listAnnounceSearch;
	}
	
	/**
	 * searchById
	 * @param conn
	 * @param id
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected Announce searchById(CCTConnection conn, String id, LoginUser loginUser) throws Exception {
		getLogger().debug(" Admin searchById ");
		
		Announce result = new Announce();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.stringToNull(id);
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"searchAnnounceById",
				params
				);
		
		if(getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				result.setHiddenToken(EExtensionApiSecurityUtil.encryptId(
							loginUser.getSalt(), 
							loginUser.getSecret(), 
							rst.getString("AnnounceId")
							));
				result.setAnnounceType(StringUtil.nullToString(rst.getString("Category")));
				result.setTitle(StringUtil.nullToString(rst.getString(TITLE)));
				result.setDetail(StringUtil.nullToString(rst.getString(DETAIL)));
				result.setAnnounceDate(StringUtil.nullToString(rst.getString("Fm_AnnounceDate")));
				result.setStatus(StringUtil.nullToString(rst.getString("Status")));
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * addAnnounce
	 * @param conn
	 * @param announce
	 * @param category
	 * @throws Exception
	 */
	protected void addAnnounce(CCTConnection conn, Announce announce, String category) throws Exception {
		getLogger().debug(" Admin addAnnounce ");
		
		int paramIndex = 0;
		Object[] params = new Object[8];
		params[paramIndex++] = StringUtil.stringToNull(announce.getAnnounceType());
		params[paramIndex++] = StringUtil.stringToNull(announce.getTitle());
		params[paramIndex++] = StringUtil.stringToNull(announce.getDetail());
		params[paramIndex++] = StringUtil.stringToNull(announce.getAnnounceDate());
		params[paramIndex++] = StringUtil.stringToNull(announce.getCoverPicPath());
		params[paramIndex++] = StringUtil.stringToNull(announce.getFilePath());
		params[paramIndex++] = StringUtil.stringToNull(announce.getStatus());
		params[paramIndex] = StringUtil.stringToNull(category);
		
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"insertAnnounce",
				params
				);
		
		if(getLogger().isDebugEnabled()) {
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
	 * updateAnnounce
	 * @param conn
	 * @param announce
	 * @param id
	 * @throws Exception
	 */
	protected void updateAnnounce(CCTConnection conn, Announce announce, String id) throws Exception {
		getLogger().debug(" Admin updateAnnounce ");
		
		int paramIndex = 0;
		Object[] params = new Object[7];
		
		params[paramIndex++] = StringUtil.stringToNull(announce.getTitle());
		params[paramIndex++] = StringUtil.stringToNull(announce.getDetail());
		params[paramIndex++] = StringUtil.stringToNull(announce.getAnnounceDate());
		params[paramIndex++] = new SQLExtraParam(SQLParamType.COLUMN_VALUE, StringUtil.stringToNull(announce.getCoverPicPath()), false);// StringUtil.stringToNull(announce.getCoverPicPath());
		params[paramIndex++] = new SQLExtraParam(SQLParamType.COLUMN_VALUE, StringUtil.stringToNull(announce.getFilePath()), false);// StringUtil.stringToNull(announce.getFilePath());
		params[paramIndex++] = StringUtil.stringToNull(announce.getStatus());
		params[paramIndex] = id;
		
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"updateAnnounce",
				params
				);
		
		if(getLogger().isDebugEnabled()) {
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
	 * updateStatus
	 * @param conn
	 * @param announce
	 * @param ids
	 * @throws Exception
	 */
	protected void updateStatus(CCTConnection conn, Announce announce, String ids) throws Exception {
		getLogger().debug(" Admin updateStatus ");
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		
		params[paramIndex++] = StringUtil.stringToNull(announce.getStatus());
		params[paramIndex] = new SQLExtraParam(SQLParamType.WHERE_IN_NUMBER, ids, true);
		
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"updateStatus",
				params
				);
		
		if(getLogger().isDebugEnabled()) {
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

}
