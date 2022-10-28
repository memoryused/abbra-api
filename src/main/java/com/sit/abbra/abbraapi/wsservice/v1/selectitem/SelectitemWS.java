package com.sit.abbra.abbraapi.wsservice.v1.selectitem;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sit.abbra.abbraapi.common.EExtensionApiCommonWS;
import com.sit.abbra.abbraapi.core.security.login.domain.LoginUser;
import com.sit.abbra.abbraapi.core.selectitem.service.SelectItemManager;
import com.sit.common.CommonSelectItem;

import util.string.StringUtil;

@Path("/selectitem")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class SelectitemWS extends EExtensionApiCommonWS {

	private static final String LOG_TERM_FORMAT = "term : {}";
	private static final String LOG_LIMIT_FORMAT = "limit : {}";

	/**
	 * SQL : username 
	 * @param request
	 * @param term
	 * @param limit
	 * @param active
	 * @return
	 */
	@GET
	@Path("/username")
	public Response searchUserSelectItem(@Context HttpServletRequest request, @QueryParam("term") String term,
			@QueryParam("limit") int limit) {
		
		getLogger().debug("[searchUserSelectItem]");
		
		Response response = null;
		List<CommonSelectItem> items = new ArrayList<>();
		try {
			LoginUser loginUser = getLoginUser(request);
			
			
			SelectItemManager manager = new SelectItemManager(getLogger());
			
			if (StringUtil.stringToNull(term) != null) {
				term = URLDecoder.decode(term, StandardCharsets.UTF_8.name());
			}
			
			//  Popup > Autocomplete + Suggestion กด Spacebar 2 ครั้งแล้วต้องไม่แสดงข้อมูลให้เลือก
			if (StringUtil.stringToNull(term) != null) {
				items = manager.searchUserSelectItem(term, loginUser);
			}
			
			response = getMessageUtil().manageResult(items);
			
		} catch (Exception e) {
			response = getMessageUtil().manageException(null, e);
		}
		return response;
	}
	
	/**
	 * SQL : Load [Function] filter_SQL
	 * @param request
	 * @param term
	 * @param limit
	 * @param active
	 * @return
	 */
	@GET
	@Path("/functionfilter")
	public Response searchFunctionFilterSelectItem(@Context HttpServletRequest request, @QueryParam("term") String term,
			@QueryParam("limit") int limit) {
		
		getLogger().debug("[searchFunctionFilterSelectItem]");
		getLogger().debug(LOG_TERM_FORMAT, term);
		getLogger().debug(LOG_LIMIT_FORMAT, limit);
		
		Response response = null;
		List<CommonSelectItem> items = new ArrayList<>();
		try {
			LoginUser loginUser = getLoginUser(request);
			
			
			SelectItemManager manager = new SelectItemManager(getLogger());
			
			if (StringUtil.stringToNull(term) != null) {
				term = URLDecoder.decode(term, StandardCharsets.UTF_8.name());
			}
			
			//  Popup > Autocomplete + Suggestion กด Spacebar 2 ครั้งแล้วต้องไม่แสดงข้อมูลให้เลือก
			if (StringUtil.stringToNull(term) != null) {
				items = manager.searchFunctionFilterSelectItem(term, loginUser);
			}
			
			response = getMessageUtil().manageResult(items);
			
		} catch (Exception e) {
			response = getMessageUtil().manageException(null, e);
		}
		return response;
	}
	
	@GET
	@Path("/menu")
	public Response searchMenuSelectItem(@Context HttpServletRequest request, @QueryParam("term") String term,
			@QueryParam("limit") int limit) {
		
		getLogger().debug("[searchMenuSelectItem]");
		getLogger().debug(LOG_TERM_FORMAT, term);
		getLogger().debug(LOG_LIMIT_FORMAT, limit);
		
		Response response = null;
		List<CommonSelectItem> items = new ArrayList<>();
		try {
			LoginUser loginUser = getLoginUser(request);
			
			
			SelectItemManager manager = new SelectItemManager(getLogger());
			
			if (StringUtil.stringToNull(term) != null) {
				term = URLDecoder.decode(term, StandardCharsets.UTF_8.name());
			}
			
			//  Popup > Autocomplete + Suggestion กด Spacebar 2 ครั้งแล้วต้องไม่แสดงข้อมูลให้เลือก
			if (StringUtil.stringToNull(term) != null) {
				items = manager.searchMenuSelectItem(term, loginUser);
			}
			
			response = getMessageUtil().manageResult(items);
			
		} catch (Exception e) {
			response = getMessageUtil().manageException(null, e);
		}
		return response;
	}
	
	/**
	 * SQL :  ComboBox[Push No.(Timing)_status]_SQL
	 * 
	 * @param request
	 * @param term
	 * @return
	 */
	@GET
	@Path("/pushNo")
	public Response searchPushNoSelectItem(@Context HttpServletRequest request, @QueryParam("pushConfigId") String pushConfigId) {
		getLogger().debug("[searchPushNoSelectItem]");
		getLogger().debug("pushConfigId : {}", pushConfigId);
		
		Response response = null;
		List<CommonSelectItem> list = new ArrayList<>();
		
		try {
			LoginUser loginUser = getLoginUser(request);
			SelectItemManager manager = new SelectItemManager(getLogger());
			
			if (StringUtil.stringToNull(pushConfigId) != null) {
				pushConfigId = URLDecoder.decode(pushConfigId, StandardCharsets.UTF_8.name());
				list = manager.searchPushNoSelectItem(pushConfigId, loginUser);
			}
			
			response = getMessageUtil().manageResult(list);
			
		} catch (Exception e) {
			response = getMessageUtil().manageException(null, e);
		}
		
		return response;
	}

	/**
	 * SQL : usernameAll 
	 * @param request
	 * @param term
	 * @param limit
	 * @param active
	 * @return
	 */
	@GET
	@Path("/usernameAll")
	public Response searchUserAllSelectItem(@Context HttpServletRequest request, @QueryParam("term") String term,
			@QueryParam("limit") int limit) {
		
		getLogger().debug("[searchUserSelectItem]");
		getLogger().debug(LOG_TERM_FORMAT, term);
		getLogger().debug(LOG_LIMIT_FORMAT, limit);
		
		Response response = null;
		List<CommonSelectItem> items = new ArrayList<>();
		try {
			LoginUser loginUser = getLoginUser(request);
			
			
			SelectItemManager manager = new SelectItemManager(getLogger());
			
			if (StringUtil.stringToNull(term) != null) {
				term = URLDecoder.decode(term, StandardCharsets.UTF_8.name());
			}
			
			//  Popup > Autocomplete + Suggestion กด Spacebar 2 ครั้งแล้วต้องไม่แสดงข้อมูลให้เลือก
			if (StringUtil.stringToNull(term) != null) {
				items = manager.searchUserAllSelectItem(term, loginUser);
			}
			
			response = getMessageUtil().manageResult(items);
			
		} catch (Exception e) {
			response = getMessageUtil().manageException(null, e);
		}
		return response;
	}
	
	@GET
	@Path("/nationality")
	public Response searchNationalitySelectItem(@Context HttpServletRequest request, @QueryParam("term") String term, @QueryParam("limit") int limit) 
	{	
		getLogger().debug("[searchNationalitySelectItem]");
		getLogger().debug(LOG_TERM_FORMAT, term);
		getLogger().debug(LOG_LIMIT_FORMAT, limit);
		
		Response response = null;
		List<CommonSelectItem> items = new ArrayList<>();
		try {
			SelectItemManager manager = new SelectItemManager(getLogger());
			
			if (StringUtil.stringToNull(term) != null) {
				term = URLDecoder.decode(term, StandardCharsets.UTF_8.name());
			}
			
			if (StringUtil.stringToNull(term) != null){
				items = manager.searchNationalitySelectItem(term);
			}
			
			response = getMessageUtil().manageResult(items);	
		} 
		catch (Exception e) {
			response = getMessageUtil().manageException(null, e);
		}
		return response;
	}
	
	
	@GET
	@Path("/apoCheckpoint")
	public Response searchDepartmentSelectItem(@Context HttpServletRequest request, @QueryParam("PV_SEQNO") String provinceNo, @QueryParam("AMP_SEQNO") String ampurNo) 
	{
		getLogger().debug("[searchDepartmentSelectItem]");
		getLogger().debug("PV_SEQNO : {}", provinceNo);
		getLogger().debug("AMP_SEQNO : {}", ampurNo);
		
		Response response = null;
		List<CommonSelectItem> items = new ArrayList<>();
		try {
			SelectItemManager manager = new SelectItemManager(getLogger());
			items = manager.searchDepartmentSelectItem(provinceNo, ampurNo);
			response = getMessageUtil().manageResult(items);
		} 
		catch (Exception e) {
			response = getMessageUtil().manageException(null, e);
		}
		return response;
	}
	
}
