<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglib.jsp"%>
<%@ include file="/common/import.jsp"%>
<html>
	<head>
		<title>新增（修改）商品类型</title>
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
		       $("#producttypeform").validate({
		                rules: {
		                  "ptName":{ required:true},  
		                  "ptCode":{ required:true}
		                },  
		                messages: {
		                  "ptName":{ required:"请填写商品类型名称"},  
		                  "ptCode":{ required:"请填写商品类型编码"}
		                }    
		        });   
		})
</script>
	</head>

	<body>
		<form action="/back/product/productTypeAction!save" method="post" id="producttypeform">
			<input type="hidden" name="isModify" value="true" />
			<table cellspacing="5px;">
			    <tr>
					<td align="right">
						<span style="color:red">*</span><span class="title">商品分类编码：</span>
					</td>
					<td>
						<input class="type_input_box" name="ptCode" type="text" id="ptCode" value="${ptCode}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						<span style="color:red">*</span><span class="title">商品分类名称：</span>
					</td>
					<td>
						<input class="type_input_box" name="ptName"  type="text" id="ptName" value="${ptName}" />
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
