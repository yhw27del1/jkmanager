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
import com.kmfex.service.BusinessCenterService;
import com.kmfex.service.MemberTypeService;
import com.wisdoor.core.exception.EngineException;
import com.wisdoor.core.page.PageView;
import com.wisdoor.struts.BaseAction;

/**
 * 商务中心Action
 * */
@Controller
@Scope("prototype")
public class BusinessCenterAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3300741683131513060L;

	/**
	 * 列出商务中心
	 * */
	private static final String LIST = "list";

	private static final String EDIT = "edit";

	// private static final String

	@Resource
	private  BusinessCenterService  businessCenterService;

	private String id;
	private String centerName;
	private String code;
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
		BusinessCenter mt = null;
		if (modify) {
			mt = businessCenterService.selectById(id);
			mt.setCenterName(centerName);
			mt.setCode(code);
			mt.setCreateDate(new Date());
			try {
				businessCenterService.update(mt);
			} catch (EngineException e) {
				e.printStackTrace();
				return EDIT;
			}
		} else {
			mt = new BusinessCenter();
			mt.setCenterName(centerName);
			mt.setCode(code);
			try {
				businessCenterService.insert(mt);
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
				businessCenterService.delete(id);
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
			BusinessCenter mt = businessCenterService.selectById(id);
			this.centerName = mt.getCenterName();
			this.code = mt.getCode();
		}
		return EDIT;
	}

	public String list() {
		PageView<BusinessCenter> pageView = new PageView<BusinessCenter>(
				getShowRecord(), getPage());
		StringBuilder sb = new StringBuilder("");
		List<String> params = new ArrayList<String>();
		if (null != keyword && !"".equals(keyword)) {
			sb.append("centerName like ? or code like ? ");
			params.add(keyword);
			params.add(keyword);
		}
		try {
			pageView.setQueryResult(businessCenterService.getScrollData(pageView
					.getFirstResult(), pageView.getMaxresult(), sb.toString(),
					params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletActionContext.getRequest().setAttribute("pageView", pageView);
		return LIST;
	}

	public BusinessCenterService getBusinessCenterService() {
		return businessCenterService;
	}

	public void setBusinessCenterService(BusinessCenterService businessCenterService) {
		this.businessCenterService = businessCenterService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String name) {
		this.centerName = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
