package com.sit.abbra.abbraapi.util.token.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.core.common.service.CommonService;

import util.database.connection.CCTConnection;

public class TokenService extends CommonService{
	
	private TokenDAO dao = null;
	
	public TokenService(Logger logger) {
		super(logger);
		this.dao = new TokenDAO(getLogger(), SQLPath.TOKEN_SQL.getSqlPath());
	}
	
	protected String searchTokenAuthorize(CCTConnection conn, String clientId, String secretId) throws Exception {
		return dao.searchTokenAuthor(conn, clientId, secretId);
	}
	
}
