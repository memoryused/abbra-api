package com.sit.common;

import java.io.Serializable;
import java.util.List;

public class CommonResponse implements Serializable {

	private static final long serialVersionUID = -5167469020561219477L;

	private String messageCode; // message code
	private String messageDesc; // message description
	private String displayStatus; // success, warn, info, error
	private String componentType; // alert, confirm, snackbar
	private transient Object data; // pr ocess result
	private CommonError error; // error
	private List<CommonInputValidate> invalid;

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessageDesc() {
		return messageDesc;
	}

	public void setMessageDesc(String messageDesc) {
		this.messageDesc = messageDesc;
	}

	public String getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public CommonError getError() {
		return error;
	}

	public void setError(CommonError error) {
		this.error = error;
	}

	public List<CommonInputValidate> getInvalid() {
		return invalid;
	}

	public void setInvalid(List<CommonInputValidate> invalid) {
		this.invalid = invalid;
	}

}