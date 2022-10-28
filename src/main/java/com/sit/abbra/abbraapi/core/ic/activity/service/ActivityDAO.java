package com.sit.abbra.abbraapi.core.ic.activity.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.common.AbbraCommonDomain;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.core.common.domain.CommonSQLPath;
import com.sit.core.common.service.CommonDAO;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.sql.SQLParameterizedDebugUtil;
import util.sql.SQLParameterizedUtil;
import util.string.StringUtil;

public class ActivityDAO extends CommonDAO {

	public ActivityDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	protected List<AbbraCommonDomain> searchActivity(CCTConnection conn, int annType) throws Exception {
		getLogger().debug(" searchActivity ");
		
		List<AbbraCommonDomain> listActivity = new ArrayList<>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = annType;
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"searchActivity",
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
				AbbraCommonDomain release = new AbbraCommonDomain();
				
				release.setSubject(StringUtil.nullToString(rst.getString("Title")));
				release.setDetail(StringUtil.nullToString(rst.getString("Detail")));
				release.setSrc(StringUtil.nullToString(rst.getString("File")));
				
				if(!release.getSrc().isEmpty()) {
					release.setThumb(
							EExtensionApiUtil.getThumbnailByScale(
									ParameterConfig.getApplication().getSharePath() + release.getSrc(), 
									ParameterConfig.getThumbnailConfig().getScale(), 
									ParameterConfig.getThumbnailConfig().isWatermark(), 
									ParameterConfig.getThumbnailConfig().getWatermarkImage())
							);
				}
				listActivity.add(release);
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listActivity;
	}
	
	protected List<AbbraCommonDomain> searchAllActivity(CCTConnection conn, int annType) throws Exception {
		getLogger().debug(" searchAllActivity ");
		
		List<AbbraCommonDomain> listActivity = new ArrayList<>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = annType;
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"searchAllActivity",
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
				AbbraCommonDomain release = new AbbraCommonDomain();
				
				release.setSubject(StringUtil.nullToString(rst.getString("Title")));
				release.setDetail(StringUtil.nullToString(rst.getString("Detail")));
				release.setSrc(StringUtil.nullToString(rst.getString("File")));
				
				if(!release.getSrc().isEmpty()) {
					release.setThumb(
							EExtensionApiUtil.getThumbnailByScale(
									ParameterConfig.getApplication().getSharePath() + release.getSrc(), 
									ParameterConfig.getThumbnailConfig().getScale(), 
									ParameterConfig.getThumbnailConfig().isWatermark(), 
									ParameterConfig.getThumbnailConfig().getWatermarkImage())
							);
				}
				listActivity.add(release);
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listActivity;
	}
}
