<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>销售资料管理</title>
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

            laydate.render({
                elem: '#paidDate',
                trigger:'click'
            });

		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="serSale" action="${ctx}/att/serSale/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tbody>
			<tr>
				<td class="width-15 active"><label class="pull-right">銷售單編號：</label></td>
				<td class="width-35">
					<form:input path="code" htmlEscape="false" disabled="true"    class="form-control"/>
				</td>
				<td class="width-15 active"><label class="pull-right">課程編號：</label></td>
				<td class="width-35">
					<sys:treeselect  id="course"  name="courseCode" value="${serSale.courseCode}" labelName="courseName"
										  labelValue="" title="课程编号" url="/att/serCourse/treeData" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
				</td>
			</tr>

			<tr>
				<td class="width-15 active"><label class="pull-right">學員名稱：</label></td>
				<td class="width-35">
					<sys:treeselect  id="student"  name="studentCode" value="${serSale.studentCode}" labelName="studentName"
										  labelValue="${serSale.studentName}" title="学生" url="/att/sysBaseStudent/treeData" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
				</td>
				<td class="width-15 active"><label class="pull-right">折扣:</label></td>
				<td class="width-35">
					<form:input path="discount" htmlEscape="false"    class="form-control"/>
				</td>
			</tr>

			<tr>
				<td class="width-15 active"><label class="pull-right">付款金額：</label></td>
				<td class="width-35">
					<form:input path="payAmount" htmlEscape="false"    class="form-control"/>
				</td>
				<td class="width-15 active"><label class="pull-right">是否付款：</label></td>
				<td class="width-35">
					<form:select placeholder="是否付款" path="paidFlag"  class="form-control required"  >
						<form:option value="" label="請選擇"/>
						<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>
			</tr>

			<tr>
				<td class="width-15 active"><label class="pull-right">付款日期：</label></td>
				<td class="width-35">
					<input id="paidDate" name="paidDate"  type="text"  class="form-control required"
						   value="<fmt:formatDate value="${serSale.paidDate}" pattern="yyyy-MM-dd"/>"/>
				</td>
				<%--<td class="width-35">--%>
					<%--<form:input path="paidDate" htmlEscape="false"    class="form-control"/>--%>
				<%--</td>--%>
				<td class="width-15 active"><label class="pull-right">付款方式：</label></td>
				<td class="width-35">
					<form:input path="paymentType" htmlEscape="false" class="form-control"/>
				</td>
			</tr>

			<tr>
				<td class="width-15 active"><label class="pull-right">备注：</label></td>
				<td class="width-35" colspan="3">
					<form:textarea path="remarks" htmlEscape="false" rows="3"   class="form-control "/>
				</td>
			</tr>


			</tbody>
	</form:form>
</body>
</html>