package com.sit.abbra.abbraapi.core.admin.announce.service;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.sit.abbra.abbraapi.core.admin.domain.Announce;
import com.sit.abbra.abbraapi.core.admin.domain.AnnounceModel;
import com.sit.abbra.abbraapi.core.admin.domain.AnnounceSearch;
import com.sit.abbra.abbraapi.core.attachment.service.AttachMentManager;
import com.sit.abbra.abbraapi.core.attachment.service.AttachMentManager.FilterType;
import com.sit.abbra.abbraapi.core.config.parameter.domain.DBLookup;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.selectitem.enums.GlobalType;
import com.sit.abbra.abbraapi.core.selectitem.service.SelectItemManager;
import com.sit.abbra.abbraapi.util.database.CCTConnectionProvider;
import com.sit.common.CommonSearchResult;
import com.sit.core.common.service.CommonManager;
import com.sit.domain.GlobalVariable;
import com.sit.domain.GlobalVariable.AnnounceType;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;

public class AnnounceManager extends CommonManager {

	private AnnounceService service;
	
	public AnnounceManager(Logger logger) {
		super(logger);
		this.service = new AnnounceService(logger);
	}

	/**
	 * initSearchAnnounce
	 * @param modelResponse
	 * @param loginUser
	 * @throws Exception
	 */
	public void initSearchAnnounce(AnnounceModel modelResponse, LoginUser loginUser) throws Exception {
		getLogger().debug(" Admin initSearchAnnounce ");
		
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			// Combo AnnounceType
			SelectItemManager selectItemManager = new SelectItemManager(getLogger());
			modelResponse.setLstAnnounceType(selectItemManager.getGlobalSelectitem(GlobalType.ANNOUNCE_TYPE, loginUser.getLocale()));
			// Combo Status
			modelResponse.setLstStatus(selectItemManager.getGlobalSelectitem(GlobalType.ACTIVE_STATUS, loginUser.getLocale()));
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	/**
	 * searchAnnounce
	 * @param modelReq
	 * @param searchResult
	 * @param loginUser
	 * @throws Exception
	 */
	public void searchAnnounce(AnnounceModel modelReq, CommonSearchResult<AnnounceSearch> searchResult,LoginUser loginUser) throws Exception {
		getLogger().debug(" Admin SearchAnnounce ");
		
		CCTConnection conn = null;
		try {
			// Validate
			service.validateSearch(modelReq.getCriteria(), loginUser);
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			// Search
			searchResult.setListResult(service.searchAnnounce(conn, modelReq.getCriteria(), loginUser));
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
	/**
	 * initAddEditAnnounce
	 * @param modelResponse
	 * @param modelReq
	 * @param isEdit
	 * @param loginUser
	 * @throws Exception
	 */
	public void initAddEditAnnounce(AnnounceModel modelResponse, AnnounceModel modelReq, boolean isEdit, LoginUser loginUser) throws Exception {
		getLogger().debug(" Admin initAddEditAnnounce ");
		
		CCTConnection conn = null;
		try {
			String id = null;
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			if(isEdit) {
				modelReq.setAnnounce(new Announce());
				modelReq.getAnnounce().setHiddenToken(modelReq.getHiddenToken());
				id = service.validateEdit(modelReq.getAnnounce(), loginUser);
				// search by id
				modelResponse.setAnnounce(service.searchById(conn, id, loginUser));
			}
			
			// Combo AnnounceType
			SelectItemManager selectItemManager = new SelectItemManager(getLogger());
			modelResponse.setLstAnnounceType(selectItemManager.getGlobalSelectitem(GlobalType.ANNOUNCE_TYPE, loginUser.getLocale()));
			// Combo Status
			modelResponse.setLstStatus(selectItemManager.getGlobalSelectitem(GlobalType.ACTIVE_STATUS, loginUser.getLocale()));
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}

	/**
	 * addAnnounce
	 * @param modelReq
	 * @param fileCoverMeta
	 * @param fileCover
	 * @param fileMeta
	 * @param file
	 * @param loginUser
	 * @throws Exception
	 */
	public void addAnnounce(AnnounceModel modelReq, 
			FormDataContentDisposition fileCoverMeta, 
			File fileCover,
			FormDataContentDisposition fileMeta, 
			File file,
			LoginUser loginUser) throws Exception {
		getLogger().debug(" Admin addAnnounce ");
		
		CCTConnection conn = null;
		AttachMentManager attachMentManager = new AttachMentManager(getLogger());
		try {
			String sharedPath = ParameterConfig.getApplication().getSharePath();
			//1. Validate
			service.validateAddEdit(modelReq.getAnnounce(), file, loginUser, false);
			//2. Manage file
			// find path by announceType
			Optional<AnnounceType> enumType = Arrays.stream(GlobalVariable.AnnounceType.values()).filter(objEnum -> objEnum.getKey().equals(modelReq.getAnnounce().getAnnounceType())).findFirst();
			String pathbyType = enumType.get().getPath();
			String category = enumType.get().getValue();
			getLogger().debug("Category: {}",category);
			
			// fileCover
			String pathCover = null;
			if(fileCover != null) {
				getLogger().debug("Has fileCover");
				pathCover = attachMentManager.manageFile(fileCover, fileCoverMeta, pathbyType, modelReq.getAnnounce().getAnnounceDate(), FilterType.IMG);
			}
			
			// file
			String pathFile = attachMentManager.manageFile(file, fileMeta, pathbyType, modelReq.getAnnounce().getAnnounceDate(), FilterType.IMG_DOC);
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			CCTConnectionUtil.disableAutoCommit(conn);
			getLogger().debug("Begin Transaction");
			//3. Insert
			service.addAnnounce(conn, modelReq.getAnnounce(), pathCover, pathFile, category);
			// Upload
			service.uploadFile(fileCover, pathCover, file, pathFile, sharedPath);
			
			conn.commit();
			getLogger().debug("Commit");
		}catch (Exception e) {
			getLogger().error("Rollback");
			CCTConnectionUtil.rollback(conn);
			throw e;
		} finally {
			getLogger().debug("End Transaction");
			CCTConnectionUtil.enableAutoCommit(conn);
			CCTConnectionUtil.close(conn);
		}
	}

	/**
	 * editAnnounce
	 * @param modelReq
	 * @param fileCoverMeta
	 * @param fileCover
	 * @param fileMeta
	 * @param file
	 * @param loginUser
	 * @throws Exception
	 */
	public void editAnnounce(AnnounceModel modelReq, 
			FormDataContentDisposition fileCoverMeta, 
			File fileCover,
			FormDataContentDisposition fileMeta, 
			File file,
			LoginUser loginUser) throws Exception {
		getLogger().debug(" Admin editAnnounce ");
		
		CCTConnection conn = null;
		AttachMentManager attachMentManager = new AttachMentManager(getLogger());
		try {
			String sharedPath = ParameterConfig.getApplication().getSharePath();
			//1. Validate
			String id = service.validateAddEdit(modelReq.getAnnounce(), file, loginUser, true);

			//2. Manage file
			// find path by announceType
			Optional<AnnounceType> enumType = Arrays.stream(GlobalVariable.AnnounceType.values()).filter(objEnum -> objEnum.getKey().equals(modelReq.getAnnounce().getAnnounceType())).findFirst();
			String pathbyType = enumType.get().getPath();
			
			// fileCover
			String pathCover = null;
			if(fileCover != null) {
				getLogger().debug("Has fileCover");
				pathCover = attachMentManager.manageFile(fileCover, fileCoverMeta, pathbyType, modelReq.getAnnounce().getAnnounceDate(), FilterType.IMG);
			}
			
			// file
			String pathFile = null;
			if(file != null) {
				getLogger().debug("Has file");
				pathFile = attachMentManager.manageFile(file, fileMeta, pathbyType, modelReq.getAnnounce().getAnnounceDate(), FilterType.IMG_DOC);
			}
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			CCTConnectionUtil.disableAutoCommit(conn);
			getLogger().debug("Begin Transaction");
			
			//3. Update
			service.updateAnnounce(conn, modelReq.getAnnounce(), pathCover, pathFile, id);
			// Upload
			service.uploadFile(fileCover, pathCover, file, pathFile, sharedPath);
			
			conn.commit();
			getLogger().debug("Commit");
		}catch (Exception e) {
			getLogger().error("Rollback");
			CCTConnectionUtil.rollback(conn);
			throw e;
		} finally {
			getLogger().debug("End Transaction");
			CCTConnectionUtil.enableAutoCommit(conn);
			CCTConnectionUtil.close(conn);
		}
	}
	
	/**
	 * updateActiveAnnounce
	 * @param modelReq
	 * @param loginUser
	 * @throws Exception
	 */
	public void updateActiveAnnounce(AnnounceModel modelReq,LoginUser loginUser) throws Exception {
		getLogger().debug(" Admin updateActiveAnnounce ");
		
		CCTConnection conn = null;
		try {
			// Validate
			String ids = service.validateActive(modelReq.getAnnounce(), loginUser);
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.E_EXT_API.getLookup());
			// Update status
			service.updateActiveAnnounce(conn, modelReq.getAnnounce(), ids);
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}
	
}
