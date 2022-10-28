package com.sit.abbra.abbraapi.core.department.domain;

import com.sit.common.CommonRequest;

public class DepartmentModel extends CommonRequest {

	private static final long serialVersionUID = 7580206425813600793L;

	private String abbrDep;	//รับค่ามาจากหน้าจอ ว่าจะไปที่ Department ไหน
	private Department department = new Department();

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getAbbrDep() {
		return abbrDep;
	}

	public void setAbbrDep(String abbrDep) {
		this.abbrDep = abbrDep;
	}
}
