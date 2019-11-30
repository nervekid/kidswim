<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>学员资料查看</title>
	<meta name="decorator" content="default"/>
</head>
<body class="hideScroll">
<form:form id="inputForm" modelAttribute="sysBaseStudent" action="" class="form-horizontal">
	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		<tbody>
		<tr>
			<td class="width-15 active"><label class="pull-right">学员编号：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.code}" />
			</td>
			<td class="width-15 active"><label class="pull-right">中文名：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.nameCn}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">英文名：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.nameEn}" />
			</td>
			<td class="width-15 active"><label class="pull-right">身份证号码：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.idNo}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">性别：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${fns:getDictLabel(sysBaseStudent.sex, 'sex_flag', '')}" />
			</td>
			<td class="width-15 active"><label class="pull-right">电邮：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.email}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">电话号码：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.phone}" />
			</td>
			<td class="width-15 active"><label class="pull-right">出生日期：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="<fmt:formatDate value="${sysBaseStudent.birthday}" pattern="yyyy-MM-dd"/>" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">联系地址：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.contactAddress}" />
			</td>
			<td class="width-15 active"><label class="pull-right">就读学校：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.attendingSchool}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">年级：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.grade}" />
			</td>
			<td class="width-15 active"><label class="pull-right">是否曾学习过游泳：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${fns:getDictLabel(sysBaseStudent.studiedSwimFlag, 'yes_no', '')}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">是否曾遇溺：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${fns:getDictLabel(sysBaseStudent.drownedFlag, 'yes_no', '')}" />
			</td>
			<td class="width-15 active"><label class="pull-right">遇溺岁数：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.drownedAge}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">习泳机构：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.studySwimmingOrgan}" />
			</td>
			<td class="width-15 active"><label class="pull-right">已懂泳式：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.studiedSwimmingStyle}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">长期病患：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.longTermDisease}" />
			</td>
			<td class="width-15 active"><label class="pull-right">长期服药：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.longTermMedicine}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">课程等级：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${fns:getDictLabel(sysBaseStudent.courseLevelFlag, 'courseLevel_flag', '')}" />
			</td>
			<td class="width-15 active"><label class="pull-right">联系人号码：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.contactPhone}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">联系人关系：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.contactRelationship}" />
			</td>
			<td class="width-15 active"><label class="pull-right">紧急联系人号码：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.urgentPhone}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">紧急联系人关系：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.urgentRelationship}" />
			</td>
			<td class="width-15 active"><label class="pull-right">监护人姓名：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.guardianName}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">监护人手机号码：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.guardianPhone}" />
			</td>
			<td class="width-15 active"><label class="pull-right">监护人身份证号码：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.guardianIdNo}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">监护人关系：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.guardianRelationship}" />
			</td>
			<td class="width-15 active"><label class="pull-right">facebook账号：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.facebook}" />
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">whatsapp账号：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="${sysBaseStudent.whatsApp}" />
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