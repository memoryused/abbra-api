package com.sit.domain;

import java.util.Locale;

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
	
}
