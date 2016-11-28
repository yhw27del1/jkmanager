package com.kmfex.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name = "t_member_type", schema = "JK_MANAGER")
public class MemberType implements Serializable{
	public String id;
	/**
	 * 类型名称
	 **/
	public String name;
	/**
	 * 类型代码
	 **/
	public String code;
	
	/**
	 * 此类型的创建时间
	 **/
	public Date createDate=new Date();
	
	/**
	 * 此类型的修改时间
	 **/
	public Date updateDate;
	
	
	
	/**
	 *  会员注册    是否下拉显示   0  不显示   1  显示
	 */
	public String flag;
	

	
	
	

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MemberType() {  
	}
	public MemberType(String id) { 
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}


}
