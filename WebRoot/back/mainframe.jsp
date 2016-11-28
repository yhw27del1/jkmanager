<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="/wisdoorStatic/css/newtemplate/common.css" type="text/css" />
<title>管理导航区域</title>
<jsp:include page="/common/import.jsp"></jsp:include>
</head>
<script  type="text/javascript">
function Submit_onclick(){
    if(window.top.document.getElementById('myFrame').cols == "206,*") {
        window.top.document.getElementById('myFrame').cols = "0,*";
        document.getElementById("ImgArrow").src="/wisdoorStatic/images/newtemplate/show_1.png";
        document.getElementById("ImgArrow").alt="打开左侧导航栏";
    } else {
        window.top.document.getElementById('myFrame').cols = "206,*";
        document.getElementById("ImgArrow").src="/wisdoorStatic/images/newtemplate/hold_1.png";
        document.getElementById("ImgArrow").alt="隐藏左侧导航栏";
    }
}

function MyLoad() {
    if(window.parent.location.href.indexOf("MainUrl")>0) {
        window.top.midFrame.document.getElementById("ImgArrow").src="/wisdoorStatic/images/newtemplate/show_1.png";
    }
}
</script>
<body>
<div id="nav">

</div>
<div id="sub_info" >&nbsp;&nbsp;
<div style="position: relative;float: left;width: 40px;height: 23px;margin-left:12px;">
<a href="javascript:Submit_onclick()"><img src="/wisdoorStatic/images/newtemplate/hold_1.png" alt="隐藏左侧导航栏" id="ImgArrow"  style="width: 20px;height: 20px;"/></a>
</div>
<div id="show_text" style="height:30px;overflow:hidden;width:900px;float:left;margin-left:10px;margin-top:-10px;">
</div>
</div>
</body>
</html>
