package com.sit.abbra.abbraapi.core.reference.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.reference.domain.ReferenceModel;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.enums.DisplayStatus;
import com.sit.abbra.abbraapi.enums.MessageAlert;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.core.common.service.CommonManager;
import com.sit.exception.CustomException;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;

public class ReferenceManager extends CommonManager {

	private ReferenceService service;
	
	public ReferenceManager(Logger logger) {
		super(logger);
		this.service = new ReferenceService(logger);
	}
	
	public void gotoSearchReference(ReferenceModel modelResponse, String category, LoginUser loginUser) throws Exception {
		getLogger().debug(" gotoSearchReference ");
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			if(category.isEmpty()) {
				throw new CustomException(MessageAlert.UNSUCCESS.getVal(), DisplayStatus.WARN.getValue());
			}
			modelResponse.setListReference(service.searchReference(conn, category, loginUser));
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
}
