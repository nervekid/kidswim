<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>多组织架构用户对应数据权限组查看</title>
	<meta name="decorator" content="default"/>
</head>

<body class="hideScroll">
<form:form id="inputForm" modelAttribute="sysTDataaccessuser" action="" class="form-horizontal">
	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		<tbody>
		<tr>
			<td class="width-15 active"><label class="pull-right">数据权限组：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysTDataaccessuser.dataAccessName}" />
			</td>
		</tr>
		<tr>
					<td class="width-15 active"><label class="pull-right">创建人：</label></td>
					<td class="width-35" >
						<input type="text" readonly="readonly" class="form-control" value="${sysTDataaccessuser.createName}" />
					</td>
					<td class="width-15 active"><label class="pull-right">创建时间：</label></td>
					<td class="width-35">
						<input type="text" readonly="readonly" class="form-control"  value="<fmt:formatDate value='${sysTDataaccessuser.createDate}' pattern='yyyy-MM-dd  HH:mm'/>" />
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">更新人：</label></td>
					<td class="width-35" >
						<input type="text" readonly="readonly" class="form-control" value="${sysTDataaccessuser.updateName}" />
					</td>
					<td class="width-15 active"><label class="pull-right">更新时间：</label></td>
					<td class="width-35">
						<input type="text" readonly="readonly" class="form-control"  value="<fmt:formatDate value='${sysTDataaccessuser.updateDate}' pattern='yyyy-MM-dd  HH:mm'/>" />
					</td>
				</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">备注：</label></td>
			<td class="width-35" colspan="3">
				<form:textarea path="remarks" htmlEscape="false" rows="3" readonly="true"   class="form-control "/>
			</td>
		</tr>
		</tbody>
	</table>
</form:form>
</body>
</html>