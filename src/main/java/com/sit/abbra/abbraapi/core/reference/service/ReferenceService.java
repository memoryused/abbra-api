package com.sit.abbra.abbraapi.core.reference.service;

import java.util.List;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.abbra.abbraapi.core.reference.domain.Reference;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.core.common.service.CommonService;

import util.database.connection.CCTConnection;

public class ReferenceService extends CommonService {

	private ReferenceDAO dao;
	
	public ReferenceService(Logger logger) {
		super(logger);
		this.dao = new ReferenceDAO(logger, SQLPath.REFERENCE_SQL.getSqlPath());
	}

	protected List<Reference> searchReference(CCTConnection conn, String category, LoginUser loginUser) throws Exception {
		return dao.searchReference(conn, category, loginUser);
	}
}
