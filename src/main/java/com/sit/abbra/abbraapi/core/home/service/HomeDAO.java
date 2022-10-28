package com.sit.abbra.abbraapi.core.home.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.sit.abbra.abbraapi.common.AbbraCommonDomain;
import com.sit.abbra.abbraapi.core.config.parameter.domain.ParameterConfig;
import com.sit.abbra.abbraapi.core.home.domain.HomeModel;
import com.sit.abbra.abbraapi.core.home.domain.Promotion;
import com.sit.abbra.abbraapi.util.EExtensionApiUtil;
import com.sit.core.common.domain.CommonSQLPath;
import com.sit.core.common.service.CommonDAO;

import util.database.connection.CCTConnection;
import util.database.connection.CCTConnectionUtil;
import util.sql.SQLParameterizedDebugUtil;
import util.sql.SQLParameterizedUtil;
import util.string.StringUtil;

public class HomeDAO extends CommonDAO {

	public HomeDAO(Logger logger, CommonSQLPath sqlPath) {
		super(logger, sqlPath);
	}

	protected List<Promotion> searchPromotion(CCTConnection conn) throws Exception {
		getLogger().debug(" searchPromotion ");
		
		List<Promotion> lstPromotion = new ArrayList<>();
		
		String sql = SQLParameterizedUtil.getSQLString(
	            conn.getSchemas(), 
	            getSqlPath().getClassName(), 
	            getSqlPath().getPath(), 
	            "searchPromotion"
				);

		if(getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debug(sql));
		}

	    PreparedStatement stmt = null;
	    ResultSet rst = null;
	    
	    try {
	    	stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql);
	        rst = stmt.executeQuery();
	        
	        while (rst.next()) {
	        	Promotion pro = new Promotion();
	        	
	        	pro.setSrc(StringUtil.nullToString(rst.getString("File")));
	       
				if(!pro.getSrc().isEmpty()) {
					pro.setThumb(
							EExtensionApiUtil.getThumbnailByScale(
									ParameterConfig.getApplication().getSharePath() + pro.getSrc(), 
									ParameterConfig.getThumbnailConfig().getScale(), 
									ParameterConfig.getThumbnailConfig().isWatermark(), 
									ParameterConfig.getThumbnailConfig().getWatermarkImage())
							);
				}
	        	lstPromotion.add(pro);
			}
	        
	    } finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return lstPromotion;
	}
	
	protected List<AbbraCommonDomain> searchPressRelease(CCTConnection conn) throws Exception {
		getLogger().debug(" searchPressRelease ");
		
		List<AbbraCommonDomain> lstPressRelease = new ArrayList<>();
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"searchPressRelease"
				);
		
		if(getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debug(sql));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql);
			rst = stmt.executeQuery();
			
			while (rst.next()) {
				AbbraCommonDomain release = new AbbraCommonDomain();
				
				release.setSubject(StringUtil.nullToString(rst.getString("Title")));
				release.setDetail(StringUtil.nullToString(rst.getString("Detail")));
				release.setSrc(StringUtil.nullToString(rst.getString("File")));
				
				if(!release.getSrc().isEmpty()) {
					release.setThumb(
							EExtensionApiUtil.getThumbnailByScale(
									ParameterConfig.getApplication().getSharePath() + release.getSrc(), 
									ParameterConfig.getThumbnailConfig().getScale(), 
									ParameterConfig.getThumbnailConfig().isWatermark(), 
									ParameterConfig.getThumbnailConfig().getWatermarkImage())
							);
				}
				lstPressRelease.add(release);
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return lstPressRelease;
	}
	
	protected void searchVdoAndMarquee(CCTConnection conn, HomeModel modelResponse) throws Exception {
		getLogger().debug(" searchVdoAndMarquee ");
		
		String sql = SQLParameterizedUtil.getSQLString(
				conn.getSchemas(), 
				getSqlPath().getClassName(), 
				getSqlPath().getPath(), 
				"searchVdoAndMarquee"
				);
		
		if(getLogger().isDebugEnabled()) {
			getLogger().debug(SQLParameterizedDebugUtil.debug(sql));
		}
		
		PreparedStatement stmt = null;
		ResultSet rst = null;
		
		try {
			stmt = SQLParameterizedUtil.createPrepareStatement(conn.getConn(), sql);
			rst = stmt.executeQuery();
			
			if (rst.next()) {
				modelResponse.setSrcVdo(StringUtil.nullToString(rst.getString("File")));
				modelResponse.setMarquee(StringUtil.nullToString(rst.getString("Detail")));
			}
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
	}
}
