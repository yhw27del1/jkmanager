package com.kmfex.service;

import java.util.List;

import com.kmfex.model.BusinessCenter;
import com.kmfex.model.MemberType;
import com.wisdoor.core.model.User;
import com.wisdoor.core.service.BaseService;

/**
 *商务中心接口
 * 
 * @author
 **/
public interface BusinessCenterService extends BaseService<BusinessCenter> {
	//public List<MemberType> getList(String flag);

	/**
	 * 根据指定的商务中心名称查找商务中心
	 * 
	 * @param name
	 *            指定的商务中心名称
	 * @return 商务中心对象
	 * */
	public BusinessCenter selectByName(String name);
	
	
	public BusinessCenter selectByCode(String code);
}
