<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>系统组织架构管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="sysOrganizational" action="${ctx}/sys/sysOrganizational/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">一级部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="officeOne" name="officeOne.id" value="${sysOrganizational.officeOne.id}" labelName="officeOneName" labelValue="${sysOrganizational.officeOne.name}"
										title="部门" url="/sys/office/treeDataByOwer?organTagA=${sysOrganizational.organTag}" cssClass="form-control required" allowClear="true" notAllowSelectParent="false"/>
					</td>
					<td class="width-15 active"><label class="pull-right">上级部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="companyId" name="companyId" value="${sysOrganizational.companyId}" labelName="companyName" labelValue="${sysOrganizational.companyName}"
							title="部门" url="/sys/office/treeDataByOwer?organTagA=${sysOrganizational.organTag}" cssClass="form-control required" allowClear="true" notAllowSelectParent="false"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">归属部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="office.id" value="${sysOrganizational.office.id}" labelName="office.name" labelValue="${sysOrganizational.office.name}"
							title="部门" url="/sys/office/treeDataByOwer?organTagA=${sysOrganizational.organTag}" cssClass="form-control required" allowClear="true" notAllowSelectParent="false"/>
					</td>
					<td class="width-15 active"><label class="pull-right">人员：</label></td>
					<td class="width-35">
						<sys:treeselect id="user" name="user.id" value="${sysOrganizational.user.id}" labelName="user.name" labelValue="${sysOrganizational.user.name}" disabled="${editFlag}"
							title="用户" url="/sys/user/treeDataAllIncludeSlectd" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"
							placeholder="显示所有用户"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">组织架构：</label></td>
					<td class="width-35">
						<input readonly="readonly" value=${fns:getDictLabel(sysOrganizational.organTag, 'org_flag', '')}  htmlEscape="false"    class="form-control "/>
						<input type="hidden" name="organTag" value=${sysOrganizational.organTag}  htmlEscape="false"    class="form-control "/>
					</td>
					<c:if test="${sysOrganizational.organTag eq '4'}">
						<td class="width-15 active"><label class="pull-right">职级:</label></td>
						<td class="width-35">
							<form:select placeholder="组织架构职级" path="rankFlag"  class="form-control m-b"  >
								<form:option value="" label="请选择"/>
								<form:options items="${fns:getDictList('rank_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
							</form:select>
						</td>
					</c:if>

				</tr>
				<c:if test="${sysOrganizational.organTag eq '4'}">
				<tr>
					<td class="width-15 active"><label class="pull-right">是否上级:</label></td>
					 <td class="width-35">
					 <form:select placeholder="组织架构职级" path="superiorFlag"  class="form-control m-b required"  >
								<form:option value="" label="请选择"/>
								<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
							</form:select>
					 </td>
				</tr>
				</c:if>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="remarks" htmlEscape="false" rows="3"   class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>