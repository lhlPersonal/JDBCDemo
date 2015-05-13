package com.lhl.dao;

import java.util.List;

import com.lhl.bean.Employee;

public interface IEmployeeDAO {
	List<Employee> getEmpList();

	Employee getEmpById(String eid);
}
