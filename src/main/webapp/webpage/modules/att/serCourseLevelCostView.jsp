<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>课程等级对应收费查看</title>
	<meta name="decorator" content="default"/>
</head>
<body class="hideScroll">
<form:form id="inputForm" modelAttribute="serCourseLevelCost" action="" class="form-horizontal">
	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		<tbody>
		<tr>
			<td class="width-15 active"><label class="pull-right">课程等级：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${fns:getDictLabel(serCourseLevelCost.courseLevelFlag, 'courseLevel_flag', '')}" />
			</td>
			<td class="width-15 active"><label class="pull-right">收费：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${serCourseLevelCost.costAmount}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">是否包含入场费：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${fns:getDictLabel(serCourseLevelCost.containEntranceFeeFlag, 'yes_no', '')}" />
			</td>
			<td class="width-15 active"><label class="pull-right">收费标准：</label></td>
			<td class="width-35">
			<input type="text" readonly="readonly" class="form-control" value="${fns:getDictLabel(serCourseLevelCost.costStandardFlag, 'cost_standard_flag', '')}" />
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