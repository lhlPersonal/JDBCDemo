package com.lhl.bean;

import java.sql.Date;

public class Employee {
	private int e_no;
	private String e_name;
	private String e_gender;
	private int dept_no;
	private String e_job;
	private double e_salary;
	private Date hireDate;
	
	public int getE_no() {
		return e_no;
	}
	public void setE_no(int e_no) {
		this.e_no = e_no;
	}
	public String getE_name() {
		return e_name;
	}
	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
	public String getE_gender() {
		return e_gender;
	}
	public void setE_gender(String e_gender) {
		this.e_gender = e_gender;
	}
	public int getDept_no() {
		return dept_no;
	}
	public void setDept_no(int dept_no) {
		this.dept_no = dept_no;
	}
	public String getE_job() {
		return e_job;
	}
	public void setE_job(String e_job) {
		this.e_job = e_job;
	}
	public double getE_salary() {
		return e_salary;
	}
	public void setE_salary(double e_salary) {
		this.e_salary = e_salary;
	}
	public Date getHireDate() {
		return hireDate;
	}
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
}
