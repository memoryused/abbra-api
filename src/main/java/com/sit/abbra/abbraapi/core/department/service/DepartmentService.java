package com.sit.abbra.abbraapi.core.department.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.abbra.abbraapi.core.department.domain.Department;
import com.sit.core.common.service.CommonService;

import util.database.connection.CCTConnection;

public class DepartmentService extends CommonService {

	private DepartmentDAO dao;
	
	public DepartmentService(Logger logger) {
		super(logger);
		this.dao = new DepartmentDAO(logger, SQLPath.DEPARTMENT_SQL.getSqlPath());
	}
	
	protected Department searchDepartment(CCTConnection conn, String category) throws Exception {
		return dao.searchDepartment(conn, category);
	}

}
