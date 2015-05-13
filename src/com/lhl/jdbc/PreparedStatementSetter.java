/**
 * @author bulusli
 * @description interface as method reference.
 * @date 2015-05-11
 */
package com.lhl.jdbc;

import java.sql.PreparedStatement;

import com.sun.accessibility.internal.resources.accessibility;

@FunctionalInterface
public interface PreparedStatementSetter {
	/**
	 * dynamic setting preparedStatement paras.
	 * 
	 * @param ps
	 */
	void setPreparedStatement(PreparedStatement ps);
}
