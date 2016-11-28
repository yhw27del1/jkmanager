<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglib.jsp"%>
<%@ include file="/common/import.jsp"%>
<html>
	<head>
		<title>新增（修改）商务中心</title>
		<script type="text/javascript"
			src="/wisdoorStatic/js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript"
			src="/wisdoorStatic/js/tree/dhtmlXTree/dhtmlxcommon.js"></script>
		<script type="text/javascript"
			src="/wisdoorStatic/js/tree/dhtmlXTree/dhtmlxtree.js"></script>
		<script type="text/javascript" src="/wisdoorStatic/js/validate/jquery.validate.js"></script>
		<link rel="stylesheet" type="text/css"
			href="/wisdoorStatic/js/tree/dhtmlXTree/dhtmlxtree.css">
		<link rel="stylesheet" href="/wisdoorStatic/js/validate/validateself-skin1.css" type="text/css"/>
		<link rel="stylesheet" href="/wisdoorStatic/css/member.css"
			type="text/css" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript">
		$(function() {    
		       $("#businesstypeform").validate({
		                rules: {
		                  "centerName":{ required:true},  
		                  "code":{ required:true}
		                },  
		                messages: {
		                  "centerName":{ required:"请填写中心名称"},  
		                  "code":{ required:"请填写中心编码"}
		                }    
		        });   
		})
</script>
	</head>

	<body>
		<form action="/back/member/businessCenterAction!save" method="post" id="businesstypeform">
			<input type="hidden" name="isModify" value="true" />
			<table cellspacing="5px;">
			    <tr>
					<td align="right">
						<span style="color:red">*</span><span class="title">中心编码：</span>
					</td>
					<td>
						<input class="type_input_box" name="code" type="text" id="code" value="${code}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						<span style="color:red">*</span><span class="title">中心名称：</span>
					</td>
					<td>
						<input class="type_input_box" name="centerName" id="centerName" type="text" value="${centerName}" />
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<input type="hidden" name="menuIds" id="menuIds" />
						<input type="hidden" name="id" value="${id}" />
						<input type="hidden" name="modify" value="${modify}" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="ui-state-default" type="submit" value="保存" />
					</td>
				</tr>
			</table>
		</form>
		<div id="treebox_tree"
			style="width: 300px; height300px: border :   0px"></div>
	</body>
</html>
