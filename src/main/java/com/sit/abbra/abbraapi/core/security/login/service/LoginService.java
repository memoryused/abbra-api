package com.sit.abbra.abbraapi.core.security.login.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.ClientSystem;
import com.sit.abbra.abbraapi.core.security.login.domain.Login;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.security.login.domain.PibicsAuthenModel;
import com.sit.abbra.abbraapi.core.security.login.domain.SecLogin;
import com.sit.abbra.abbraapi.core.security.login.domain.Token;
import com.sit.abbra.abbraapi.enums.MessageCode;
import com.sit.abbra.abbraapi.util.date.DateUtil;
import com.sit.abbra.abbraapi.util.object.ObjectUtil;
import com.sit.abbra.abbraapi.util.rest.RestClientUtil;
import com.sit.abbra.abbraapi.util.validate.InputValidateUtil;
import com.sit.common.CommonResponse;
import com.sit.core.common.service.CommonService;
import com.sit.exception.CustomException;
import com.sit.exception.TokenExpireException;

import util.database.connection.CCTConnection;

public class LoginService extends CommonService {

	private static final String INVALID_DATA = "30014";// Invalid Data - แสดง Alert 30014 [ข้อมูลไม่ถูกต้อง] 
	
	private LoginDAO dao = null;

	public LoginService(Logger logger) {
		super(logger);
		this.dao = new LoginDAO(logger, SQLPath.LOGIN_SQL.getSqlPath());
	}
	
	/**
	 * Get client system by client id
	 * @implNote
	 * @param conn
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	protected ClientSystem searchClientSystem(CCTConnection conn, String clientId) throws Exception {
		return dao.searchClientSystem(conn, clientId);
	}
	
	/**
	 * Check login duplicate
	 * @implNote
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	protected long searchLoginDup(CCTConnection conn, String userId) throws Exception {
		return dao.searchLoginDup(conn, userId);
	}

	protected String searchLoginDupCheckLogouDateTime(CCTConnection conn, String userId) throws Exception {
		return dao.searchLoginDupCheckLogouDateTime(conn, userId);
	}

	/**
	 * Update Last Login
	 * @implNote
	 * @param conn
	 * @param loginUser
	 * @param token
	 * @param agentName
	 * @param loginDatetime
	 * @param logoutDatetime
	 * @param actionDatetime
	 * @throws Exception
	 */
	protected void updateLastLogin(CCTConnection conn, LoginUser loginUser, Token token, String agentName, String loginDatetime, String logoutDatetime, String actionDatetime) throws Exception {
		SecLogin secLogin = new SecLogin();
		secLogin.setLoginUser(loginUser);
		secLogin.setToken(token);
		secLogin.setAgentName(agentName);
		secLogin.setLoginDatetime(loginDatetime);
		secLogin.setLogoutDatetime(logoutDatetime);
		secLogin.setActionDatetime(actionDatetime);
		
		int rowCount = dao.updateSecLogin(conn, secLogin);
		getLogger().debug("updateSecLogin: {}" , rowCount);
		if (rowCount == 0) {
			//secLogin.setLoginId(dao.searchSecLoginSeq(conn));
			dao.insertSecLogin(conn, secLogin);
		}
	}

	/**
	 * Get Login by token
	 * @implNote
	 * @param conn
	 * @param token
	 * @return
	 * @throws Exception
	 */
	protected Login getLoginByToken(CCTConnection conn, String token) throws Exception {
		return dao.searchLoginByToken(conn, token);
	}
	
	/**
	 * Get LoginUser by userId
	 * @implNote
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	protected LoginUser getLoginUserByUserId(CCTConnection conn, String userId) throws Exception{
		return dao.searchLoginUserByUserId(conn, userId);
	}
	
	/**
	 * Logout
	 * @implNote
	 * @param conn
	 * @param userId
	 * @throws Exception
	 */
	protected void logout(CCTConnection conn, String userId) throws Exception {
		String logoutDatetime = "";
		dao.updateLogout(conn, userId, logoutDatetime);
	}
	
	protected void updateLogoutTime(CCTConnection conn, LoginUser loginUser, String currentDateTime) throws Exception {
		dao.updateLogoutTime(conn, loginUser.getUserId(), currentDateTime);
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
	
	protected String searchConfigAuthenPB(CCTConnection conn) throws Exception {
		return dao.searchConfigAuthenPB(conn);
	}
	
	/**
	 * call AuthPB-WS
	 * @implNote
	 * @param methodAPI
	 * @param loginJson
	 * @param headerMap
	 * @return
	 * @throws CustomException
	 */
	protected CommonResponse callAuthPBWS(String methodAPI, String loginJson, Map<String, String> headerMap) throws CustomException {
		CommonResponse resp = null;
		try {
			resp = (CommonResponse) RestClientUtil
					.callServicePost(ParameterConfig.getLoginWebService().getUrl()
							, methodAPI
							, ParameterConfig.getLoginWebService().isSecure()
							, ParameterConfig.getLoginWebService().getTimeout()
							, loginJson
							, CommonResponse.class
							, headerMap
							, ParameterConfig.getLoginWebService().getRound());
		} catch (Exception e) {
			throw new CustomException("30015", e); //แสดง Alert 30015 [Service Timeout.] 
		}
		
		return resp;
	}
	
	/**
	 * Check Response : LoginUser with PB.
	 * @implNote
	 * @Create 2022-01-20 [-] sitthinarong.j
	 * @param respLogin
	 * @param authPB
	 * @return
	 * @throws CustomException
	 */
	protected PibicsAuthenModel checkReponseLoginUser(CommonResponse respLogin, String authPB) throws CustomException, TokenExpireException {
		getLogger().debug("--checkReponseLoginUser--");
		PibicsAuthenModel pbAuth = null;
		
		// Check : Null
		if(InputValidateUtil.isNull(respLogin)) {
			getLogger().error("Response = Null!!!");
			throw new CustomException(INVALID_DATA);
			
		}
		
		// Check : MessageCode
		getLogger().debug("[{}]", respLogin.getMessageDesc());
		if(!MessageCode.SUCCESS.getValue().equals(respLogin.getMessageCode())) {
			if(respLogin.getError() != null) {
				
				String invalidUser = "30039";// แสดง Alert 30039 [รหัสผู้ใช้หรือรหัสผ่านไม่ถูกต้อง]
				if(invalidUser.equals(respLogin.getError().getErrorCode())) {
					throw new CustomException(invalidUser);
				}			
				
				getLogger().error("--> Error : [{}]", respLogin.getError().getErrorDesc());
				
				/*----------------case Token Expire----------------- */
				if("10008".equals(respLogin.getError().getErrorCode())) {
					throw new TokenExpireException();
				}	
			}
			
			throw new CustomException("00001");//แสดง Alert 00001 [ทำรายการไม่สำเร็จ] 
		}
		
		// Check : There isn't The Data.
		if(InputValidateUtil.isNull(respLogin.getData())) {
			getLogger().error("Data = Null!!!");
			throw new CustomException(INVALID_DATA);
			
		}
		
		// Data-Resp to Object
		pbAuth = (PibicsAuthenModel) ObjectUtil.objectToBean(respLogin.getData(), PibicsAuthenModel.class);
		
		// Check : authPB = 'Y' and Count-User = 0?
		if("Y".equals(authPB) && pbAuth.getCheckedAuthen() == 0) {
			getLogger().error("CheckedAuthen = 0!!!");
			throw new CustomException("30020");//แสดง Alert 30020 [Unauthorization.] 
		}
		
		return pbAuth;
	}
	
	/**
	 * Update UserOfficer
	 * @implNote
	 * @Create 2022-01-19 [-] sitthinarong.j
	 * @param conn
	 * @param pbAuth
	 * @throws Exception
	 */
	protected void updateUserOfficer(CCTConnection conn, PibicsAuthenModel pbAuth) throws Exception {
		getLogger().debug("--updateUserOfficer--");
		
		// Check_SEC_USER_OFFICER_SQL #1 -> CNT_USER
		int countUser = dao.countSecUserOfficer(conn, pbAuth.getUserId());
		getLogger().debug("countUser [{}]", countUser);
//		if(countUser == 0) {
//			//Insert_SEC_USER_OFFICER_SQL
//			dao.insertSecUserOfficer(conn, pbAuth);
//		} else {
//			//Check_SEC_USER_OFFICER_SQL #2 -> CNT_DUP
//			int countDup = dao.countDupUserOfficer(conn, pbAuth);
//			getLogger().debug("countDuplicate [{}]", countDup);
//			if(countDup == 0) {
//				//Update_SEC_USER_OFFICER_SQL
//				dao.updateSecUserOfficer(conn, pbAuth);
//			}
//		}
	}
	
	protected String searchPopupChangeLog(CCTConnection conn, String userName) throws Exception {
		return dao.searchPopupChangeLog(conn, userName);
	}
	
	protected void updatePopupChangeLog(CCTConnection conn, String userName) throws Exception {
		dao.updatePopupChangeLog(conn, userName);
	}
	
	protected PibicsAuthenModel searchUserDB(CCTConnection conn, String userName, String pass) throws Exception {
		return dao.searchUserDB(conn, userName, pass);
	}
	
	protected boolean searchCountIsAdminGroup(CCTConnection conn, String loginId) throws Exception {
		return dao.searchCountIsAdminGroup(conn, loginId);
	}
	
	protected void updateChangePassword(CCTConnection conn, String userId, String newPass) throws Exception {
		dao.updateChangePassword(conn, userId, newPass);
	}
}
