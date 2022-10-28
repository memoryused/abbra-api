package com.sit.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommonSearchResult<T extends CommonSearch> implements Serializable {
	
	private static final long serialVersionUID = 7489256514364665851L;

	private long totalResult;
	private List<T> listResult = new ArrayList<>();

	public long getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(long totalResult) {
		this.totalResult = totalResult;
	}

	public List<T> getListResult() {
		return listResult;
	}

	public void setListResult(List<T> listResult) {
		this.listResult = listResult;
	}
}
