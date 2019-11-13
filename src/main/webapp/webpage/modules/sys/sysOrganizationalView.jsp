<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>系统组织架构查看</title>
	<meta name="decorator" content="default"/>
</head>
<body class="hideScroll">
<form:form id="inputForm" modelAttribute="sysOrganizational" action="" class="form-horizontal">
	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		<tbody>
		<tr>
			<td class="width-15 active"><label class="pull-right">上级部门：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysOrganizational.companyName}" />
			</td>
			<td class="width-15 active"><label class="pull-right">一级部门：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysOrganizational.officeOne.name}" />
			</td>
		</tr>
		<tr>
		<td class="width-15 active"><label class="pull-right">归属部门：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysOrganizational.office.name}" />
			</td>
			<td class="width-15 active"><label class="pull-right">人员：</label></td>
			<td class="width-35">
			<input type="text" readonly="readonly" class="form-control" value="${sysOrganizational.user.name}" />
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">组织架构：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${fns:getDictLabel(sysOrganizational.organTag, 'org_flag', '')}" />
			</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">创建人：</label></td>
					<td class="width-35" >
						<input type="text" readonly="readonly" class="form-control" value="${sysOrganizational.createName}" />
					</td>
					<td class="width-15 active"><label class="pull-right">创建时间：</label></td>
					<td class="width-35">
						<input type="text" readonly="readonly" class="form-control"  value="<fmt:formatDate value='${sysOrganizational.createDate}' pattern='yyyy-MM-dd  HH:mm'/>" />
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">更新人：</label></td>
					<td class="width-35" >
						<input type="text" readonly="readonly" class="form-control" value="${sysOrganizational.updateName}" />
					</td>
					<td class="width-15 active"><label class="pull-right">更新时间：</label></td>
					<td class="width-35">
						<input type="text" readonly="readonly" class="form-control"  value="<fmt:formatDate value='${sysOrganizational.updateDate}' pattern='yyyy-MM-dd  HH:mm'/>" />
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