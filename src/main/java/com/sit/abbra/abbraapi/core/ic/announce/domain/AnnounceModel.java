package com.sit.abbra.abbraapi.core.ic.announce.domain;

import java.util.ArrayList;
import java.util.List;

import com.sit.abbra.abbraapi.common.AbbraCommonDomain;
import com.sit.common.CommonRequest;

public class AnnounceModel extends CommonRequest {

	private static final long serialVersionUID = -4554699788372871364L;

	private List<AbbraCommonDomain> listAnnounce = new ArrayList<>();

	public List<AbbraCommonDomain> getListAnnounce() {
		return listAnnounce;
	}

	public void setListAnnounce(List<AbbraCommonDomain> listAnnounce) {
		this.listAnnounce = listAnnounce;
	}
}
