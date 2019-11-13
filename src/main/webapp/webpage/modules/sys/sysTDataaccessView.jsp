<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>多组织架构数据权限查看</title>
	<meta name="decorator" content="default"/>
</head>
<body class="hideScroll" >

	<form:form id="inputForm" modelAttribute="sysTDataaccess" action="" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<form:hidden path="menuId"/>
	<sys:message content="${message}"/>
	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
                         <tr>
                             <td class="width-15 active"><label class="pull-right">多组织架构数据权限组：</label></td>
                             <td class="width-35" >
						<input type="text" readonly="readonly" class="form-control" value="${sysTDataaccess.name}" />
					</td>
					<td class="width-15 active"><label class="pull-right">创建人：</label></td>
					<td class="width-35" >
						<input type="text" readonly="readonly" class="form-control" value="${sysTDataaccess.createName}" />
					</td>
                         </tr>
                    <tr>

					<td class="width-15 active"><label class="pull-right">创建时间：</label></td>
					<td class="width-35">
						<input type="text" readonly="readonly" class="form-control"  value="<fmt:formatDate value='${sysTDataaccess.createDate}' pattern='yyyy-MM-dd  HH:mm'/>" />
					</td>

					<td class="width-15 active"><label class="pull-right">更新人：</label></td>
					<td class="width-35" >
						<input type="text" readonly="readonly" class="form-control" value="${sysTDataaccess.updateName}" />
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">更新时间：</label></td>
					<td class="width-35">
						<input type="text" readonly="readonly" class="form-control"  value="<fmt:formatDate value='${sysTDataaccess.updateDate}' pattern='yyyy-MM-dd  HH:mm'/>" />
					</td>
				</tr>
         <tr>
			<td class="width-15 active"><label class="pull-right">备注：</label></td>
			<td class="width-35" colspan="3">
				<form:textarea path="remarks" htmlEscape="false" rows="3" readonly="true"   class="form-control "/>
			</td>
		</tr>
	</table>
		<div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<td style="width:15%">数据库表名</td>
						<td style="width:15%">数据库表中文名</td>
						<td style="width:70%">条件</td>
					</tr>
				</thead>
				<tbody><c:forEach items="${sysTDataaccess.sysTDataaccessentityList}" var="sysTDataaccessentity">
				<tr style="height:100px">

				<td>
					${sysTDataaccessentity.dataTableName}
                </td>
                <td>
					${sysTDataaccessentity.dataTableNameCN}
                </td>
                <td>
					${sysTDataaccessentity.conditions}
                </td>
                    </tr>
                </c:forEach>
              </tbody>
			</table>
			</div>

		</div>
	</form:form>

</body>
</html>