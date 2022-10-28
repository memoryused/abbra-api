package com.sit.abbra.abbraapi.core.department.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.department.domain.Department;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.core.common.domain.CommonSQLPath;
import com.sit.core.common.service.CommonDAO;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.sql.SQLParameterizedDebugUtil;
import util.sql.SQLParameterizedUtil;
import util.string.StringUtil;

public class DepartmentDAO extends CommonDAO{

	public DepartmentDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	protected Department searchDepartment(CCTConnection conn, String category) throws Exception {
		getLogger().debug(" searchDepartment ");
		
		Department dep = null;
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.stringToNull(category);
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"searchDepartment",
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
				dep = new Department();
				dep.setSubject(StringUtil.nullToString(rst.getString("Title")));
				dep.setDetail(StringUtil.nullToString(rst.getString("Detail")));
				String srcPath = StringUtil.nullToString(rst.getString("File"));
				
				if(!srcPath.isEmpty()) {
					dep.setSrc(EExtensionApiUtil.convertBase64(ParameterConfig.getApplication().getSharePath() + srcPath));
				}
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return dep;
	}
}
