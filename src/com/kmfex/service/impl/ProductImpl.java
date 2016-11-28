package com.kmfex.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kmfex.model.Product;
import com.kmfex.model.ProductType;
import com.kmfex.service.BusinessCenterService;
import com.kmfex.service.MemberTypeService;
import com.kmfex.service.ProductService;
import com.kmfex.service.ProductTypeService;
import com.wisdoor.core.model.Role;
import com.wisdoor.core.model.User;
import com.wisdoor.core.service.impl.BaseServiceImpl;
import com.wisdoor.core.utils.StringUtils;

/**
 * @author 
 * @version
 * */
@Service
public class ProductImpl extends BaseServiceImpl<Product> implements
      ProductService {

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
	public Product selectByName(String ptName) {
		Product product = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.eq("ptname", ptName));
		List<Product> list = this.getHibernateTemplate().findByCriteria(
				criteria);
		if (null != list && !list.isEmpty()) {
			product = list.get(0);
		}
		return product;
	}
	
	
	
	@Override
	public Product selectByCode(String code) {
		Product product = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.eq("pcode", code));
		List<Product> list = this.getHibernateTemplate().findByCriteria(
				criteria);
		if (null != list && !list.isEmpty()) {
			product = list.get(0);
		}
		return product;
	}
}
