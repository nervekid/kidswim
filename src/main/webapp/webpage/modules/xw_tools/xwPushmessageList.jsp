<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>消息推送管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
        function keyDownEnter(e){
            var ev= window.event||e;
            if (ev.keyCode == 13) {
                search();
            }
        }
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">

    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="xwPushmessage" action="${ctx}/xw_tools/xwPushmessage/${self?'self':'list'}" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>标题：</span>
				<form:input path="title" onkeydown="keyDownEnter(event)"  htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
			<c:if test="${self}">
				<span>查阅状态：</span>
				<form:select path="readFlag"  class="form-control m-b" onchange="search()">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('xw_message_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</c:if>

		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<c:if test="${!self}">
				<%--<shiro:hasPermission name="xw_tools:xwPushmessage:add">
					<table:addRow url="${ctx}/xw_tools/xwPushmessage/form" title="消息推送"></table:addRow><!-- 增加按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="xw_tools:xwPushmessage:edit">
					<table:editRow url="${ctx}/xw_tools/xwPushmessage/form" title="消息推送" id="contentTable"></table:editRow><!-- 编辑按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="xw_tools:xwPushmessage:del">
					<table:delRow url="${ctx}/xw_tools/xwPushmessage/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="xw_tools:xwPushmessage:import">
					<table:importExcel url="${ctx}/xw_tools/xwPushmessage/import"></table:importExcel><!-- 导入按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="xw_tools:xwPushmessage:export">
					<table:exportExcel url="${ctx}/xw_tools/xwPushmessage/export"></table:exportExcel><!-- 导出按钮 -->
				</shiro:hasPermission>--%>
			</c:if>
			<c:if test="${self}">
					<table:consultMessage url="${ctx}/xw_tools/xwPushmessage/consultMessage" title="消息推送" id="contentTable"></table:consultMessage><!-- 编辑按钮 -->
			</c:if>
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<c:if test="${self}">
					<th> <input type="checkbox" class="i-checks"></th>
				</c:if>
				<th>标题</th>
				<th>内容</th>
				<th>查阅状态</th>
				<th>更新时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="xwPushmessage">
			<tr>
				<c:if test="${self}">
					<td> <input type="checkbox" id="${xwPushmessage.id}" class="i-checks"></td>
				</c:if>
				<td><a  href="#" onclick="openDialogView('查看消息推送', '${ctx}/xw_tools/xwPushmessage/${self?'view':'form'}?id=${xwPushmessage.id}&self=${self?'true':'false'}','800px', '500px')">
					${fns:abbr(xwPushmessage.title,50)}
				</a></td>
				<td>
						${fns:abbr(xwPushmessage.content,150)}
				</td>
				<td>
					<c:if test="${self}">
						${fns:getDictLabel(xwPushmessage.readFlag, 'xw_message_status', '')}
					</c:if>
					<c:if test="${!self}">
						${xwPushmessage.readNum} / ${xwPushmessage.readNum + xwPushmessage.unReadNum}
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${xwPushmessage.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%--<td>
					<shiro:hasPermission name="xw_tools:xwPushmessage:view">
						<a href="#" onclick="openDialogView('查看消息推送', '${ctx}/xw_tools/xwPushmessage/view?id=${xwPushmessage.id}','950px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="xw_tools:xwPushmessage:edit">
    					<a href="#" onclick="openDialog('修改消息推送', '${ctx}/xw_tools/xwPushmessage/form?id=${xwPushmessage.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="xw_tools:xwPushmessage:del">
						<a href="${ctx}/xw_tools/xwPushmessage/delete?id=${xwPushmessage.id}" onclick="return confirmx('确认要删除该消息推送吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>