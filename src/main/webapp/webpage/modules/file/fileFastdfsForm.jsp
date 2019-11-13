<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文件管理</title>
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
		<form:form id="inputForm" modelAttribute="fileFastdfs" action="${ctx}/file/fileFastdfs/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">文件名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"  readonly="true"   class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">文件目录：</label></td>
					<td class="width-35">
						<sys:treeselect id="catalog" name="catalogId" value="${fileFastdfs.catalogId}" labelName="catalogName" labelValue="${fileFastdfs.catalogName}"
										title="文件目录" url="/file/fileCatalog/treeData" cssClass="form-control"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">文件大小：</label></td>
					<td class="width-35">
						<form:input path="sizeStr" htmlEscape="false"  readonly="true"   class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">文件等级：</label></td>
					<td class="width-35">
						<input type="text" readonly="readonly" class="form-control" value="${fns:getDictLabel(fileFastdfs.level, 'file_level', '')}" />
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">文件类型</label></td>
					<td class="width-35">
						<form:input path="type" htmlEscape="false"  readonly="true"   class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">文件分组</label></td>
					<td class="width-35">
						<input type="text" readonly="readonly" class="form-control" value="${fns:getDictLabel(fileFastdfs.group, 'file_group', '')} " />
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">创建人</label></td>
					<td class="width-35">
						<input type="type" readonly="readonly" class="form-control" value="${fileFastdfs.createBy.name}" />
					</td>
					<td class="width-15 active"><label class="pull-right">创建时间</label></td>
					<td class="width-35">
						<input type="text" readonly="readonly" class="form-control" value="<fmt:formatDate value="${fileFastdfs.createDate}" pattern="yyyy-MM-dd"/>" />
					</td>
				</tr>

				<c:if test="${fileFastdfs.delFlag eq '1'}">
					<tr>
						<td class="width-15 active"><label class="pull-right">删除人</label></td>
						<td class="width-35">
							<form:input path="delName" htmlEscape="false"  readonly="true"   class="form-control "/>
						</td>
						<td class="width-15 active"><label class="pull-right">删除时间</label></td>
						<td class="width-35">
							<input type="text" readonly="readonly" class="form-control" value="<fmt:formatDate value="${fileFastdfs.delDate}" pattern="yyyy-MM-dd"/>" />
						</td>
					</tr>
				</c:if>


		   		<tr>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="remarks" htmlEscape="false" rows="3"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>