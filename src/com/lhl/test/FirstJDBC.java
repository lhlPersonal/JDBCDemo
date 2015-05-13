package com.lhl.test;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lhl.bean.Dept;
import com.lhl.bean.Employee;
import com.lhl.dao.impl.DeptDAOImpl;
import com.lhl.dao.impl.EmployeeDAOImpl;
import com.lhl.jdbc.ConnPool;
import com.lhl.jdbc.JDBCTemp_Imp;
import com.lhl.jdbc.ProxyConnPool;

interface AA {
	void a1();

	Object a2();
}

public class FirstJDBC {

	public static void main(String[] args) {
		List<Employee> list = EmployeeDAOImpl.singleton().getEmpList();
		if (list != null && !list.isEmpty()) {
			list.forEach(l -> {
				System.out.println(l.getE_no() + " " + l.getE_name() + " "
						+ l.getE_salary() + " " + l.getHireDate());
			});
		}

		Dept d = DeptDAOImpl.singleton().getDeptById(10);
		if (d != null) {
			System.out.println(d.getD_no() + " " + d.getD_name() + " "
					+ d.getD_location());
		}

		List<Dept> dList = DeptDAOImpl.singleton().getDeptList();

		if (dList != null && !dList.isEmpty()) {
			dList.forEach(l -> {
				System.out.println(l.getD_no() + " " + l.getD_name() + " "
						+ l.getD_location());
			});
		}

		int count = DeptDAOImpl.singleton().updateLocationbyId(20, "TianJin");

		System.out.println(count);

	}

	private static void connPoolTest() {
		ExecutorService es = Executors.newCachedThreadPool();

		for (int i = 0; i < 200; i++) {
			es.execute(() -> firstQuery());
		}
	}

	private static void firstQuery() {
		Connection conn = null;
		try {
			System.out.println("start to get connection:"
					+ Thread.currentThread().getName());
			conn = ProxyConnPool.singleton().getConn();

			if (conn != null) {
				Properties properties = conn.getClientInfo();

				properties.forEach((k, v) -> {
					System.out.println(MessageFormat.format(
							"Key:{0},Value:{1}", k, v));
				});

				DatabaseMetaData metaData = conn.getMetaData();
				System.out.println(metaData.getDatabaseProductVersion()
						+ "----------" + Thread.currentThread().getId()
						+ "-------" + Thread.currentThread().getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnPool.singleton().close(conn);
			System.out.println("close to connection:"
					+ Thread.currentThread().getName());
		}
	}

	private static void GetConnection() throws Exception {
		Properties properties = new Properties();
		properties.load(new FileInputStream("src/conn.properties"));

		String className = properties.getProperty("className");
		String url = properties.getProperty("url");
		String userName = properties.getProperty("userName");
		String pwd = properties.getProperty("pwd");

		Class.forName(className);

		Connection connection = DriverManager.getConnection(url, userName, pwd);
		if (connection != null) {

			DatabaseMetaData metaData = connection.getMetaData();
			System.out.println(metaData.getDriverMajorVersion());
		}
	}

	private static void Test(AA a) {
		a.a1();
		a.a2();
	}
}
