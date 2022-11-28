package com.sit.abbra.abbraapi.core.selectitem.enums;

public enum GlobalType {
	LOCK_STATUS("LockStatus"),
	ACTIVE_STATUS("ActiveStatus"),
	CONTACT_US("CONTACT_US"),
	MESSAGE_SOURCE("MsgSource"),
	FLIGHT_STATUS("FltStatus"),
	PUSH_STATUS("pushStatus"),
	RESPONSE_STATUS("respStatus"),
	CARRIER_CODE("carrierCode"),
	ASM_STATUS("ASMStat"),
	ANNOUNCE_TYPE("AnnounceType"),
	DEPARTMENT_ANNOUNCE("DepartmentAnnounce"),
	;
	
	private String globalTypeCode;

	private GlobalType(String globalTypeCode) {
		this.globalTypeCode = globalTypeCode;
	}

	public String getGlobalType() {
		return globalTypeCode;
	}
}
