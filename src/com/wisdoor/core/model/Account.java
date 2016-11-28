package com.wisdoor.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table; 
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * 用户帐户表
 * @author
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "sys_Account",schema="JK_MANAGER")
public class Account  implements java.io.Serializable{
	//public final static String CENTER = "1";//中心账户
	
  	private long id;   
	/**账号创建时间 **/
	private Date createDate = new Date(); 
	/**账号余额 **/  
	private double balance = 0d;//可用余额
	
	/** 所属用户 **/
	@Column
	private User user;//通过user找到会员
	/** 所属机构和公司 **/
	private Org org;

	
	
	private int version=0;
	
	
	
	@Id   
	@Column(name="id",length=50)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SYS_ACCOUNT_SE")  
    @SequenceGenerator(name="SYS_ACCOUNT_SE",sequenceName="SYS_ACCOUNT_SEQUENCE",allocationSize=1)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Version
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	@Column(name="createDate_")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="balance_")
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	} 
	@OneToOne(mappedBy = "userAccount") 
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@OneToOne(mappedBy = "orgAccount") 
	public Org getOrg() {
		return org;
	}
	
	
	
	public void setOrg(Org org) {
		this.org = org;
	}
	
	
	
	
}

