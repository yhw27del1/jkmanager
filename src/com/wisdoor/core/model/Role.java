package com.wisdoor.core.model;
 
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 角色表
 * @author by 
 */
@SuppressWarnings("serial")
@Entity
@Table(name="sys_Role",schema="JK_MANAGER")
public class Role  implements java.io.Serializable{
	private long id;
	/**角色名称**/
	private String name;
	/**角色描述**/
	private String desc; 
	/**某个角色下的用户**/
	private Set<User> users = new HashSet<User>();

	/**
	 * 角色类型：顶层角色0，组织机构下属角色1
	 */
	private String type;
	
	

	public Role(){}
 
	@Id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public Role(long id) { 
		this.id = id;
	}

	public Role(String name, String desc) { 
		this.name = name;
		this.desc = desc;
	}


	
	@Column(name="name_")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name="desc_")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
 
	@ManyToMany(mappedBy="roles",cascade=CascadeType.REFRESH)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Column(columnDefinition="varchar2(2) default '0'")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
