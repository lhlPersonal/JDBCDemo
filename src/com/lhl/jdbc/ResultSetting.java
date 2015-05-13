/**
 * @author bulusli
 * @description get dynamic query result.Note:interface with single method can be instanced by lamda expression.
 * @date 2015-05-11 
 */
package com.lhl.jdbc;

import java.sql.ResultSet;

//���ҽ���һ���������κνӿڶ����Ա��Ϊ����ʽ�ӿڣ�ʹ��lamda���ʽʵ����
@FunctionalInterface
public interface ResultSetting {
	Object getResultSet(ResultSet rs);
}
