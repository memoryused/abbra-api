package com.sit.abbra.abbraapi.util.token.service;

import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.enums.MessageCode;
import com.sit.abbra.abbraapi.util.rest.RestClientUtil;
import com.sit.abbra.abbraapi.util.validate.InputValidateUtil;
import com.sit.common.CommonResponse;
import com.sit.core.common.service.CommonManager;
import com.sit.exception.CustomException;

import util.database.connection.CCTConnection;

public class TokenManager extends CommonManager{
	
	private TokenService service = null;
	private static final String INVALID_DATA = "30014";// Invalid Data - แสดง Alert 30014 [ข้อมูลไม่ถูกต้อง] 

	public TokenManager(Logger logger) {
		super(logger);
		this.service = new TokenService(getLogger());
	}
	
	public String searchTokenAuthor(CCTConnection conn, String clientId, String secretId) throws Exception {
		return service.searchTokenAuthorize(conn, clientId, secretId);
	}
	
	public CommonResponse getTokenAuthWS(Map<String, String> headerMap) throws CustomException {
		CommonResponse resp = null;
		try {
			resp = (CommonResponse) RestClientUtil
					.callServicePost(ParameterConfig.getAuthWebService().getUrl()
							, ParameterConfig.getAuthWebService().getEndPointRequestToken()
							, ParameterConfig.getAuthWebService().isSecure()
							, ParameterConfig.getAuthWebService().getTimeout()
							, null
							, CommonResponse.class
							, headerMap
							, ParameterConfig.getAuthWebService().getRound());
		} catch (Exception e) {
			throw new CustomException("30015", e); //แสดง Alert 30015 [Service Timeout.] 
		}
		
		return resp;
	}
	
	public void checkReponseToken(CommonResponse respToken) throws CustomException {
		getLogger().debug("--checkReponseToken--");
		
		// Check : Null
		if(InputValidateUtil.isNull(respToken)) {
			getLogger().error("Response = Null!!!");
			throw new CustomException(INVALID_DATA);
			
		}
		
		// Check : MessageCode
		getLogger().debug("[{}]", respToken.getMessageDesc());
		if(!MessageCode.SUCCESS.getValue().equals(respToken.getMessageCode())) {
			if(respToken.getError() != null) {
				getLogger().error("--> Error : [{}]", respToken.getError().getErrorDesc());
			}
			
			throw new CustomException("30020");//แสดง Alert 30020 [Unauthorization.] 
		}
		
		// Check : There isn't The Token.
		if(!InputValidateUtil.hasValue(respToken.getData())) {
			getLogger().error("Token = Null!!!");
			throw new CustomException(INVALID_DATA);
			
		}
	}
	

}
