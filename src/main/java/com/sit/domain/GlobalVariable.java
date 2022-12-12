package com.sit.domain;

import java.util.Locale;

import com.sit.common.CommonSelectItem;

public class GlobalVariable {	
	
	private GlobalVariable() {
		
	}
	
	public static final String ACTIVE = "Y";
	public static final String INACTIVE = "N";
	
	public static final Locale LOCALE_OFFICER = new Locale("th","TH");
	public static final Locale LOCALE_INSERT_LOG = new Locale("en","EN");
	public static final String DELIMITER_OPERATOR = ",";
	public static final String HEADER_USER_AGENT = "user-agent";
	public static final String SPACE = " ";
	public static final String SLASH = "/";
	public static final String DEFAULT_PATH = SLASH +"default";
	public static final String SPLITER_FILE_UPLOAD_KEYWORD = "@24ee;@";
	//16/09/2022 thanat.s 2022EXTVFS-23 : แก้ไข format visaNo
	public static final String VISA_FORMAT = "E{CURRENT_NO}/{RUNNING_YEAR}";
	private static String[] arrLinePerPage = { "10", "20", "30", "40", "50", "60", "70", "80", "90", "100" };
	public static String[] getArrLinePerPage() {
		return arrLinePerPage;
	}
	
	public enum FilterSkipEventLog {
		GOTO("(.*)/goto(.*)"), INIT_SEARCH("(.*)/initSearch(.*)");

		private String value;

		private FilterSkipEventLog(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public enum PreApvStatus {
		WAITING("W"), PREAPPROVED("P"), DISAPPROVE("D"), NOT_IN_CONDITION("N"), REVIEW("R") ;

		private String value;

		private PreApvStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public enum ApvStatus {
		WAIT("W"), APPROVE("A"), DISAPPROVE("D") ;

		private String value;

		private ApvStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public enum SendType {
		APV("APV"), APVPB("APVPB"), PRE("PRE"), PREPB("PREPB");

		private String value;

		private SendType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	
	public enum TypeCheck {
		TYPECHECK_A("A"), TYPECHECK_B("B");

		private String value;

		private TypeCheck(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public enum SendStatus {
		SUCCESS("Y"), FAIL("N");

		private String value;

		private SendStatus(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public enum AnnounceType 
	{
		PROMOTION("1", "Promotion","/promotion"),
		RELEASE("2", "Release", "/release"),
		ACTIVITY("5", "Activity", "/activity"),
		ACTIVITY_VDO("6", "Video Activity", "/activity"),
		POLICIES("11", "Policies","/announcement/policies"),
		DIRECTION("12", "Direction","/announcement/direction"),
		COMPANY_ANNOUNCE("13", "Company Announce","/announcement/companyAnnounce"),
		COMPANY_REGULATIONS("14", "Company Regulations","/announcement/companyRegulations"),
		// DepartmentAnnounce
		ICT_DEP("15", "ICT Department", "/announcement/ictDepartment"),
		HR_ADMIN_SHE_DEP("16", "HR&ADMIN&SHE Department", "/announcement/hr-admin-sheDepartment"),
		FA_AC_DEP("17", "FA&AC Department", "/announcement/fa-acDepartment"),
		CS_DEP("18", "CS Department", "/announcement/CsDepartment"),
		MKT_DEP("19", "MKT Department", "/announcement/mktDepartment"),
		SA_DEP("20", "SA Department", "/announcement/SaDepartment"),
		;
		
		private CommonSelectItem selItem = new CommonSelectItem();
		private String key;
		private String value;
		private String path; 
	    AnnounceType(String key, String value, String path) {
	    	this.selItem.setKey(key);
	    	this.selItem.setValue(value);
	    	this.key = key;
	    	this.value = value;
	    	this.path = path;
	    }
	    public CommonSelectItem getSelectItem() {
			return selItem;
		}
	    public String getKey() {
			return key;
		}
	    public String getValue() {
			return value;
		}
	    public String getPath() {
			return path;
		}
	}

}
