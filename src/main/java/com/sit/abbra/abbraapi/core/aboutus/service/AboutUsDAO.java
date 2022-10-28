package com.sit.abbra.abbraapi.core.aboutus.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.core.common.domain.CommonSQLPath;
import com.sit.core.common.service.CommonDAO;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.sql.SQLParameterizedDebugUtil;
import util.sql.SQLParameterizedUtil;
import util.string.StringUtil;

public class AboutUsDAO extends CommonDAO {

	public AboutUsDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	protected String searchAboutUs(CCTConnection conn, int annType) throws Exception {
		getLogger().debug(" searchAboutUs ");
		
		String str = "";
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = annType;
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"searchAboutUs",
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
				String strPath = StringUtil.nullToString(rst.getString("File"));
				if(annType == 7) {
					str = strPath;	// vdo ส่ง path ไปเลย
				} else if(!strPath.isEmpty()) {
					str = EExtensionApiUtil.convertBase64(ParameterConfig.getApplication().getSharePath() + strPath);
				}
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return str;
	}

}
