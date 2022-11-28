package com.sit.abbra.abbraapi.core.selectitem.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.selectitem.enums.GlobalType;
import com.sit.abbra.abbraapi.core.selectitem.enums.SelectItemMapper;
import com.sit.common.CommonSelectItem;
import com.sit.core.common.service.CommonService;
import com.sit.domain.GlobalVariable;
import com.sit.domain.GlobalVariable.AnnounceType;

import util.database.connection.CCTConnection;

public class SelectItemService extends CommonService {
	
	private SelectItemDAO dao = null;

	public SelectItemService(Logger logger) {
		super(logger);
		this.dao = new SelectItemDAO(logger, SQLPath.SELECT_ITEM_SQL.getSqlPath());
	}
	
	protected Map<String, List<CommonSelectItem>> searchGlobalDataSelectItem(CCTConnection conn, Locale locale) {
		//FIXME: START Load GlobalData
		Map<String, List<CommonSelectItem>> mapGlobalData = new HashMap<>();
		// Combo AnnounceType
		mapGlobalData.put(GlobalType.ANNOUNCE_TYPE.getGlobalType(), Arrays.asList(AnnounceType.values()).stream().filter(obj -> !obj.getKey().equals("6")).map(AnnounceType::getSelectItem).collect(Collectors.toList()));
		
		// Combo DepartmentAnnounce
		List<String> lstKeyDep = Arrays.asList("15","16","17","18","19","20");
		mapGlobalData.put(GlobalType.DEPARTMENT_ANNOUNCE.getGlobalType(), Arrays.asList(AnnounceType.values()).stream().filter(obj -> lstKeyDep.contains(obj.getKey())).map(AnnounceType::getSelectItem).collect(Collectors.toList()));
		
		// Combo Status
		List<CommonSelectItem> lstStatus = new ArrayList<>();
		CommonSelectItem selctItm = new CommonSelectItem();
		selctItm.setKey(GlobalVariable.ACTIVE);
		selctItm.setValue("Active");
		lstStatus.add(selctItm);
		selctItm = new CommonSelectItem();
		selctItm.setKey(GlobalVariable.INACTIVE);
		selctItm.setValue("Inactive");
		lstStatus.add(selctItm);
		mapGlobalData.put(GlobalType.ACTIVE_STATUS.getGlobalType(), lstStatus );
		
		
		return mapGlobalData;
		// END Load GlobalData
		//return dao.searchGlobalDataSelectItem(conn, locale);
	}
	
	protected List<CommonSelectItem> searchCommonSelectitem(CCTConnection conn, SelectItemMapper mapper
			, String term, Integer limit) throws Exception {
		return dao.searchCommonSelectitem(conn, mapper, term, limit);
	}
	
	/**
	 * 
	 * @param conn
	 * @param term
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchUserSelectItem(CCTConnection conn, String term, LoginUser loginUser) throws Exception {
		return dao.searchUserSelectItem(conn, term, loginUser);
	}
	protected boolean searchUserSelectItemValidate(CCTConnection conn, String userId) throws Exception {
		return dao.searchUserSelectItemValidate(conn, userId);
	}
	protected boolean searchUserAllSelectItemValidate(CCTConnection conn, String userId) throws Exception {
		return dao.searchUserAllSelectItemValidate(conn, userId);
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
	protected List<CommonSelectItem> searchFunctionFilterSelectItem(CCTConnection conn, String term, LoginUser loginUser) throws Exception {
		return dao.searchFunctionFilterSelectItem(conn, term, loginUser);
	}
	
	protected boolean searchFunctionFilterSelectItemValidate(CCTConnection conn, String parentId, String id) throws Exception {
		return dao.searchFunctionFilterSelectItemValidate(conn, parentId, id);
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
	protected List<CommonSelectItem> searchMenuSelectItem(CCTConnection conn, String term, LoginUser loginUser) throws Exception {
		return dao.searchMenuSelectItem(conn, term, loginUser);
	}
	protected boolean searchMenuSelectItemValidate(CCTConnection conn, String id) throws Exception {
		return dao.searchMenuSelectItemValidate(conn, id);
	}
	
	/**
	 * ComboBox[Recipient]_SQL
	 * 
	 * @param conn
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchRecipientSelectItem(CCTConnection conn, LoginUser loginUser) throws Exception {
		return dao.searchRecipientSelectItem(conn, loginUser);
	}
	
	/**
	 * ComboBox[Recipient]_SQL	Validate
	 * 
	 * @param conn
	 * @param pushConfigId
	 * @return
	 * @throws Exception
	 */
	protected boolean searchRecipientSelectItemValidate(CCTConnection conn, String pushConfigId) throws Exception {
		return dao.searchRecipientSelectItemValidate(conn, pushConfigId);
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
		return dao.searchPushNoSelectItem(conn, pushConfigId, loginUser);
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
		return dao.searchPushNoSelectItemValidate(conn, pushKey);
	}
	
	/**
	 * searchUserAllSelectItem : Load [User ID] Search_SQL
	 * @param conn
	 * @param term
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchUserAllSelectItem(CCTConnection conn, String term, LoginUser loginUser) throws Exception {
		return dao.searchUserAllSelectItem(conn, term, loginUser);
	}
	
	/**
	 * SQL : COMBO_NATIONALITY_SQL
	 * @param conn
	 * @param nationality
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchNationalitySelectItem(CCTConnection conn, String term) throws Exception {
		return dao.searchNationalitySelectItem(conn, term);
	}
	
	protected boolean searchNationalitySelectItemValidate(CCTConnection conn, String nationality) throws Exception {
		return dao.searchNationalitySelectItemValidate(conn, nationality);
	}
	
	
	/**
	 * SQL : COMBO_PRIORLITY_SQL
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchPrioritySelectItem(CCTConnection conn) throws Exception {
		return dao.searchPrioritySelectItem(conn);
	}
	
	protected boolean searchPrioritySelectItemValidate(CCTConnection conn, String code) throws Exception {
		return dao.searchPrioritySelectItemValidate(conn, code);
	}
	
	
	/**
	 * SQL : COMBO_PROVINCE_SQL
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchProvinceSelectItem(CCTConnection conn) throws Exception {
		return dao.searchProvinceSelectItem(conn);
	}
	
	protected boolean searchProvinceSelectItemValidate(CCTConnection conn, String provinceNo) throws Exception {
		return dao.searchProvinceSelectItemValidate(conn, provinceNo);
	}
	
	
	/**
	 * SQL : COMBO_AMPUR_SQL
	 * @param conn
	 * @param provinceNo
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchAmpurSelectItem(CCTConnection conn, String provinceNo) throws Exception {
		return dao.searchAmpurSelectItem(conn, provinceNo);
	}
	
	protected boolean searchAmpurSelectItemValidate(CCTConnection conn, String provinceNo, String ampurNo) throws Exception {
		return dao.searchAmpurSelectItemValidate(conn, provinceNo, ampurNo);
	}
	
	
	/**
	 * SQL : COMBO_DEPARTMENT_SQL
	 * @param conn
	 * @param provinceNo
	 * @param ampurNo
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchDepartmentSelectItem(CCTConnection conn, String provinceNo, String ampurNo) throws Exception {
		return dao.searchDepartmentSelectItem(conn, provinceNo, ampurNo);
	}
	
	protected boolean searchDepartmentSelectItemValidate(CCTConnection conn, String provinceNo, String ampurNo, String depNo) throws Exception {
		return dao.searchDepartmentSelectItemValidate(conn, provinceNo, ampurNo, depNo);
	}
	
	/**
	 * for validate search Approve
	 * @param conn
	 * @param provinceNo
	 * @param depNo
	 * @return
	 * @throws Exception
	 */
	protected boolean searchDepartmentSelectItemValidate(CCTConnection conn, String provinceNo, String depNo) throws Exception {
		return dao.searchDepartmentSelectItemValidate(conn, provinceNo, depNo);
	}
	
	
	/**
	 * SQL : COMBO_PREAPV_STATUS_SQL
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchPreApproveStatusSelectItem(CCTConnection conn) throws Exception {
		return dao.searchPreApproveStatusSelectItem(conn);
	}
	
	protected boolean searchPreApproveStatusSelectItemValidate(CCTConnection conn, String code) throws Exception {
		return dao.searchPreApproveStatusSelectItemValidate(conn, code);
	}
	
	/**
	 * SQL : COMBO_ACTIVE_SQL
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchActiveSelectItem(CCTConnection conn) throws Exception {
		return dao.searchActiveSelectItem(conn);
	}
	
	protected boolean searchActiveSelectItemValidate(CCTConnection conn, String code) throws Exception {
		return dao.searchActiveSelectItemValidate(conn, code);
	}
	
	/**
	 * SQL : COMBO_APV_STATUS_SQL
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchApproveStatusSelectItem(CCTConnection conn) throws Exception {
		return dao.searchApproveStatusSelectItem(conn);
	}
	
	protected boolean searchApproveStatusSelectItemValidate(CCTConnection conn, String code) throws Exception {
		return dao.searchApproveStatusSelectItemValidate(conn, code);
	}
	
	/**
	 * SQL : COMBO_EXT_REASON_SQL
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchReasonExtSelectItem(CCTConnection conn) throws Exception {
		return dao.searchReasonExtSelectItem(conn);
	}
}
