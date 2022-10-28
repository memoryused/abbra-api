package com.sit.abbra.abbraapi.web.config.parameter.servlet;


import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.config.parameter.service.ParameterManager;

import util.log4j2.DefaultLogUtil;

public class ParameterServlet extends HttpServlet {

	private static final long serialVersionUID = -2473461747972119759L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initParameter();
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initParameter();
	}

	@Override
	public void init() throws ServletException {
		initParameter();
	}
	
	@Override
	public void destroy() {
		getLogger().info("");
        Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                getLogger().debug("Deregistering jdbc driver: {}", driver);
            } catch (SQLException e) {
            	getLogger().error("Error deregistering driver {}", driver);
            	getLogger().catching(e);
            }
        }
	}
	
	private void initParameter() {
		getLogger().info("Locale.getDefault: {}", Locale.getDefault());
		ParameterConfig.initParameter(getServletContext().getRealPath(getInitParameter("parameterfile")));
		ParameterConfig.initSecure(getServletContext().getRealPath(getInitParameter("securefile")));
		ParameterConfig.initSecurityUtil();
		
		ParameterManager parameterManager = new ParameterManager(getLogger());
		//parameterManager.initZabbixItem();
		parameterManager.testSQL();
		parameterManager.setSystemProperties();
		
		//parameterManager.loadGlobalData();
	}
	
	public Logger getLogger() {
		return DefaultLogUtil.INITIAL;
	}
}
