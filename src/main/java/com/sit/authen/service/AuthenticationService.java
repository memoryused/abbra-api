package com.sit.authen.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.abbra.abbraapi.core.security.login.domain.AuthorizeRequest;
import com.sit.abbra.abbraapi.core.security.login.domain.ClientSystem;
import com.sit.core.common.service.CommonService;

import util.database.connection.CCTConnection;

public class AuthenticationService extends CommonService{

	private AuthenticationDAO dao;
	
	public AuthenticationService(Logger logger) {
		super(logger);
		this.dao = new AuthenticationDAO(getLogger(), SQLPath.AUTHEN_SQL.getSqlPath());
	}
	
	protected void deleteExpireAuthorize(CCTConnection conn, String currentDateTime) throws Exception {
		dao.deleteExpireAuthorize(conn, currentDateTime);
	}
	
	protected ClientSystem searchClientSystem(CCTConnection conn, String clientId) throws Exception {
		return dao.searchClientSystem(conn, clientId);
	}
	
	protected void deleteAuthorizeByKey(CCTConnection conn, String key) throws Exception {
		dao.deleteAuthorizeByKey(conn, key);
	}
	
	protected AuthorizeRequest searchAuthorizeByKey(CCTConnection conn, String key) throws Exception {
		return dao.searchAuthorizeByKey(conn, key);
	}
	
	protected void addAuthorize(CCTConnection conn, AuthorizeRequest authorizeRequest) throws Exception {
		AuthorizeRequest result = dao.searchAuthorizeByKey(conn, authorizeRequest.getKey());
		if(result == null) {
			getLogger().debug("Not found authorize request");
			dao.insertAuthorize(conn, authorizeRequest);
		} else {
			getLogger().debug("Found authorize request: {}", authorizeRequest.getKey());
			dao.updateAuthorize(conn, authorizeRequest);
		}
	}

}
