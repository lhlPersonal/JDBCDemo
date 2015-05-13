/**
 * @author bulusli
 * @description get dynamic query result.Note:interface with single method can be instanced by lamda expression.
 * @date 2015-05-11 
 */
package com.lhl.jdbc;

import java.sql.ResultSet;

//有且仅有一个方法的任何接口都可以标记为函数式接口，使用lamda表达式实例化
@FunctionalInterface
public interface ResultSetting {
	Object getResultSet(ResultSet rs);
}
