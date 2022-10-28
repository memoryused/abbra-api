package com.sit.abbra.abbraapi.core.selectitem.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.selectitem.enums.SelectItemMapper;
import com.sit.abbra.abbraapi.enums.ColumnName;
import com.sit.abbra.abbraapi.util.security.EExtensionApiSecurityUtil;
import com.sit.common.CommonSelectItem;
import com.sit.core.common.domain.CommonSQLPath;
import com.sit.core.common.service.CommonDAO;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.sql.SQLParameterizedDebugUtil;
import util.sql.SQLParameterizedUtil;
import util.string.StringUtil;

public class SelectItemDAO extends CommonDAO {

	public SelectItemDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	protected Map<String, List<CommonSelectItem>> searchGlobalDataSelectItem(CCTConnection conn, Locale locale)
			throws Exception {
		Map<String, List<CommonSelectItem>> mapGlobalData = new HashMap<>();

		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				,"searchGlobalDataSelectItem"
				);

		PreparedStatement stmt = null;
	    ResultSet rst = null;

	    try {
	        stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql);
	        rst = stmt.executeQuery();

	        while (rst.next()) {
	        	String globalType = StringUtil.nullToString(rst.getString("GLOBAL_TYPE_CODE"));
				if (mapGlobalData.get(globalType) == null) {
					mapGlobalData.put(globalType, new ArrayList<>());
				}

				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString("GLOBAL_DATA_CODE")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("GLOBAL_DATA_VALUE_" + locale.getLanguage().toUpperCase())));
				mapGlobalData.get(globalType).add(selectItem);
			}
	    } finally {
	        CCTConnectionUtil.closeAll(rst, stmt);
	    }
		return mapGlobalData;
	}

	protected List<CommonSelectItem> searchCommonSelectitem(CCTConnection conn, SelectItemMapper mapper, String term,
			Integer limit ) throws Exception {
		getLogger().debug("searchCommonSelectitem [{}]", mapper.getSqlName());

		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.stringToNull(term);
		params[paramIndex] = limit;

		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, mapper.getSqlName()
				, params
				);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
	    ResultSet rst = null;

	    try {
	        stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
	        rst = stmt.executeQuery();

	        while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString("ID")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("VALUE")));
				listSelectItem.add(selectItem);
			}
	    } finally {
	        CCTConnectionUtil.closeAll(rst, stmt);
	    }

		return listSelectItem;
	}
	
	
	protected List<CommonSelectItem> searchUserSelectItem(CCTConnection conn, String term,LoginUser loginUser) throws Exception {
		
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.stringToNull(term);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchUserSelectItem"
				, params
				);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(EExtensionApiSecurityUtil.encryptId(loginUser.getSalt(), loginUser.getSecret(), StringUtil.nullToString(rst.getString("userID"))));
				selectItem.setValue(StringUtil.nullToString(rst.getString("tgUserID")));
				listSelectItem.add(selectItem);
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return listSelectItem;
	}
	
	/**
	 * searchUserAllSelectItem
	 * @param conn
	 * @param term
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchUserAllSelectItem(CCTConnection conn, String term,LoginUser loginUser) throws Exception {
		
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.stringToNull(term);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchUserALLSelectItem"
				, params
				);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(EExtensionApiSecurityUtil.encryptId(loginUser.getSalt(), loginUser.getSecret(), StringUtil.nullToString(rst.getString("userID"))));
				selectItem.setValue(StringUtil.nullToString(rst.getString("tgUserID")));
				listSelectItem.add(selectItem);
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return listSelectItem;
	}
	
	/**
	 * searchUserSelectItemValidate
	 * @param conn
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	protected boolean searchUserSelectItemValidate(CCTConnection conn, String userId) throws Exception {
		
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.nullToString(userId);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "validateUserSelectItem"
				, params
				);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return isValid;
	}
	
	/**
	 * searchUserAllSelectItemValidate
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	protected boolean searchUserAllSelectItemValidate(CCTConnection conn, String userId) throws Exception {
		
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.nullToString(userId);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "validateUserALLSelectItem"
				, params
				);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return isValid;
	}
	
	/**
	 * SQL :  Load [Function] filter_SQL
	 * 
	 * @param conn
	 * @param term
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchFunctionFilterSelectItem(CCTConnection conn, String term,LoginUser loginUser) throws Exception {
		
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.stringToNull(EExtensionApiSecurityUtil.decryptId(loginUser.getSalt(), loginUser.getSecret(), term));
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchFunctionFilterSelectItem"
				, params
				);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(EExtensionApiSecurityUtil.encryptId(loginUser.getSalt(), loginUser.getSecret(), StringUtil.nullToString(rst.getString("functionID"))));
				selectItem.setValue(StringUtil.nullToString(rst.getString("operatorNameUI")));
				listSelectItem.add(selectItem);
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return listSelectItem;
	}
	
	/**
	 * SQL :  Load [Function] filter_SQL
	 * For Validate
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected boolean searchFunctionFilterSelectItemValidate(CCTConnection conn, String parentId, String id) throws Exception {
		
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.stringToLong(id);
		params[paramIndex] = StringUtil.stringToLong(parentId);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "validateFunctionFilterSelectItem"
				, params
				);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return isValid;
	}
	
	/**
	 * SQL :  Load [Menu]_SQL
	 * 
	 * @param conn
	 * @param term
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchMenuSelectItem(CCTConnection conn, String term,LoginUser loginUser) throws Exception {
		
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.stringToNull(term);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchMenuSelectItem"
				, params
				);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(EExtensionApiSecurityUtil.encryptId(loginUser.getSalt(), loginUser.getSecret(), StringUtil.nullToString(rst.getString("menuID"))));
				// 20220615: TGPNR2022-58: ปรับแก้ให้เอา operatorNameUI มาแสดงแทน operatorNameSrch
				selectItem.setValue(StringUtil.nullToString(rst.getString("operatorNameUI")));
				listSelectItem.add(selectItem);
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return listSelectItem;
	}
	
	/**
	 * SQL :  Load [Menu]_SQL
	 * For Validate
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected boolean searchMenuSelectItemValidate(CCTConnection conn, String id) throws Exception {
		
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.stringToLong(id);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "validateMenuSelectItem"
				, params
				);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return isValid;
	}
	
	/**
	 * ComboBox[Recipient]_SQL
	 * 
	 * @param conn
	 * @param term
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchRecipientSelectItem(CCTConnection conn, LoginUser loginUser) throws Exception {
		// แก้ไข unused method parameter
		getLogger().debug(loginUser);
		
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchRecipientSelectItem");
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, null));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql);
			rst = stmt.executeQuery();
			
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				
				selectItem.setKey(StringUtil.nullToString(rst.getString("PUSH_CONFIG_ID")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("RECIPIENT_ID")));
				
				listSelectItem.add(selectItem);
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return listSelectItem;

	}
	
	/**
	 * ComboBox[Recipient]_SQL	Validate
	 * 
	 * @param conn
	 * @param term
	 * @param pushConfigId
	 * @return
	 * @throws Exception
	 */
	protected boolean searchRecipientSelectItemValidate(CCTConnection conn, String pushConfigId) throws Exception {
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		
		params[paramIndex] = StringUtil.stringToNull(pushConfigId);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchRecipientSelectItemValidate"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return isValid;
	}
	
	/**
	 * SQL :  ComboBox[Push No.(Timing)_status]_SQL
	 * 
	 * @param conn
	 * @param pushConfigId
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchPushNoSelectItem(CCTConnection conn, String pushConfigId, LoginUser loginUser) throws Exception {
		// แก้ไข unused method parameter
		getLogger().debug(loginUser);
		
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		int paramIndex = 0;
		Object[] params = new Object[1];
		
		params[paramIndex] = StringUtil.stringToNull(pushConfigId);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchPushNoSelectItem"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
			rst = stmt.executeQuery();
			
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				
				selectItem.setKey(StringUtil.nullToString(rst.getString("PUSH_KEY")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("PUSH_KEY_TXT")));
				
				listSelectItem.add(selectItem);
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return listSelectItem;
	}
	
	/**
	 * SQL :  ComboBox[Push No.(Timing)_status]_SQL		Validate
	 * 
	 * @param conn
	 * @param pushKey
	 * @return
	 * @throws Exception
	 */
	protected boolean searchPushNoSelectItemValidate(CCTConnection conn, String pushKey) throws Exception {
		boolean isValid = false;
		int paramIndex = 0;
		Object[] params = new Object[1];
		
		params[paramIndex] = StringUtil.stringToNull(pushKey);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchPushNoSelectItemValidate"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return isValid;
	}
	
	
	/**
	 * SQL : COMBO_NATIONALITY_SQL
	 * @param conn
	 * @param term
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchNationalitySelectItem(CCTConnection conn, String term) throws Exception 
	{	
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.stringToNull(term);
		params[paramIndex] = StringUtil.stringToNull(term);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchNationalitySelectItem"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			while (rst.next()) 
			{
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString("NATIONALITY")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("NAT_NAME")));
				listSelectItem.add(selectItem);
			}
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	/**
	 * COMBO_NATIONALITY validate
	 * @param conn
	 * @param nationality
	 * @return
	 * @throws Exception
	 */
	protected boolean searchNationalitySelectItemValidate(CCTConnection conn, String nationality) throws Exception 
	{
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.nullToString(nationality);
		params[paramIndex] = StringUtil.nullToString(nationality);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchNationalitySelectItemValidate"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return isValid;
	}
	
	
	/**
	 * SQL : COMBO_PRIORLITY_SQL
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchPrioritySelectItem(CCTConnection conn) throws Exception 
	{
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchPrioritySelectItem");
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, null));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql);
			rst = stmt.executeQuery();
			
			while (rst.next()) 
			{
				CommonSelectItem selectItem = new CommonSelectItem();				
				selectItem.setKey(StringUtil.nullToString(rst.getString(ColumnName.GLOBAL_DATA_CODE.toString())));
				selectItem.setValue(StringUtil.nullToString(rst.getString(ColumnName.GLOBAL_DATA_VALUE_TH.toString())));
				listSelectItem.add(selectItem);
			}	
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	/**
	 * COMBO_PRIORLITY validate
	 * @param conn
	 * @param code
	 * @return
	 * @throws Exception
	 */
	protected boolean searchPrioritySelectItemValidate(CCTConnection conn, String code) throws Exception 
	{	
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.nullToString(code);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchPrioritySelectItemValidate"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return isValid;
	}
	
	

	/**
	 * SQL : COMBO_PROVINCE_SQL
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchProvinceSelectItem(CCTConnection conn) throws Exception 
	{
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchProvinceSelectItem");
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, null));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql);
			rst = stmt.executeQuery();
			
			while (rst.next()) 
			{
				CommonSelectItem selectItem = new CommonSelectItem();				
				selectItem.setKey(StringUtil.nullToString(rst.getString("PV_SEQNO")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("PV_DESC")));
				listSelectItem.add(selectItem);
			}	
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	/**
	 * COMBO_PROVINCE validate
	 * @param conn
	 * @param provinceNo
	 * @return
	 * @throws Exception
	 */
	protected boolean searchProvinceSelectItemValidate(CCTConnection conn, String provinceNo) throws Exception 
	{
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.nullToString(provinceNo);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchProvinceSelectItemValidate"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return isValid;
	}
	
	
	/**
	 * SQL : COMBO_AMPUR_SQL
	 * @param conn
	 * @param provinceNo
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchAmpurSelectItem(CCTConnection conn, String provinceNo) throws Exception 
	{
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		int paramIndex = 0;
		Object[] params = new Object[1];
		
		params[paramIndex] = StringUtil.stringToNull(provinceNo);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchAmpurSelectItem"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
			rst = stmt.executeQuery();
			
			while (rst.next()) 
			{
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString("AMP_SEQNO")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("AMP_DESC")));
				listSelectItem.add(selectItem);
			}	
		}
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	/**
	 * COMBO_AMPUR validate
	 * @param conn
	 * @param provinceNo
	 * @param ampurNo
	 * @return
	 * @throws Exception
	 */
	protected boolean searchAmpurSelectItemValidate(CCTConnection conn, String provinceNo, String ampurNo) throws Exception 
	{
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[2];		
		params[paramIndex++] = StringUtil.nullToString(provinceNo);
		params[paramIndex] = StringUtil.nullToString(ampurNo);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchAmpurSelectItemValidate"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return isValid;
	}
	
	
	
	/**
	 * SQL : COMBO_DEPARTMENT_SQL
	 * @param conn
	 * @param provinceNo
	 * @param ampurNo
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchDepartmentSelectItem(CCTConnection conn, String provinceNo, String ampurNo) throws Exception 
	{
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		int paramIndex = 0;
		Object[] params = new Object[2];
		
		params[paramIndex++] = StringUtil.stringToNull(provinceNo);
		params[paramIndex] = StringUtil.stringToNull(ampurNo);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchDepartmentSelectItem"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql, params);
			rst = stmt.executeQuery();
			
			while (rst.next()) 
			{
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString("DEPT_SEQNO")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("DEPTTNM")));
				listSelectItem.add(selectItem);
			}	
		}
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	/**
	 * COMBO_DEPARTMENT validate
	 * @param conn
	 * @param provinceNo
	 * @param ampurNo
	 * @param depNo
	 * @return
	 * @throws Exception
	 */
	protected boolean searchDepartmentSelectItemValidate(CCTConnection conn, String provinceNo, String ampurNo, String depNo) throws Exception 
	{
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[3];		
		params[paramIndex++] = StringUtil.nullToString(provinceNo);
		params[paramIndex++] = StringUtil.stringToNull(ampurNo);
		params[paramIndex] = StringUtil.nullToString(depNo);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchDepartmentSelectItemValidate"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return isValid;
	}
	
	/**
	 * COMBO_DEPARTMENT validate (search Approve)
	 * @param conn
	 * @param provinceNo
	 * @param depNo
	 * @return
	 * @throws Exception
	 */
	protected boolean searchDepartmentSelectItemValidate(CCTConnection conn, String provinceNo, String depNo) throws Exception 
	{
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[2];		
		params[paramIndex++] = StringUtil.stringToNull(provinceNo);
		params[paramIndex] = StringUtil.nullToString(depNo);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchApproveDepartmentSelectItemValidate"
				, params);
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return isValid;
	}
	
	

	
	/**
	 * SQL : COMBO_PREAPV_STATUS_SQL
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchPreApproveStatusSelectItem(CCTConnection conn) throws Exception 
	{
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchPreApproveStatusSelectItem");
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, null));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql);
			rst = stmt.executeQuery();
			
			while (rst.next()) 
			{
				CommonSelectItem selectItem = new CommonSelectItem();				
				selectItem.setKey(StringUtil.nullToString(rst.getString(ColumnName.GLOBAL_DATA_CODE.toString())));
				selectItem.setValue(StringUtil.nullToString(rst.getString(ColumnName.GLOBAL_DATA_VALUE_TH.toString())));
				listSelectItem.add(selectItem);
			}	
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	/**
	 * COMBO_PREAPV_STATUS validate
	 * @param conn
	 * @param code
	 * @return
	 * @throws Exception
	 */
	protected boolean searchPreApproveStatusSelectItemValidate(CCTConnection conn, String code) throws Exception 
	{	
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.nullToString(code);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchPreApproveStatusSelectItemValidate"
				, params
				);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return isValid;
	}
	
	
	
	/**
	 * SQL : COMBO_ACTIVE_SQL
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchActiveSelectItem(CCTConnection conn) throws Exception 
	{
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchActiveSelectItem");
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, null));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql);
			rst = stmt.executeQuery();
			
			while (rst.next()) 
			{
				CommonSelectItem selectItem = new CommonSelectItem();				
				selectItem.setKey(StringUtil.nullToString(rst.getString(ColumnName.GLOBAL_DATA_CODE.toString())));
				selectItem.setValue(StringUtil.nullToString(rst.getString(ColumnName.GLOBAL_DATA_VALUE_TH.toString())));
				listSelectItem.add(selectItem);
			}	
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	/**
	 * COMBO_ACTIVE validate
	 * @param conn
	 * @param code
	 * @return
	 * @throws Exception
	 */
	protected boolean searchActiveSelectItemValidate(CCTConnection conn, String code) throws Exception 
	{	
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[1];		
		params[paramIndex] = StringUtil.nullToString(code);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchActiveSelectItemValidate"
				, params
				);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return isValid;
	}
	
	
	/**
	 * SQL : COMBO_APV_STATUS_SQL
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchApproveStatusSelectItem(CCTConnection conn) throws Exception 
	{
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchApproveStatusSelectItem");
		
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, null));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), conn.getDbType(), sql);
			rst = stmt.executeQuery();
			
			while (rst.next()) 
			{
				CommonSelectItem selectItem = new CommonSelectItem();				
				selectItem.setKey(StringUtil.nullToString(rst.getString(ColumnName.GLOBAL_DATA_CODE.toString())));
				selectItem.setValue(StringUtil.nullToString(rst.getString(ColumnName.GLOBAL_DATA_VALUE_TH.toString())));
				listSelectItem.add(selectItem);
			}	
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	/**
	 * COMBO_APV_STATUS validate
	 * @param conn
	 * @param code
	 * @return
	 * @throws Exception
	 */
	protected boolean searchApproveStatusSelectItemValidate(CCTConnection conn, String code) throws Exception 
	{	
		boolean isValid = false;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.nullToString(code);
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchApproveStatusSelectItemValidate"
				, params
				);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				isValid = true;
			}
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return isValid;
	}
	
	/**
	 * SQL : COMBO_EXT_REASON_SQL
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchReasonExtSelectItem(CCTConnection conn) throws Exception 
	{	
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		
		String sql = SQLParameterizedUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchReasonExtSelectItem"
				);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, null));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql);
			rst = stmt.executeQuery();
			
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();				
				selectItem.setKey(StringUtil.nullToString(rst.getString(ColumnName.REASON_SEQNO.toString())));
				selectItem.setValue(StringUtil.nullToString(rst.getString(ColumnName.REASONEXTTNM.toString())));
				listSelectItem.add(selectItem);
			}
		} 
		finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
}
