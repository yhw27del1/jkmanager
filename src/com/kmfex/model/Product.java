package com.kmfex.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_product", schema = "JK_MANAGER")
public class Product implements Serializable{

	public String id;
	public String pcode;
	public String pname;
	public int pjf;
	public double price;
	
	public ProductType ptype;

	
	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
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

	@OneToOne
	public ProductType getPtype() {
		return ptype;
	}

	public void setPtype(ProductType ptype) {
		this.ptype = ptype;
	}
	
	//public 
	
	
	
	
}
