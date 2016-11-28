<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="/wisdoorStatic/css/newtemplate/common.css" type="text/css" />
<link rel="shortcut icon" href="/wisdoorStatic/images/logo_ico.ico"/>
<!--  <link rel="bookmark" href="/wisdoorStatic/images/logo_ico.png" type="image/x-icon" />-->
<title>昆明小微企业金融交易服务</title>
<jsp:include page="/common/import.jsp"></jsp:include> 

</head> 
<frameset rows="72,*,30" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="userAction!menuroot_top" name="topFrame" frameborder="no" scrolling="no" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset name="myFrame" id="myFrame" cols="206,*" frameborder="no" border="0" framespacing="0">
  	<frameset name="myFrame" rows="0,*" frameborder="no" border="0" framespacing="0">
    	<frame src="" frameborder="no" scrolling="no" noresize="noresize" />
    	<frame src="userAction!loadLeftMenu" name="leftFrame" frameborder="no" scrolling="auto" noresize="noresize" id="leftFrame" title="leftFrame" />
    </frameset>
    <frameset rows="33,*" cols="*" frameborder="no" border="0" framespacing="0">
         <frame src="/back/mainframe.jsp" name="mainFrame" frameborder="no" scrolling="no"  noresize="noresize" id="mainFrame" title="mainFrame" />
         <frame src="#" name="manFrame" frameborder="no" id="manFrame" title="manFrame" />
     </frameset>
  </frameset>
  <frame src="/back/foot.jsp" name="footFrame" frameborder="no" scrolling="no" noresize="noresize" id="footFrame" title="footFrame" />
</frameset>
<noframes><body>
<div id="iddiv" style="width: 100%;height: 100%"></div>
</body>
</noframes>
</html>
