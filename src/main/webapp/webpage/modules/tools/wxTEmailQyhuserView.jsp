<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>企业微信推送消息查看</title>
	<meta name="decorator" content="default"/>
</head>
<body >
	<div class="print-box">
	    <div id="print">
			 <div class="print-title">
				企业微信推送消息
			</div>
			<div class="print-info">
				<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tr>
								<th class="formTitle">qyh_userid：</th>
								<td class="formValue" style="padding-right: 20px;">
					<input type="text" readonly="readonly" class="form-control" value="${wxTEmailQyhuser.qyhUserid}" />
								</td>
								<th class="formTitle">kite_userid：</th>
								<td class="formValue">
					<input type="text" readonly="readonly" class="form-control" value="${wxTEmailQyhuser.kiteUserid}" />
								</td>
							</tr>
							<tr>
								<th class="formTitle">phone：</th>
								<td class="formValue" style="padding-right: 20px;">
					<input type="text" readonly="readonly" class="form-control" value="${wxTEmailQyhuser.phone}" />
								</td>
								<th class="formTitle">dutie：</th>
								<td class="formValue">
					<input type="text" readonly="readonly" class="form-control" value="${wxTEmailQyhuser.dutie}" />
								</td>
							</tr>
							<tr>
								<th class="formTitle">emailaccount：</th>
								<td class="formValue" style="padding-right: 20px;">
					<input type="text" readonly="readonly" class="form-control" value="${wxTEmailQyhuser.emailaccount}" />
								</td>
								<th class="formTitle">password：</th>
								<td class="formValue">
					<input type="text" readonly="readonly" class="form-control" value="${wxTEmailQyhuser.password}" />
								</td>
							</tr>
				</table>
			</div>
        </div>
	</div>
</body>
</html>