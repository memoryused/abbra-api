package com.sit.abbra.abbraapi.wsservice.v1.employee;

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
import com.sit.abbra.abbraapi.core.employee.domain.EmployeeModel;
import com.sit.abbra.abbraapi.core.employee.service.EmployeeManager;
import com.sit.abbra.abbraapi.core.home.domain.HomeModel;
import com.sit.abbra.abbraapi.core.home.service.HomeManager;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.enums.ActionType;
import com.sit.abbra.abbraapi.enums.MessageAlert;
import com.sit.common.CommonWS;

import util.json.JSONObjectMapperUtil;

@Path("/api/employee")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class EmployeeWS extends CommonWS {

	@POST
	@Path("/search")
	public Response searchEmployee(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("searchEmployee");
		
		Response response = null;
		EmployeeModel modelResponse = null;

		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			EmployeeModel modelRequest = (EmployeeModel) JSONObjectMapperUtil.convertJson2Object(jsonReq, EmployeeModel.class);
			
			// Call manage Business
			modelResponse = new EmployeeModel();
			
			EmployeeManager manager = new EmployeeManager(getLogger());
			manager.searchEmployee(modelResponse, modelRequest.getQuery());
			
			// #4 create manage util
			if(modelResponse.getImg().isEmpty()) {
				response = getMessageUtil().manageResult(modelResponse, MessageAlert.DATA_NOT_FOUND.getVal());
			} else {
				response = getMessageUtil().manageResult(modelResponse);
			}
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(modelResponse, e);
		}	
		return response;
	}
}
