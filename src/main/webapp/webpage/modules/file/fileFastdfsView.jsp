<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">

	</script>
</head>
<body class="hideScroll">
<form:form id="inputForm" modelAttribute="fileFastdfs"  method="POST" class="form-horizontal">
	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		<tbody>
		<tr>
			<td class="width-15 active"><label class="pull-right">文件名称：</label></td>
			<td class="width-35">
				<form:input path="name" htmlEscape="false"  readonly="true"   class="form-control "/>
			</td>
			<td class="width-15 active"><label class="pull-right">文件目录：</label></td>
			<td class="width-35">
				<form:input path="catalogName" htmlEscape="false"  readonly="true"   class="form-control "/>
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">文件大小：</label></td>
			<td class="width-35">
				<form:input path="sizeStr" htmlEscape="false"  readonly="true"   class="form-control "/>
			</td>
			<td class="width-15 active"><label class="pull-right">文件等级：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${fns:getDictLabel(fileFastdfs.level, 'file_level', '')}" />
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">文件类型</label></td>
			<td class="width-35">
				<form:input path="type" htmlEscape="false"  readonly="true"   class="form-control "/>
			</td>
			<td class="width-15 active"><label class="pull-right">文件分组</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${fns:getDictLabel(fileFastdfs.group, 'file_group', '')} " />
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">创建人</label></td>
			<td class="width-35">
				<input type="type" readonly="readonly" class="form-control" value="${fileFastdfs.createBy.name}" />
			</td>
			<td class="width-15 active"><label class="pull-right">创建时间</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="<fmt:formatDate value="${fileFastdfs.createDate}" pattern="yyyy-MM-dd"/>" />
			</td>
		</tr>

		<c:if test="${fileFastdfs.delFlag eq '1'}">
			<tr>
				<td class="width-15 active"><label class="pull-right">删除人</label></td>
				<td class="width-35">
					<form:input path="delName" htmlEscape="false"  readonly="true"   class="form-control "/>
				</td>
				<td class="width-15 active"><label class="pull-right">删除时间</label></td>
				<td class="width-35">
					<input type="text" readonly="readonly" class="form-control" value="<fmt:formatDate value="${fileFastdfs.delDate}" pattern="yyyy-MM-dd"/>" />
				</td>
			</tr>
		</c:if>

		<tr>
			<td class="width-15 active"><label class="pull-right">备注：</label></td>
			<td class="width-35" colspan="3">
				<form:textarea path="remarks" htmlEscape="false" rows="3"  readonly="true"   class="form-control "/>
			</td>
		</tr>
		</tbody>
	</table>
</form:form>
</body>
</html>