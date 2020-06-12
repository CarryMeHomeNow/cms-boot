package com.cheejee.cms.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密类
 * @author CARRY ME HOME
 * 2019年12月9日下午5:08:21
 */
public class Encryption {

	/**
	 * 使用SHA-256对字符串进行加密
	 * @param str 要加密的字符串
	 * @return 加密过的字符串
	 * @throws NoSuchAlgorithmException 如果SHA-256不受支持则抛出此异常.
	 */
	public static String encipher(String str) throws NoSuchAlgorithmException {

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(str.getBytes());

		return toHexString(encodedhash);
	}

	   public static String toHexString(byte[] byteArray) {
	        final StringBuilder hexString = new StringBuilder("");
	        
	        if (byteArray == null || byteArray.length <= 0) {
	            return null;
	        }
	        
	        for (int i = 0; i < byteArray.length; i++) {
	            int v = byteArray[i] & 0xFF;
	            String hv = Integer.toHexString(v);
	            if (hv.length() < 2) {
	                hexString.append(0);
	            }
	            hexString.append(hv);
	        }
	        
	        return hexString.toString().toLowerCase();
	    }

	
    public static byte[] toByteArray(String hexString) {
    	
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() >> 1];
        
        int index = 0;
        for (int i = 0; i < hexString.length(); i++) {
            if (index  > hexString.length() - 1)
                return byteArray;
            byte highDit = (byte) (Character.digit(hexString.charAt(index), 16) & 0xFF);
            byte lowDit = (byte) (Character.digit(hexString.charAt(index + 1), 16) & 0xFF);
            byteArray[i] = (byte) (highDit << 4 | lowDit);
            index += 2;
        }
        
        return byteArray;
    }

}
