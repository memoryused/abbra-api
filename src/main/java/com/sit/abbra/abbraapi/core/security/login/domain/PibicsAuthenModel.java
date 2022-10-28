package com.sit.abbra.abbraapi.core.security.login.domain;

import java.io.Serializable;

public class PibicsAuthenModel implements Serializable {

	private static final long serialVersionUID = 8992946844044616075L;
	
	private String userId;
	private String rankNm;
	private String name;
	private String surname;
	private String sex;
	private Long deptSeqNo;
	private Long pvSeqNo;
	private String deptAbbFmt1;
	private String deptAbbFmt3;
	private String deptTnm;
	private String positionName;
	private long checkedAuthen; // จำนวน user ที่ count (กรณี authPB='Y')

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRankNm() {
		return rankNm;
	}

	public void setRankNm(String rankNm) {
		this.rankNm = rankNm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Long getDeptSeqNo() {
		return deptSeqNo;
	}

	public void setDeptSeqNo(Long deptSeqNo) {
		this.deptSeqNo = deptSeqNo;
	}

	public Long getPvSeqNo() {
		return pvSeqNo;
	}

	public void setPvSeqNo(Long pvSeqNo) {
		this.pvSeqNo = pvSeqNo;
	}

	public String getDeptAbbFmt1() {
		return deptAbbFmt1;
	}

	public void setDeptAbbFmt1(String deptAbbFmt1) {
		this.deptAbbFmt1 = deptAbbFmt1;
	}

	public String getDeptAbbFmt3() {
		return deptAbbFmt3;
	}

	public void setDeptAbbFmt3(String deptAbbFmt3) {
		this.deptAbbFmt3 = deptAbbFmt3;
	}

	public String getDeptTnm() {
		return deptTnm;
	}

	public void setDeptTnm(String deptTnm) {
		this.deptTnm = deptTnm;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public long getCheckedAuthen() {
		return checkedAuthen;
	}

	public void setCheckedAuthen(long checkedAuthen) {
		this.checkedAuthen = checkedAuthen;
	}

}
