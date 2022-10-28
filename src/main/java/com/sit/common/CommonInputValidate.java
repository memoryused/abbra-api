package com.sit.common;

import java.io.Serializable;

public class CommonInputValidate implements Serializable {

	private static final long serialVersionUID = 9113084120005064830L;

	private String element;
	private String msg;

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
