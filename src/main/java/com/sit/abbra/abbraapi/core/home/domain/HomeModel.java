package com.sit.abbra.abbraapi.core.home.domain;

import java.util.ArrayList;
import java.util.List;

import com.sit.abbra.abbraapi.common.AbbraCommonDomain;
import com.sit.common.CommonRequest;

public class HomeModel extends CommonRequest {

	private static final long serialVersionUID = -1105997307813382412L;
	
	private String marquee;
	private String srcVdo;
	private List<Promotion> lstPromotion = new ArrayList<>();
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

	public String getMarquee() {
		return marquee;
	}

	public void setMarquee(String marquee) {
		this.marquee = marquee;
	}

	public String getSrcVdo() {
		return srcVdo;
	}

	public void setSrcVdo(String srcVdo) {
		this.srcVdo = srcVdo;
	}

}
