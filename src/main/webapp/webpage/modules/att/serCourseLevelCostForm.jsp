<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>課程等級對應收費管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回調函數，在編輯和保存動作時，供openDialog調用提交表單。
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

		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="serCourseLevelCost" action="${ctx}/att/serCourseLevelCost/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>

		   		<tr>
					<td class="width-15 active"><label class="pull-right">課程等級：</label></td>
					<td class="width-35">
						<form:select placeholder="課程等級" path="courseLevelFlag"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('course_level')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">課程地址：</label></td>
					<td class="width-35">
						<form:select placeholder="課程等級" path="courseAddress"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('course_addrese_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>

				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">收費 單位(港幣)：</label></td>
					<td class="width-35">
						<form:input path="costAmount" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">是否包含入場費：</label></td>
					<td class="width-35">
						<form:select placeholder="是否包含入場費" path="containEntranceFeeFlag"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">收費標準：</label></td>
					<td class="width-35">
						<form:select placeholder="收費標準" path="costStandardFlag"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('cost_standard_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
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