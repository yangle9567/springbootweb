package com.legendyang.springboot.bean;

public class Department {

    private Integer id;
    private String departmentName;

    public Department Department(int i, String s) {
        Department department = new Department();
        department.setId(i);
        department.setDepartmentName(s);
        return department;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getId() {
        return id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

}
