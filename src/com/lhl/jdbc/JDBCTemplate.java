/**
 * @author bulusli
 * @description jdbc template
 * @date 2015-05-11
 */
package com.lhl.jdbc;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import com.lhl.bean.Employee;
import com.lhl.common.Util;

public abstract class JDBCTemplate {
	private boolean needClose;

	public abstract Connection getConnection();

	public abstract void close();

	public void setNeedClose(boolean needClose) {
		this.needClose = needClose;
	}

	private int update(PreparedStatementSetter pss, String sql) {
		int rows = 0;

		if (getConnection() != null && !Util.isNullOrEmpty(sql)) {
			try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
				if (ps != null) {
					pss.setPreparedStatement(ps);
					rows = ps.executeUpdate();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (needClose) {
					this.close();
				}
			}
		}
		return rows;
	}

	/**
	 * 
	 * @param c
	 * @param sql
	 * @param pss
	 *            自定义的接口实现，这里传入匿名内部类或者lamda表达式
	 * @param rs
	 *            同上
	 * @return 结果集或者单个结果
	 */
	private <T> Object queryObject(Class<T> c, String sql,
			PreparedStatementSetter pss, ResultSetting rs) {
		if (getConnection() != null) {
			try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
				if (pss != null) {
					pss.setPreparedStatement(ps);
				}
				if (rs != null) {
					try (ResultSet rs1 = ps.executeQuery()) {
						return rs.getResultSet(rs1);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (this.needClose) {
					this.close();
				}
			}
		}
		return null;
	}

	public <T> List<T> getEntityList(String sql, Class<T> c) {
		return (List<T>) queryObject(
				c,
				sql,
				null,
				// 通过lamda表达式构建接口实例
				rs -> {
					if (rs != null) {
						try {
							List<T> list = new ArrayList<T>();
							int totalColumns = rs.getMetaData()
									.getColumnCount();

							if (totalColumns != 0) {
								List<java.lang.reflect.Method> methods = Arrays
										.asList(c.getMethods());
								java.sql.ResultSetMetaData metaData = rs
										.getMetaData();

								while (rs.next()) {
									T e = c.newInstance();

									for (int j = 0; j < totalColumns; j++) {
										if (methods != null) {
											String colName = metaData
													.getColumnName(j + 1);

											methods.stream()
													.filter(m -> m
															.getName()
															.toLowerCase()
															.equals("set"
																	+ colName
																			.toLowerCase()))
													.findFirst()
													.get()
													.invoke(e,
															new Object[] { rs
																	.getObject(j + 1) });
										}
									}
									list.add(e);
								}
							}
							return list;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					return null;
				});
	}

	public <T> T getEntity(Class<T> c, String sql, Object[] paras) {
		return (T) queryObject(
				c,
				sql,
				p -> {
					if (paras != null) {
						int len = paras.length;
						for (int i = 0; i < len; i++) {
							try {
								p.setObject(i + 1, paras[i]);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				},
				rs -> {
					if (rs != null) {
						try {
							int totalColumns = rs.getMetaData()
									.getColumnCount();

							if (totalColumns != 0) {
								List<java.lang.reflect.Method> methods = Arrays
										.asList(c.getMethods());
								java.sql.ResultSetMetaData metaData = rs
										.getMetaData();

								if (rs.next()) {
									T e = c.newInstance();

									for (int j = 0; j < totalColumns; j++) {
										if (methods != null) {
											String colName = metaData
													.getColumnName(j + 1);

											methods.stream()
													.filter(m -> m
															.getName()
															.toLowerCase()
															.equals("set"
																	+ colName
																			.toLowerCase()))
													.findFirst()
													.get()
													.invoke(e,
															new Object[] { rs
																	.getObject(j + 1) });
										}
									}
									return e;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					return null;
				});

	}

	public int updateEntity(String sql, Object[] paras) {
		// lamda表达式代替匿名内部类
		return update(p -> {
			if (paras != null) {
				int len = paras.length;
				for (int i = 0; i < len; i++) {
					try {
						p.setObject(i + 1, paras[i]);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, sql);
	}
}