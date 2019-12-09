package com.haozi.nio.utils;

public final class StringUtils {

	private final static char[] HEX_ARR = "0123456789ABCDEF".toCharArray();

	/**
	 * trim a string
	 * 
	 * @param str
	 * @param chars
	 * @return
	 */
	public static String trim(String str, String chars) {
		int len = str.length();
		int st = 0;
		int et = len - 1;
		boolean matched = false;

		while (st < len) {
			matched = false;
			for (int i = 0; i < chars.length(); i++) {
				if (str.charAt(st) == chars.charAt(i)) {
					matched = true;
					break;
				}
			}
			if (!matched) {
				break;
			}
			st++;
		}
		while (et > st) {
			matched = false;
			for (int i = 0; i < chars.length(); i++) {
				if (str.charAt(et) == chars.charAt(i)) {
					matched = true;
					break;
				}
			}
			if (!matched) {
				break;
			}
			et--;
		}
		return str.substring(st, et + 1);
	}

	/**
	 * bytes to hex string
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARR[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARR[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static int HexToInt(byte[] src) {
		return HexToInt(bytesToHexString(src));
	}
	
	public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]) & 0xff);
            
        }
        return d;
    }
	
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

	
	// 16进制转10进制
	public static int HexToInt(String strHex) {
		int nResult = 0;
		if (!IsHex(strHex))
			return nResult;
		String str = strHex.toUpperCase();
		if (str.length() > 2) {
			if (str.charAt(0) == '0' && str.charAt(1) == 'X') {
				str = str.substring(2);
			}
		}
		int nLen = str.length();
		for (int i = 0; i < nLen; ++i) {
			char ch = str.charAt(nLen - i - 1);
			try {
				nResult += (GetHex(ch) * GetPower(16, i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nResult;
	}

	// 计算16进制对应的数值
	public static int GetHex(char ch) throws Exception {
		if (ch >= '0' && ch <= '9')
			return (int) (ch - '0');
		if (ch >= 'a' && ch <= 'f')
			return (int) (ch - 'a' + 10);
		if (ch >= 'A' && ch <= 'F')
			return (int) (ch - 'A' + 10);
		throw new Exception("error param");
	}

	// 计算幂
	public static int GetPower(int nValue, int nCount) throws Exception {
		if (nCount < 0)
			throw new Exception("nCount can't small than 1!");
		if (nCount == 0)
			return 1;
		int nSum = 1;
		for (int i = 0; i < nCount; ++i) {
			nSum = nSum * nValue;
		}
		return nSum;
	}

	// 判断是否是16进制数
	public static boolean IsHex(String strHex) {
		int i = 0;
		if (strHex.length() > 2) {
			if (strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x')) {
				i = 2;
			}
		}
		for (; i < strHex.length(); ++i) {
			char ch = strHex.charAt(i);
			if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'F') || (ch >= 'a' && ch <= 'f'))
				continue;
			return false;
		}
		return true;
	}

	public static void swap(byte a[]) {
		int len = a.length;
		for (int i = 0; i < len / 2; i++) {
			byte tmp = a[i];
			a[i] = a[len - 1 - i];
			a[len - 1 - i] = tmp;
		}
	}

	/**
	 * 获取校验码
	 * 
	 * @param srcBytes
	 * @param random
	 * @return
	 */
	public static byte getCheckSum(byte[] srcBytes, int random) {
		byte[] randomBytes = toLH(random);
		byte randomByte = randomBytes[0];
		for (int i = 0; i < srcBytes.length; i++) {
			randomByte ^= srcBytes[i];
		}
		return randomByte;
	}

	public static byte[] toHH(int n) {
		byte[] b = new byte[2];
		b[1] = (byte) (n & 0xff);
		b[0] = (byte) (n >> 8 & 0xff);
		return b;
	}

	public static byte[] toLH(int n) {
		byte[] b = new byte[2];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		return b;
	}

}