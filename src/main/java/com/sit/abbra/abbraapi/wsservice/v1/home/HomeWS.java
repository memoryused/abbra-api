package com.sit.abbra.abbraapi.wsservice.v1.home;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sit.abbra.abbraapi.core.home.domain.HomeModel;
import com.sit.abbra.abbraapi.core.home.service.HomeManager;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.common.CommonWS;

@Path("/api/home")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class HomeWS extends CommonWS {

	@POST
	@Path("/initSearch")
	public Response initSearch(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("initSearch");
		
		Response response = null;
		HomeModel modelResponse = null;

		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			// Call manage Business
			modelResponse = new HomeModel();
			
			HomeManager manager = new HomeManager(getLogger());
			manager.initData(modelResponse);
			
			// #4 create manage util
			response = getMessageUtil().manageResult(modelResponse);
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(modelResponse, e);
		}	
		return response;
	}
}
