package com.kmfex.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmfex.model.BusinessCenter;
import com.kmfex.model.MemberType;
import com.kmfex.model.ProductType;
import com.kmfex.service.BusinessCenterService;
import com.kmfex.service.MemberTypeService;
import com.kmfex.service.ProductTypeService;
import com.wisdoor.core.exception.EngineException;
import com.wisdoor.core.page.PageView;
import com.wisdoor.struts.BaseAction;

/**
 * 商品类型Action
 * */
@Controller
@Scope("prototype")
public class ProductTypeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3300741683131513060L;

	/**
	 * 列出商品类型
	 * */
	private static final String LIST = "list";

	private static final String EDIT = "edit";

	// private static final String

	@Resource
	private  ProductTypeService  productTypeService;

	private String id;
	private String ptName;
	private String ptCode;
	private String keyword;
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
		ProductType mt = null;
		if (modify) {
			mt = productTypeService.selectById(id);
			mt.setPtname(ptName);
			mt.setPtcode(ptCode);
			mt.setCreateDate(new Date());
			try {
				productTypeService.update(mt);
			} catch (EngineException e) {
				e.printStackTrace();
				return EDIT;
			}
		} else {
			mt = new ProductType();
			mt.setPtname(ptName);
			mt.setPtcode(ptCode);
			try {
				productTypeService.insert(mt);
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
				productTypeService.delete(id);
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
		if (modify) {
			ProductType mt = productTypeService.selectById(id);
			this.ptName = mt.getPtname();
			this.ptCode = mt.getPtcode();
		}
		return EDIT;
	}

	public String list() {
		PageView<ProductType> pageView = new PageView<ProductType>(
				getShowRecord(), getPage());
		StringBuilder sb = new StringBuilder("");
		List<String> params = new ArrayList<String>();
		if (null != keyword && !"".equals(keyword)) {
			sb.append("ptame like ? or ptcode like ? ");
			params.add(keyword);
			params.add(keyword);
		}
		try {
			pageView.setQueryResult(productTypeService.getScrollData(pageView
					.getFirstResult(), pageView.getMaxresult(), sb.toString(),
					params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletActionContext.getRequest().setAttribute("pageView", pageView);
		return LIST;
	}

	public ProductTypeService getProductTypeService() {
		return productTypeService;
	}

	public void setProductTypeService(ProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtCode() {
		return ptCode;
	}

	public void setPtCode(String code) {
		this.ptCode = code;
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

}
