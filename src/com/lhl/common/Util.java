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
	 *            ����key
	 * @return ����ֵ
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
	 *            Ҫ�ж����ַ���
	 * @return �Ƿ�Ϊ��
	 */
	public static boolean isNullOrEmpty(String string) {
		return string.isEmpty() || string == null;
	}
}
