package com.wisdoor.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户(个人、企业、交易中心管理员)
 */ 
@Entity 
@Table(name="sys_User",schema="JK_MANAGER")
public class User     implements java.io.Serializable,UserDetails{ 
	private static final long serialVersionUID = -6732569191310529603L;
	private long id;
	/**用户名**/
	private String username;
	
	private String password;
	
	private Org org;  
	
	private String coding; 
	
	/**姓名 **/
	private String realname;  
	
	

	/**1前台2后台用户**/ 
	private String typeFlag;  
	

	private boolean enabled=true;
	

	/* 拥有的角色 */
	private Set<Role> roles = new HashSet<Role>();   
	
	/**
	 * 用户的默认密码
	 * */
    public static final String defaultPassword="111111"; 

    @Column
	private Account userAccount;
    
    /**联系信息 **/
	private Contact userContact; 
    
    
	

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SYS_USER_SE")  
    @SequenceGenerator(name="SYS_USER_SE",sequenceName="SYS_USER_SEQUENCE",allocationSize=1)
	@Column(length=50)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public User(){}
	
	public User(String username) {
		this.username = username;
	}
 
	
	public User(String username, String password) { 
		this.username = username;
		this.password = password;
	}
 
	
	public User(String username, String password, String typeFlag) { 
		this.username = username;
		this.password = password;
		this.typeFlag = typeFlag;
	}

	/**
	 * 返回一个已指定名称为用户名，指定默认密码的用户
	 * */
	public static User createUser(String username){
		return new User(username,defaultPassword);
	}
	
	@Column(length=50,unique=true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@Column(length=100)
	public String getPassword() {   
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="org_id")
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
	@Column(length=255)
	public String getCoding() {
		return coding;
	}

	public void setCoding(String coding) {
		this.coding = coding;
	}
 
	
	@ManyToMany(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinTable(name="sys_user_role",joinColumns=@JoinColumn(name="userid"),
			inverseJoinColumns=@JoinColumn(name="roleid"))
 	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	/**
	 * 添加权限角色
	 * @param group
	 */
	public void addRole(Role role){
		this.roles.add(role);
	}
	
	
	@OneToOne
    public Account getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(Account userAccount) {
		this.userAccount = userAccount;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Transient
	public Collection<GrantedAuthority> getAuthorities() { 
		Collection<GrantedAuthority> grantedAuthoritis = new ArrayList<GrantedAuthority>(roles.size());
		for(Role role : roles){
			grantedAuthoritis.add(new GrantedAuthorityImpl("ROLE_"+role.getId()));
		}
		return grantedAuthoritis;
	}
	 
	@Transient
	public boolean isAccountNonExpired() { 
		return true;
	} 
	@Transient
	public boolean isAccountNonLocked() { 
		return true;
	} 
	@Transient
	public boolean isCredentialsNonExpired() { 
		return true;
	} 
	//当前用户是否处于激活状态
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

    //回逗号分割形式的的所有角色
	@Transient
	public String getRoleString(){
		List<String> roleList = new ArrayList<String>();
		for(Role role : roles){
			roleList.add(role.getId()+"");
		}
		return StringUtils.join(roleList,",");
	}
	
	public String getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
	
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="contactid")
	public Contact getUserContact() {
		return userContact;
	}

	public void setUserContact(Contact userContact) {
		this.userContact = userContact;
	}
}
