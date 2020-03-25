package com.legendyang.springboot.dao;

import java.util.Collection;
import java.util.Map;

import com.legendyang.springboot.bean.Department;
import com.legendyang.springboot.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class DepartmentDao {

	@Autowired
	DepartmentMapper departmentMapper;

	private static Map<Integer, Department> departments = null;
	

	public Collection<Department> getDepartments(){
		return departmentMapper.getDepts();
	}
	
	public Department getDepartment(Integer id){
		return departmentMapper.getDeptById(id);
	}

	public void insertDept(Department department){
		departmentMapper.insertDept(department);
	}

	public void deleteDeptById(Integer id){
		departmentMapper.deleteDeptById(id);
	}



	
}
