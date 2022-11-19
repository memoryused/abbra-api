package com.sit.abbra.abbraapi.core.culture.domain;

import java.util.ArrayList;
import java.util.List;

import com.sit.abbra.abbraapi.common.AbbraCommonDomain;
import com.sit.abbra.abbraapi.core.home.domain.Promotion;
import com.sit.common.CommonRequest;

public class CultureModel extends CommonRequest {

	private static final long serialVersionUID = -8672836106355014562L;
	
	private String announceType;//รับมาจากหน้าจอ
	private List<Promotion> lstPromotion = new ArrayList<>();// Slide show
	private List<AbbraCommonDomain> listPressRelease = new ArrayList<>();

	public List<Promotion> getLstPromotion() {
		return lstPromotion;
	}

	public void setLstPromotion(List<Promotion> lstPromotion) {
		this.lstPromotion = lstPromotion;
	}

	public List<AbbraCommonDomain> getListPressRelease() {
		return listPressRelease;
	}

	public void setListPressRelease(List<AbbraCommonDomain> listPressRelease) {
		this.listPressRelease = listPressRelease;
	}

	public String getAnnounceType() {
		return announceType;
	}

	public void setAnnounceType(String announceType) {
		this.announceType = announceType;
	}


}
