package com.kmfex.service;

import java.util.List;

import com.kmfex.model.ProductType;
import com.wisdoor.core.service.BaseService;

/**
 *商品类别接口
 * 
 * @author
 **/
public interface ProductTypeService extends BaseService<ProductType> {
	public List<ProductType> getList();

	public ProductType selectByName(String name);
	
	
	public ProductType selectByCode(String code);
}
