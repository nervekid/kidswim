<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>學員管理</title>
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
					loading('正在提交，請稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("輸入有誤，請先更正。");
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
					<td class="width-15 active"><label class="pull-right">身份證：</label></td>
					<td class="width-35">
						<form:input path="idNo" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">性別:</label></td>
					<td class="width-35">
						<form:select placeholder="性別" path="sex"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('sex_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">電郵：</label></td>
					<td class="width-35">
						<form:input path="email" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">電話號碼：</label></td>
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
					<td class="width-15 active"><label class="pull-right">聯系地址：</label></td>
					<td class="width-35">
						<form:input path="contactAddress" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">就讀學校：</label></td>
					<td class="width-35">
						<form:input path="attendingSchool" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">年級：</label></td>
					<td class="width-35">
						<form:input path="grade" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">是否曾學習過遊泳:</label></td>
					<td class="width-35">
						<form:select placeholder="是否曾學習過遊泳" path="studiedSwimFlag"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">習泳機構：</label></td>
					<td class="width-35">
						<form:input path="studySwimmingOrgan" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">已懂泳式 以,號分割</label></td>
					<td class="width-35">
						<form:input path="studiedSwimmingStyle" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">是否曾遇溺:</label></td>
					<td class="width-35">
						<form:select placeholder="是否曾遇溺" path="drownedFlag"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">預溺歲數：</label></td>
					<td class="width-35">
						<form:input path="drownedAge" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">長期病患：</label></td>
					<td class="width-35">
						<form:input path="longTermDisease" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">長期服藥</label></td>
					<td class="width-35">
						<form:input path="longTermMedicine" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">課程等級:</label></td>
					<td class="width-35">
						<form:select placeholder="課程等級" path="courseLevelFlag"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('courseLevel_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">聯系人號碼：</label></td>
					<td class="width-35">
						<form:input path="contactPhone" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">聯系人關系：</label></td>
					<td class="width-35">
						<form:input path="contactRelationship" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">緊急聯系人號碼：</label></td>
					<td class="width-35">
						<form:input path="urgentPhone" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">緊急聯系人關系：</label></td>
					<td class="width-35">
						<form:input path="urgentRelationship" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">監護人姓名：</label></td>
					<td class="width-35">
						<form:input path="guardianName" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">監護人手機號碼：</label></td>
					<td class="width-35">
						<form:input path="guardianPhone" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">監護人身份證號碼：</label></td>
					<td class="width-35">
						<form:input path="guardianIdNo" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">監護人關系：</label></td>
					<td class="width-35">
						<form:input path="guardianRelationship" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">facebook賬號：</label></td>
					<td class="width-35">
						<form:input path="facebook" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">whatsapp賬號：</label></td>
					<td class="width-35">
						<form:input path="whatsApp" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">備註：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="remarks" htmlEscape="false" rows="3"   class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>