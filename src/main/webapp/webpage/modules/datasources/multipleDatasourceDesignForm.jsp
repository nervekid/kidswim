<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>多数据配置设计管理</title>
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
        function executeSql(){
        	var sqlText = $("#sqlText").val();
        	var dataSourceConfigId = $("#dataSourceConfigId").val();
            $.post("${ctx}/datasources/multipleDatasourceDesign/loadSqlColumns",
                {
                    sqlText:sqlText,
                    dataSourceConfigId:dataSourceConfigId
                },
                function(data,status){
                    $("#column").html(data);
                });
        }
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="multipleDatasourceDesign" action="${ctx}/datasources/multipleDatasourceDesign/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
			   <tr>
				   <td  class="width-15 active">
					   <label class="pull-right">名称:</label></td>
				   <td class="width-35" >
					   <form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/>
				   </td>
				   <td  class="width-15 active">
					   <label class="pull-right">数据源:</label>
				   </td>
				   <td  class="width-35" >
					   <form:select id="dataSourceConfigId" path="dataSourceConfig.id" class="form-control required">
						   <c:forEach items="${dataSourceConfigList}" var="dataSourceConfig">
								<form:option value="${dataSourceConfig.id}">${dataSourceConfig.name}</form:option>
						   </c:forEach>
					   </form:select>
				   </td>
			   </tr>
			   <tr>
				   <td  class="width-15 active">
					   <label class="pull-right">状态:</label></td>
				   <td class="width-35" >
					   <form:select path="status" class="form-control required">
						   <form:options items="${fns:getDictList('multip_datasource_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					   </form:select>
				   </td>
				   <td  class="width-15 active">
					   <label class="pull-right">备注:</label>
				   </td>
				   <td  class="width-35" >
					   <form:textarea id="remarks" htmlEscape="true" path="remarks" rows="3" maxlength="200" class="input-xxlarge"/>
				   </td>
			   </tr>

			   <tr>
				   <td  class="width-15 active">
					   <label class="pull-right">sql语句:</label></td>
				   <td class="width-85" colspan="3" >
					   <form:textarea id="sqlText" htmlEscape="true" path="sqlText" rows="4" maxlength="400" class="input-xxlarge required"/>
					   <button  class="btn btn-primary  " onclick="executeSql()" ><i class="fa fa-search"></i> 执行语句</button>
				   </td>
			   </tr>
		 	</tbody>
		</table>
			<div id="column">

			</div>

			<div class="form-actions">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
	</form:form>
</body>
</html>