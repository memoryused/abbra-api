package com.sit.abbra.abbraapi.core.security.authorize.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.security.login.domain.OperatorButton;
import com.sit.abbra.abbraapi.core.security.login.service.LoginManager;
import com.sit.abbra.abbraapi.enums.PFOperator;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.core.common.service.CommonManager;
import com.sit.domain.FunctionType;
import com.sit.domain.GlobalVariable;
import com.sit.domain.Permission;
import com.sit.exception.AuthorizationException;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;

public class AuthorizeManager extends CommonManager {

	private static final String NOT_HAVE_OPERATOR_IDS_FORMAT = "{} Not have operatorIds in [{}]!!!";
	private static final String METHOD_OPER_FORMAT = "MenuId: {}";
	private static final String AUTHORIZATION_EXCEPTION_FORMAT = "{}, Token: {}";
	
	public AuthorizeManager(Logger logger) {
		super(logger);
	}

	/**
	 * return true เมื่อไม่มีสิทธิ์
	 * @param operatorIds
	 * @param methodOper
	 * @return
	 */
	private boolean notHasPermission(String operatorIds, String currentOper) {
		return (operatorIds.indexOf(GlobalVariable.DELIMITER_OPERATOR + currentOper + GlobalVariable.DELIMITER_OPERATOR) < 0);
	}
	
	private boolean hasPermission(String operatorIds, String currentOper) {
		return (operatorIds.indexOf(GlobalVariable.DELIMITER_OPERATOR + currentOper + GlobalVariable.DELIMITER_OPERATOR) >= 0);
	}
	
	/**
	 * ตรวจสอบสิทธิ์การใช้งาน Menu
	 * @param loginUser
	 * @param menuId
	 * @throws Exception เมื่อตรวจสอบไม่ผ่าน
	 */
	public Permission checkAuthorize(LoginUser loginUser, String currentOper, Set<PFOperator> pfOperators) throws Exception {
		
		Permission result = new Permission();
		
		CCTConnection conn = null;
		try {
			getLogger().debug(METHOD_OPER_FORMAT, currentOper);
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			if (loginUser.getUserId() != null) {
				
				LoginManager loginManager = new LoginManager(getLogger());
				String operatorIds = ""; //loginManager.searchAuthorizeAndPermission(conn, loginUser);
				if (notHasPermission(operatorIds, currentOper)) {
					getLogger().error(NOT_HAVE_OPERATOR_IDS_FORMAT, currentOper, operatorIds);
					// ผลการตรวจสอบสิทธิ์ไม่ผ่าน
					throw new AuthorizationException();
				} else {
					result = initPermission(operatorIds, pfOperators);
				}
			} else {
				getLogger().error("UserId is null!!!");
				// ไม่มี user ให้ตรวจสอบ
				throw new AuthorizationException();
			}
		} catch (Exception e) {
			if (!(e instanceof AuthorizationException)) {
				getLogger().catching(e);
			}
			throw new AuthorizationException();
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return result;
	}
	
	/**
	 * ใช้สำหรับตรวจสอบแบบรับผลลัพธ์ true false กลับไป
	 * <br>true เมื่อตรวจสอบผ่าน
	 * <br>false เมื่อตรวจสอบไม่ผ่าน
	 * @param user
	 * @param methodOper
	 * @return
	 * @throws Exception
	 */
	public boolean checkAuthorizeQuietly(LoginUser loginUser, String currentOper, Set<PFOperator> pfOperators) throws Exception {
		boolean result = false;
		try {
			checkAuthorize(loginUser, currentOper, pfOperators);
			
			result = true;
		} catch (AuthorizationException e) {
			getLogger().error(AUTHORIZATION_EXCEPTION_FORMAT, e, loginUser.getToken());
		} catch (Exception e) {
			getLogger().catching(e);
		}
		return result;
	}
	
	public Permission initPermission(String operatorIds, Set<PFOperator> pfOperators) {
		if (pfOperators == null) {
			return null;
		} else {
			return getPermission(operatorIds, pfOperators);
		}
	}
	
	private Permission getPermission(String operatorIds, Set<PFOperator> pfOperators) {
		Permission permission = new Permission();
		if (!operatorIds.isEmpty()) {

			Map<String, String> mapPFCode = pfOperators.stream()
					.collect(Collectors.toMap(PFOperator::getFunction, PFOperator::getOperator));

			permission.setSearch(checkPermission(mapPFCode, operatorIds, FunctionType.SEARCH));
			permission.setAdd(checkPermission(mapPFCode, operatorIds, FunctionType.ADD));
			permission.setEdit(checkPermission(mapPFCode, operatorIds, FunctionType.EDIT));
			permission.setView(checkPermission(mapPFCode, operatorIds, FunctionType.VIEW));
			permission.setDelete(checkPermission(mapPFCode, operatorIds, FunctionType.DELETE));
			permission.setExport(checkPermission(mapPFCode, operatorIds, FunctionType.EXPORT));
			permission.setViewPNRGOV(checkPermission(mapPFCode, operatorIds, FunctionType.VIEW_PNRGOV_MESSAGE));
			permission.setViewRecLocator(checkPermission(mapPFCode, operatorIds, FunctionType.VIEW_RECORD_LOCATOR));
			permission.setRegenerate(checkPermission(mapPFCode, operatorIds, FunctionType.REGENERATE_PNRGOV));
			permission.setRetransmit(checkPermission(mapPFCode, operatorIds, FunctionType.RETRANSMIT_PNRGOV));
		}
		return permission;
	}
	
	private Boolean checkPermission(Map<String, String> mapPFCode, String operatorIds, String functionType) {
		Boolean hasPermission = null;
		if (mapPFCode.get(functionType) != null && hasPermission(operatorIds, mapPFCode.get(functionType))) {
			hasPermission = true;
		}
		return hasPermission;
	}
	
	public void checkAdminGroup(LoginUser loginUser) throws Exception {
		
		CCTConnection conn = null;
		try {
			getLogger().debug("checkAdminGroup");
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			if (loginUser.getUserId() != null) {
				
				LoginManager loginManager = new LoginManager(getLogger());
				boolean isAdminGroup = loginManager.searchCountIsAdminGroup(conn, loginUser.getUserId());
				if (!isAdminGroup) {
					// ผลการตรวจสอบสิทธิ์ไม่ผ่าน
					throw new AuthorizationException();
				}
			} else {
				getLogger().error("UserId is null!!!");
				// ไม่มี user ให้ตรวจสอบ
				throw new AuthorizationException();
			}
		} catch (Exception e) {
			if (!(e instanceof AuthorizationException)) {
				getLogger().catching(e);
			}
			throw new AuthorizationException();
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	/**
	 * ตรวจสอบสิทธิ์การใช้งาน Menu
	 * @param loginUser
	 * @param menuId
	 * @throws Exception เมื่อตรวจสอบไม่ผ่าน
	 */
	public HashMap<String, OperatorButton> checkAuthorize(LoginUser loginUser) throws Exception {
		
		HashMap<String, OperatorButton> mapOper = new LinkedHashMap<>();
		
		CCTConnection conn = null;
		try {
			getLogger().debug(METHOD_OPER_FORMAT);
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			
			if (loginUser.getUserId() != null) {
				
				LoginManager loginManager = new LoginManager(getLogger());
				mapOper = loginManager.searchOperBtnByUserId(conn, loginUser.getUserId());
			} else {
				getLogger().error("UserId is null!!!");
				// ไม่มี user ให้ตรวจสอบ
				throw new AuthorizationException();
			}
		} catch (Exception e) {
			if (!(e instanceof AuthorizationException)) {
				getLogger().catching(e);
			}
			throw new AuthorizationException();
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return mapOper;
	}
}
