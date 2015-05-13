package com.lhl.dao.impl;

import java.util.List;

import com.lhl.bean.Dept;
import com.lhl.dao.IDeptDAO;
import com.lhl.jdbc.JDBCTemp_Imp;

public class DeptDAOImpl implements IDeptDAO {

	private static DeptDAOImpl singleton;

	private DeptDAOImpl() {

	}

	static {
		singleton = new DeptDAOImpl();
	}

	public static DeptDAOImpl singleton() {
		return singleton;
	}

	@Override
	public List<Dept> getDeptList() {
		String sql = "select * from dept";
		return JDBCTemp_Imp.getInstance().getEntityList(sql, Dept.class);
	}

	@Override
	public Dept getDeptById(int did) {
		String sql = "select * from dept where d_no=?";
		return JDBCTemp_Imp.getInstance().getEntity(Dept.class, sql,
				new Object[] { did });
	}

	@Override
	public int updateLocationbyId(int did, String location) {
		String sql = "update dept set d_location=? where d_no=?";
		return JDBCTemp_Imp.getInstance().updateEntity(sql,
				new Object[] { location, did });
	}
}
