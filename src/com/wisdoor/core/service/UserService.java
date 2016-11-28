package com.wisdoor.core.service;
 
import java.io.Serializable;
import java.util.List;

import com.wisdoor.core.exception.EngineException;
//import com.wisdoor.core.model.HTMain;
import com.wisdoor.core.model.User;

/*** 
 * 用户(个人、企业、网站管理员
 * @author wuzuguo  
 */
public interface UserService  extends BaseService<User>{ 
	/*
	 * 判断用户是否存在
	 */

	public User findUser(String username,String password);
	public User findUser(String username);
	public Serializable insertUser(User obj) throws EngineException; 
	
	public String autocomplate(String term);
}
