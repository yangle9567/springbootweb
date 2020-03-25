package com.legendyang.springboot.dao;

import com.legendyang.springboot.bean.User;
import com.legendyang.springboot.example.UserExample;
import com.legendyang.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao{

	//注入Mapper接口
	@Autowired
	UserMapper userMapper;

	public List<User> selectByExample(UserExample userExample){
		return userMapper.selectByExample(userExample);
	}

	
}
