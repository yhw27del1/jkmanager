package com.kmfex.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_order_item", schema = "JK_MANAGER")
public class OrderItem implements Serializable {

	public String id;
	public Order OrderId;
	public Product productid;
	public int num = 1; //数量
	public double je; //总金额
	public int jf;       //总积分
	
	
	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@OneToOne
	public Product getProductid() {
		return productid;
	}
	public void setProductid(Product productid) {
		this.productid = productid;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getJe() {
		return je;
	}
	public void setJe(double je) {
		this.je = je;
	}
	public int getJf() {
		return jf;
	}
	public void setJf(int jf) {
		this.jf = jf;
	}
	
	@OneToOne
	public Order getOrderId() {
		return OrderId;
	}
	public void setOrderId(Order orderId) {
		OrderId = orderId;
	}
	
	
}
