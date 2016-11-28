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
import com.kmfex.model.ProductType;
import com.kmfex.service.BusinessCenterService;
import com.kmfex.service.MemberTypeService;
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
public class ProductTypeImpl extends BaseServiceImpl<ProductType> implements
      ProductTypeService {

	public List<ProductType> getList() {
		List<ProductType> result = new ArrayList<ProductType>();
		   result = this.getScrollDataCommon("from ProductType m");
		   return result;

	}

	@Override
	public ProductType selectByName(String ptName) {
		ProductType productType = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductType.class);
		criteria.add(Restrictions.eq("ptname", ptName));
		List<ProductType> list = this.getHibernateTemplate().findByCriteria(
				criteria);
		if (null != list && !list.isEmpty()) {
			productType = list.get(0);
		}
		return productType;
	}
	
	
	
	@Override
	public ProductType selectByCode(String code) {
		ProductType productType = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductType.class);
		criteria.add(Restrictions.eq("ptcode", code));
		List<ProductType> list = this.getHibernateTemplate().findByCriteria(
				criteria);
		if (null != list && !list.isEmpty()) {
			productType = list.get(0);
		}
		return productType;
	}
}
