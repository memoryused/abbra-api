package com.sit.abbra.abbraapi.core.attachment.domain;

public class Attachment {
	private String rep;		//code อ้างอิงที่อยู่ attach file
	private String operator;//code อ้างอิง Operator enum ที่ PFCode เพื่อใช่ตรวจสอบสิทธิ์

	public String getRep() {
		return rep;
	}

	public void setRep(String rep) {
		this.rep = rep;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
