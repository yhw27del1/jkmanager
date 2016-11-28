package com.wisdoor.core.service.impl;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.StaleObjectStateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//import com.kmfex.model.AccountDeal;
import com.kmfex.model.MemberBase;
//import com.kmfex.service.AccountDealService;
import com.kmfex.service.MemberBaseService;
import com.wisdoor.core.exception.EngineException;
import com.wisdoor.core.model.Account;
import com.wisdoor.core.model.User;
import com.wisdoor.core.service.AccountService;
import com.wisdoor.core.service.UserService;
import com.wisdoor.core.utils.DoubleUtils;
import com.wisdoor.core.utils.StringUtils;

@Service

public class AccountImpl extends BaseServiceImpl<Account> implements AccountService {
	@Resource UserService userService; 
	@Resource MemberBaseService memberBaseService;
//	@Resource AccountDealService accountDealService;
	//会员注册时，为会员创建交易账户，此时账户状态为：未开通
	@Transactional
	public Account createAccount(){
		Account account = new Account();
		//account.setState(0);
		//account.setAccountId(StringUtils.generateAccountId());//随机生成19位账户编号
		//account.setBalance(100000d);//测试用的金额
		try {
			this.insert(account);
			return account;
		} catch (EngineException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//通过账户编号找账户
	@Transactional
	public Account selectByAccountId(String accountId){
		return this.selectByHql("from Account a where a.accountId = '"+accountId+"'");
		/*List<Account> as = this.getCommonListData("from Account a where a.accountId = "+accountId);
		if(null!=as && as.size()==1){
			return as.get(0);
		}else{
			return null;
		}*/
	}
	//通过用户编号找账户
	//取用户的账户信息，并计算债权
	@Transactional
	public Account selectByUserId(long userId){
		User user=this.userService.selectById(userId);
		MemberBase member = this.memberBaseService.getMemByUser(user);  
		
		String hql = "from InvestRecord o where o.state='2' and o.investor.id='"+member.getId()+"' ";
		double sumAmount=0d;//持有债权
		return user.getUserAccount();
 
	}
	
	//实时查询账户余额
	@Transactional
	public double queryBalance(Account account){
		//查余额时，余额保留三位小数返回
		return DoubleUtils.doubleCheck(this.selectById(account.getId()).getBalance(), 3);
	}
	

	


	/*@Override
	@Transactional
	public Account centerAccount() {
		//return this.selectByAccountId(1);
	}*/
	

	/*@Override
	@Transactional
	public boolean frozenAccount_old(Account a, double money) {
		Account account = this.selectById(a.getId());
		account.setFrozenAmount(DoubleUtils.doubleCheck(account.getFrozenAmount()+money, 2));//冻结金额增加
		account.setBalance(DoubleUtils.doubleCheck(account.getBalance()-money,2));//可用金额减少
		account.setOld_balance(DoubleUtils.doubleCheck(account.getOld_balance()-money,2));//可转金额减少
		try {
			this.update(account);
			return true;
		} catch (EngineException e) {
			e.printStackTrace();
			return false;
		}
	}*/

	@Override
	@Transactional
	public boolean frozenAccount(Account a, double money) {
		Account account = this.selectById(a.getId());
	//	account.setFrozenAmount(DoubleUtils.doubleCheck(account.getFrozenAmount()+money, 3));//冻结金额增加
		account.setBalance(DoubleUtils.doubleCheck(account.getBalance()-money,3));//可用金额减少
		try {
			this.update(account);
			return true;
		} catch (EngineException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	

	@Override
	@Transactional
	public boolean thawAccount1(Account a, double money) {
		Account account = this.selectById(a.getId());
	//	account.setFrozenAmount(DoubleUtils.doubleCheck(account.getFrozenAmount()-money,3));//冻结金额减少
		account.setBalance(DoubleUtils.doubleCheck(account.getBalance()+money,3));//可用金额增加
		try {
			this.update(account);
			return true;
		} catch (EngineException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	@Transactional
	public boolean thawAccount_old(Account a, double money) {
		Account account = this.selectById(a.getId());
		//account.setFrozenAmount(DoubleUtils.doubleCheck(account.getFrozenAmount()-money,2));//冻结金额减少
		account.setBalance(DoubleUtils.doubleCheck(account.getBalance()+money,2));//可用金额增加
	//	account.setOld_balance(DoubleUtils.doubleCheck(account.getOld_balance()+money,2));//可转金额增加
		try {
			this.update(account);
			return true;
		} catch (EngineException e) {
			e.printStackTrace();
			return false;
		}
	}



	//为某账户增加金额：如充值，还款，签约，融资确认等等，充值需要审核时调用此方法
	@Override
	@Transactional
	public boolean addMoney(Account a, double money) throws StaleObjectStateException,EngineException,Exception {
		Account account = this.selectById(a.getId());
		account.setBalance(DoubleUtils.doubleCheck(account.getBalance()+money,3));
		this.update(account);
		return true;
	}
	
	@Override
	@Transactional
	public boolean addMoney_oldbalance(Account a, double money) {
		Account account = this.selectById(a.getId());
		account.setBalance(DoubleUtils.doubleCheck(account.getBalance()+money,2));
		//account.setOld_balance(DoubleUtils.doubleCheck(account.getOld_balance()+money,2));//oldbalance累加
		try {
			this.update(account);
			return true;
		} catch (EngineException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//为某账户增加金额及冻结金额；交割时用到了此方法
	@Override
	@Transactional
	public boolean addMoney(Account a, double money,double djmoney) {
		Account account = this.selectById(a.getId());
		account.setBalance(DoubleUtils.doubleCheck(account.getBalance()+money,3));
	///	account.setFrozenAmount(DoubleUtils.doubleCheck(account.getFrozenAmount()+djmoney,3));
		try {
			this.update(account);
			return true;
		} catch (EngineException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//为某账户减少金额：如提现
	@Override
	@Transactional
	public strictfp boolean loseMoney(Account a, double money) {
		Account account = this.selectById(a.getId());
		if(money>account.getBalance()){
			System.out.println("loseMoney:账户减钱出现负值了。--------------------");
			return false;
		}
		account.setBalance(DoubleUtils.doubleCheck(account.getBalance()-money,3));
		try {
			this.update(account);
			return true;
		} catch (EngineException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	@Transactional
	public boolean loseMoney_oldbalance(Account a, double money) {
		Account account = this.selectById(a.getId());
		if(money>account.getBalance()){
			System.out.println("loseMoney:账户减钱出现负值了。--------------------");
			return false;
		}
		account.setBalance(DoubleUtils.doubleCheck(account.getBalance()-money,3));
	//	account.setOld_balance(DoubleUtils.doubleCheck(account.getOld_balance()-money,3));//oldbalance累减
		try {
			this.update(account);
			return true;
		} catch (EngineException e) {
			e.printStackTrace();
			return false;
		}
	}

	
}