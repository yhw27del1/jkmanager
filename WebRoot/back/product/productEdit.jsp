<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglib.jsp"%>
<%@ include file="/common/import.jsp"%>
<html>
	<head>
		<title>新增（修改）商品</title>
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
		       $("#productform").validate({
		                rules: {
		                  "pName":{ required:true},  
		                  "pCode":{ required:true}
		                },  
		                messages: {
		                  "pName":{ required:"请填写商品名称"},  
		                  "pCode":{ required:"请填写商品编码"}
		                }    
		        });   
		})
</script>
	</head>

	<body>
		<form action="/back/product/productAction!save" method="post" id="productform">
			<input type="hidden" name="isModify" value="true" />
			<table cellspacing="5px;">
			     <tr>
                    <td align="right">
						<input type="hidden" name="id" value="${id}" />
						<span style="color: red">*</span><span class="title">商品类型：</span>
					</td>
					<td>
							<s:select id="pTypeId" cssClass="input_select" name="pTypeId" 
									        headerKey="" headerValue="请选择" list="productTypes" 
									        listKey="id" listValue="ptname" onchange="changeProductType()"
											cssStyle="padding:1px;width:170px;">
							</s:select>
							<s:if test="null==id">
								<span id="p_type_name" style="display: none;"></span>
							</s:if>
							<s:else>									      
								<span>${product.productType.name}</span>
							</s:else>
					</td>
				</tr>
			    <tr>
					<td align="right">
						<span style="color:red">*</span><span class="title">商品编码：</span>
					</td>
					<td>
						<input class="type_input_box" name="pcode" type="text" id="pcode" value="${pcode}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						<span style="color:red">*</span><span class="title">商品名称：</span>
					</td>
					<td>
						<input class="type_input_box" name="pname"  type="text" id="pname" value="${pname}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						<span style="color:red">*</span><span class="title">商品单价：</span>
					</td>
					<td>
						<input class="type_input_box" name="price"  type="text" id="price" value="${price}" />
					</td>
				</tr>
				<tr>
					<td align="right">
						<span style="color:red">*</span><span class="title">积分：</span>
					</td>
					<td>
						<input class="type_input_box" name="pjf"  type="text" id="pjf" value="${pjf}" />
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
