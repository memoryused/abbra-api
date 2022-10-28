package com.sit.abbra.abbraapi.core.security.login.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sit.common.CommonSearch;

public class Operator extends CommonSearch {
	
	private static final long serialVersionUID = -6847211882188419052L;
	
	private long operatorId;
	private long parentId;

	private String visible;
	private String label;
	private String iconName;

	@JsonIgnore
	private String labelEn;

	@JsonIgnore
	private String labelTh;

	private Integer listNo;
	private Integer operatorLevel;
	private String operatorType;

	private String path;

	private String systemName;
	private String menuName;
	private String functionName;
	
	private String operatorNameTH;
	private String operatorNameEN;
	private String parentOperatorNameEN;
	private String parentOperatorNameTH;
	private String groupOperatorNameEN;
	private String groupOperatorNameTH;
	private String active;
	private String leveList;
	private String detail;

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getLeveList() {
		return leveList;
	}

	public void setLeveList(String leveList) {
		this.leveList = leveList;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabelEn() {
		return labelEn;
	}

	public void setLabelEn(String labelEn) {
		this.labelEn = labelEn;
	}

	public String getLabelTh() {
		return labelTh;
	}

	public void setLabelTh(String labelTh) {
		this.labelTh = labelTh;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public Integer getListNo() {
		return listNo;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public Integer getOperatorLevel() {
		return operatorLevel;
	}

	public void setOperatorLevel(Integer operatorLevel) {
		this.operatorLevel = operatorLevel;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getOperatorNameTH() {
		return operatorNameTH;
	}

	public void setOperatorNameTH(String operatorNameTH) {
		this.operatorNameTH = operatorNameTH;
	}

	public String getOperatorNameEN() {
		return operatorNameEN;
	}

	public void setOperatorNameEN(String operatorNameEN) {
		this.operatorNameEN = operatorNameEN;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getParentOperatorNameEN() {
		return parentOperatorNameEN;
	}

	public void setParentOperatorNameEN(String parentOperatorNameEN) {
		this.parentOperatorNameEN = parentOperatorNameEN;
	}

	public String getParentOperatorNameTH() {
		return parentOperatorNameTH;
	}

	public void setParentOperatorNameTH(String parentOperatorNameTH) {
		this.parentOperatorNameTH = parentOperatorNameTH;
	}

	public String getGroupOperatorNameEN() {
		return groupOperatorNameEN;
	}

	public void setGroupOperatorNameEN(String groupOperatorNameEN) {
		this.groupOperatorNameEN = groupOperatorNameEN;
	}

	public String getGroupOperatorNameTH() {
		return groupOperatorNameTH;
	}

	public void setGroupOperatorNameTH(String groupOperatorNameTH) {
		this.groupOperatorNameTH = groupOperatorNameTH;
	}
		
}
