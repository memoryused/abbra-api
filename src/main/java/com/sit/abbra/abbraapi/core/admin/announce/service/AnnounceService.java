package com.sit.abbra.abbraapi.core.admin.announce.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.admin.domain.Announce;
import com.sit.abbra.abbraapi.core.admin.domain.AnnounceSearch;
import com.sit.abbra.abbraapi.core.admin.domain.AnnounceSearchCriteria;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.config.parameter.domain.SQLPath;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.enums.MessageAlert;
import com.sit.abbra.abbraapi.util.security.EExtensionApiSecurityUtil;
import com.sit.abbra.abbraapi.util.validate.InputValidateUtil;
import com.sit.common.CommonInputValidate;
import com.sit.core.common.service.CommonService;
import com.sit.domain.GlobalVariable;
import com.sit.exception.InputValidateException;
import com.sit.exception.ServerValidateException;

import util.bundle.BundleUtil;
import util.database.connection.CCTConnection;
import util.file.FileManagerUtil;
import util.string.StringUtil;

public class AnnounceService extends CommonService {

	private AnnounceDAO dao;
	
	public AnnounceService(Logger logger) {
		super(logger);
		this.dao = new AnnounceDAO(logger, SQLPath.ADMIN_SQL.getSqlPath());
	}
	
	protected int searchCountAnnounce(CCTConnection conn, AnnounceSearchCriteria criteria) throws Exception {
		return dao.searchCountAnnounce(conn, criteria);
	}
	
	/**
	 * searchAnnounce
	 * @param conn
	 * @param criteria
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected List<AnnounceSearch> searchAnnounce(CCTConnection conn, AnnounceSearchCriteria criteria, LoginUser loginUser) throws Exception {
		
		return dao.searchAnnounce(conn, criteria, loginUser);
		
	}
	
	/**
	 * searchById
	 * @param conn
	 * @param id
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected Announce searchById(CCTConnection conn, String id, LoginUser loginUser) throws Exception {
		
		return dao.searchById(conn, id, loginUser);
		
	}
	
	/**
	 * addAnnounce
	 * @param conn
	 * @param announce
	 * @param pathCover
	 * @param pathFile
	 * @param category
	 * @throws Exception
	 */
	protected void addAnnounce(CCTConnection conn, Announce announce, String pathCover, String pathFile, String category) throws Exception {
		
		announce.setCoverPicPath(pathCover);
		announce.setFilePath(pathFile);
		dao.addAnnounce(conn, announce, category);
	}
	
	/**
	 * updateAnnounce
	 * @param conn
	 * @param announce
	 * @param pathCover
	 * @param pathFile
	 * @param id
	 * @throws Exception
	 */
	protected void updateAnnounce(CCTConnection conn, Announce announce, String pathCover, String pathFile, String id) throws Exception {
		
		announce.setCoverPicPath(pathCover);
		announce.setFilePath(pathFile);
		dao.updateAnnounce(conn, announce, id);
	}
	
	/**
	 * updateActiveAnnounce
	 * @param conn
	 * @param announce
	 * @param ids
	 * @throws Exception
	 */
	protected void updateActiveAnnounce(CCTConnection conn, Announce announce, String ids) throws Exception {
		
		dao.updateStatus(conn, announce, ids);
	}
	
	/**
	 * validateSearch
	 * @param criteria
	 * @param loginUser
	 * @throws Exception
	 */
	protected void validateSearch(AnnounceSearchCriteria criteria, LoginUser loginUser) throws Exception {	
		getLogger().debug("validateSearch");
		ResourceBundle bundle = BundleUtil.getBundle("resources.bundle.common.MessageAlert", loginUser.getLocale());
		List<CommonInputValidate> invalidInputs = new ArrayList<>();
		
		String ele = "";
		
		// Check announceType
		getLogger().debug("Validate announceType");
		if (InputValidateUtil.hasValue(criteria.getAnnounceType())) {
			ele = "criteria.announceType";
			// Check format number
			if (!InputValidateUtil.isNumber(criteria.getAnnounceType(), getLogger())) {	
				addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INVALID_DATA.getVal()), criteria.getAnnounceType());
			}
		}
		
		// Check title
		getLogger().debug("Validate title");
		if (InputValidateUtil.hasValue(criteria.getTitle())) {
			ele = "criteria.title";
			// Check max length
			if (criteria.getTitle().length() > 100) {
				addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INVALID_DATA.getVal()), criteria.getTitle());
			}
		}
		
		// Check Detail
		getLogger().debug("Validate detail");
		if (InputValidateUtil.hasValue(criteria.getDetail())) {
			ele = "criteria.detail";
			// Check max length
			if (criteria.getDetail().length() > 100) {
				addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INVALID_DATA.getVal()), criteria.getDetail());
			}
		}
		
		// Check date
		getLogger().debug("Validate date");
		if (InputValidateUtil.hasValue(criteria.getAnnounceDate())) {
			/* Request date From */
			ele = "criteria.announceDate";
			// Check format
			if (!InputValidateUtil.isDate(criteria.getAnnounceDate(), ParameterConfig.getDateFormat().getUiDate(), getLogger())) {	
				addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INVALID_DATA.getVal()), criteria.getAnnounceDate());
				
			}
		}
		
		
		if (!invalidInputs.isEmpty()) {
			throw new InputValidateException(invalidInputs);
		}
		
	}
	
	/**
	 * validateAddEdit
	 * @param announce
	 * @param file
	 * @param loginUser
	 * @throws Exception
	 */
	protected String validateAddEdit(Announce announce, LoginUser loginUser, boolean isEdit) throws Exception {	
		ResourceBundle bundle = BundleUtil.getBundle("resources.bundle.common.MessageAlert", loginUser.getLocale());
		List<CommonInputValidate> invalidInputs = new ArrayList<>();
		String id = null;
		if(isEdit) {
			id = validateEdit(announce, loginUser);
		}
		
		// Check announceType
		getLogger().debug("Validate announceType");
		checkRequireAnnounceType(bundle, invalidInputs, announce);
		
		// Check title
		getLogger().debug("Validate title");
		checkRequireTitle(bundle, invalidInputs, announce);
		
		// Check Detail
		getLogger().debug("Validate detail");
		checkRequireDetail(bundle, invalidInputs, announce);
		
		// Check date
		getLogger().debug("Validate date");
		checkRequireAnnounceDate(bundle, invalidInputs, announce);
		
		// Check status
		getLogger().debug("Validate status");
		checkRequireStatus(bundle, invalidInputs, announce);
		
		// Check file
		getLogger().debug("Validate file");
		if(announce.getCoverPicPath() == null && !isEdit) {
			addInvalidInput(invalidInputs, "coverPicName", bundle.getString(MessageAlert.INSUFFICIENT_DATA.getVal()), null);
		}
		
		if(announce.getFilePath() == null && !isEdit) {
			addInvalidInput(invalidInputs, "fileName", bundle.getString(MessageAlert.INSUFFICIENT_DATA.getVal()), null);
		}
		
		if (!invalidInputs.isEmpty()) {
			throw new InputValidateException(invalidInputs);
		}
		return id;
		
	}
	
	/**
	 * validateEdit
	 * @param announce
	 * @param user
	 * @return
	 * @throws Exception
	 */
	protected String validateEdit(Announce announce, LoginUser user) throws Exception {
		getLogger().debug("Validate id");
		String id = null;
		if(StringUtil.stringToNull(announce.getHiddenToken()) != null) {
			try {
				id = checkId(announce.getHiddenToken(), user);
			}catch (Exception e) {
				throw new ServerValidateException();
			}
		}else {
			getLogger().debug("Id is  null");
			throw new ServerValidateException();
		}
		
		if(StringUtil.stringToNull(id) == null) {
			getLogger().debug("Id is null");
			throw new ServerValidateException();
		}
		
		return id;
		
		
	}
	
	/**
	 * validateActive
	 * @param announce
	 * @param user
	 * @return
	 * @throws Exception
	 */
	protected String validateActive(Announce announce, LoginUser user) throws Exception {
		getLogger().debug("validateActive");
		StringBuilder ids = new StringBuilder("");
		// status
		if (!GlobalVariable.ACTIVE.equals(announce.getStatus()) &&  !GlobalVariable.INACTIVE.equals(announce.getStatus())) {	
			getLogger().debug("Status is not Y,N");
			throw new ServerValidateException();
		}
		// id
		if(StringUtil.stringToNull(announce.getHiddenToken()) != null) {
			try {
				String[] hidIds =  announce.getHiddenToken().split(",");
				for (String hidToken: hidIds) {
					ids.append(","+checkId(hidToken, user));
				}
			}catch (Exception e) {
				throw new ServerValidateException();
			}
		}else {
			throw new ServerValidateException();
		}
		
		if(!ids.toString().isEmpty()) {
			ids.deleteCharAt(0);
		}
		
		return ids.toString();
		
		
	}
	
	/**
	 * checkId
	 * @param hidenId
	 * @param user
	 * @return
	 * @throws Exception
	 */
	protected String checkId(String hidenId, LoginUser user) throws Exception {
		String id = null;
		if(StringUtil.stringToNull(hidenId) != null) {
			try {
				id = EExtensionApiSecurityUtil.decryptId(user.getSalt(), user.getSecret(), hidenId);
			}catch (Exception e) {
				getLogger().error("", e);
				throw new ServerValidateException();
			}
		}else {
			getLogger().debug(" Id is null");
			throw new ServerValidateException();
		}
		
		if(StringUtil.stringToNull(id) == null) {
			getLogger().debug("Id  is null");
			throw new ServerValidateException();
		}
		
		return id;
		
		
	}
	
	/**
	 * checkRequireAnnounceType
	 * @param bundle
	 * @param invalidInputs
	 * @param announce
	 */
	private void checkRequireAnnounceType(ResourceBundle bundle, List<CommonInputValidate> invalidInputs, Announce announce) {
		String ele = "announceType";
		// Check announceType
		
		if (InputValidateUtil.hasValue(announce.getAnnounceType())) {
			// Check format number
			if (!InputValidateUtil.isNumber(announce.getAnnounceType(), getLogger())) {	
				addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INVALID_DATA.getVal()), announce.getAnnounceType());
			}
		}else {
			addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INSUFFICIENT_DATA.getVal()), announce.getAnnounceType());
		}
	}
	
	/**
	 * checkRequireTitle
	 * @param bundle
	 * @param invalidInputs
	 * @param announce
	 */
	private void checkRequireTitle(ResourceBundle bundle, List<CommonInputValidate> invalidInputs, Announce announce) {
		String ele = "title";
		// Check title
		if (InputValidateUtil.hasValue(announce.getTitle())) {
			// Check max length
			if (announce.getTitle().length() > 100) {
				addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INVALID_DATA.getVal()), announce.getTitle());
			}
		}else {
			addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INSUFFICIENT_DATA.getVal()), announce.getTitle());
		}
	}
	
	/**
	 * checkRequireDetail
	 * @param bundle
	 * @param invalidInputs
	 * @param announce
	 */
	private void checkRequireDetail(ResourceBundle bundle, List<CommonInputValidate> invalidInputs, Announce announce) {
		String  ele = "detail";
		// Check detail
		if (InputValidateUtil.hasValue(announce.getDetail())) {
			// Check max length
			if (announce.getDetail().length() > 2500) {
				addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INVALID_DATA.getVal()), announce.getDetail());
			}
		}else {
			addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INVALID_DATA.getVal()), announce.getDetail());
		}
	}
	
	/**
	 * checkRequireAnnounceDate
	 * @param bundle
	 * @param invalidInputs
	 * @param announce
	 */
	private void checkRequireAnnounceDate(ResourceBundle bundle, List<CommonInputValidate> invalidInputs, Announce announce) {
		String ele = "announceDate";
		// Check announceDate
		if (InputValidateUtil.hasValue(announce.getAnnounceDate())) {
			/* Request date From */
			
			// Check format
			if (!InputValidateUtil.isDate(announce.getAnnounceDate(), ParameterConfig.getDateFormat().getUiDate(), getLogger())) {	
				addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INVALID_DATA.getVal()), announce.getAnnounceDate());
				
			}
		}else {
			addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INSUFFICIENT_DATA.getVal()), announce.getAnnounceDate());
			
		}
	}
	
	/**
	 * checkRequireStatus
	 * @param bundle
	 * @param invalidInputs
	 * @param announce
	 */
	private void checkRequireStatus(ResourceBundle bundle, List<CommonInputValidate> invalidInputs, Announce announce) {
		String ele = "status";
		// Check status
		if (InputValidateUtil.hasValue(announce.getStatus())) {
			// Check Y,N
			if (!GlobalVariable.ACTIVE.equals(announce.getStatus()) &&  !GlobalVariable.INACTIVE.equals(announce.getStatus())) {	
				addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INVALID_DATA.getVal()), announce.getStatus());
				
			}
		}else {
			addInvalidInput(invalidInputs, ele, bundle.getString(MessageAlert.INSUFFICIENT_DATA.getVal()), announce.getStatus());
			
		}
	}
	
	/**
	 * create msg validate
	 * @param invalidInputs
	 * @param element
	 * @param msg
	 * @param value
	 */
	protected void addInvalidInput(List<CommonInputValidate> invalidInputs, String element, String msg, String value) {
		  CommonInputValidate input = new CommonInputValidate();
		  input.setElement(element);
		  input.setMsg(msg);
		  invalidInputs.add(input);
		  getLogger().error("[{}] [{}] [{}]", element, value , msg );
	}
	
	/**
	 * uploadFile
	 * @param fileCover
	 * @param pathCover
	 * @param file
	 * @param pathFile
	 * @param sharedPath
	 * @throws Exception
	 */
	protected void uploadFile(File fileCover, String pathCover, File file, String pathFile , String sharedPath) throws Exception {
		pathCover = sharedPath + pathCover;
		pathFile = sharedPath + pathFile;
		
		if(fileCover != null) {
			getLogger().debug("upload fileCover");
			FileManagerUtil.copy(fileCover.getAbsolutePath(), pathCover);
		}
		
		try {
			if(file != null) {
				getLogger().debug("upload file");
				FileManagerUtil.copy(file.getAbsolutePath(), pathFile);
			}
		}catch (Exception e) {
			try {
				if(fileCover != null) {
					FileManagerUtil.forceDelete(pathCover);
				}
			}catch (Exception se) {
				// 
			}
			
			throw e;
		}
	}
	
}
