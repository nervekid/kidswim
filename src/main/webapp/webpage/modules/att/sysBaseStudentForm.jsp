<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>学员管理</title>
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
			laydate.render({
                elem: '#birthday',
				trigger:'click'
            });

		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="sysBaseStudent" action="${ctx}/att/sysBaseStudent/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>

		   		<tr>
					<td class="width-15 active"><label class="pull-right">中文名：</label></td>
					<td class="width-35">
						<form:input path="nameCn" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">英文名：</label></td>
					<td class="width-35">
						<form:input path="nameEn" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">身份证：</label></td>
					<td class="width-35">
						<form:input path="idNo" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">性别:</label></td>
					<td class="width-35">
						<form:select placeholder="性别" path="sex"  class="form-control m-b"  >
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('sex_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">电邮：</label></td>
					<td class="width-35">
						<form:input path="email" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">电话号码：</label></td>
					<td class="width-35">
						<form:input path="phone" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">出生日期：</label></td>
					<td class="width-35">
						<input id="birthday" name="birthday"  type="text"  class="form-control required"
						value="<fmt:formatDate value="${birthday}" pattern="yyyy-MM-dd"/>" />
					</td>
					<td class="width-15 active"><label class="pull-right">联系地址：</label></td>
					<td class="width-35">
						<form:input path="contactAddress" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">就读学校：</label></td>
					<td class="width-35">
						<form:input path="attendingSchool" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">年级：</label></td>
					<td class="width-35">
						<form:input path="grade" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">是否曾学习过游泳:</label></td>
					<td class="width-35">
						<form:select placeholder="是否曾学习过游泳" path="studiedSwimFlag"  class="form-control m-b"  >
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">习泳机构：</label></td>
					<td class="width-35">
						<form:input path="studySwimmingOrgan" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">已懂泳式 以,号分割</label></td>
					<td class="width-35">
						<form:input path="studiedSwimmingStyle" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">是否曾遇溺:</label></td>
					<td class="width-35">
						<form:select placeholder="是否曾遇溺" path="drownedFlag"  class="form-control m-b"  >
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">预溺岁数：</label></td>
					<td class="width-35">
						<form:input path="drownedAge" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">长期病患：</label></td>
					<td class="width-35">
						<form:input path="longTermDisease" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">长期服药</label></td>
					<td class="width-35">
						<form:input path="longTermMedicine" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">课程等级:</label></td>
					<td class="width-35">
						<form:select placeholder="课程等级" path="courseLevelFlag"  class="form-control m-b"  >
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('courseLevel_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">联系人号码：</label></td>
					<td class="width-35">
						<form:input path="contactPhone" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">联系人关系：</label></td>
					<td class="width-35">
						<form:input path="contactRelationship" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">紧急联系人号码：</label></td>
					<td class="width-35">
						<form:input path="urgentPhone" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">紧急联系人关系：</label></td>
					<td class="width-35">
						<form:input path="urgentRelationship" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">监护人姓名：</label></td>
					<td class="width-35">
						<form:input path="guardianName" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">监护人手机号码：</label></td>
					<td class="width-35">
						<form:input path="guardianPhone" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">监护人身份证号码：</label></td>
					<td class="width-35">
						<form:input path="guardianIdNo" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">监护人关系：</label></td>
					<td class="width-35">
						<form:input path="guardianRelationship" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">facebook账号：</label></td>
					<td class="width-35">
						<form:input path="facebook" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">whatsapp账号：</label></td>
					<td class="width-35">
						<form:input path="whatsApp" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

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