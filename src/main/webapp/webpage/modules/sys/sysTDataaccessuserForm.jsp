<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>多组织架构用户对应数据权限组管理</title>
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
		<form:form id="inputForm" modelAttribute="sysTDataaccessuser" action="${ctx}/sys/sysTDataaccessuser/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">数据权限组：</label></td>
					<td class="width-35">
						<select id="dateAccessId" name="dateAccessId" class="form-control" type="select" >
                        <c:forEach var="dataAccess" items="${dataAccessList}" varStatus="status">
                            <option value="${dataAccess.id}" title="${dataAccess.name}" }>${dataAccess.name}</option>
                        </c:forEach>
                </select>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>


</body>
</html>