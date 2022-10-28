package com.sit.abbra.abbraapi.core.employee.domain;

import com.sit.common.CommonRequest;

public class EmployeeModel extends CommonRequest {

	private static final long serialVersionUID = -4095924152604937797L;

	private String query;	// ค้นหาตามชื่อเล่น / ชื่อจริง / รหัสพนักงาน
	private String img;
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	} 
}
