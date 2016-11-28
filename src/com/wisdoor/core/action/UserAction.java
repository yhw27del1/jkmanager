package com.wisdoor.core.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import com.wisdoor.core.symbol.UserTags;
//import com.wisdoor.core.model.HTMain;
import com.wisdoor.core.model.Logs;
import com.wisdoor.core.model.Menu;
import com.wisdoor.core.model.Role;
import com.wisdoor.core.model.User;
import com.wisdoor.core.page.PageView;
import com.wisdoor.core.service.LogsService;
import com.wisdoor.core.service.RoleMenuService;
import com.wisdoor.core.service.RoleService;
import com.wisdoor.core.service.UserService;
import com.wisdoor.core.utils.Constant;
import com.wisdoor.core.utils.DoResultUtil;
import com.wisdoor.core.utils.MD5;
import com.wisdoor.core.utils.MenuUtils;
import com.wisdoor.core.utils.ObjectSwitch;
import com.wisdoor.core.utils.SortList;
import com.wisdoor.core.utils.StringUtils;
import com.wisdoor.struts.BaseAction;
/*** 
 * 管理员用户类
 * @author wuzuguo  
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction implements Preparable {
	@Resource UserService userService;
	//@Resource MenuService menuService;
	@Resource RoleMenuService roleMenuService;
	@Resource RoleService roleService; 
	@Resource LogsService logsService;

	private long id; 
	private long parent_id;
	private User user;
	private String roleIds;
	private Set<Role> inUserRoleList;
	private List<Role> notInUserRoleList;
	private String qkeyWord = ""; 
	private long leftParent_id=3;
	private String userName;
	private String password;
	private String validCode;// 验证码
	private HashMap<String,List<Menu>> leftMenuMap;
	private String directUrl="/back/index.jsp";
	
	private String inputPath="/wisdoorStatic/userfiles/";
	private String keyWord = ""; 
	
	
	private List<Menu> rootmenulist;
	
	public void prepare() throws Exception {
      if(id!=0) {
    	   user=userService.selectById(id);
       }else{
    	   user=new User();
       }
	}   
	
	/**跳转页面**/
	/**跳转页面**/
	public String ui() throws Exception { 
		try {
			this.inUserRoleList=user.getRoles();//用户拥有的角色列表 
			this.notInUserRoleList=this.roleService.findNotInRole(this.inUserRoleList);//用户未拥有的角色列表 
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return "ui"; 
	}
	public String list() throws Exception {
		try {
			PageView<User> pageView = new PageView<User>(getShowRecord(), getPage());
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuilder sb = new StringBuilder(" 1=1 and o.typeFlag='2' "); //后台用户
			List<String> params = new ArrayList<String>(); 
			if(null!=keyWord&&!"".equals(keyWord.trim())){

				keyWord = keyWord.trim(); 
				sb.append(" and (");
				sb.append(" o.username like ?");
				params.add("%"+keyWord+"%");
				sb.append(" or ");
				sb.append(" o.realname like ?"); 
				params.add("%"+keyWord+"%"); 
				sb.append(" )");
	 		} 
			pageView.setQueryResult(userService.getScrollData(pageView.getFirstResult(), pageView.getMaxresult(),sb.toString(), params,orderby));
			ServletActionContext.getRequest().setAttribute("pageView", pageView);
		} catch (Exception e) { 
			e.printStackTrace();
		} 
        return "list";
	}
	
	
	
	
	
	public String userinfo() throws Exception{
		User u  = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
		this.user = u;
		return "userinfo";
	}
	
	

	public String validateUser() throws Exception {
		try {  
			User userTemp=userService.findUser(userName,password); 
			if(userTemp == null){ 
				DoResultUtil.doStringResult(ServletActionContext.getResponse(),"0"); 
			}else{ 
				DoResultUtil.doStringResult(ServletActionContext.getResponse(),"1"); 
			} 
		} catch (Exception e) {
			e.printStackTrace(); 
		} 
		return null;
	}
	
	public String validateUser2() throws Exception {
		try {  
			//password=MD5.MD5Encode(password);
			//User userTemp=userService.findUser(userName, password);
			User userByUserName=userService.findUser(userName); 
			if(userByUserName == null){//用户名不存在
				LOG.warn("用户名或密码错误");
				DoResultUtil.doStringResult(ServletActionContext.getResponse(),"9"); 
				return null;
			}else{
				User loginIngUser=userService.findUser(userName, password);
				if(loginIngUser == null){//密码错误
					LOG.warn("用户名或密码错误");
					DoResultUtil.doStringResult(ServletActionContext.getResponse(),"8"); 
					return null;
				}else{
					Logs log0 = new Logs();// 日志
					HttpServletRequest request = ServletActionContext. getRequest(); 
					String ip = getIpAddr(request);
					log0.setIp(ip);
					log0.setOperate("登录");
				    log0.setOperator(loginIngUser.getUsername());
				    this.logsService.insert(log0);
				    DoResultUtil.doStringResult(ServletActionContext.getResponse(),"6");
				    return null;
				}
			} 
		} catch (Exception e) {
			e.printStackTrace(); 
		} 
		return null;
	}  
	

	public String validateCode() throws Exception {
		try {  
			//取验证码
			Object random = ActionContext.getContext().getSession().get("_TXPT_AUTHKEY");  
			if (random==null) {             
				this.addActionError("验证码不能为空"); 
				    DoResultUtil.doStringResult(ServletActionContext.getResponse(),"0"); 
			}
			else
			{
				if(!random.toString().equals(validCode)) {
				 	this.addActionError("验证码不正确"); 
				 	DoResultUtil.doStringResult(ServletActionContext.getResponse(),"1"); 
				 } else{
					 DoResultUtil.doStringResult(ServletActionContext.getResponse(),"2"); 
				 }  
			} 
		} catch (Exception e) {
			e.printStackTrace(); 
		} 
		return null;
	}
	
	
	public String index() throws Exception {
	    Map<String,String> menuMap = null; 
	    Map<String,String> urlMap = null; 
		try {
			/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			HTMain user;
	        if (auth.getPrincipal() instanceof UserDetails) {
	        	user = (HTMain) auth.getPrincipal();
	        } else {
	        	directUrl="/common/login.jsp"; 
	        	return "loginPage";
	        }*/
			User user = null;
			try {
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();				 
				 if(null!=principal ) user=(User)principal;
			} catch (Exception e) { 
				e.printStackTrace();
				directUrl="/common/login.jsp"; 
                return "loginPage";
			}
	        if(null!=user)
	         { 
				if(menuMap == null){
					menuMap = new HashMap<String,String>();
					urlMap = new HashMap<String,String>();
					List<Menu> menuList =(new MenuUtils()).getAll(); 
					for(Menu menu: menuList)
					{
						if(this.roleMenuService.findRoleMenu(user.getRoleString(), menu.getId())&&null!=menu.getPrivilegeValue()&&!"".equals(menu.getPrivilegeValue()))
						//if(this.roleMenuService.findRoleMenu("1", menu.getId())&&null!=menu.getPrivilegeValue()&&!"".equals(menu.getPrivilegeValue()))
						{
							menuMap.put(menu.getPrivilegeValue(), "inline");
							urlMap.put(menu.getHref(), "inline");
						}else
						{
							menuMap.put(menu.getPrivilegeValue(), "none");
							urlMap.put(menu.getHref(),  "none");
						}
					}
					ServletActionContext.getRequest().getSession().setAttribute(UserTags.MENUMAP, menuMap);
					ServletActionContext.getRequest().getSession().setAttribute(UserTags.URLMAP, urlMap);
					ServletActionContext.getRequest().getSession().setAttribute(UserTags.LOGININFO, user);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace(); 
		} 
		return "index";
	}	
	
	
	
	public String loginPage() throws Exception { 
		try {           
			  directUrl="/common/login.jsp"; 
		    }    
		 catch (Exception e) {
			e.printStackTrace(); 
		} 
		return "loginPage";
	}	
	
	/**
	 * 加载后台首页左侧用户有权限访问的链接 
	 */
	public String loadLeftMenu(){
		try {
			User u  = null;
			try {
			     u = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			     this.user = u;
			} catch (Exception e) { 
				e.printStackTrace();
                 directUrl="/common/login.jsp";
                return "index";
			}  
			Set<Menu> rootMenuSet = this.roleMenuService.findRoleMenu(u.getRoleString(),"class",leftParent_id);
			leftMenuMap = new LinkedHashMap<String,List<Menu>>(); 
			//调用排序类     
			List<Menu> rootMenuList=ObjectSwitch.Set2List(rootMenuSet); 
			SortList<Menu> sortList = new SortList<Menu>(); 
		    sortList.Sort(rootMenuList, "getOrder", "asc");    
		    
			for(Menu menu : rootMenuList){ 
				Set<Menu> subMenuSet = this.roleMenuService.findRoleMenu(u.getRoleString(),"link",menu.getId());
				//调用排序类  
				List<Menu> subMenuList=ObjectSwitch.Set2List(subMenuSet); 
				SortList<Menu> sortSubList = new SortList<Menu>(); 
				sortSubList.Sort(subMenuList, "getOrder", "asc");  
				for(Menu menuSub : subMenuList){
					if(null!=menuSub.getHref()) 
					{
						//if(menuSub.getHref().indexOf("/") == 0){
							String v = menuSub.getHref();
							menuSub.setHref(v);
						//}
					}
				}
				 
 				leftMenuMap.put(menu.getCoding(), subMenuList);
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return "loadLeftMenu";
	}
	/**
	 * 加载上面权限
	 */
	public String menuroot(){
		try {
			User u  = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
			Set<Menu> rootMenuSet = this.roleMenuService.findRoleMenu(u.getRoleString(),"class",leftParent_id);
			//leftMenuMap = new LinkedHashMap<String,List<Menu>>(); 
			//调用排序类     
			List<Menu> rootMenuList=ObjectSwitch.Set2List(rootMenuSet); 
			SortList<Menu> sortList = new SortList<Menu>(); 
			sortList.Sort(rootMenuList, "getOrder", "asc");    
			rootmenulist = rootMenuList;
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return "menuroot";
	}
	
	/**
	 * 加载上面权限，修改到topframe.jsp
	 * 并加入mainFrame的内容 信息，userinfo的信息
	 */
	public String menuroot_top(){
		try {
			User u  = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
			this.user = u;
			Set<Menu> rootMenuSet = this.roleMenuService.findRoleMenu(u.getRoleString(),"class",leftParent_id);
			leftMenuMap = new LinkedHashMap<String,List<Menu>>(); 
			//调用排序类     
			List<Menu> rootMenuList=ObjectSwitch.Set2List(rootMenuSet); 
			SortList<Menu> sortList = new SortList<Menu>(); 
			sortList.Sort(rootMenuList, "getOrder", "asc");    
			rootmenulist = rootMenuList;
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return "menuroot_top";
	}
	
	
	public void getList(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			int rows = Integer.parseInt(request.getParameter("rows"));
			//SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

			StringBuilder subsql = new StringBuilder(" 1=1 ");

			ArrayList<Object> args_list = new ArrayList<Object>();
						
			User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String acct_no =u.getUsername();
			
			if (!"".equals(acct_no)&&null!=acct_no) {
				subsql.append(" and acct_no = ?");
				args_list.add(acct_no);
				
			}
			if (StringUtils.isNotBlank(this.qkeyWord)){
				subsql.append(" and pkg_id = ? ");
				args_list.add(qkeyWord.trim());				
			}			 
		
			Object [] args = args_list.toArray();
			
		  if (StringUtils.isNotBlank(this.qkeyWord)){
			List<Map<String, Object>> result = this.userService.queryForList("*","HT_Main",subsql.toString(),args, getPage(), rows);
			int total = this.userService.queryForListTotal("id","HT_Main",subsql.toString(),args);

	
			for (Map<String, Object> obj : result) {
				if (null != obj.get("pkg_id")) {
                     obj.put("showok", true);
					}
			}
			JSONArray object = JSONArray.fromObject(result);
			JSONObject o = new JSONObject();
			o.element("total", total);
			o.element("rows", object);
			//long[] totalData = userService.queryForExpiredListCount(subsql.toString(),args);
			JSONArray footer = new JSONArray();
			JSONObject _totalDate = new JSONObject();
			/*_totalDate.element("MAXAMOUNT", totalData[0]);
			_totalDate.element("CURCANINVEST", totalData[1]);
			_totalDate.element("CURRENYAMOUNT", totalData[2]);
			_totalDate.element("CREATEORG_SHORT", "合计");
			footer.add(_totalDate);
			o.element("footer", footer);*/
		
			ServletActionContext.getResponse().getWriter().write(o.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*public void pdf() {
		try {
			
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			
			
			request.setCharacterEncoding("utf-8");
			String filename = request.getParameter("num");  
	          
	        //设置文件MIME类型  
	        response.setContentType(ServletActionContext.getServletContext().getMimeType(filename));  
	        //设置Content-Disposition  
	     //   response.setHeader("Content-Disposition", "attachment;filename="+filename);
			
	        //读取目标文件，通过response将目标文件写到客户端  
	        //获取目标文件的绝对路径  
	        String fullFileName =  ServletActionContext.getServletContext().getRealPath(inputPath + filename);  
	       // System.out.println(fullFileName);
	        File f=new File(fullFileName);
	       if (f.exists()){
	    	//设置文件MIME类型  
		    response.setContentType(ServletActionContext.getServletContext().getMimeType(filename));  
		   //设置Content-Disposition  
		    response.setHeader("Content-Disposition", "attachment;filename="+filename);   
	        //读取文件
	        InputStream in = new FileInputStream(fullFileName);
	        
	        OutputStream out = response.getOutputStream();  
	          
	        //写文件   
	        byte[] b = new byte[1024];  
	        int len;  
	        while((len=in.read(b)) >0)  
	        out.write(b,0,len);
	        response.setStatus( response.SC_OK );  
	        response.flushBuffer();	        
	        out.close();
	        in.close();
	        f=null;
	       }else{
	    	   response.setContentType("text/html;charset=UTF-8");
	    	 //  response.setCharacterEncoding("UTF-8");
	    	   PrintWriter out = response.getWriter();
	    	   out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
	    	   out.println("<HTML>");
	    	   out.println(" <HEAD><TITLE>错误</TITLE>");
	    	   out.println("</HEAD>");
	    	   out.println(" <BODY>");
	    	   out.println("<p><span class=\"error\">该融资包的保证合同文件暂不存在！</span></p>");
	    	   out.println("<a href=\"/sys_/userAction!list\">返回</a></p>");
	    	   out.println(" </BODY>");
	    	   out.println("</HTML>");
	    	   out.flush();
	    	   out.close();
	       }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}*/

	public String getIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	} 
	
	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parentId) {
		parent_id = parentId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public long getLeftParent_id() {
		return leftParent_id;
	}

	public void setLeftParent_id(long leftParentId) {
		leftParent_id = leftParentId;
	}
 
	public HashMap<String, List<Menu>> getLeftMenuMap() {
		return leftMenuMap;
	}

	public void setLeftMenuMap(HashMap<String, List<Menu>> leftMenuMap) {
		this.leftMenuMap = leftMenuMap;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public Set<Role> getInUserRoleList() {
		return inUserRoleList;
	}

	public void setInUserRoleList(Set<Role> inUserRoleList) {
		this.inUserRoleList = inUserRoleList;
	}

	public List<Role> getNotInUserRoleList() {
		return notInUserRoleList;
	}

	public void setNotInUserRoleList(List<Role> notInUserRoleList) {
		this.notInUserRoleList = notInUserRoleList;
	}

	public String getQkeyWord() {
		return qkeyWord;
	}

	public void setQkeyWord(String qkeyWord) {
		this.qkeyWord = qkeyWord;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public void setRootmenulist(List<Menu> rootmenulist) {
		this.rootmenulist = rootmenulist;
	}

	public List<Menu> getRootmenulist() {
		return rootmenulist;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDirectUrl() {
		return directUrl;
	}

	public void setDirectUrl(String directUrl) {
		this.directUrl = directUrl;
	}
  
	public String getInputPath() {
		return inputPath;
	}
	
	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	
}
