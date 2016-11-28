package com.wisdoor.core.security;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//import com.wisdoor.core.model.HTMain;
import com.wisdoor.core.model.User;
import com.wisdoor.core.service.UserService;
/*
 *  运行身份管理类
 */
public class MyUserDetailsServiceImpl implements UserDetailsService{
	@Resource UserService userService; 
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException { 
		User users=null;
		try{
		    users =userService.findUser(userName);
			if(users!= null) {
				users.setRoles(users.getRoles());
				return users;
			}else{
				throw new UsernameNotFoundException("登录号" + userName + "不存在");
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return users;
	}

}
