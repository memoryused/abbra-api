package com.sit.abbra.abbraapi.util.token;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.util.token.service.TokenManager;
import com.sit.common.CommonResponse;

import util.database.connection.CCTConnection;
import util.tranp.TranpUtil;

public class TokenUtil {
	
	private TokenUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static String getToken(CCTConnection conn, Logger logger, boolean checkDB) throws Exception {
		String token = "";
		
		String clientId = ParameterConfig.getParameter().getClientRegenerate().getClientId();
		//String secret = TranpUtil.doTranp(ParameterConfig.getParameter().getClientRegenerate().getSecret());
		String secret = ParameterConfig.getParameter().getClientRegenerate().getSecret();
		try {
			TokenManager manager = new TokenManager(logger);
			if(checkDB) {
				token = manager.searchTokenAuthor(conn, clientId, secret);
			}
			
			if(token.isEmpty()) {
				
				// Generate Authorization
				String colon = ":"; // สำหรับค่อ string
				StringBuilder sbAuth = new StringBuilder();
				sbAuth.append(clientId);//clientId
				sbAuth.append(colon);
				sbAuth.append(secret);//secret
				sbAuth.append(colon);
				sbAuth.append("client_credentials");//grant_type
				// encode by base64
				String encode = Base64.getEncoder().encodeToString(sbAuth.toString().getBytes());
				
				// Create Header: Authorization
				Map<String, String> headerAuthMap = new HashMap<>();
				headerAuthMap.put("Authorization", encode);
				
				logger.debug("-> getToken");
				CommonResponse respToken = manager.getTokenAuthWS(headerAuthMap);
				manager.checkReponseToken(respToken);
				
				token = respToken.getData().toString();
			}
			
			return token;
		}catch(Exception e) { 
			logger.error("",e);
			throw e;
		}

	}

}
