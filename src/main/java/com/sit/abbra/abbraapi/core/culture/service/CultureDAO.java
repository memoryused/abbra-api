package com.sit.abbra.abbraapi.core.culture.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.common.AbbraCommonDomain;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.culture.domain.CultureModel;
import com.sit.abbra.abbraapi.core.home.domain.Promotion;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.core.common.domain.CommonSQLPath;
import com.sit.core.common.service.CommonDAO;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.sql.SQLParameterizedDebugUtil;
import util.sql.SQLParameterizedUtil;
import util.string.StringUtil;

public class CultureDAO extends CommonDAO {

	public static final String TITLE = "Title";
	public static final String DETAIL = "Detail";
	public static final String FILE = "File";
	
	public CultureDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	/**
	 * searchCulture
	 * @param conn
	 * @param announceType
	 * @param culture
	 * @throws Exception
	 */
	protected void searchCulture(CCTConnection conn, String announceType, CultureModel culture) throws Exception {
		getLogger().debug(" searchCulture ");
		
		List<Promotion> lstPromotion = new ArrayList<>();
		List<AbbraCommonDomain> lstPressRelease = new ArrayList<>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = StringUtil.stringToNull(announceType);
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"searchCulture",
				params
				);
		
		if(getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debugReplaceParameter(sql, params));
		}

	    PreparedStatement stmt = null;
	    ResultSet rst = null;
	    
	    try {
	    	stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql, params);
	        rst = stmt.executeQuery();
	        
	        while (rst.next()) {
	        	Promotion pro = new Promotion();
	        	AbbraCommonDomain release= new AbbraCommonDomain();
	        	
	        	pro.setSrc(StringUtil.nullToString(rst.getString(FILE)));
	       
				if(!pro.getSrc().isEmpty()) {
					
					pro.setThumb(
							EExtensionApiUtil.getThumbnailByScale(
									ParameterConfig.getApplication().getSharePath() + pro.getSrc(), 
									ParameterConfig.getThumbnailConfig().getScale(), 
									ParameterConfig.getThumbnailConfig().isWatermark(), 
									ParameterConfig.getThumbnailConfig().getWatermarkImage())
							);
					
					pro.setSrc(EExtensionApiUtil.convertBase64(ParameterConfig.getApplication().getSharePath() + pro.getSrc()));
					
					release.setSrc(pro.getSrc());
					release.setThumb(pro.getThumb());
				}
				
				release.setSubject(StringUtil.nullToString(rst.getString(TITLE)));
				release.setDetail(StringUtil.nullToString(rst.getString(DETAIL)));
				// set list
	        	lstPromotion.add(pro);
	        	lstPressRelease.add(release);
			}
	        
	        culture.setLstPromotion(lstPromotion);
	        culture.setListPressRelease(lstPressRelease);
	        
	    } finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

	}

}
