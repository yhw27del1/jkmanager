package com.kmfex.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;




import com.wisdoor.core.model.User;

/**
 * 会员基本信息实体类
 * 
*/

@Entity
@Table(name = "t_member", schema = "JK_MANAGER")
public class MemberBase implements Serializable {
	/**
	 *    
	 */
	private static final long serialVersionUID = 4683014439862315854L;
	
	
	/** 会员的id号 */
	public String id;

	

	/** 姓名 */
	public String pName;

	/**
	 * 性别：0，男；1，女
	 * */
	public String pSex;
	
	/** 会员类型 **/
	public MemberType memberType;
	
	
	/**分类  0  个人   1 商务中心**/
	public String category;
	
	public String code;
	
	/**
	 * 会员类别：个人
	 * */
	public final static String CATEGORY_PERSON = "0";
	/**
	 * 会员类别：商务中心
	 * */
	public final static String CATEGORY_CENTER = "1";




	/**
	 * 介绍人   开户人
	 * */
	public User creator;

	
	/**
	 * 此会员的用户
	 * */
	public User user;

	/** 所在省 */
	public String province;

	/**
	 * 所在省名
	 * */
	public String provinceName;

	/** 所在市 */
	public String city;

	/** 所在市 */
	public String cityName;

	// 0 博士  1 硕士  2 本科  3 专科  4 高中    5 初中   6  小学
	public String edu; 



	/**
	 * 住址
	 * */
	public String pAddress;
	/**
	 * 座机
	 * */
	public String pPhone;

	/** 移动电话 */
	public String pMobile;

	/** 出生年月 */
	public Date pBirthday;

	/** 身份证号 */
	public String idCardNo;


	/** 资金开户行全称 */
	public String bank;
	/** 资金账号 */
	public String bankAccount;


	/** 注册日期 */
	public Date createDate = new Date();

	/**
	 * 修改(变更日期)
	 * */
	public Date updateDate = null;

	/**
	 * 手机(辅助字段)
	 * */
	public String pMobile2;
	
	
	private String fudaoren;//辅导人

	
	
	private String qq;
	private String email;

	
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	public String getId() {
		return id;
	}

	public MemberBase() {
	}

	public MemberBase(String id) { 
		this.id = id;
	}
	
	public MemberBase(String id,String pName) { 
		this.id = id;
		this.pName = pName;
	}

	public MemberBase(String id, String category, String pName, String pPhone,
			String pMobile,
			MemberType memberType) {
		this.id = id;
		this.pName = pName;
		this.pPhone = pPhone;
		this.pMobile = pMobile;
		this.memberType = memberType;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getpSex() {
		return pSex;
	}

	public void setpSex(String pSex) {
		this.pSex = pSex;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getpMobile2() {
		return pMobile2;
	}

	public void setpMobile2(String pMobile2) {
		this.pMobile2 = pMobile2;
	}

	public String getFudaoren() {
		return fudaoren;
	}

	public void setFudaoren(String fudaoren) {
		this.fudaoren = fudaoren;
	}



	@OneToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToOne
	public MemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}



	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	@OneToOne
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}
		

	public String getpAddress() {
		return pAddress;
	}

	public void setpAddress(String pAddress) {
		this.pAddress = pAddress;
	}

	public String getpPhone() {
		return pPhone;
	}

	public void setpPhone(String pPhone) {
		this.pPhone = pPhone;
	}

	public String getpMobile() {
		return pMobile;
	}

	public void setpMobile(String pMobile) {
		this.pMobile = pMobile;
	}
	 
	public Date getpBirthday() {
		return pBirthday;
	}

	public void setpBirthday(Date pBirthday) {
		this.pBirthday = pBirthday;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
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

	
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code=code;
	}
	

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName());
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			sb.append("[name:" + this.pName + ",phone:" + this.pPhone
					+ ",mobile:" + this.pMobile + ",bank:" + this.bank);
			sb.append(",bankAccount:" + this.bankAccount);
			sb.append(",sex:" + this.pSex);
			if (null != pBirthday) {
				sb.append(",birthday:" + df.format(this.pBirthday));
			}
			sb.append(",province:" + this.provinceName);
			sb.append(",city:" + this.cityName);
			sb.append(",address:" + this.pAddress + "]");
		return sb.toString();
	}



	public MemberBase clone(){
		MemberBase obj = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			obj = (MemberBase) ois.readObject();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				bos.close();
				oos.close();
				bis.close();
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}
