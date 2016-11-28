package com.kmfex.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_product_type", schema = "JK_MANAGER")
public class ProductType implements Serializable{
	
	public String id;
	/**
	 * 类型名称
	 **/
	public String ptname;
	/**
	 * 类型代码
	 **/
	public String ptcode;
	
	public Date createDate=new Date();
	
	
	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPtname() {
		return ptname;
	}
	public void setPtname(String ptname) {
		this.ptname = ptname;
	}
	public String getPtcode() {
		return ptcode;
	}
	public void setPtcode(String ptcode) {
		this.ptcode = ptcode;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	//public 
	
	
	
	
}
