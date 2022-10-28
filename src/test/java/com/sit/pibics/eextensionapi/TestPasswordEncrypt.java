package com.sit.pibics.eextensionapi;

import util.cryptography.EncryptionUtil;
import util.cryptography.enums.EncType;
import util.tranp.TranpUtil;

public class TestPasswordEncrypt {

	public static void main(String[] args) {
		try {
			String secret = "LcD1BVXT5Y0bhVXPekp+2iXPVLgVoMefrjO4G3V/mKQ=";
			String pass = "scienceSPAINcloud04";
			System.out.println(secret + " : " + TranpUtil.doBefore(secret));
			System.out.println(pass + " : " + TranpUtil.doBefore(pass));
			

			System.out.println("DB DEV : " + EncryptionUtil.doEncrypt("scienceSPAINcloud04", "52:54:00:34:a0:f6", EncType.AES256));
			System.out.println("DB PRE : " + EncryptionUtil.doEncrypt("laughBORNcomplete27", "52:54:00:6d:79:08", EncType.AES256));
			System.out.println("DB PRD1 : " + EncryptionUtil.doEncrypt("plantFIRSTdried47", "52:54:00:fe:ee:12", EncType.AES256));
			System.out.println("DB PRD2 : " + EncryptionUtil.doEncrypt("plantFIRSTdried47", "52:54:00:96:3e:3e", EncType.AES256));
			System.out.println("SECRET : " + EncryptionUtil.doEncrypt(secret, "52:54:00:34:a0:f6", EncType.AES256));
			System.out.println("SECRET PRD1 << : " + EncryptionUtil.doEncrypt("LcD1BVXT5Y0bhVXPekp+2iXPVLgVoMefrjO4G3V/mKQ=", "52:54:00:fe:ee:12", EncType.AES256));
			System.out.println("SECRET PRD2 << : " + EncryptionUtil.doEncrypt("LcD1BVXT5Y0bhVXPekp+2iXPVLgVoMefrjO4G3V/mKQ=", "52:54:00:96:3e:3e", EncType.AES256));
			//System.out.println("SECRET << : " + DecryptionUtil.doDecrypt("fK1iPBY9chv8aYEoHPc/d1xh4Z00F9XZT3MgP5KFsOdnBNoxhBMTgqSafCU7FhzP", "52:54:00:6d:79:08", DecType.AES256));
			//System.out.println("SECRET << : " + DecryptionUtil.doDecrypt("YFGVMlflyJuwxoJwPVkRwZPjv/LYBYhiryEPUNRzOrQOcEEhNG2ieyhGxwilV4bo", "52:54:00:6d:79:08", DecType.AES256));
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
