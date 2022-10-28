package com.sit.domain;

import java.io.Serializable;

/**
 * class สำหรับเก็บสิทธิ์การใช้งานของแต่ระบบ ใช้ในการส่งให้ client
 * นำไปคุมสิทธิ์การเข้าใช้งานปุ่ม และหน้าต่างๆ
 * 
 */
public class Permission implements Serializable {

	private static final long serialVersionUID = -4173695711497286999L;
	
	private Boolean isSearch;
	private Boolean isAdd;
	private Boolean isEdit;
	private Boolean isView;
	private Boolean isDelete;
	private Boolean isExport;
	private Boolean isViewPNRGOV;
	private Boolean isViewRecLocator;
	private Boolean isRegenerate;
	private Boolean isRetransmit;
	
	public Boolean isSearch() {
		return isSearch;
	}

	public void setSearch(Boolean isSearch) {
		this.isSearch = isSearch;
	}

	public Boolean isAdd() {
		return isAdd;
	}

	public void setAdd(Boolean isAdd) {
		this.isAdd = isAdd;
	}

	public Boolean isEdit() {
		return isEdit;
	}

	public void setEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}

	public Boolean isView() {
		return isView;
	}

	public void setView(Boolean isView) {
		this.isView = isView;
	}

	public Boolean isDelete() {
		return isDelete;
	}

	public void setDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean isExport() {
		return isExport;
	}

	public void setExport(Boolean isExport) {
		this.isExport = isExport;
	}

	public Boolean isViewPNRGOV() {
		return isViewPNRGOV;
	}

	public void setViewPNRGOV(Boolean isViewPNRGOV) {
		this.isViewPNRGOV = isViewPNRGOV;
	}

	public Boolean isViewRecLocator() {
		return isViewRecLocator;
	}

	public void setViewRecLocator(Boolean isViewRecLocator) {
		this.isViewRecLocator = isViewRecLocator;
	}

	public Boolean isRegenerate() {
		return isRegenerate;
	}

	public void setRegenerate(Boolean isRegenerate) {
		this.isRegenerate = isRegenerate;
	}

	public Boolean isRetransmit() {
		return isRetransmit;
	}

	public void setRetransmit(Boolean isRetransmit) {
		this.isRetransmit = isRetransmit;
	}
}
