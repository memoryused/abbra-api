package com.sit.abbra.abbraapi.wsservice.v1.admin.vdo;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.sit.abbra.abbraapi.core.admin.domain.AnnounceModel;
import com.sit.abbra.abbraapi.core.admin.domain.AnnounceSearch;
import com.sit.abbra.abbraapi.core.admin.vdo.VdoAnnounceManager;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.enums.ActionType;
import com.sit.common.CommonSearchResult;
import com.sit.common.CommonWS;

import util.json.JSONObjectMapperUtil;

@Path("/api/admin/vdoannounce")
public class AdminVdoAnnounceWS extends CommonWS {

	@POST
	@Path("/initSearchAnnounce")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response initSearch(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("Admin initSearchAnnounce");
		
		Response response = null;
		AnnounceModel modelResponse = null;

		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			// Call manage Business
			modelResponse = new AnnounceModel();
			
			// #4 create manage util
			response = getMessageUtil().manageResult(modelResponse);
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(modelResponse, e);
		}	
		return response;
	}
	
	@POST
	@Path("/searchAnnounce")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response searchAnnounce(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("Admin SearchAnnounce");
		
		Response response = null;
		CommonSearchResult<AnnounceSearch> searchResult = new CommonSearchResult<>();
		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			// Call manage Business
			AnnounceModel modelReq = (AnnounceModel) JSONObjectMapperUtil.convertJson2Object(jsonReq, AnnounceModel.class);
			
			VdoAnnounceManager manager = new VdoAnnounceManager(getLogger());
			manager.searchAnnounce(modelReq, searchResult, loginUser);
			
			// #4 create manage util
			response = getMessageUtil().manageResult(searchResult, ActionType.SEARCH);
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(searchResult, e, ActionType.SEARCH);
		}	
		return response;
	}
	
	@POST
	@Path("/initEditAnnounce")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response initEditAnnounce(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("Admin initEditAnnounce");
		
		Response response = null;
		AnnounceModel modelResponse = null;

		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			// Call manage Business
			AnnounceModel modelReq = (AnnounceModel) JSONObjectMapperUtil.convertJson2Object(jsonReq, AnnounceModel.class);
			modelResponse = new AnnounceModel();
			
			VdoAnnounceManager manager = new VdoAnnounceManager(getLogger());
			manager.initAddEditAnnounce(modelResponse, modelReq, true, loginUser);
			
			// #4 create manage util
			response = getMessageUtil().manageResult(modelResponse);
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(modelResponse, e);
		}	
		return response;
	}
	
	@POST
	@Path("/initAddAnnounce")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response initAddAnnounce(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("Admin initAddAnnounce");
		
		Response response = null;
		AnnounceModel modelResponse = null;

		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			// Call manage Business
			modelResponse = new AnnounceModel();
			
			VdoAnnounceManager manager = new VdoAnnounceManager(getLogger());
			manager.initAddEditAnnounce(modelResponse, null, false, loginUser);
			
			// #4 create manage util
			response = getMessageUtil().manageResult(modelResponse);
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(modelResponse, e);
		}	
		return response;
	}
	
	@POST
	@Path("/addAnnounce")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAnnounce(
			@Context HttpServletRequest request, 
			@FormDataParam("jsonReq") String jsonReq,
			@FormDataParam("fileCover") FormDataContentDisposition fileCoverMeta, 
			@FormDataParam("fileCover") File fileCover,
			@FormDataParam("fileAtt") FormDataContentDisposition fileMeta, 
			@FormDataParam("fileAtt") File file) {
		getLogger().info("Admin addAnnounce");
		
		Response response = null;
		CommonSearchResult<AnnounceSearch> searchResult = new CommonSearchResult<>();
		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			// Call manage Business
			AnnounceModel modelReq = (AnnounceModel) JSONObjectMapperUtil.convertJson2Object(jsonReq, AnnounceModel.class);
			
			VdoAnnounceManager manager = new VdoAnnounceManager(getLogger());
			manager.addAnnounce(modelReq, fileCoverMeta, fileCover, fileMeta, file, loginUser);

			// #4 create manage util
			response = getMessageUtil().manageResult(searchResult, ActionType.ADD);
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(searchResult, e, ActionType.ADD);
		}	
		return response;
	}
	
	@POST
	@Path("/editAnnounce")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces(MediaType.APPLICATION_JSON)
	public Response editAnnounce(
			@Context HttpServletRequest request, 
			@FormDataParam("jsonReq") String jsonReq,
			@FormDataParam("fileCover") FormDataContentDisposition fileCoverMeta, 
			@FormDataParam("fileCover") File fileCover,
			@FormDataParam("fileAtt") FormDataContentDisposition fileMeta, 
			@FormDataParam("fileAtt") File file) {
		getLogger().info("Admin editAnnounce");
		
		Response response = null;
		CommonSearchResult<AnnounceSearch> searchResult = new CommonSearchResult<>();
		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			// Call manage Business
			AnnounceModel modelReq = (AnnounceModel) JSONObjectMapperUtil.convertJson2Object(jsonReq, AnnounceModel.class);
			
			VdoAnnounceManager manager = new VdoAnnounceManager(getLogger());
			manager.editAnnounce(modelReq, fileCoverMeta, fileCover, fileMeta, file, loginUser);
			
			// #4 create manage util
			response = getMessageUtil().manageResult(searchResult, ActionType.EDIT);
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(searchResult, e, ActionType.EDIT);
		}	
		return response;
	}
	
	@POST
	@Path("/updateActiveAnnounce")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateActiveAnnounce(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("Admin updateActiveAnnounce");
		
		Response response = null;
		CommonSearchResult<AnnounceSearch> searchResult = new CommonSearchResult<>();
		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			// Call manage Business
			AnnounceModel modelReq = (AnnounceModel) JSONObjectMapperUtil.convertJson2Object(jsonReq, AnnounceModel.class);
			
			VdoAnnounceManager manager = new VdoAnnounceManager(getLogger());
			manager.updateActiveAnnounce(modelReq, loginUser);
			
			// #4 create manage util
			response = getMessageUtil().manageResult(searchResult);
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(searchResult, e);
		}	
		return response;
	}
	
}
