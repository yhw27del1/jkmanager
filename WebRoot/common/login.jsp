<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎登录综合管理系统</title>
<link href="/assets/css/style.css" rel="stylesheet"/>
<script type="text/javascript" src="/assets/js/jquery.js"></script>
<script type="text/javascript" src="/assets/js/cloud.js"></script>
<link href="/wisdoorStatic/css/jquery-ui-1.8.16.custom.css" rel="stylesheet">
<script src="/wisdoorStatic/js/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="/wisdoorStatic/js/jquery.keyboard.js"></script>
<script type="text/javascript" src="/wisdoorStatic/js/dialogHelper.js"></script>
<script type="text/javascript" src="/wisdoorStatic/js/md5.js"></script>

<script language="javascript">
	$(function(){
     $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	 $(window).resize(function(){  
         $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
      });
	 
	 $("#code").click(function() { 
			var timenow = new Date().getTime(); 
				$( this ).attr("src", "/randomMyCode.jsp?Height=14&CodeType=3&d=" + timenow);
	 	});
	 
	 
	 $("#loginId" ).click(function() {
		  
	     if ($("#userName").val() == "") { 
           messageTip("用户名不能为空!");
            $("#userCode").val('');
            $( "#code" ).click();
            return false;
         }
		
         if ($("#userCode").val() == "") { 
            messageTip("验证码不能为空!");
            $( "#code" ).click();
            return false;
         }	
         
         	$.getJSON("/sysCommon/sysUserAction!validateCode?time="+new Date().getTime()+"&validCode="+$("#userCode").val(),function(data){
		         
		         if(0==data){ 
	                 messageTip("验证码不能为空!");
	                 $("#userCode").val('');
	                 $( "#code" ).click();
                     return false;   
	              }
	            if(1==data){ 
	               messageTip("验证码不正确!");
	               $("#userCode").val('');
	               $( "#code" ).click();
                   return false;
	            }
	            else{
	            	var userPassword=hex_md5($("#userPassword").val());
	                 $.getJSON("/sysCommon/sysUserAction!validateUser2?time="+new Date().getTime()+"&userName="+$("#userName").val()+"&password="+userPassword,function(data){
		                 if(9==data||8==data){ 
	                        messageTip("用户名或密码错误!");
	                        $("#userPassword").val('');	
	                        $("#userCode").val('');
	                        $( "#code" ).click();		                        
                            return false;   
	                     }			                    
	                    
	                     
	                     if(6==data){ 
	                     $("#userPassword").val(userPassword);
	                      $("#form1").submit(); 
	                     }
	                     
	                 });
	             }
	       });             			       
		   return false;

	   });	
   });
	
	
	  function isTop(){  
	       if(parent.window != window){ 
	       		var form = document.forms[0];  
		          form.action="/sys_/userAction!loginPage";
		          form.target="_top";
		          form.submit();
	         }
    }
	 
	 function messageTip(message){ 
	        var dlgHelper = new dialogHelper();
	        dlgHelper.set_Title("系统提示");
	        dlgHelper.set_Msg(message);
	        dlgHelper.set_Height("150");
	        dlgHelper.set_Width("400");
	        dlgHelper.set_Buttons({
	            '关闭': function(ev) { 
	                    $(this).dialog('close'); 
	            } 
	        });      
	        dlgHelper.open();  
   }
	 
	
</script> 

</head>

<body style="background-color:#df7611; background-image:url(/static/images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;" id="login" onload="isTop();">

  <div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
   </div>  

   <div class="logintop">    
       <span>欢迎登录综合管理平台</span>    
       <ul>
      <!--  <li><a href="#">回首页</a></li> -->
        <li><a href="#">帮助</a></li>
        <li><a href="#">关于</a></li>
       </ul>    
    </div>
    
    <div class="loginbody">   
       <span class="systemlogo"></span>
       <form id="form1" name="form1" action="j_spring_security_check" method="post" >      
       <div class="loginbox loginbox3">   
        <ul>
          <li><input name="username" id="userName" type="text" class="loginuser" /></li>
          <li><input name="password" id="userPassword" type="password" class="loginpwd"/></li>
          <li class="yzm">
<!--              <label class="loginlabel" for="userPassword"> &nbsp;&nbsp;&nbsp;&nbsp;验证码:</label> -->
            <span><input id="userCode" name="userCode" type="text"/></span>
            <cite>  
                 <img id="code" border="0" src="/randomMyCode.jsp?Height=14&d=<%=Math.random() * 100000%>&CodeType=3" alt="点击刷新" title="点击刷新" height="46" width="114px;"/>
			</cite> 
         </li>
         <li><button id="loginId" class="loginbtn">登录</button> &nbsp;&nbsp;<font color="red">${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message} </font>
                	<s:actionerror cssStyle="font-size:13px;color:#B82525"/>
                	<!--   <label><input name="" type="checkbox" value="" checked="checked" />记住密码</label><label><a href="#">忘记密码？</a></label>--></li> 
        </ul>
      </div> 
      </form>  
    </div>   
    <div class="loginbm">2016  Copyright ©. 云南康健溢生健康管理有限公司  All rights reserved
    </div> 
    <%  
	   Enumeration e=session.getAttributeNames();  
	   while(e.hasMoreElements()){
	       String sessionName=(String)e.nextElement(); 
		   if(!"_TXPT_AUTHKEY".equals(sessionName))
		   {
		     session.removeAttribute(sessionName); 
		   }
		    
	   }
	 %>     
</body>

</html>
