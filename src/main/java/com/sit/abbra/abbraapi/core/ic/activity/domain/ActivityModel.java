package com.sit.abbra.abbraapi.core.ic.activity.domain;

import java.util.ArrayList;
import java.util.List;

import com.sit.abbra.abbraapi.common.AbbraCommonDomain;
import com.sit.common.CommonRequest;

public class ActivityModel extends CommonRequest{

	private static final long serialVersionUID = -4554699788372871364L;

	private List<AbbraCommonDomain> listActivity = new ArrayList<>();
	private List<AbbraCommonDomain> listVdoActivity = new ArrayList<>();

	public List<AbbraCommonDomain> getListActivity() {
		return listActivity;
	}

	public void setListActivity(List<AbbraCommonDomain> listActivity) {
		this.listActivity = listActivity;
	}

	public List<AbbraCommonDomain> getListVdoActivity() {
		return listVdoActivity;
	}

	public void setListVdoActivity(List<AbbraCommonDomain> listVdoActivity) {
		this.listVdoActivity = listVdoActivity;
	}
}
