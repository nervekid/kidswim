<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>实体功能对应组织架构管理</title>
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
		<form:form id="inputForm" modelAttribute="sysTEntityuseorgan" action="${ctx}/sys/sysTEntityuseorgan/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<c:choose>
	        			<c:when test="${isAdd eq true}">
							<td class="width-15 active"><label class="pull-right">数据库表名：</label></td>
								<td class="width-35">
									<form:select path="dataTableName" class="form-control ">
			                    		<form:options name="dataTableName" itemValue="name" itemLabel="nameAndComments" items="${tableList}"/>
			                		</form:select>
								</td>
	       				</c:when>
	       				<c:otherwise>
	       					<td class="width-15 active"><label class="pull-right">数据库表名：</label></td>
								<td class="width-35">
									<form:input path="dataTableName" htmlEscape="false"   readonly="true" class="form-control "/>
								</td>
	        			</c:otherwise>
	     			</c:choose>

					<td class="width-15 active"><label class="pull-right">组织架构：</label></td>
					<td class="width-35">
						<form:select placeholder="组织架构" path="organTag"  class="form-control m-b"  style="width:200px;">
						<form:option value="" label="请选择"/>
						<form:options items="${fns:getDictList('org_flag')}" name="organTag" itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>