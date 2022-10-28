package com.sit.common;

public class CommonSelectItemObject<T> extends CommonSelectItem {

	private static final long serialVersionUID = -8436984043244105009L;

	private transient T object;

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
	
}