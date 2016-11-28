package com.wisdoor.core.service.impl;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdoor.core.exception.EngineException;
import com.wisdoor.core.model.Account;
import com.wisdoor.core.model.User;
import com.wisdoor.core.service.AccountService;
import com.wisdoor.core.service.UserService;
import com.wisdoor.core.utils.MD5;

@Service
@Transactional
public class UserImpl extends BaseServiceImpl<User> implements UserService {
 
	@Resource AccountService accountService; 
	@Override
	public User findUser(String username,String password) {
		String queryString = "from User c where c.username = ? and c.password=? ";
		User user=selectById(queryString, new String[] { String.valueOf(username), String.valueOf(password)});
		return user;
	}

	@Override
	public User findUser(String username) {
		String queryString = "from User c where c.username = ? ";
		User user=selectById(queryString, new String[] { String.valueOf(username)});
		return user;
	}
	
	@Override
	@Transactional
	public Serializable insertUser(User obj) throws EngineException
	{
		Account account=accountService.createAccount();
		account.setUser(obj); 
		obj.setUserAccount(account);
		obj.setPassword(MD5.MD5Encode(obj.getPassword()));
		return this.insert(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String autocomplate(final String term) {
		String result = (String) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public String doInHibernate(Session session) throws HibernateException, SQLException {
						StringBuilder sb = new StringBuilder("[");
						List list = session.createQuery("select username from User where username = '"+term+"' ").setFirstResult(1).setMaxResults(10).list();
						for(int x = 0; x<list.size(); x++){
							if(x!=0){sb.append(",");}
							Object[] obj = (Object[])list.get(x);
							sb.append("{\"label\":\"").append(obj[0]).append("-").append(obj[1]).append("\",");
							sb.append("\"value\":\"").append(obj[0]).append("\"}");
						}
						sb.append("]");
						return sb.toString();
					}
				});

		return result;
	}
	
}