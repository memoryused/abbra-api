package com.sit.abbra.abbraapi.core.admin.domain;

import java.util.ArrayList;
import java.util.List;

import com.sit.common.CommonRequest;
import com.sit.common.CommonSelectItem;

public class AnnounceModel extends CommonRequest {

	private static final long serialVersionUID = -8441651483749017575L;
	
	// SEARCH
	private AnnounceSearchCriteria criteria = new AnnounceSearchCriteria();
	
	// ADD/EDIT
	private Announce announce = new Announce();
	
	// Combo AnnounceType
	private List<CommonSelectItem> lstAnnounceType = new ArrayList<>();
	// Combo Department
	private List<CommonSelectItem> lstDepAnnounceType = new ArrayList<>();
	// Combo Status
	private List<CommonSelectItem> lstStatus = new ArrayList<>();

	public AnnounceSearchCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(AnnounceSearchCriteria criteria) {
		this.criteria = criteria;
	}

	public List<CommonSelectItem> getLstAnnounceType() {
		return lstAnnounceType;
	}

	public void setLstAnnounceType(List<CommonSelectItem> lstAnnounceType) {
		this.lstAnnounceType = lstAnnounceType;
	}

	public Announce getAnnounce() {
		return announce;
	}

	public void setAnnounce(Announce announce) {
		this.announce = announce;
	}

	public List<CommonSelectItem> getLstDepAnnounceType() {
		return lstDepAnnounceType;
	}

	public void setLstDepAnnounceType(List<CommonSelectItem> lstDepAnnounceType) {
		this.lstDepAnnounceType = lstDepAnnounceType;
	}

	public List<CommonSelectItem> getLstStatus() {
		return lstStatus;
	}

	public void setLstStatus(List<CommonSelectItem> lstStatus) {
		this.lstStatus = lstStatus;
	}

}
