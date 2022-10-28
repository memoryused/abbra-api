package com.sit.abbra.abbraapi.wsservice.v1.aboutus;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sit.abbra.abbraapi.core.aboutus.domain.AboutUsModel;
import com.sit.abbra.abbraapi.core.aboutus.service.AboutUsManager;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.common.CommonWS;

@Path("/api/aboutus")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class AboutUsWS extends CommonWS {

	@POST
	@Path("/initSearch")
	public Response initSearch(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("initSearch");
		
		Response response = null;
		AboutUsModel modelResponse = null;

		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			// Call manage Business
			modelResponse = new AboutUsModel();
			
			AboutUsManager manager = new AboutUsManager(getLogger());
			manager.initData(modelResponse);
			
			// #4 create manage util
			response = getMessageUtil().manageResult(modelResponse);
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(modelResponse, e);
		}	
		return response;
	}
	
	@POST
	@Path("/gotoExecutive")
	public Response gotoExecutive(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("gotoExecutive");
		
		Response response = null;
		AboutUsModel modelResponse = null;

		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			// Call manage Business
			modelResponse = new AboutUsModel();
			
			AboutUsManager manager = new AboutUsManager(getLogger());
			manager.gotoExecutive(modelResponse);
			
			// #4 create manage util
			response = getMessageUtil().manageResult(modelResponse);
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(modelResponse, e);
		}	
		return response;
	}
	
	@POST
	@Path("/gotoOrganization")
	public Response gotoOrganization(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("gotoOrganization");
		
		Response response = null;
		AboutUsModel modelResponse = null;
		
		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
			
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			// Call manage Business
			modelResponse = new AboutUsModel();
			
			AboutUsManager manager = new AboutUsManager(getLogger());
			manager.gotoOrganization(modelResponse);
			
			// #4 create manage util
			response = getMessageUtil().manageResult(modelResponse);
			
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(modelResponse, e);
		}	
		return response;
	}
}
