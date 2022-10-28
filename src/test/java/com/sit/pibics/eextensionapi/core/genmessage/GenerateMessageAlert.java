package com.sit.pibics.eextensionapi.core.genmessage;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.sit.pibics.eextensionapi.TestMaster;

public class GenerateMessageAlert extends TestMaster {

	public static void main(String[] args) {

		try {
			GenerateMessageAlert gen = new GenerateMessageAlert();
//			gen.genMessage();
			gen.genMessageAngular();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String convert(String str) {

		StringBuffer ostr = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);

			// Does the char need to be converted to unicode?
			if ((ch >= 0x0020) && (ch <= 0x007e)) {
				ostr.append(ch); // No.
			} else {// Yes.
				ostr.append("\\u"); // standard unicode format.

				// Get hex value of the char.
				String hex = Integer.toHexString(str.charAt(i) & 0xFFFF);

				for (int j = 0; j < 4 - hex.length(); j++)
					// Prepend zeros because unicode requires 4 digits
					ostr.append("0");

				ostr.append(hex.toLowerCase()); // standard unicode format.
			}
		}

		return (new String(ostr));
	}

	public void genMessage() throws Exception {
		String pathEN = "src/main/resources/resources/bundle/common/MessageAlert_en.properties";
		String pathTH = "src/main/resources/resources/bundle/common/MessageAlert_th.properties";

		Connection conn = null;
		try {
			
			conn = getConnection();

			String sql = "SELECT MESSAGE_CODE, MESSAGE_DESCRIPTION , MESSAGE_DESC_TH FROM CON_MESSAGE ORDER BY MESSAGE_CODE";

			List<String> messageEN = new ArrayList<String>();
			List<String> messageTH = new ArrayList<String>();

			Statement stmt = null;
			ResultSet rst = null;
			try {
				stmt = conn.createStatement();
				rst = stmt.executeQuery(sql);
				while (rst.next()) {
					messageEN.add(convert(rst.getString("MESSAGE_CODE") + "=" + (rst.getString("MESSAGE_DESCRIPTION") != null ? rst.getString("MESSAGE_DESCRIPTION") : "")));
					messageTH.add(convert(rst.getString("MESSAGE_CODE") + "=" + (rst.getString("MESSAGE_DESC_TH") != null ? rst.getString("MESSAGE_DESC_TH") : "")));
				}

				// Gen file MessageAlert
				writeLines(new java.io.File(pathEN), messageEN, false);
				writeLines(new java.io.File(pathTH), messageTH, false);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				close(rst);
				close(stmt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
	}
	
	public void genMessageAngular() throws Exception {
		String pathEN = "src/main/resources/resources/bundle/common/en.json";
		String pathTH = "src/main/resources/resources/bundle/common/th.json";
		
		Connection conn = null;
		try {
			conn = getConnection();
			
			String sql = "SELECT MESSAGE_CODE, MESSAGE_DESCRIPTION , MESSAGE_DESC_TH FROM CON_MESSAGE ORDER BY MESSAGE_CODE";
			
			System.out.println(sql);
			
			List<String> messageEn = new ArrayList<String>();
			List<String> messageTh = new ArrayList<String>();
			
			Statement stmt = null;
			ResultSet rst = null;
			try {
				stmt = conn.createStatement();
				rst = stmt.executeQuery(sql);
				messageEn.add("{");
				messageTh.add("{");
				while (rst.next()) {
					messageEn.add("\t\"" + rst.getString("MESSAGE_CODE") + "\": \"" + (rst.getString("MESSAGE_DESCRIPTION") != null ? rst.getString("MESSAGE_DESCRIPTION").replaceAll("\"", "'") + "\"," : "\"\","));
					messageTh.add("\t\"" + rst.getString("MESSAGE_CODE") + "\": \"" + (rst.getString("MESSAGE_DESC_TH") != null ? rst.getString("MESSAGE_DESC_TH").replaceAll("\"", "'") + "\"," : "\"\","));
				}
				messageEn.add("}");
				messageTh.add("}");
				
				// Gen file MessageAlert
				writeLines(new java.io.File(pathEN), messageEn, false);
				writeLines(new java.io.File(pathTH), messageTh, false);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				close(rst);
				close(stmt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
	}

	public static void writeLines(File file, List<String> lines, boolean append) throws Exception {
		try {
			if (append == true) {
				FileUtils.writeLines(file, lines, append);
			} else {
				FileUtils.writeLines(file, lines);
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
