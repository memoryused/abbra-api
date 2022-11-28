package com.sit.abbra.abbraapi.web.config.globaldata.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.selectitem.service.SelectItemManager;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.log4j2.DefaultLogUtil;

public class GlobalDataServlet extends HttpServlet {
	
	// REVIEW : Code Analysis by anusorn.l : Servlets should not have mutable instance fields
	private static final Logger logger = DefaultLogUtil.INITIAL;
	
	public Logger getLogger() {
		return logger;
	}

	private static final long serialVersionUID = -1326931967347560900L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getLogger().info("Get");
		try {
			init();
		} catch (Exception e) {
			getLogger().error(e);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getLogger().info("Post");
		try {
			init();
		} catch (Exception e) {
			getLogger().error(e);
		}
	}

	@Override
	public void init() {

		CCTConnection conn = null;
		try {
			// Connect database
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());

			getLogger().debug("Initial global data");
			// โหลด GlobalData ผ่าน Method SelectItemManager.init()
			SelectItemManager selectItemManager = new SelectItemManager(getLogger());
			selectItemManager.init(conn);
			getLogger().debug("Initial global data completed.");
			
		} catch (Exception e) {
			getLogger().error("Can't load global data!!!", e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
}
