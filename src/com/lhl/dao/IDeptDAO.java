package com.lhl.dao;

import java.util.List;

import com.lhl.bean.Dept;
import com.lhl.bean.Employee;

public interface IDeptDAO {
	List<Dept> getDeptList();

	Dept getDeptById(int did);

	int updateLocationbyId(int did,String location);
}
