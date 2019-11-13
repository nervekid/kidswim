<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>定时任务管理</title>
	<meta name="decorator" content="default"/>

	<script src="${ctxStatic}/cron/cronGen.js"></script>
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
            $("#cronExpression").cronGen();
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
		<form:form id="inputForm" modelAttribute="taskScheduleJob" action="${ctx}/sys/taskScheduleJob/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
		<input type="hidden" name="jobStatus" value="0">
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   <tr>
			   <td class="width-15 active"><label class="pull-right"><font color="red">*</font>任务分组：</label></td>
			   <td class="width-35">
				   <form:input path="jobGroup" htmlEscape="false"    class="form-control required"/>
			   </td>
			   <td class="width-15 active"><label class="pull-right"><font color="red">*</font>任务名：</label></td>
			   <td class="width-35">
				   <form:input path="jobName" htmlEscape="false"    class="form-control required"/>
			   </td>
		   </tr>
		   <tr>
			   <td class="width-15 active"><label class="pull-right"><font color="red">*</font>包名+类名：</label></td>
			   <td class="width-35" colspan="3">
				   <form:input path="beanClass" htmlEscape="false"    class="form-control required"/>
			   </td>
		   </tr>
			<tr>
				<td class="width-15 active"><label class="pull-right"><font color="red">*</font>方法名：</label></td>
				<td class="width-35">
					<form:input path="methodName" htmlEscape="false"    class="form-control required"/>
				</td>
				<td class="width-15 active"><label class="pull-right"><font color="red">*</font>cron表达式：</label></td>
				<td class="width-35">
					<form:input path="cronExpression" htmlEscape="false"    class="form-control required"/>
				</td>

			</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">任务描述：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="description"  rows="5" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>