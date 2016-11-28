package com.kmfex.service;

import java.util.List;

import com.kmfex.model.Product;
import com.kmfex.model.ProductType;
import com.wisdoor.core.service.BaseService;

/**
 *商品接口
 * 
 * @author
 **/
public interface ProductService extends BaseService<Product> {
	//public List<MemberType> getList(String flag);

	public Product selectByName(String name);
	
	
	public Product selectByCode(String code);
}
