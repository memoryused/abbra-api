package com.sit.abbra.abbraapi.core.reference.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.reference.domain.Reference;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.util.security.EExtensionApiSecurityUtil;
import com.sit.core.common.domain.CommonSQLPath;
import com.sit.core.common.service.CommonDAO;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.sql.SQLParameterizedDebugUtil;
import util.sql.SQLParameterizedUtil;
import util.string.StringUtil;

public class ReferenceDAO extends CommonDAO {

	public ReferenceDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	protected List<Reference> searchReference(CCTConnection conn, String category, LoginUser loginUser) throws Exception {
		getLogger().debug(" searchReference ");
		
		List<Reference> listReference = new ArrayList<>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.stringToNull(category);
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"searchReference",
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
				Reference ref = new Reference();
				
				String filename = StringUtil.nullToString(rst.getString("File"));
				if(!filename.isEmpty()) {
					String[] fnArray = filename.split("/", -1);
					// [, promotion, 2022, 10, thumbnail.3.jpg]
					
					Calendar cal = Calendar.getInstance(Locale.ENGLISH);
					cal.set(Integer.valueOf(fnArray[fnArray.length-3]), Integer.valueOf(fnArray[fnArray.length-2])-1, 1);
					String fMonth = cal.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.ENGLISH);
					
					ref.setLabel(fMonth + "-" + fnArray[fnArray.length-2]);
					ref.setExtension(fnArray[fnArray.length-1].substring(fnArray[fnArray.length-1].lastIndexOf(".")+1));
					ref.setFn(filename);
					ref.setFnPath(EExtensionApiSecurityUtil.encryptId(
							loginUser.getSalt(), 
							loginUser.getSecret(), 
							filename
							)
						);
					
					listReference.add(ref);
				}
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listReference;
	}
}
