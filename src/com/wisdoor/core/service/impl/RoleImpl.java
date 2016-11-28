package com.wisdoor.core.service.impl;


import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdoor.core.model.Role;
import com.wisdoor.core.service.RoleService;
 

@Service
@Transactional
public class RoleImpl extends BaseServiceImpl<Role> implements RoleService {
    @Override
	public List<Role> findNotInRole(Set<Role> roleMenus){
 	  StringBuffer buffer=new StringBuffer("from Role c where 1=1  ");
	  String ids="";
	  if(null!=roleMenus)
	  {
		  for(Role r:roleMenus)
		  {
			  ids+=r.getId()+",";
		  }
	  } 
	  if(!"".equals(ids)){
		  ids=ids.substring(0, ids.length()-1);
		  buffer.append("   and  c.id not in("+ids+")");
	  } 
	  return this.getCommonListData(buffer.toString());
  }



}