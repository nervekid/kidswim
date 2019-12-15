<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>銷售資料管理</title>
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
			memberFeeFlagChange ();
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

            laydate.render({
                elem: '#paidDate',
                trigger:'click'
            });

		});

		function memberFeeFlagChange () {
			var a = $("#memberFeeFlag").val();
			if (a == '1') {
				$("#memberFeeFlagStr").val("$170");
			}
			else {
				$("#memberFeeFlagStr").val("$0");
			}
		}
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
					  labelValue="${serSale.courseCode}" title="課程編號" url="/att/serCourse/treeData" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
				</td>
			</tr>

			<tr>
				<td class="width-15 active"><label class="pull-right">學員名稱：</label></td>
				<td class="width-35">
					<sys:treeselect  id="student"  name="studentCode" value="${serSale.studentCode}" labelName="studentName"
					  labelValue="${serSale.studentName}" title="學生" url="/att/sysBaseStudent/treeData" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
				</td>
				<td class="width-15 active"><label class="pull-right">折扣:</label></td>
				<td class="width-35">
					<form:input path="discount" htmlEscape="false"    class="form-control"/>
				</td>
			</tr>

			<tr>
				<td class="width-15 active"><label class="pull-right">付款日期：</label></td>
				<td class="width-35">
					<input id="paidDate" name="paidDate"  type="text"  class="form-control required"
						   value="<fmt:formatDate value="${serSale.paidDate}" pattern="yyyy-MM-dd"/>"/>
				</td>
				<td class="width-15 active"><label class="pull-right">付款方式：</label></td>
				<td class="width-35">
					<form:select placeholder="付款方式" path="paymentType" class="form-control required"  >
						<form:option value="" label="請選擇"/>
						<form:options items="${fns:getDictList('pay_type')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>
			</tr>

			<tr>
				<td class="width-15 active"><label class="pull-right">是否收取會員費：</label></td>
				<td class="width-35">
					<form:select placeholder="是否收取會員費" path="memberFeeFlag" onchange="memberFeeFlagChange()" class="form-control required"  >
						<form:option value="" label="請選擇"/>
						<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>
				<td class="width-15 active"><label class="pull-right">會員費金額：</label></td>
				<td class="width-35">
					<input id="memberFeeFlagStr" name="memberFeeFlagStr" disabled="true" htmlEscape="false"    class="form-control"/>
				</td>
			</tr>

			<tr>
				<td class="width-15 active"><label class="pull-right">是否付款：</label></td>
				<td class="width-35">
					<form:select placeholder="是否付款" path="paidFlag"  class="form-control required"  >
						<form:option value="" label="請選擇"/>
						<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
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
	</form:form>
</body>
</html>