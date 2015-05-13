/**
 * @author bulusli
 * @description common method
 * @date 2015-05-06
 */
package com.lhl.common;

import java.io.FileInputStream;
import java.util.Properties;

public class Util {
	/**
	 * 
	 * @param key
	 *            属性key
	 * @return 属性值
	 */
	public static String getValue(String key) {
		if (!isNullOrEmpty(key)) {
			try {
				Properties properties = new Properties();
				properties.load(new FileInputStream("src/conn.properties"));

				return properties.getProperty(key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param string
	 *            要判定的字符串
	 * @return 是否为空
	 */
	public static boolean isNullOrEmpty(String string) {
		return string.isEmpty() || string == null;
	}
}
