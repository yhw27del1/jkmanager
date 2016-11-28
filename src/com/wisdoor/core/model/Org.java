package com.wisdoor.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/*
 * 公司、机构表   
 */
@Entity
@Table(name = "sys_Org", schema = "JK_MANAGER")
public class Org implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3762424299913330858L;
	/**
	 * 顶级机构内部编码
	 * */
	public static final String TOP_ORG_CODEING = "1";
	
	/**
	 * 顶级机构内部编码
	 * */
	public static final String TOP_ORG_CODEING2 = "1m1";


	private long id;
	private String name;
	
	private Set<User> users = new HashSet<User>();
	private boolean leaf;
	private Org parent;
	private Set<Org> children;
	private long parentId; // 辅助变量不参与映射数据库
	/**
	 * 编码规则： 一级 1 二级 1m2 三级 1m2m3 依此类推 唯一,最关键字段 ,体现层次
	 */
	private String coding;

	/**
	 * 显示编码 (人工维护)
	 */
	private String showCoding;
	
	private String parentCoding;
	
	/** 帐号信息 **/
	private Account orgAccount;
	
	/**
	 * 简称、logo、品牌 
	 */
	private String shortName;
	private String type;   /* 0: 总公司   1:服务中心  2:经销商 */
	

	
	

	public Org() {
	}

	public Org(long id) {
		this.id = id;
	}

	public Org(String name, Org parent, String coding, boolean leaf) {
		this.name = name;
		this.leaf = leaf;
		this.parent = parent;
		this.coding = coding;
	}

	
	public Org(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "org", cascade = CascadeType.REFRESH)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "name_", length = 128, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE }, mappedBy = "parent")
	public Set<Org> getChildren() {
		return children;
	}

	public void setChildren(Set<Org> children) {
		this.children = children;
	}

	@Column(name = "leaf_")
	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "parentid_")
	public Org getParent() {
		return parent;
	}

	public void setParent(Org parent) {
		this.parent = parent;
	}


	@Transient
	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getCoding() {
		return coding;
	}

	public void setCoding(String coding) {
		this.coding = coding;
	}

	
	public String getShowCoding() {
		return showCoding;
	}

	public void setShowCoding(String showCoding) {
		this.showCoding = showCoding;
	}
	

	public String getParentCoding() {
		return parentCoding;
	}

	public void setParentCoding(String parentCoding) {
		this.parentCoding = parentCoding;
	}
	
	@OneToOne
	public Account getOrgAccount() {
		return orgAccount;
	}

	public void setOrgAccount(Account orgAccount) {
		this.orgAccount = orgAccount;
	}

	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
