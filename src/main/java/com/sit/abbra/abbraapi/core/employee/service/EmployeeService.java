package com.sit.abbra.abbraapi.core.employee.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.core.common.service.CommonService;

import util.database.connection.CCTConnection;

public class EmployeeService extends CommonService {

	private EmployeeDAO dao;
	
	public EmployeeService(Logger logger) {
		super(logger);
		this.dao = new EmployeeDAO(logger, SQLPath.EMPLOYEE_SQL.getSqlPath());
	}
	
	protected String searchEmployee(CCTConnection conn, String query) throws Exception {
		return dao.searchEmployee(conn, query);
	}

}
