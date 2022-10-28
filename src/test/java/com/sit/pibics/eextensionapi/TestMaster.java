package com.sit.pibics.eextensionapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.Logger;

import unittest.TestVariable;
import util.database.connection.CCTConnection;
import util.database.enums.DbType;
import util.log4j2.DefaultLogUtil;

public class TestMaster {

	public static Connection getConnection() throws SQLException {
		String uri = TestVariable.DATABASE_URI;
		String username = TestVariable.DATABASE_USERNAME;
		String password = TestVariable.DATABASE_PASSWORD;
		return DriverManager.getConnection(uri, username, password); 
	}
	
	public static CCTConnection getCCTConnection() throws SQLException {
		CCTConnection cctConn = new CCTConnection();
		Connection conn = getConnection();
		
		cctConn.setConn(conn);
		cctConn.setDbType(DbType.MARIADB);
		cctConn.setSchemas(new LinkedHashMap<>());
		return cctConn;
	}
	
	public void setAutoCommit(Connection conn, boolean flag) {
		try {
			conn.setAutoCommit(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(ResultSet rst) {
		try {
			if (rst != null) {
				rst.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Logger getLogger() {
		return DefaultLogUtil.UTIL;
	}
}
