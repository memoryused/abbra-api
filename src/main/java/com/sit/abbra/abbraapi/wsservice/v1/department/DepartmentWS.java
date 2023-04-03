package com.sit.abbra.abbraapi.wsservice.v1.department;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sit.abbra.abbraapi.core.department.domain.DepartmentModel;
import com.sit.abbra.abbraapi.core.department.service.DepartmentManager;
import com.sit.abbra.abbraapi.core.security.authorize.service.AuthorizeManager;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.security.login.domain.OperatorButton;
import com.sit.common.CommonWS;

import util.json.JSONObjectMapperUtil;

@Path("/api/department")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class DepartmentWS extends CommonWS {

	@POST
	@Path("/gotoDepartment")
	public Response gotoDepartment(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("gotoDepartment");
		
		Response response = null;
		DepartmentModel modelResponse = null;

		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			AuthorizeManager authorizeManager = new AuthorizeManager(getLogger());
			HashMap<String, OperatorButton> permission = authorizeManager.checkAuthorize(loginUser);
					
			// #3 Insert event log
			
			DepartmentModel modelRequest = (DepartmentModel) JSONObjectMapperUtil.convertJson2Object(jsonReq, DepartmentModel.class);
			
			// Call manage Business
			modelResponse = new DepartmentModel();
			modelResponse.setMapOperBtn(permission);
			
			DepartmentManager manager = new DepartmentManager(getLogger());
			manager.gotoDepartment(modelResponse, modelRequest.getAbbrDep());
			
			// #4 create manage util
			response = getMessageUtil().manageResult(modelResponse);
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(modelResponse, e);
		}	
		return response;
	}
}
