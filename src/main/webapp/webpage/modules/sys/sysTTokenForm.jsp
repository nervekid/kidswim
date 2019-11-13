<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>token配置信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
			var id = $("#id").val();
			var pid = $("#pid").val();
			var secret = $("#secret").val();
			var workspaceId = $("#workspaceId").val();

			console.log(pid+"--"+secret);
			//console.log(userName+"--"+userId);
			if (pid == null || pid ==''){
				alert("pid不能为空，请填写pid ！");
				return ;
			}
			if (secret == null || secret ==''){
				alert("secret不能为空，请填写secret ！");
				return ;
			}
			var flag = false;
			var msg = "";
			$.ajax({
				type: "post",
				url: "${ctx}/sys/sysTToken/checkPidAndScretAndWorspaceIdExist",
				cache:false,
				async:false,
				dataType: "json",
				data:{"pid":pid  ,"secret":secret ,"id":id ,"workspaceId":workspaceId },
				success: function(data){
					flag = data.success ;
					if (flag){
						msg="新增或者修改后的：\nPid:【"+pid+"】\nSecret:【"+secret+"】\nWorkspaceId:【"+workspaceId+"】\n对应关系已经存在！保存失败！";
					}else {
					}
				},
				failure: function () {
					msg = "校验失败，请核对后重新提交！";
					flag = true ;
				}
			});
			if (flag){
				alert(msg)
				return;
			}
			//console.log("校验通过...");
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
		<form:form id="inputForm" modelAttribute="sysTToken" action="${ctx}/sys/sysTToken/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>pid：</label></td>
					<td class="width-35">
						<form:input path="pid" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>secret：</label></td>
					<td class="width-35">
						<form:input path="secret" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>ip：</label></td>
		   			<td class="width-35" >
						<form:input path="ip" htmlEscape="false"    class="form-control required"/>
					</td>
		  		</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>访问次数：</label></td>
					<td class="width-35">
						<form:input path="accessNum" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">TAPD项目ID：</label></td>
					<td class="width-35" >
						<form:input path="workspaceId" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">是否启用：</label></td>
					<td class="width-35">
						<form:select path="useflag" class="form-control "  itemValue="${sysTToken.useflag== null?0:sysTToken.useflag}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>