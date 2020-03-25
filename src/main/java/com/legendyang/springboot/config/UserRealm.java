package com.legendyang.springboot.config;

import com.legendyang.springboot.bean.User;
import com.legendyang.springboot.dao.UserDao;
import com.legendyang.springboot.example.UserExample;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义Realm
 * @author lenovo
 *
 */
public class UserRealm extends AuthorizingRealm{

	/**
	 * 执行授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("执行授权逻辑");
		
		//给资源进行授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		//添加资源的授权字符串
		//info.addStringPermission("user:add");
		
		//到数据库查询当前登录用户的授权字符串
		//获取当前登录用户
		Subject subject = SecurityUtils.getSubject();
		User user = (User)subject.getPrincipal();
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(user.getId());
		User dbUser = userDao.selectByExample(example).get(0);
		if(!dbUser.getPerms().contains("&")){
			info.addStringPermission(dbUser.getPerms());
		}else{
			List<String> list = Arrays.asList(dbUser.getPerms().split("&"));
			for (String perms : list) {
				info.addStringPermission(perms);
			}
		}
		
		
		return info;
	}
	
	@Autowired
	private UserDao userDao;

	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("执行认证逻辑");
		
		//编写shiro判断逻辑，判断用户名和密码
		//1.判断用户名
		UsernamePasswordToken token = (UsernamePasswordToken)arg0;

		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(token.getUsername());
		User user = userDao.selectByExample(example).get(0);
		
		if(user==null){
			//用户名不存在
			return null;//shiro底层会抛出UnKnowAccountException
		}
		
		//2.判断密码
		return new SimpleAuthenticationInfo(user,user.getPassword(),"");
	}

}
