package com.sit.abbra.abbraapi.core.admin.vdo;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.admin.announce.service.AnnounceService;
import com.sit.abbra.abbraapi.core.admin.domain.Announce;
import com.sit.abbra.abbraapi.core.admin.domain.AnnounceSearch;
import com.sit.abbra.abbraapi.core.admin.domain.AnnounceSearchCriteria;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;

import util.database.connection.CCTConnection;

public class VdoAnnounceService extends AnnounceService {

	public VdoAnnounceService(Logger logger) {
		super(logger);
	}
	
	/**
	 * searchVdoAnnounce
	 * @param conn
	 * @param criteria
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected List<AnnounceSearch> searchVdoAnnounce(CCTConnection conn, AnnounceSearchCriteria criteria, LoginUser loginUser) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		return super.searchAnnounce(conn, criteria, loginUser);
		
	}
	
	/**
	 * searchByIdVdo
	 * @param conn
	 * @param id
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected Announce searchByIdVdo(CCTConnection conn, String id, LoginUser loginUser) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		return super.searchById(conn, id, loginUser);
		
	}
	
	/**
	 * validateIdVdo
	 * @param announce
	 * @param user
	 * @return
	 * @throws Exception
	 */
	protected String validateIdVdo(Announce announce, LoginUser user) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		return super.validateEdit(announce, user);
		
	}
	
	/**
	 * addAnnounceVdo
	 * @param conn
	 * @param announce
	 * @param pathCover
	 * @param pathFile
	 * @param category
	 * @throws Exception
	 */
	protected void addAnnounceVdo(CCTConnection conn, Announce announce, String pathCover, String pathFile, String category) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		super.addAnnounce(conn, announce, pathCover, pathFile, category);
	}
	
	/**
	 * updateAnnounceVdo
	 * @param conn
	 * @param announce
	 * @param pathCover
	 * @param pathFile
	 * @param id
	 * @throws Exception
	 */
	protected void updateAnnounceVdo(CCTConnection conn, Announce announce, String pathCover, String pathFile, String id) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		super.updateAnnounce(conn, announce, pathCover, pathFile, id);
	}
	
	/**
	 * updateActiveAnnounceVdo
	 * @param conn
	 * @param announce
	 * @param ids
	 * @throws Exception
	 */
	protected void updateActiveAnnounceVdo(CCTConnection conn, Announce announce, String ids) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		super.updateActiveAnnounce(conn, announce, ids);
	}
	
	/**
	 * validateSearchVdo
	 * @param criteria
	 * @param loginUser
	 * @throws Exception
	 */
	protected void validateSearchVdo(AnnounceSearchCriteria criteria, LoginUser loginUser) throws Exception {	
		// *** ใช้ร่วมกันกับ AnnounceService
		super.validateSearch(criteria, loginUser);
	}
	
	/**
	 * validateAddEdit
	 * @param announce
	 * @param file
	 * @param loginUser
	 * @throws Exception
	 */
	protected String validateAddEditVdo(Announce announce, File file, LoginUser loginUser, boolean isEdit) throws Exception {	
		// *** ใช้ร่วมกันกับ AnnounceService
		return super.validateAddEdit(announce, loginUser, isEdit);
		
	}
	
	/**
	 * validateActiveVdo
	 * @param announce
	 * @param user
	 * @return
	 * @throws Exception
	 */
	protected String validateActiveVdo(Announce announce, LoginUser user) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		return super.validateActive(announce, user);
		
	}

	/**
	 * uploadFileVdo
	 * @param fileCover
	 * @param pathCover
	 * @param file
	 * @param pathFile
	 * @param sharedPath
	 * @throws Exception
	 */
	protected void uploadFileVdo(File fileCover, String pathCover, File file, String pathFile , String sharedPath) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		super.uploadFile(fileCover, pathCover, file, pathFile, sharedPath);
	}
	
}
