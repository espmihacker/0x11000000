package com.tencent.dog.YZ;
import java.io.*;
import java.math.*;
public class BaseUtil
{
	private static String base64Table = EasyConfig.PASSLIST;
	private static String add = "=";

	
	
	public static String hexStringToString(String s) {
		if (s == null || s.equals("")) {
			return null;
		}
		s = s.replace(" ", "");
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "UTF-8");
			new String();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}
	

	public static String encode(String str, String charsetName) {
		StringBuilder base64Str = new StringBuilder();
		byte[] bytesStr;
		try {
			bytesStr = str.getBytes(charsetName);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		String bytesBinary = binary(bytesStr, 2);
		int addCount = 0;
		while (bytesBinary.length() % 24 != 0) {
			bytesBinary += "0";
			addCount++;
		}
		for (int i = 0; i <= bytesBinary.length() - 6; i += 6) {

			int index = Integer.parseInt(bytesBinary.substring(i, i + 6), 2);

			if (index == 0 && i >= bytesBinary.length() - addCount) {
				base64Str.append(add);
			} else {
				base64Str.append(base64Table.charAt(index));
			}
		}
		return base64Str.toString();
	}


	public static String decode(String base64str, String charsetName) {
		String base64Binarys = "";
		for (int i = 0; i < base64str.length(); i++) {
			char s = base64str.charAt(i);
			if (s != '=') {

				String binary = Integer.toBinaryString(base64Table.indexOf(s));

				while (binary.length() != 6) {
					binary = "0" + binary;
				}
				base64Binarys += binary;
			}
		}

		base64Binarys = base64Binarys.substring(0, base64Binarys.length() - base64Binarys.length() % 8);
		byte[] bytesStr = new byte[base64Binarys.length() / 8];
		for (int bytesIndex = 0; bytesIndex < base64Binarys.length() / 8; bytesIndex++) {

			bytesStr[bytesIndex] = (byte) Integer.parseInt(base64Binarys.substring(bytesIndex * 8, bytesIndex * 8 + 8), 2);
		}
		try {
			return new String(bytesStr, charsetName);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String binary(byte[] bytes, int radix) {

		String strBytes = new BigInteger(1, bytes).toString(radix);
		while (strBytes.length() % 8 != 0) {
			strBytes = "0" + strBytes;
		}
		return strBytes;
	}
	
}
