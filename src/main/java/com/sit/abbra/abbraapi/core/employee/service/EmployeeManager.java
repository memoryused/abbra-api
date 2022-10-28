package com.sit.abbra.abbraapi.core.employee.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.employee.domain.EmployeeModel;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.core.common.service.CommonManager;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.string.StringUtil;

public class EmployeeManager extends CommonManager {

	private EmployeeService service;
	
	public EmployeeManager(Logger logger) {
		super(logger);
		this.service = new EmployeeService(logger);
	}
	
	public void searchEmployee(EmployeeModel modelResponse, String query) throws Exception {
		getLogger().debug(" searchEmployee ");
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			if(StringUtil.stringToNull(query) != null) {
				modelResponse.setImg(service.searchEmployee(conn, query));
			}
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}

}
