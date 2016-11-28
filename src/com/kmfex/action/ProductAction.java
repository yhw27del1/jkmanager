package com.kmfex.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.kmfex.model.Product;
import com.kmfex.model.ProductType;
import com.kmfex.service.ProductService;
import com.kmfex.service.ProductTypeService;
import com.opensymphony.xwork2.Preparable;
import com.wisdoor.core.exception.EngineException;
import com.wisdoor.core.page.PageView;
import com.wisdoor.struts.BaseAction;

/**
 * 商品Action
 * */
@Controller
@Scope("prototype")
public class ProductAction extends BaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3300741683131513060L;

	/**
	 * 列出商品
	 * */
	private static final String LIST = "list";

	private static final String EDIT = "edit";

	// private static final String

	@Resource
	private  ProductService  productService;
	
	@Resource
	private ProductTypeService productTypeService;

	private String id;
	private String pname;
	private String pcode;
	private String keyword;
	private ProductType pType;
	private int pjf;
	private double price;
	
	
	private Product product;
	
	private String pTypeId;
	
	
	/**
	 * 商品类型列表
	 * */
	private List<ProductType> productTypes;

	
	
	/**
	 * 修改标记，false为新增；true为修改
	 * */
	private boolean modify = false;

	public String addPage() {
		return SUCCESS;
	}

	/**
	 * 修改或者新增
	 * */
	public String save() {
		Product mt = null;
		
		if (modify) {
			mt = productService.selectById(id);
			mt.setPname(pname);
			mt.setPcode(pcode);
			this.setpTypeId(product.getPtype().getId());
			mt.setPjf(pjf);
			mt.setPrice(price);
			try {
				productService.update(mt);
			} catch (EngineException e) {
				e.printStackTrace();
				return EDIT;
			}
		} else {
			mt = new Product();
			mt.setPname(pname);
			mt.setPcode(pcode);
			mt.setPtype(pType);
			mt.setPjf(pjf);
			mt.setPrice(price);
			try {
				productService.insert(mt);
			} catch (EngineException e) {
				e.printStackTrace();
				return EDIT;
			}
		}
		return list();
	}

	public String del() {
		if (null != id && !"".equals(id)) {
			try {
				productService.delete(id);
			} catch (EngineException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		return list();
	}

	/**
	 * 修改或新增页面
	 * */
	public String edit() {
		this.setProductTypes(productTypeService.getList());
		if (modify) {
			Product mt = productService.selectById(id);
			this.pname = mt.getPname();
			this.pcode = mt.getPcode();
			this.pjf = mt.getPjf();
			this.price = mt.getPrice();
			this.pTypeId=product.getPtype().getId();
			
		}
		return EDIT;
	}

	public String list() {
		PageView<Product> pageView = new PageView<Product>(
				getShowRecord(), getPage());
		StringBuilder sb = new StringBuilder("");
		List<String> params = new ArrayList<String>();
		if (null != keyword && !"".equals(keyword)) {
			sb.append("pName like ? or pCode like ? ");
			params.add(keyword);
			params.add(keyword);
		}
		try {
			pageView.setQueryResult(productService.getScrollData(pageView
					.getFirstResult(), pageView.getMaxresult(), sb.toString(),
					params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletActionContext.getRequest().setAttribute("pageView", pageView);
		return LIST;
	}
	
	
	
	@Override
	public void prepare() throws Exception {
		if (isModify()) {
			product = this.productService.selectById(id);
		} else {
			product = new Product();
		}
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean isModify() {
		return modify;
	}

	public void setModify(boolean modify) {
		this.modify = modify;
	}

	public ProductType getpType() {
		return pType;
	}

	public void setpType(ProductType pType) {
		this.pType = pType;
	}

	public int getPjf() {
		return pjf;
	}

	public void setPjf(int pjf) {
		this.pjf = pjf;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pName) {
		this.pname = pName;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pCode) {
		this.pcode = pCode;
	}

	public List<ProductType> getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(List<ProductType> productTypes) {
		this.productTypes = productTypes;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getpTypeId() {
		return pTypeId;
	}

	public void setpTypeId(String pTypeId) {
		this.pTypeId = pTypeId;
	}

}
