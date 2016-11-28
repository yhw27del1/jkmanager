package com.wisdoor.core.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.wisdoor.core.model.User;
import com.wisdoor.core.service.UserService;
import com.wisdoor.core.utils.BaseTool;

/**
 * 验证用户名，密码的类
 * 
 * 
 */
public class MyUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	public static final String UERNAME = "username";
	public static final String PASSWORD = "password";

	@Resource
	UserService userService;


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (!isAllowEmptyValidateCode())
			checkValidateCode(request);
		
		String userName = obtainUsername(request).trim();
		String password = obtainPassword(request).trim();
		User user = userService.findUser(userName,password);
		if (user == null) {
				throw new AuthenticationServiceException("登录号或密码错误,请重试");
		}
		else {
		 if (!user.getPassword().equals(password)) {				
				throw new AuthenticationServiceException("登录号或密码错误！");
		  }
		}
		UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(
				userName, password);
		setDetails(request, authenticationRequest);
		return this.getAuthenticationManager().authenticate(
				authenticationRequest);

	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(UERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}
	
	
	
	
	
	private boolean allowEmptyValidateCode = false; 
	private String validateCodeParameter = DEFAULT_VALIDATE_CODE_PARAMETER; 
	public static final String DEFAULT_VALIDATE_CODE_PARAMETER = "userCode";	

	/**
	 * 
	 * <li>比较session中的验证码和用户输入的验证码是否相等</li>
	 * 
	 */
	protected void checkValidateCode(HttpServletRequest request) {
		String sessionValidateCode = obtainSessionValidateCode(request);
		String validateCodeParameter = obtainValidateCodeParameter(request);
		if (BaseTool.isNull(validateCodeParameter) || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
			throw new AuthenticationServiceException("验证码错误");
		}
	}

	private String obtainValidateCodeParameter(HttpServletRequest request) {
		return request.getParameter(validateCodeParameter);
	}

	protected String obtainSessionValidateCode(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute("_TXPT_AUTHKEY"); 
		return null == obj ? "" : obj.toString();
	}
 
 
	public boolean isAllowEmptyValidateCode() {
		return allowEmptyValidateCode;
	}

	public void setAllowEmptyValidateCode(boolean allowEmptyValidateCode) {
		this.allowEmptyValidateCode = allowEmptyValidateCode;
	}
}
