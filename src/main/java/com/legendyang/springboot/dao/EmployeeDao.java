package com.legendyang.springboot.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.legendyang.springboot.bean.Employee;
import com.legendyang.springboot.example.EmployeeExample;
import com.legendyang.springboot.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao {

	private static Map<Integer, Employee> employees = null;
	@Autowired
	private DepartmentDao departmentDao;
	@Autowired
	private EmployeeMapper employeeMapper;

	public void save(Employee employee){
		employeeMapper.insert(employee);
	}

	//查询所有员工
	public Collection<Employee> getAll(){
		List<Employee> allEmployees = employeeMapper.selectByExample(null);
		return allEmployees;
	}
	
	public Employee get(Integer id){
		EmployeeExample employeeExample = new EmployeeExample();
		EmployeeExample.Criteria criteria = employeeExample.createCriteria();
		criteria.andDIdEqualTo(id);
		return employeeMapper.selectByPrimaryKey(id);
	}
	
	public void delete(Integer id){
		EmployeeExample employeeExample = new EmployeeExample();
		EmployeeExample.Criteria criteria = employeeExample.createCriteria();
		criteria.andIdEqualTo(id);
		employeeMapper.deleteByExample(employeeExample);
	}
	// 返回最大的id
	public int getBiggestId() {
		Employee biggestIdEmp = employeeMapper.getBiggestIdEmp();
		if(biggestIdEmp == null){
			return 1;
		}else{
			return biggestIdEmp.getId();
		}
	}
}
