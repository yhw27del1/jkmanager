package com.kmfex.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kmfex.model.BusinessCenter;
import com.kmfex.model.MemberType;
import com.kmfex.service.BusinessCenterService;
import com.kmfex.service.MemberTypeService;
import com.wisdoor.core.model.Role;
import com.wisdoor.core.model.User;
import com.wisdoor.core.service.impl.BaseServiceImpl;
import com.wisdoor.core.utils.StringUtils;

/**
 * @author 
 * @version
 * */
@Service
public class BusinessCenterImpl extends BaseServiceImpl<BusinessCenter> implements
     BusinessCenterService {

	/*public List<MemberType> getList(String flag) {
		List<MemberType> result = new ArrayList<MemberType>();
		//if(StringUtils.isNotBlank(sb.toString())){
		//	result = this.getScrollDataCommon("from MemberType m where m.code in ("+sb.toString()+")");
		//}
		if(StringUtils.isNotBlank(flag)){
			result = this.getScrollDataCommon("from MemberType m where m.flag="+flag);
		}else{
		    result = this.getScrollDataCommon("from MemberType m");
		}
		return result;
	}*/

	@Override
	public BusinessCenter selectByName(String centerName) {
		BusinessCenter businessCenter = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(BusinessCenter.class);
		criteria.add(Restrictions.eq("centerName", centerName));
		List<BusinessCenter> list = this.getHibernateTemplate().findByCriteria(
				criteria);
		if (null != list && !list.isEmpty()) {
			businessCenter = list.get(0);
		}
		return businessCenter;
	}
	
	
	
	@Override
	public BusinessCenter selectByCode(String code) {
		BusinessCenter businessCenter = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(BusinessCenter.class);
		criteria.add(Restrictions.eq("code", code));
		List<BusinessCenter> list = this.getHibernateTemplate().findByCriteria(
				criteria);
		if (null != list && !list.isEmpty()) {
			businessCenter = list.get(0);
		}
		return businessCenter;
	}
}
