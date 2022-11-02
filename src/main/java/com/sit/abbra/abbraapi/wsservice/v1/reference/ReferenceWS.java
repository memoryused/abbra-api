package com.sit.abbra.abbraapi.wsservice.v1.reference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.reference.domain.ReferenceModel;
import com.sit.abbra.abbraapi.core.reference.service.ReferenceManager;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.enums.ActionType;
import com.sit.abbra.abbraapi.util.security.EExtensionApiSecurityUtil;
import com.sit.common.CommonWS;

import util.json.JSONObjectMapperUtil;
import util.string.StringUtil;

@Path("/api/reference")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class ReferenceWS extends CommonWS {

	private static final String FORMAT_EXP = "File : %s doesn't exist.";
	
	@POST
	@Path("/initSearch")
	public Response initSearch(@Context HttpServletRequest request, String jsonReq) {
		getLogger().info("initSearch");
		
		Response response = null;
		ReferenceModel modelResponse = null;

		try {
			// #1 Get User Login
			LoginUser loginUser = getLoginUser(request);
				
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			// Call manage Business
			ReferenceModel modelRequest = (ReferenceModel) JSONObjectMapperUtil.convertJson2Object(jsonReq, ReferenceModel.class);
			modelResponse = new ReferenceModel();
			
			ReferenceManager manager = new ReferenceManager(getLogger());
			manager.gotoSearchReference(modelResponse, modelRequest.getCategory(), loginUser);
			
			// #4 create manage util
			response = getMessageUtil().manageResult(modelResponse);
						
		}catch(Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(modelResponse, e);
		}	
		return response;
	}
	
	@GET
	@Path("/getatt")
	public Response getAttatchment(@Context HttpServletRequest request, @QueryParam("rep") String rep) {
		getLogger().info("##### GET FILE #####");
		
		Response response = null;
		
		try {
			// #1 Get Login User
			LoginUser loginUser = getLoginUser(request);
						
			// #2 [Skip] Validate permission
			
			// #3 Insert event log
			
			if(StringUtil.nullToString(rep).isEmpty() || rep.equalsIgnoreCase("null")) {
				response = getMessageUtil().manageException(rep, new FileNotFoundException(String.format(FORMAT_EXP, rep)), ActionType.DOWNLOAD);
				return response;
			}
			
			// #4 Call Business
			String file = EExtensionApiSecurityUtil.decryptId(loginUser.getSalt(), loginUser.getSecret(), rep);
			String pathFile = ParameterConfig.getApplication().getSharePath().concat(file);
			getLogger().debug("Get File : {}", pathFile);
			
			if (new File(pathFile).exists()) {
				StreamingOutput fileStream = new StreamingOutput() {
	
					@Override
					public void write(java.io.OutputStream output) throws IOException, WebApplicationException {
						try {
							
							java.nio.file.Path path = Paths.get(pathFile);
							byte[] data = Files.readAllBytes(path);
							output.write(data);
							output.flush();
						} catch (Exception e) {
							getLogger().catching(e);
							throw new WebApplicationException("File Not Found !!");
						}
					}
				};
				
				String mimeType = Files.probeContentType(new File(pathFile).toPath());
				if (StringUtil.stringToNull(mimeType) == null) {
					mimeType = "application/octet-stream";			
				}
				// #6 custom manage result
				
				// Encode fileName from String to UTF-8 
			    String encodedFileName = URLEncoder.encode(pathFile, "UTF-8").replace("+", "%20");
			    
				response = Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
					.header("Content-Disposition", "attachment; filename=\""+ encodedFileName +"\"")
					.type(mimeType)
					.build();
			}else {
				response = getMessageUtil().manageException(rep, new FileNotFoundException(String.format(FORMAT_EXP, pathFile)), ActionType.DOWNLOAD);
			}

		} catch (Exception e) {
			// call manage Exception
			response = getMessageUtil().manageException(null, e);
		}
		return response;
	}
}
