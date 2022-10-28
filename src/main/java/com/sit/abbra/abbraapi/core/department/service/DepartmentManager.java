package com.sit.abbra.abbraapi.core.department.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.department.domain.DepartmentModel;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.core.common.service.CommonManager;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;

public class DepartmentManager extends CommonManager {

	private DepartmentService service;
	
	public DepartmentManager(Logger logger) {
		super(logger);
		this.service = new DepartmentService(logger);
	}

	public void gotoDepartment(DepartmentModel modelResponse, String category) throws Exception {
		getLogger().debug(" gotoDepartment ");
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			modelResponse.setDepartment(service.searchDepartment(conn, category));
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
}
