package com.sit.abbra.abbraapi.wsservice.v1.ic.announce;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sit.abbra.abbraapi.core.ic.announce.domain.AnnounceModel;
import com.sit.abbra.abbraapi.core.ic.announce.service.AnnounceManager;
import com.sit.abbra.abbraapi.core.security.authorize.service.AuthorizeManager;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.security.login.domain.OperatorButton;
import com.sit.common.CommonWS;

@Path("/api/announce")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class AnnounceWS extends CommonWS {

	@POST
	@Path("/initSearch")
	public Response initSearch(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("initSearch");
		
		Response response = null;
		AnnounceModel modelResponse = null;

		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			AuthorizeManager authorizeManager = new AuthorizeManager(getLogger());
			HashMap<String, OperatorButton> permission = authorizeManager.checkAuthorize(loginUser);
			
			// #3 Insert event log
			
			// Call manage Business
			modelResponse = new AnnounceModel();
			modelResponse.setMapOperBtn(permission);
			
			AnnounceManager manager = new AnnounceManager(getLogger());
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
