package com.sit.abbra.abbraapi.core.config.parameter.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.config.parameter.domain.Parameter;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.config.parameter.domain.SecureConfig;
import com.sit.abbra.abbraapi.core.selectitem.service.SelectItemManager;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.abbra.abbraapi.util.rest.RestClientUtil;
import com.sit.core.common.service.CommonManager;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;

public class ParameterManager extends CommonManager {

	private ParameterService service = null;

	public ParameterManager(Logger logger) {
		super(logger);
		this.service = new ParameterService(getLogger());
	}

	public void testSQL() {
		CCTConnection conn = null;
		try {
			getLogger().info("DBConnection test...");
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			getLogger().info("DBConnection test is OK, Conn isOpen: {}", !conn.getConn().isClosed());
			
			getLogger().info("SQL test...");
			String testResult = service.testSQL(conn);
        	getLogger().info("SQL test [{}]...ok", testResult);
		} catch (Exception e) {
			getLogger().catching(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	public void setSystemProperties() {
		if(ParameterConfig.getSockProxy() != null && ParameterConfig.getSockProxy().isUseProxy()) {
			RestClientUtil.setSocksProxy(ParameterConfig.getSockProxy().getProxyIP()
					, ParameterConfig.getSockProxy().getProxyPort()
					, ParameterConfig.getSockProxy().getProxyVersion()
					, ParameterConfig.getSockProxy().getNonProxyHosts());			
		}
	}
	
	public Parameter loadParameter(String xmlPath) throws Exception {
		return getService().loadParameter(xmlPath);
	}
	
	public SecureConfig loadSecre(String securePath) throws IOException {
		SecureConfig secure = null;
		try (InputStream inputStream = new FileInputStream(securePath)) {
			if (new File(securePath).exists()) {
				secure = new SecureConfig();
				Properties prop = new Properties();
				prop.load(inputStream);

				String origin = prop.getProperty("origin");
				secure.setOrigin(origin);

				if (origin != null && !origin.equals("")) {
					String[] arrOrigin = origin.split(",");
					for (int i = 0; i < arrOrigin.length; i++) {
						secure.getAllowedDomains().add(arrOrigin[i]);
					}
				}

				secure.setMethod(prop.getProperty("method"));
				secure.setHeader(prop.getProperty("header"));
				secure.setMaxage(prop.getProperty("maxage"));
				secure.setHost(prop.getProperty("host"));
			}
		}
		return secure;
	}

	public void loadGlobalData() {
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			getLogger().info("Load global data...");
			SelectItemManager manager = new SelectItemManager(getLogger());
			manager.init(conn);
			
			getLogger().info("Load global data size: {}", SelectItemManager.getMapGlobalData().get(ParameterConfig.getApplication().getApplicationLocale()).size());
		} catch (Exception e) {
			getLogger().catching(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	public ParameterService getService() {
		return service;
	}
}
