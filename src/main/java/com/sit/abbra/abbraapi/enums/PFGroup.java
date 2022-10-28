package com.sit.abbra.abbraapi.enums;

import java.util.EnumSet;
import java.util.Set;

public enum PFGroup {
	
	PRE_APPROVE(EnumSet.of(PFOperator.PRE_APPROVE_ADD, PFOperator.PRE_APPROVE_EDIT, PFOperator.PRE_APPROVE_SEARCH, PFOperator.PRE_APPROVE_VIEW, PFOperator.PRE_APPROVE_EXPORT))	
	, APPROVE(EnumSet.of(PFOperator.APPROVE_ADD, PFOperator.APPROVE_EDIT, PFOperator.APPROVE_SEARCH, PFOperator.APPROVE_VIEW, PFOperator.APPROVE_EXPORT))
	;

	
	
	private EnumSet<PFOperator> lstOperator;
	
	private PFGroup(EnumSet<PFOperator> lstOperator) {
		this.lstOperator = lstOperator;
	}

	public Set<PFOperator> getLstOperator() {
		return lstOperator;
	}

}
