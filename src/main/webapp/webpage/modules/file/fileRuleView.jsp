<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文件权限规则管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body class="hideScroll">
<form:form id="inputForm" modelAttribute="fileRule" action="${ctx}/file/fileRule/save" method="post" class="form-horizontal">
	<form:hidden path="id" id="pathId" value="${fileRule.id}" />
	<form:hidden path="menuId"/>
	<sys:message content="${message}"/>
	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		<tbody>
		<tr>
			<td class="width-15 active"><label class="pull-right">规则名称：</label></td>
			<td class="width-35">
				<form:input path="name" htmlEscape="false"  readonly="true"   class="form-control"/>
			</td>
			<td class="width-15 active"><label class="pull-right">部门：</label></td>
			<td class="width-35">
				<form:textarea id="deptName" path="deptName" htmlEscape="false" rows="4"  readonly="true"  class="form-control "/>
				<form:hidden path="deptId"/>
			</td>

		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">用户：</label></td>
			<td class="width-35">
				<form:textarea path="userName" id="userName" htmlEscape="false" rows="4"  readonly="true"  class="form-control "/>
			</td>
			<td class="width-15 active"><label class="pull-right">备注：</label></td>
			<td class="width-35">
				<form:textarea path="remarks" htmlEscape="false" readonly="true"   rows="4"  class="form-control "/>
			</td>
		</tr>
		</tbody>
	</table>
</form:form>


</body>
</html>