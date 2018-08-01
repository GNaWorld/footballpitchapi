package com.gn.kit;

import java.util.Arrays;
import java.util.List;

import com.jfinal.kit.PropKit;

public class IAWStringKit {
	/**
	 * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
	 *
	 * @param s
	 *            原文件名
	 * @return 重新编码后的文件�?
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public static List<String> getConfigList(String name) {
		PropKit.use("a_little_config.txt");
		String nameStr = PropKit.get(name).trim();
		String[] nameArr = nameStr.split(",");
		return Arrays.asList(nameArr);
	}
}
