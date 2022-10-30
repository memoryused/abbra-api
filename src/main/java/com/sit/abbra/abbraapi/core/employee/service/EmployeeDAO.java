package com.sit.abbra.abbraapi.core.employee.service;

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

public class EmployeeDAO extends CommonDAO{

	public EmployeeDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	protected String searchEmployee(CCTConnection conn, String query) throws Exception {
		getLogger().debug(" searchEmployee ");
		
		String img = "";
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.stringToNull(query);
		params[paramIndex++] = StringUtil.stringToNull(query);
		params[paramIndex++] = StringUtil.stringToNull(query);
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"searchEmployee",
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
				String imgPath = StringUtil.nullToString(rst.getString("img_card"));
				if(!imgPath.isEmpty()) {
					img = EExtensionApiUtil.convertBase64(ParameterConfig.getApplication().getSharePath() + imgPath);
				}
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return img;
	}
}
