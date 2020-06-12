package com.cheejee.cms.tools;

import java.util.Arrays;

import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import inet.ipaddr.ipv6.IPv6Address;

public class ConvertIP {

	public static byte[] toBinary(String ip) {
		IPAddressString irs = new IPAddressString(ip);
		IPAddress ips = irs.getAddress();

		if (ips.isIPv4())
			ips = ips.toIPv6();

		byte[] ipb = ips.getBytes();

		return ipb;
	}

	public static String toString(byte[] ip) {
		String str = "";
		byte[] bi = Arrays.copyOf(ip, 12);
		byte[] v4 = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1 };
		IPAddress ips = new IPv6Address(ip);

		if (Arrays.equals(bi, v4)) {
			str = ips.toIPv4().toString();
		} else {
			str = ips.toString();
		}
		return str;
	}

	/**
	 * 将不完整的ip地址后面补零，ip必须以符号结尾。 只会按“.”和“：”识别IP地址。 如果ip地址不能被识别 则会返回空字符串。<br>
	 * 
	 * ipv6不能使用省略形式。
	 * 
	 * @param ip
	 * @return 返回补全的ip地址
	 */
	public static String appendIP(String ip) {
		
		if(ip.split("\\.").length == 4 || ip.split(":").length == 8)
			return ip;
		
		String ips = "";
		if (ip.contains("."))
			ips = v4Apperd(ip);

		if (ip.contains(":"))
			ips = v6Apperd(ip);
		return ips;
	}

	private static String v4Apperd(String ip) {
		int a = ip.split("\\.").length;
		StringBuilder sb = new StringBuilder(ip);

		for (int i = 0; i < (4 - a); i++) {
			sb.append("0.");
		}
		return sb.substring(0, sb.length() - 1);
	}

	private static String v6Apperd(String ip) {

		if (ip.contains("::"))
			return "";

		int a = ip.split(":").length;
		StringBuilder sb = new StringBuilder(ip);

		for (int i = 0; i < (8 - a); i++) {
			sb.append("0000:");
		}
		return sb.substring(0, sb.length() - 1);
	}

}
