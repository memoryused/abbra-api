package com.sit.abbra.abbraapi.web.config.parameter.servlet;


import java.io.IOException;
import java.io.PrintWriter;
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
import com.sit.abbra.abbraapi.util.json.JsonUtil;

import util.cryptography.EncryptionUtil;
import util.cryptography.enums.EncType;
import util.log4j2.DefaultLogUtil;
import util.string.StringUtil;

public class ParameterServlet extends HttpServlet {

	private static final long serialVersionUID = -2473461747972119759L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] generatedPassArr = genseratePassword(request, response);
		
		PrintWriter writer = response.getWriter();
		writer.println(JsonUtil.convertObject2Json(generatedPassArr));
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
	
	private String[] genseratePassword(HttpServletRequest request, HttpServletResponse response) {
		String[] generatedPassword = null;
		int limit = 19;
		try {
			String pw = request.getParameter("pw");
			getLogger().info("{}", pw);
			
			if(StringUtil.stringToNull(pw) != null) {
				String[] pwdArray = pw.split(",", -1);
				generatedPassword = new String[pwdArray.length];
				
				for (int i = 0; i < pwdArray.length; i++) {
					if(i>limit)
						break;
						
					if(!pwdArray[i].trim().isEmpty()) {
						generatedPassword[i] = EncryptionUtil.doEncrypt(pwdArray[i], EncType.SHA256);
					}
				}
			}
		} catch (Exception e) {
			getLogger().error("", e);
		}
		return generatedPassword;
	}
}
