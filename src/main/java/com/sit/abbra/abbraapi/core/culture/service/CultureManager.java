package com.sit.abbra.abbraapi.core.culture.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.culture.domain.CultureModel;
import com.sit.abbra.abbraapi.enums.DisplayStatus;
import com.sit.abbra.abbraapi.enums.MessageAlert;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.core.common.service.CommonManager;
import com.sit.exception.CustomException;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.string.StringUtil;

public class CultureManager extends CommonManager {

	private CultureService service;
	
	public CultureManager(Logger logger) {
		super(logger);
		this.service = new CultureService(logger);
	}

	/**
	 * searchCulture
	 * 
	 * @param modelResponse
	 * @param announceType
	 * @throws Exception
	 */
	public void searchCulture(CultureModel modelResponse, String announceType) throws Exception {
		getLogger().debug(" searchCulture ");
		
		CCTConnection conn = null;
		try {
			// Validate
			if(StringUtil.nullToString(announceType).isEmpty()) {
				throw new CustomException(MessageAlert.UNSUCCESS.getVal(), DisplayStatus.WARN.getValue());
			}
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			service.searchCulture(conn, announceType, modelResponse);

		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
}
