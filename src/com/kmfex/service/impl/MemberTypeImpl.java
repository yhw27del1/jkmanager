package com.kmfex.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kmfex.model.MemberType;
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
public class MemberTypeImpl extends BaseServiceImpl<MemberType> implements
		MemberTypeService {

	public List<MemberType> getList(String flag) {
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
	}

	@Override
	public MemberType selectByName(String name) {
		MemberType memberType = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(MemberType.class);
		criteria.add(Restrictions.eq("name", name));
		List<MemberType> list = this.getHibernateTemplate().findByCriteria(
				criteria);
		if (null != list && !list.isEmpty()) {
			memberType = list.get(0);
		}
		return memberType;
	}
}
