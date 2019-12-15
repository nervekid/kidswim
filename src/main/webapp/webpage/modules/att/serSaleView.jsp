<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>銷售資料查看</title>
	<meta name="decorator" content="default"/>
</head>
<body >
	<div class="print-box">
	    <div id="print">
			 <div class="print-title">
				銷售資料
			</div>
			<div class="print-info">
				<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
				</table>
			</div>
        </div>
	</div>

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
				<form:input path="courseCode" htmlEscape="false" disabled="true" class="form-control"/>
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">學員名稱：</label></td>
			<td class="width-35">
				<form:input path="studentName" htmlEscape="false"  disabled="true"  class="form-control"/>
			</td>
			<td class="width-15 active"><label class="pull-right">折扣:</label></td>
			<td class="width-35">
				<form:input path="discount" htmlEscape="false"  disabled="true"  class="form-control"/>
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">付款金額：</label></td>
			<td class="width-35">
				<form:input path="payAmount" htmlEscape="false" disabled="true"   class="form-control"/>
			</td>
			<td class="width-15 active"><label class="pull-right">是否付款：</label></td>
			<td class="width-35">
				<form:select placeholder="是否付款" path="paidFlag" disabled="true" class="form-control m-b"  >
					<form:option value="" label="請選擇"/>
					<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
				</form:select>
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">付款日期：</label></td>
			<td class="width-35">
				<input type="text" readonly="readonly" class="form-control" value="<fmt:formatDate value="${serSale.paidDate}" pattern="yyyy-MM-dd"/>" />
			</td>
			<td class="width-15 active"><label class="pull-right">付款方式：</label></td>
			<form:select placeholder="付款方式" path="paymentType" disabled="true" class="form-control m-b"  >
					<form:option value="" label="請選擇"/>
					<form:options items="${fns:getDictList('pay_type')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
				</form:select>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">是否收取會員費：</label></td>
			<td class="width-35">
				<form:select placeholder="是否收取會員費" path="memberFeeFlag" disabled="true" class="form-control m-b"  >
					<form:option value="" label="請選擇"/>
					<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
				</form:select>
			</td>
		</tr>

		<tr>
			<td class="width-15 active"><label class="pull-right">備註：</label></td>
			<td class="width-35" colspan="3">
				<form:textarea path="remarks" htmlEscape="false" rows="3" disabled="true"  class="form-control "/>
			</td>
		</tr>


		</tbody>
		</form:form>
	</body>
</body>
</html>