<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>多数据配置管理</title>
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
		<form:form id="inputForm" modelAttribute="dataSourceConfig" action="${ctx}/datasources/dataSourceConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
			<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
				<tbody>
				<tr>
					<td  class="width-15 active">
						<label class="pull-right">数据源名称:</label></td>
					<td class="width-35" >
						<form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/>
					</td>
					<td  class="width-15 active">
						<label class="pull-right">数据库类型:</label>
					</td>
					<td  class="width-35" >
						<form:select path="dataType" class="form-control required">
							<form:options items="${fns:getDictList('data_source_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td  class="width-15 active">
						<label class="pull-right">用户名:</label>
					</td>
					<td  class="width-35" >
						<form:input path="user" type="password" htmlEscape="false" maxlength="50" class="form-control required"/>
					</td>
					<td  class="width-15 active">
						<label class="pull-right">密码:</label>
					</td>
					<td class="width-35" >
						<form:input type="password" path="password" htmlEscape="false" maxlength="50" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td  class="width-15 active">
						<label class="pull-right">状态:</label>
					</td>
					<td  class="width-35" >
						<form:select path="status" class="form-control required">
							<form:options items="${fns:getDictList('multip_datasource_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td  class="width-15 active"><label class="pull-right">JDBC连接字符串:</label></td>
					<td  class="width-35" >
						<form:textarea id="jdbcUrl" htmlEscape="true" path="jdbcUrl" rows="4" maxlength="200" class="input-xxlarge"/>
					</td>
				</tr>
				</tbody>
			</table>
	</form:form>
</body>
</html>