package com.sit.abbra.abbraapi.core.reference.domain;

import java.util.ArrayList;
import java.util.List;

import com.sit.common.CommonRequest;

public class ReferenceModel extends CommonRequest {

	private static final long serialVersionUID = 4030278168644594210L;

	private String category;	//รับมาจากหน้าจอ
	
	private List<Reference> listReference = new ArrayList<>();

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Reference> getListReference() {
		return listReference;
	}

	public void setListReference(List<Reference> listReference) {
		this.listReference = listReference;
	}
}
