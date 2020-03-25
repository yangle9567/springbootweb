package com.legendyang.springboot.controller;

import com.legendyang.springboot.bean.Department;
import com.legendyang.springboot.bean.Employee;
import com.legendyang.springboot.dao.DepartmentDao;
import com.legendyang.springboot.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;

    //查询所有员工返回列表页面
    @RequestMapping("/emp/list")
    public String  list(Model model){
        //Collection<Employee> employees = employeeDao.getAll();
        List<Employee> employees = (List<Employee>) employeeDao.getAll();
        for (Employee employee : employees) {
            int did=employee.getdId();
            Department dept = departmentDao.getDepartment(did);
            employee.setDepartment(dept);
        }

        //放在请求域中
        model.addAttribute("emps",employees);
        // thymeleaf默认就会拼串
        // classpath:/templates/xxxx.html
        return "emp/list";
    }

    //来到员工添加页面
    @GetMapping("/emp/toadd")
    public String toAddPage(Model model){
        //来到添加页面,查出所有的部门，在页面显示
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";
    }

    //员工添加
    //SpringMVC自动将请求参数和入参对象的属性进行一一绑定；要求请求参数的名字和javaBean入参的对象里面的属性名是一样的
    @PostMapping("/emp/add")
    public String addEmp(Employee employee){
        //来到员工列表页面
        if(employee.getdId() == null){
            int biggestId=employeeDao.getBiggestId();
            biggestId++;
            employee.setId(biggestId);
        }
        if(employee.getDepartment().getId() != null){
            employee.setdId(employee.getDepartment().getId());
        }
        System.out.println("保存的员工信息："+employee);
        //保存员工
        //employeeDao.save(employee);
        employeeDao.save(employee);
        // redirect: 表示重定向到一个地址  /代表当前项目路径
        // forward: 表示转发到一个地址
        return "redirect:/emp/list";
    }

    //来到修改页面，查出当前员工，在页面回显
    @GetMapping("/emp/toeidt/{id}")
    public String toEditPage(@PathVariable("id") Integer id,Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp",employee);

        //页面要显示所有的部门列表
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        //回到修改页面(add是一个修改添加二合一的页面);
        return "emp/add";
    }

    //员工修改；需要提交员工id；
    @PutMapping("/emp/update")
    public String updateEmployee(Employee employee){
        System.out.println("修改的员工数据："+employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    //员工删除
    @RequestMapping("/emp/delete")
    public String deleteEmployee(@RequestParam("id") String id){
        //employeeDao.delete(id);
        employeeDao.delete(Integer.valueOf(id));
        return "redirect:/emp/list";
    }


}
