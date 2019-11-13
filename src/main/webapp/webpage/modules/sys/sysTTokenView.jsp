<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>token配置信息查看</title>
	<meta name="decorator" content="default"/>
</head>
<body >
	<div class="print-box">
	    <div id="print">
			 <div class="print-title">
				token配置信息
			</div>
			<div class="print-info">
				<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tr>
								<th class="formTitle">名称：</th>
								<td class="formValue" style="padding-right: 20px;">
					<input type="text" readonly="readonly" class="form-control" value="${sysTToken.name}" />
								</td>
								<th class="formTitle">pid：</th>
								<td class="formValue">
					<input type="text" readonly="readonly" class="form-control" value="${sysTToken.pid}" />
								</td>
							</tr>
							<tr>
								<th class="formTitle">secret：</th>
								<td class="formValue" style="padding-right: 20px;">
					<input type="text" readonly="readonly" class="form-control" value="${sysTToken.secret}" />
								</td>
					<th class="formTitle"></th>
					<td class="formValue"></td>
		  		</tr>
				</table>
			</div>
        </div>
	</div>
</body>
</html>