<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>消息推送查看</title>
	<meta name="decorator" content="default"/>
</head>
<body class="hideScroll">
<form:form id="inputForm" modelAttribute="xwPushmessage" action="${ctx}/xw_tools/xwPushmessage/save" method="post" class="form-horizontal">
<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
	<tbody>
	<tr>
		<td  class="width-15 active"><label class="pull-right">标题：</label></td>
		<td class="width-35" ><form:input path="title" htmlEscape="false" maxlength="200" readonly="true" class="form-control"/></td>
	</tr>
	<tr>
		<td  class="width-15 active">	<label class="pull-right">内容：</label></td>
		<td class="width-35" colspan="3" ><form:textarea path="content" htmlEscape="false" readonly="true" rows="6" maxlength="2000" class="form-control"/></td>
	</tr>
		<tr>
			<td  class="width-15 active">	<label class="pull-right">接受人：</label></td>
			<td class="width-35" colspan="3"><table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
				<thead>
				<tr>
					<th>接受人</th>
					<th>接受部门</th>
					<th>阅读状态</th>
					<th>阅读时间</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${xwPushmessage.xwPushmessageRecordList}" var="xwPushmessageRecord">
					<tr>
						<td>
								${xwPushmessageRecord.user.name}
						</td>
						<td>
								${xwPushmessageRecord.user.office.name}
						</td>
						<td>
								${fns:getDictLabel(xwPushmessageRecord.readFlag, 'xw_message_status', '')}
						</td>
						<td>
							<fmt:formatDate value="${xwPushmessageRecord.readDate}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
				已查阅：${xwPushmessage.readNum} &nbsp; 未查阅：${xwPushmessage.unReadNum} &nbsp; 总共：${xwPushmessage.readNum + xwPushmessage.unReadNum}</td>
		</tr>
	</tbody>
</table>
</form:form>
</body>
</html>