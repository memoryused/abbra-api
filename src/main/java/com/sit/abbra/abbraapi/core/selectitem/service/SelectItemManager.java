package com.sit.abbra.abbraapi.core.selectitem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.selectitem.enums.GlobalType;
import com.sit.abbra.abbraapi.core.selectitem.enums.SelectItemMapper;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.common.CommonSelectItem;
import com.sit.core.common.service.CommonManager;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;

public class SelectItemManager extends CommonManager {

	private static Map<Locale, Map<String, List<CommonSelectItem>>> mapGlobalData = new HashMap<>();

	private SelectItemService service = null;

	public SelectItemManager(Logger logger) {
		super(logger);
		this.service = new SelectItemService(logger);
	}

	public void init(CCTConnection conn) throws Exception {
		initGlobalDataSelectItem(conn);
	}

	private void initGlobalDataSelectItem(CCTConnection conn) throws Exception {
		Locale locale = ParameterConfig.getApplication().getApplicationLocale();
		getLogger().info(locale);
		Map<String, List<CommonSelectItem>> mapSelectItem = service.searchGlobalDataSelectItem(conn, locale);
		if (mapSelectItem.size() == 0) {
			mapSelectItem = mapGlobalData.get(ParameterConfig.getApplication().getApplicationLocale());
		}
		mapGlobalData.put(locale, mapSelectItem);
	}
	
	
	public static Map<Locale, Map<String, List<CommonSelectItem>>> getMapGlobalData() {
		return mapGlobalData;
	}

	public List<CommonSelectItem> getGlobalSelectitem(GlobalType type, Locale locale) {
		
		List<CommonSelectItem> lstResult = new ArrayList<>();
		
		try {
			String globalType = type.getGlobalType();
			getLogger().debug("[GlobalType] : {}", globalType);
			
			Map<String, List<CommonSelectItem>> mapResult = getMapGlobalData().get(locale);
			if (mapResult != null) {
				lstResult = mapResult.get(globalType);

			} else {
				getLogger().debug("data not found in map.");
			}

		} catch (Exception e) {
			getLogger().catching(e);
		}
		return lstResult;
	}

	public List<CommonSelectItem> searchCommonSelectitem(SelectItemMapper mapper, String term, Integer limit) throws Exception {
		
		List<CommonSelectItem> listSelectItem = new ArrayList<>();
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchCommonSelectitem(conn, mapper, term, limit);
		} catch (Exception e) {
			getLogger().catching(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return listSelectItem;
	}
	
	
	public List<CommonSelectItem> searchUserSelectItem(String term , LoginUser loginUser) throws Exception {
		
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchUserSelectItem(conn, term ,loginUser);
			
		} catch (Exception e) {
			getLogger().catching(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		
		return listSelectItem;
	}
	
	public boolean searchUserSelectItemValidate(CCTConnection conn ,String userId) throws Exception {		
		return  service.searchUserSelectItemValidate(conn, userId);
	}
	public boolean searchUserAllSelectItemValidate(CCTConnection conn ,String userId) throws Exception {		
		return  service.searchUserAllSelectItemValidate(conn, userId);
	}
	
	/**
	 * SQL :  Load [Function] filter_SQL
	 * 
	 * @param term
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchFunctionFilterSelectItem(String term, LoginUser loginUser) throws Exception {
		
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchFunctionFilterSelectItem(conn, term ,loginUser);
			
		} catch (Exception e) {
			getLogger().catching(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		
		return listSelectItem;
	}
	
	/**
	 * Load [Function] filter_SQL
	 * For Validate
	 * @param conn
	 * @param parentId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean searchFunctionFilterSelectItemValidate(CCTConnection conn , String parentId, String id) throws Exception {		
		return  service.searchFunctionFilterSelectItemValidate(conn, parentId, id);
	}
	
	/**
	 * SQL :  Load [Menu]_SQL
	 * 
	 * @param term
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchMenuSelectItem(String term, LoginUser loginUser) throws Exception {
		
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchMenuSelectItem(conn, term ,loginUser);
			
		} catch (Exception e) {
			getLogger().catching(e);
		} finally {
			CCTConnectionUtil.close(conn);
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
	public boolean searchMenuSelectItemValidate(CCTConnection conn ,String id) throws Exception {		
		return  service.searchMenuSelectItemValidate(conn, id);
	}
	
	/**
	 * ComboBox[Recipient]_SQL
	 * 
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchRecipientSelectItem(LoginUser loginUser) throws Exception {
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchRecipientSelectItem(conn, loginUser);
			
		} catch (Exception e) {
			getLogger().catching(e);
			
		} finally {
			CCTConnectionUtil.close(conn);
		}
		
		return listSelectItem;
	}
	
	/**
	 * ComboBox[Recipient]_SQL		Validate
	 * 
	 * @param conn
	 * @param pushConfigId
	 * @return
	 * @throws Exception
	 */
	public boolean searchRecipientSelectItemValidate(CCTConnection conn, String pushConfigId) throws Exception {		
		return  service.searchRecipientSelectItemValidate(conn, pushConfigId);
	}
	
	/**
	 * SQL :  ComboBox[Push No.(Timing)_status]_SQL
	 * 
	 * @param term
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchPushNoSelectItem(String pushConfigId , LoginUser loginUser) throws Exception {
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchPushNoSelectItem(conn, pushConfigId ,loginUser);
			
		} catch (Exception e) {
			getLogger().catching(e);
			
		} finally {
			CCTConnectionUtil.close(conn);
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
	public boolean searchPushNoSelectItemValidate(CCTConnection conn, String pushKey) throws Exception {		
		return  service.searchPushNoSelectItemValidate(conn, pushKey);
	}
	
	
	/**
	 * searchUserAllSelectItem : Load [User ID] Search_SQL
	 * @param conn
	 * @param term
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchUserAllSelectItem(String term , LoginUser loginUser) throws Exception {
		
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchUserAllSelectItem(conn, term ,loginUser);
			
		} catch (Exception e) {
			getLogger().catching(e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
		
		return listSelectItem;
	}
	
	/**
	 * SQL : COMBO_NATIONALITY_SQL
	 * @param nationality
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchNationalitySelectItem(String term)
	{
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchNationalitySelectItem(conn, term);
		} 
		catch (Exception e) {
			getLogger().catching(e);
		} 
		finally {
			CCTConnectionUtil.close(conn);
		}		
		return listSelectItem;
	}
	
	public boolean searchNationalitySelectItemValidate(CCTConnection conn ,String nationality) throws Exception {		
		return  service.searchNationalitySelectItemValidate(conn, nationality);
	}
	
	
	/**
	 * SQL : COMBO_PRIORLITY_SQL
	 * @return
	 */
	public List<CommonSelectItem> searchPrioritySelectItem()
	{
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchPrioritySelectItem(conn);
		} 
		catch (Exception e) {
			getLogger().catching(e);
		} 
		finally {
			CCTConnectionUtil.close(conn);
		}		
		return listSelectItem;
	}
	
	public boolean searchPrioritySelectItemValidate(CCTConnection conn ,String code) throws Exception {		
		return  service.searchPrioritySelectItemValidate(conn, code);
	}
	
	
	
	
	/**
	 * SQL : COMBO_PROVINCE_SQL
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchProvinceSelectItem() throws Exception 
	{
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchProvinceSelectItem(conn);
		} 
		catch (Exception e) {
			getLogger().catching(e);	
		} 
		finally {
			CCTConnectionUtil.close(conn);
		}
		return listSelectItem;
	}
	
	public boolean searchProvinceSelectItemValidate(CCTConnection conn, String provinceNo) throws Exception {		
		return  service.searchProvinceSelectItemValidate(conn, provinceNo);
	}
	
	
	
	/**
	 * SQL : COMBO_AMPUR_SQL
	 * @param provinceNo
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchAmpurSelectItem(String provinceNo) throws Exception 
	{
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchAmpurSelectItem(conn, provinceNo);
		} catch (Exception e) {
			getLogger().catching(e);	
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return listSelectItem;
	}
	
	public boolean searchAmpurSelectItemValidate(CCTConnection conn, String provinceNo, String ampurNo) throws Exception {		
		return  service.searchAmpurSelectItemValidate(conn, provinceNo, ampurNo);
	}
	
	
	/**
	 * SQL : COMBO_DEPARTMENT_SQL
	 * @param provinceNo
	 * @param ampurNo
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchDepartmentSelectItem(String provinceNo, String ampurNo) throws Exception 
	{
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchDepartmentSelectItem(conn, provinceNo, ampurNo);
		} catch (Exception e) {
			getLogger().catching(e);	
		} finally {
			CCTConnectionUtil.close(conn);
		}
		return listSelectItem;
	}
	
	public boolean searchDepartmentSelectItemValidate(CCTConnection conn, String provinceNo, String ampurNo, String depNo) throws Exception {		
		return  service.searchDepartmentSelectItemValidate(conn, provinceNo, ampurNo, depNo);
	}
	
	/**
	 * for validate search Approve
	 * @param conn
	 * @param provinceNo
	 * @param depNo
	 * @return
	 * @throws Exception
	 */
	public boolean searchDepartmentSelectItemValidate(CCTConnection conn, String provinceNo, String depNo) throws Exception {		
		return  service.searchDepartmentSelectItemValidate(conn, provinceNo, depNo);
	}
	
	
	/**
	 * SQL : COMBO_PREAPV_STATUS_SQL
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchPreApproveStatusSelectItem() throws Exception 
	{
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchPreApproveStatusSelectItem(conn);
		} 
		catch (Exception e) {
			getLogger().catching(e);	
		} 
		finally {
			CCTConnectionUtil.close(conn);
		}
		return listSelectItem;
	}
	
	public boolean searchPreApproveStatusSelectItemValidate(CCTConnection conn, String code) throws Exception {		
		return  service.searchPreApproveStatusSelectItemValidate(conn, code);
	}
	
	/**
	 * SQL : COMBO_ACTIVE_SQL
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchActiveSelectItem() throws Exception 
	{
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchActiveSelectItem(conn);
		} 
		catch (Exception e) {
			getLogger().catching(e);	
		} 
		finally {
			CCTConnectionUtil.close(conn);
		}
		return listSelectItem;
	}
	
	public boolean searchActiveSelectItemValidate(CCTConnection conn, String code) throws Exception {		
		return  service.searchActiveSelectItemValidate(conn, code);
	}
	
	/**
	 * SQL : COMBO_APV_STATUS_SQL
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchApproveStatusSelectItem() throws Exception 
	{
		CCTConnection conn = null;
		List<CommonSelectItem> listSelectItem = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			listSelectItem = service.searchApproveStatusSelectItem(conn);
		} 
		catch (Exception e) {
			getLogger().catching(e);	
		} 
		finally {
			CCTConnectionUtil.close(conn);
		}
		return listSelectItem;
	}
	
	public boolean searchApproveStatusSelectItemValidate(CCTConnection conn, String code) throws Exception {		
		return  service.searchApproveStatusSelectItemValidate(conn, code);
	}
	
	public List<CommonSelectItem> searchReasonExtSelectItem(CCTConnection conn) throws Exception {
		return service.searchReasonExtSelectItem(conn);
	}
	
}
