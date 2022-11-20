package com.sit.abbra.abbraapi.wsservice.v1.culture;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sit.abbra.abbraapi.core.culture.domain.CultureModel;
import com.sit.abbra.abbraapi.core.culture.service.CultureManager;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.common.CommonWS;

import util.json.JSONObjectMapperUtil;

@Path("/api/culture")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class CultureWS extends CommonWS {

	@POST
	@Path("/initSearch")
	public Response initSearch(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("initSearch");
		
		Response response = null;
		CultureModel modelResponse = null;

		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			// Call manage Business
			CultureModel modelRequest = (CultureModel) JSONObjectMapperUtil.convertJson2Object(jsonReq, CultureModel.class);
			modelResponse = new CultureModel();
			
			CultureManager manager = new CultureManager(getLogger());
			manager.searchCulture(modelResponse, modelRequest.getAnnounceType());
			
			// #4 create manage util
			response = getMessageUtil().manageResult(modelResponse);
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(modelResponse, e);
		}	
		return response;
	}
}
