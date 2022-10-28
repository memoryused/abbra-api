package com.sit.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sit.domain.HeaderSorts;

import util.log4j2.DefaultLogUtil;

public class CommonSearchCriteria implements Serializable {

	private static final long serialVersionUID = 694869699333834855L;

	private HeaderSorts[] headerSorts;
	
	private int linePerPage = 10;
	
	private int pageIndex = 0;
	
	private boolean isCheckMaxExceed = true;
	
	public CommonSearchCriteria() {
		// Criteria Class
	}
	
	public HeaderSorts[] getHeaderSorts() {
		return headerSorts;
	}

	public void setHeaderSorts(HeaderSorts[] headerSorts) {
		this.headerSorts = headerSorts;
	}

	public int getLinePerPage() {
		return linePerPage;
	}

	public void setLinePerPage(int linePerPage) {
		this.linePerPage = linePerPage;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public boolean isCheckMaxExceed() {
		return isCheckMaxExceed;
	}

	public void setCheckMaxExceed(boolean isCheckMaxExceed) {
		this.isCheckMaxExceed = isCheckMaxExceed;
	}

	@JsonIgnore
	public String getOrderList() {
		String orderReturn = null;
		try {
			validateHeaderSort();
			
			StringBuilder orderList = new StringBuilder();
			for (HeaderSorts headerSortSelect : getHeaderSorts()) {
				int columnIndex = convertIntValue(headerSortSelect.getColumnName());
				String columnName = initDefaultHeaderSorts()[columnIndex].getColumnName();
				orderList.append(", ");
				orderList.append(columnName);
				orderList.append(" ");
				orderList.append(headerSortSelect.getOrder());
			}
			
			/**
			for (int i = 0; i < initDefaultHeaderSorts().length; i++) {
				String columnName = initDefaultHeaderSorts()[i].getColumnName();
				if (columnName.isEmpty()) {
					// กรณี column name ไม่มี ข้ามไปไม่เอามา order
					continue;
				}
				
				boolean foundOrderBy = false;
				String orderName = getHeaderSorts()[i].getOrder();
				if (HeaderSorts.DESC.equals(orderName)) {
					orderName = HeaderSorts.DESC;
					foundOrderBy = true;
				} else if (HeaderSorts.ASC.equals(orderName)) {
					orderName = HeaderSorts.ASC;
					foundOrderBy = true;
				}
				
				if (foundOrderBy) {
					// ต้องมี DESC or ASC เท่านั้นถึงนำมา Order
					orderList.append(", ");
					orderList.append(columnName);
					orderList.append(" ");
					orderList.append(orderName);
				}
			}
			**/
			
			if (orderList.length() > 0) {
				orderReturn = orderList.toString().substring(1);
			}
		} catch (Exception e) {
			DefaultLogUtil.UTIL.catching(e);
		}
		return orderReturn;
	}
	
	private void validateHeaderSort() {
		if (getHeaderSorts() == null) {
			//กรณ๊ไม่ส่ง header sort ขึ้นมาให้ใช้ default
			setHeaderSorts(createUIHeaderSorts());
		} else {
			// get headerSort ก่อน initDefaultHeaderSorts เนื่องจาก initDefaultHeaderSorts จะเป็นการ new headerSort จะทำให้ข้อมูลที่ได้จาก ui หาย
			HeaderSorts[] sort = getHeaderSorts();
			int columnSize = initDefaultHeaderSorts().length;
			for (HeaderSorts headerSort : sort) {
				int columnIndex = convertIntValue(headerSort.getColumnName());
				if ( (columnIndex < 0) 
						|| (columnIndex >= columnSize) 
						|| invalidOrder(headerSort.getOrder())) {
					
					// ขนาดของ Column ไม่เท่ากัน, พบว่ามี Order ไม่ใช่ ASC DESC 
					setHeaderSorts(createUIHeaderSorts());
					break;
				}
			}
		}
	}
	
	private boolean invalidOrder(String order) {
		return !HeaderSorts.DESC.equals(order) && !HeaderSorts.ASC.equals(order);
	}
	
	private HeaderSorts[] createUIHeaderSorts() {
		HeaderSorts[] uiHeaderSorts = initDefaultHeaderSorts();
		for (int i = 0; i < uiHeaderSorts.length; i++) {
			uiHeaderSorts[i].setColumnName(String.valueOf(i));
		}
		return uiHeaderSorts;
	}
	
	private int convertIntValue(String input) {
		int output = -1;
		try {
			if ((input != null) && !input.trim().isEmpty()) {
				output = Integer.parseInt(input);
			}
		} catch (Exception e) {
			DefaultLogUtil.UTIL.catching(e);
		}
		return output;
	}
	
	public HeaderSorts[] initDefaultHeaderSorts() {
		return new HeaderSorts[] {};
	}
}
