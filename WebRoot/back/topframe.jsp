<%@ page language="java" import="java.util.*,java.text.SimpleDateFormat" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>
 <%@ include file="/common/taglib.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="/wisdoorStatic/css/newtemplate/common.css" type="text/css" />
<jsp:include page="/common/import.jsp"></jsp:include>
<title>管理系统</title>

<script>
	$(function(){
		$("#changepassword").css({'cursor':'pointer'}).click(function(){
			//alert($(this).attr("href"));
			window.top.frames['manFrame'].location=$(this).attr("href");
			return false;
		});
		$("#tuichu").click(function(){
			window.location.href='/sys_/userAction!loginPage?time='+new Date().getTime();
			window.location.parent.reload();
			return false;
			
		});
		
		$("#nav_top_b ul li").click(function(){
            $(this).attr("class","bg_image_onclick").siblings().attr("class","bg_image");
            window.top.frames['leftFrame'].changemenu($(this).attr("tar"));
        });
        
        <%
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
	    %>

	    var days = <%=sdf2.format(now)%>;
	    var date = new Date("<%=sdf.format(now)%>");
	    var serverState = "未知";
	    var hours = null;
	    var minutes = null;
	    var seconds = null;
	    function show_server_time(){
	        date.setSeconds(date.getSeconds()+1);
	        hours = date.getHours()>9?date.getHours():"0"+date.getHours();
	        minutes = date.getMinutes()>9?date.getMinutes():"0"+date.getMinutes(); 
	        seconds = date.getSeconds()>9?date.getSeconds():"0"+date.getSeconds(); 
	        document.getElementById("show_server_time").innerHTML = hours+":"+minutes+":"+seconds;
	    }
	    setInterval(show_server_time,1000);

		
		//获取服务器上的状态信息
		/*function getNewState(){
			$.post("/back/accountDealAction!checkState",function(data,state){
				serverState = data['state'];
				document.getElementById("show_server_state").innerHTML = serverState;
			},'json');
		}
		setInterval(getNewState,20000);*/
		
		
    
    
     
		 
		      
	})
window.onbeforeunload = function() {return '';};
</script>

</head>

<body>
		
<div class="header_content"  id="">
     <div class="logo" style="width:206px;height: 70px" >
     		<img src="/wisdoorStatic/images/newtemplate/LOGO-01.jpg" width="206" height="70" style="float:left;"/>  	    	
     </div> 
     
    <div class="header_content_center" id="nav_top" style="position: absolute;left:206px;top: 45px;height: 25px;width: 100%;">
        <div id="nav_top_b" style="position: absolute;left:0px;background-image: url(/wisdoorStatic/images/newtemplate/nav_bg.jpg);">
        <ul>
            <s:iterator value="rootmenulist" status="sta">
            <s:if test="#sta.index==0"><li class="bg_image_onclick" tar="${coding}"><table cellpadding="0" cellspacing="0"><tr><td width="15"></td><td width="10"></td><td>${name}</td></tr></table></li></s:if>
            <s:else><li class="bg_image" tar="${coding}"><table cellpadding="0" cellspacing="0"><tr><td  width="15"></td><td width="10"></td><td>${name}</td></tr></table></s:else>
            
        </s:iterator>
        </ul>
        </div>
    </div>
     <div class="right_nav" style="float: right;position: relative;height: 40px;line-height: 40px;width: 600px;">
            <div class="text_right" style="margin-right:20px;padding:0;height: 20px;line-height: 20px;padding: 10px 0 0 0">
		        <ul style="list-style:none; margin:0;">

		             <li style="float: left;text-align:center;height: 20px;line-height: 20px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img width="20px" height="20px" alt=" " src="/wisdoorStatic/images/newtemplate/user_info.png"/> </li>
		             <li class="" style="float: left;text-align:center;height: 20px;line-height: 20px;">
		                <a class="white" href="#">${user.username}</a>
		                </li>
		             <li style="float: left;text-align:center;height: 20px;line-height: 20px;">
		             &nbsp;<img src="/wisdoorStatic/images/newtemplate/block.png" width="2px" height="20px" />
		             </li>	  
		            <li style="float: left;text-align:center;height: 20px;line-height: 20px;">
		            &nbsp;<img src="/wisdoorStatic/images/newtemplate/return.png" width="20px" height="20px" alt="点击此处退出系统"/></li>
		            <li style="float: left;text-align:center;height: 20px;line-height: 20px;"><a href="javascript:void(0)" class="hit" id="tuichu">退出</a></li>
		        </ul>
         </div>
      </div>
</div>
</body>
</html>
