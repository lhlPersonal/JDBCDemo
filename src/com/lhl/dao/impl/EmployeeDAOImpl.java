package com.lhl.dao.impl;

import java.util.List;

import com.lhl.bean.Dept;
import com.lhl.bean.Employee;
import com.lhl.dao.IEmployeeDAO;
import com.lhl.jdbc.JDBCTemp_Imp;

public class EmployeeDAOImpl implements IEmployeeDAO {
	private static EmployeeDAOImpl singleton;

	private EmployeeDAOImpl() {

	}

	static {
		singleton = new EmployeeDAOImpl();
	}

	public static EmployeeDAOImpl singleton() {
		return singleton;
	}
	
	@Override
	public List<Employee> getEmpList() {
		String sql = "select * from employee";
		return JDBCTemp_Imp.getInstance().getEntityList(sql, Employee.class);
	}

	@Override
	public Employee getEmpById(String eid) {
		String sql = "select * from employee where e_no=?";
		return JDBCTemp_Imp.getInstance().getEntity(Employee.class, sql,
				new Object[] { eid });
	}
}
