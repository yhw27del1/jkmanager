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
<script type="text/javascript" src="/wisdoorStatic/js/jquery.keyboard.js"></script>
<script type="text/javascript" src="/wisdoorStatic/js/dialogHelper.js"></script>
<script type="text/javascript" src="/wisdoorStatic/js/md5.js"></script>

<script language="javascript">
	$(function(){
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	$(window).resize(function(){  
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
    })  
});  
</script> 

</head>

<body style="background-color:#df7611; background-image:url(/static/images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">



    <div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
    </div>  


<div class="logintop">    
    <span>欢迎登录后台管理界面平台</span>    
    <ul>
    <li><a href="#">回首页</a></li>
    <li><a href="#">帮助</a></li>
    <li><a href="#">关于</a></li>
    </ul>    
    </div>
    
    <div class="loginbody">
    
    <span class="systemlogo"></span> 
       
    <div class="loginbox loginbox3">
    
    <ul>
    <li><input name="" type="text" class="loginuser" value="admin" onclick="JavaScript:this.value=''"/></li>
    <li><input name="" type="text" class="loginpwd" value="密码" onclick="JavaScript:this.value=''"/></li>
    <li class="yzm">
    <span><input name="" type="text" value="验证码" onclick="JavaScript:this.value=''"/></span><cite>X3D5S</cite> 
    </li>
    <li><input name="" type="button" class="loginbtn" value="登录"  onclick="javascript:window.location='main.html'"  /><label><input name="" type="checkbox" value="" checked="checked" />记住密码</label><label><a href="#">忘记密码？</a></label></li>
    </ul>
    
    
    </div>
    
    </div>
    
    
    <div class="loginbm">2016  Copyright ©. 云南康健溢生健康管理有限公司  All rights reserved</a></div>
	
    
</body>

</html>
