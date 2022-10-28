package com.sit.abbra.abbraapi.core.ic.announce.service;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.core.common.service.CommonService;

public class AnnounceService extends CommonService {

	private AnnounceDAO dao;
	
	public AnnounceService(Logger logger) {
		super(logger);
		this.dao = new AnnounceDAO(logger, SQLPath.ANNOUNCE_SQL.getSqlPath());
	}

}
