<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<fmt:setBundle basename="kite" var="kite" />
<fmt:message key="fastdfs.tomcat.url" var="fastdfsTomcatUrl" bundle="${kite}" />
<fmt:message key="fastdfs.storage.url" var="fastdfsStorageUrl" bundle="${kite}" />
<html>
<head>
	<title>系统模块配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
        //防止点击按钮的时候提交表单
        var ifSubmit = false;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            //正常路径初始化为true
            ifSubmit = true;
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
                    if(ifSubmit) {
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
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
		<form:form id="inputForm" modelAttribute="sysModuleConfig" action="${ctx}/sys/sysModuleConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">系统模块名称：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="name" htmlEscape="false"    class="form-control "/>
					</td>

				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">系统连接：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="url" htmlEscape="false"    class="form-control "/>
					</td>
		  		</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">系统图片：</label></td>
					<td class="width-35" colspan="3">
						<form:hidden id="image" path="image" htmlEscape="false" maxlength="64" class="form-control"/>
						<file:uploadFile id="upload"  catalogName="IT管理部文件信息" name="上传" group="''" hidden="true"
										 uploadUrl='${fastdfsTomcatUrl}' insertPosition="image"></file:uploadFile>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>