package com.sit.abbra.abbraapi.core.security.log.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.log.domain.LogEvent;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.abbra.abbraapi.util.date.DateUtil;
import com.sit.core.common.service.CommonService;
import com.sit.domain.GlobalVariable;

import util.database.connection.CCTConnection;
import util.string.StringUtil;

public class LogService extends CommonService {
	private LogDAO dao = null;
	
	public LogService(Logger logger) {
		super(logger);
		this.dao = new LogDAO(getLogger(), SQLPath.LOG_SQL.getSqlPath());
	}
	
	protected Long insetEvent(CCTConnection conn, String operatorId, String pathInfo, String logData, LoginUser loginUser) throws Exception {
		Long eventId = null;
		
		if (loginUser == null) {
			loginUser = new LoginUser();
		}
		
		/* 
		 * เพิ่ม filter เพื่อไม่บันทึกข้อมูล log event กรณีเข้าหน้าจอต่างๆที่เป็น /goto 
		 */
		if(!pathInfo.matches(GlobalVariable.FilterSkipEventLog.GOTO.getValue()) && !pathInfo.matches(GlobalVariable.FilterSkipEventLog.INIT_SEARCH.getValue())) {
			LogEvent event = new LogEvent();
			event.setEventId(dao.searchSeqNo(conn));
			if (!StringUtil.nullToString(operatorId).isEmpty()) {
				event.setOperatorId(EExtensionApiUtil.convertLongValue(operatorId));
			}
			event.setMethodClass(getEventMethod(pathInfo));
			event.setLoginUser(loginUser);
			event.setLogData(logData);
			
			dao.insertEvent(conn, event);
			eventId = event.getEventId();
		}
		
		return eventId;
	}
	
	private String getEventMethod(String pathInfo){
		String eventMethod = null;
		try {
			String[] arrPath = pathInfo.split("/");
			eventMethod = arrPath[arrPath.length-1];
		} catch(Exception e) {
			getLogger().catching(e);
		}
		return eventMethod;
	}
	
	/**
	 * Generate Logout DateTime
	 * @implNote
	 * @param currentDateTime
	 * @return
	 * @throws ParseException
	 */
	protected String generateLogoutDateTime(String currentDateTime) throws ParseException {
		Date date = DateUtil.toDate(currentDateTime, ParameterConfig.getDateFormat().getDbFormat());
		Calendar c = Calendar.getInstance(new Locale("en", "EN"));
		c.setTime(date);
		c.add(Calendar.MINUTE, Integer.parseInt(ParameterConfig.getSecurityUtilConfig().getLoginExpire()+""));
		return DateUtil.toString(c.getTime(), ParameterConfig.getDateFormat().getDbFormat());
	}

}
