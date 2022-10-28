package unittest;

import util.tranp.TranpUtil;

public class TestVariable {
	public static final String REST_URI = "https://tm47.somapait.com/e-extension-api-dev-temp/";
	public static final String REST_URI_PATTERN = "rest/v1";
	
	public static final String CLIENT_RESPONSE_TYPE = "code";
	public static final String CLIENT_URI_MAIN = "https://tm47.somapait.com/e-extension-api-dev-temp/#/home";
	public static final String CLIENT_HEADERS_ORIGIN = "https://tm47.somapait.com";
	public static final String CLIENT_HEADERS_HOST = "tm47.somapait.com";
	
	public static final String HEADER_ORIGIN = "Origin";
	
	public static final String CLIENT_ID = "EXTVFS866FB3025131";
	public static final String USERNAME1 = "data2.it";
	public static final String PASSWORD1 = "123456";
	
	public static final String USERNAME2 = "sittipol.m";
	public static final String PASSWORD2 = "password$1";
	
	public static final String USERNAME3 = "sittipol.m";
	public static final String PASSWORD3 = "password$1";
	
	public static final String DATABASE_URI = "jdbc:mariadb://10.100.70.73:3306/EXT_VFS";
	public static final String DATABASE_USERNAME = "EXT_VFS_WEB";
	public static final String DATABASE_PASSWORD = initDBPassword();
	
	public static final String PATH_SECURITY_UTIL_CONFIG = "com/sit/pibics/eextensionapi/util/security/security_util_config.properties";
	public static final String PATH_PARAMTETER_FILE = "src/main/webapp/WEB-INF/parameter.xml";
	public static final String PATH_SECURE_FILE = "src/main/webapp/WEB-INF/secure.properties";
	public static final String PATH_LDAP_FILE = "src/main/webapp/WEB-INF/ldap.properties";
	
	private static final String initDBPassword() {
		String result = "";
		try {
			result = TranpUtil.doTranp("BPSxaGgkMW/6tz3Hmw7iR9kFel3Za3rgknv7B3D1Uf4=");
//			result = TranpUtil.doTranp("InNkYZZK7oOgonj309IeYxdGR1aXBV3opkkIXjsaf9s=");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
