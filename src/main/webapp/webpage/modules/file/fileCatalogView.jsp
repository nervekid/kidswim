<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文档目录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

	</script>
</head>
<body>
<form:form id="inputForm" modelAttribute="fileCatalog" action="" method="post" class="form-horizontal">
	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		<tbody>
		<tr>
			<td class="width-15 active"><label class="pull-right">上级目录：</label></td>
			<td class="width-35">
				<form:input path="parentName" htmlEscape="false" readonly="true" class="form-control "/>
			</td>

			<td class="width-15 active"><label class="pull-right">文件目录名称：</label></td>
			<td class="width-35">
				<form:input path="name" htmlEscape="false"  readonly="true"  class="form-control "/>
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">备注：</label></td>
			<td colspan="3" >
				<form:textarea path="remarks" htmlEscape="false" readonly="true" rows="3" maxlength="255" class="form-control "/>
			</td>
		</tr>
		</tbody>
	</table>
</form:form>
</body>
</html>