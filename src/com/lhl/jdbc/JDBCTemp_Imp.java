package com.lhl.jdbc;

import java.sql.Connection;

import sun.security.jca.GetInstance;

public class JDBCTemp_Imp extends JDBCTemplate {
	private static JDBCTemp_Imp temp;

	static {
		temp = new JDBCTemp_Imp();
	}

	private JDBCTemp_Imp() {
		super.setNeedClose(true);
	}

	public static JDBCTemp_Imp getInstance() {
		return temp;
	}

	@Override
	public Connection getConnection() {
		try {
			return ConnPool.singleton().getConn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void close() {
		ConnPool.singleton().close(getConnection());
	}
}
