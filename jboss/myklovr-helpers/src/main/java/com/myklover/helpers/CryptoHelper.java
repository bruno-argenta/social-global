package com.myklover.helpers;

import org.apache.commons.codec.digest.Crypt;

public class CryptoHelper {

	private static final String SALT = "myKlovrSalt";
	
	public static String hashString (String string){
		String result = Crypt.crypt(string, SALT);	
		return result;		
	}

	public static String encryptString(String string){
		String result = Crypt.crypt(string, SALT);	
		return result;
	}
	
	public static String decryptString(String string){
		String result = Crypt.crypt(string, SALT);	
		return result;
	}
		
}
