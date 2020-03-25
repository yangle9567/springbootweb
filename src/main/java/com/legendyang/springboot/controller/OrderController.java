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

@Controller
public class OrderController {

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    EmployeeDao employeeDao;


    @GetMapping("/order/{id}")
    public Department getDepartment(@PathVariable("id") Integer id){
        return departmentDao.getDepartment(id);
    }

    @PostMapping("/order/insert")
    public Department insertDept(Department department){
        departmentDao.insertDept(department);
        return department;
    }

    @GetMapping("/order/list")
    public String list(Model model){
        model.addAttribute("orders",departmentDao.getDepartments());
        return "order/list";
    }

    //来到员工添加页面
    @RequestMapping("/order/toadd")
    public String toAddPage(Model model){
        //来到添加页面,查出所有的部门，在页面显示
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "order/add";
    }

    //员工添加
    //SpringMVC自动将请求参数和入参对象的属性进行一一绑定；要求请求参数的名字和javaBean入参的对象里面的属性名是一样的
    @PostMapping("/order/add")
    public String addDept(Department department){
        //来到员工列表页面
        if(department.getId() == null){
            department.setId(Math.round(4));
        }
        System.out.println("保存的员工信息："+department);
        //保存员工
        //employeeDao.save(employee);
        departmentDao.insertDept(department);
        // redirect: 表示重定向到一个地址  /代表当前项目路径
        // forward: 表示转发到一个地址
        return "redirect:/depts";
    }




}
