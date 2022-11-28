package com.sit.abbra.abbraapi.core.admin.depannounce;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.admin.announce.service.AnnounceService;
import com.sit.abbra.abbraapi.core.admin.domain.Announce;
import com.sit.abbra.abbraapi.core.admin.domain.AnnounceSearch;
import com.sit.abbra.abbraapi.core.admin.domain.AnnounceSearchCriteria;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;

import util.database.connection.CCTConnection;

public class DepAnnounceService extends AnnounceService {

	public DepAnnounceService(Logger logger) {
		super(logger);
	}
	
	/**
	 * searchDepAnnounce
	 * @param conn
	 * @param criteria
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected List<AnnounceSearch> searchDepAnnounce(CCTConnection conn, AnnounceSearchCriteria criteria, LoginUser loginUser) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		return super.searchAnnounce(conn, criteria, loginUser);
		
	}
	
	/**
	 * searchByIdDep
	 * @param conn
	 * @param id
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	protected Announce searchByIdDep(CCTConnection conn, String id, LoginUser loginUser) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		return super.searchById(conn, id, loginUser);
		
	}
	
	/**
	 * addAnnounceDep
	 * @param conn
	 * @param announce
	 * @param pathCover
	 * @param pathFile
	 * @param category
	 * @throws Exception
	 */
	protected void addAnnounceDep(CCTConnection conn, Announce announce, String pathCover, String pathFile, String category) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		super.addAnnounce(conn, announce, pathCover, pathFile, category);
	}
	
	/**
	 * updateAnnounceDep
	 * @param conn
	 * @param announce
	 * @param pathCover
	 * @param pathFile
	 * @param id
	 * @throws Exception
	 */
	protected void updateAnnounceDep(CCTConnection conn, Announce announce, String pathCover, String pathFile, String id) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		super.updateAnnounce(conn, announce, pathCover, pathFile, id);
	}
	
	/**
	 * updateActiveAnnounceDep
	 * @param conn
	 * @param announce
	 * @param ids
	 * @throws Exception
	 */
	protected void updateActiveAnnounceDep(CCTConnection conn, Announce announce, String ids) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		super.updateActiveAnnounce(conn, announce, ids);
	}
	
	/**
	 * validateSearchDep
	 * @param criteria
	 * @param loginUser
	 * @throws Exception
	 */
	protected void validateSearchDep(AnnounceSearchCriteria criteria, LoginUser loginUser) throws Exception {	
		// *** ใช้ร่วมกันกับ AnnounceService
		super.validateSearch(criteria, loginUser);
		
	}
	
	/**
	 * validateIdDep
	 * @param announce
	 * @param user
	 * @return
	 * @throws Exception
	 */
	protected String validateIdDep(Announce announce, LoginUser user) throws Exception {
		
		// *** ใช้ร่วมกันกับ AnnounceService
		return super.validateEdit(announce, user);
		
		
	}
	
	/**
	 * validateAddEdit
	 * @param announce
	 * @param file
	 * @param loginUser
	 * @throws Exception
	 */
	protected String validateAddEditDep(Announce announce, File file, LoginUser loginUser, boolean isEdit) throws Exception {	
		// *** ใช้ร่วมกันกับ AnnounceService
		return super.validateAddEdit(announce, file, loginUser, isEdit);
		
	}
	
	/**
	 * validateActiveDep
	 * @param announce
	 * @param user
	 * @return
	 * @throws Exception
	 */
	protected String validateActiveDep(Announce announce, LoginUser user) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		return super.validateActive(announce, user);
		
	}

	/**
	 * uploadFileDep
	 * @param fileCover
	 * @param pathCover
	 * @param file
	 * @param pathFile
	 * @param sharedPath
	 * @throws Exception
	 */
	protected void uploadFileDep(File fileCover, String pathCover, File file, String pathFile , String sharedPath) throws Exception {
		// *** ใช้ร่วมกันกับ AnnounceService
		super.uploadFile(fileCover, pathCover, file, pathFile, sharedPath);
	}
	
}
