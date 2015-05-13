/**
 * @author bulusli 
 * @description database connection pool with proxy pattern
 * @date 2015-05-07
 */
package com.lhl.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import com.lhl.common.Util;

public class ProxyConnPool {
	private static ProxyConnPool singleton;
	private int currConnSize = 0;

	private LinkedList<Connection> cons = new LinkedList<>();

	static {
		singleton = new ProxyConnPool();
	}

	private ProxyConnPool() {
		try {
			Class.forName(Util.getValue("className"));

			int size = getInitConnSize();
			for (int i = 0; i < size; i++) {
				cons.add(getConnection());
				currConnSize++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ProxyConnPool singleton() {
		return singleton;
	}

	private int getMaxConnSize() {
		String maxSize = Util.getValue("maxConnSize");
		int maxConnSize = 100;

		if (!Util.isNullOrEmpty(maxSize)) {
			try {
				maxConnSize = Integer.parseInt(maxSize);

				if (maxConnSize < 50 || maxConnSize > 100) {
					maxConnSize = 100;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return maxConnSize;
	}

	private int getInitConnSize() {
		String initSize = Util.getValue("initConnSize");
		int initConnSize = 50;

		if (!Util.isNullOrEmpty(initSize)) {
			try {
				initConnSize = Integer.parseInt(initSize);

				if (initConnSize < 0 || initConnSize > getMaxConnSize()) {
					initConnSize = 50;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return initConnSize;
	}

	private Connection getConnection() throws Exception {
		String url = Util.getValue("url");
		String userName = Util.getValue("userName");
		String pwd = Util.getValue("pwd");

		Connection connection = DriverManager.getConnection(url, userName, pwd);

		connection = (Connection) Proxy.newProxyInstance(
				Connection.class.getClassLoader(),
				new Class[] { Connection.class }, new ProxyHandler(connection));

		return connection;
	}

	public synchronized Connection getConn() throws Exception {
		Connection conn = null;

		try {
			synchronized (cons) {
				int size = cons.size();
				if (size == 0) {
					// 当前的连接数小于最大连接数时才添加。
					if (currConnSize < getMaxConnSize()) {
						cons.add(getConnection());
						currConnSize++;
						conn = cons.removeFirst();
					} else {
						System.out
								.println("The connection pool has reached the maxsize 100.");
						throw new Exception(
								"The connection pool has reached the maxsize 100.");
					}
				} else {
					conn = cons.removeFirst();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	private class ProxyHandler implements InvocationHandler {
		private Connection conn;

		public ProxyHandler(Connection conn) {
			this.conn = conn;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] arg)
				throws Throwable {
			if (method.getName().equals("close")) {
				synchronized (cons) {
					// 小于连接池容量则加入,否则关闭该连接。
					if (currConnSize < getMaxConnSize()) {
						cons.add(conn);
						currConnSize++;
						System.out
								.println("add an exist connection,curr conn size:"
										+ currConnSize);
					} else {
						try {
							conn.close();

						} catch (SQLException e) {
							e.printStackTrace();
						} finally {
							System.out.println("conn closed");
							currConnSize--;
						}
					}
				}
			}
			// 必须写conn。
			return method.invoke(conn, arg);
		}
	}
}
