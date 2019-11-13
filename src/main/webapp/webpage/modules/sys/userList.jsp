<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<div class="wrapper wrapper-content">
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">

			<span>登录名：</span>
				<form:input path="loginName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span>归属部门：</span>
				<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
				title="部门" url="/sys/office/treeData?type=2" cssClass=" form-control input-sm" allowClear="true" notAllowSelectParent="false"/>
			<span>姓&nbsp;&nbsp;&nbsp;名：</span>
				<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span>是否转正：</span>
			<form:select  path="formalFlag"  class="form-control m-b" onchange="search()" style="width:160px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('formal_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		 </div>
	</form:form>
	<br/>
	</div>
	</div>

	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:user:add">
				<table:addRow url="${ctx}/sys/user/form" title="用户" width="900px" height="750px" target="officeContent"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:edit">
			    <table:editRow url="${ctx}/sys/user/form" id="contentTable"  title="用户" width="900px" height="750px" target="officeContent"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:del">
				<table:delRow url="${ctx}/sys/user/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:import">
				<table:importExcel url="${ctx}/sys/user/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:export">
	       		<table:exportExcel url="${ctx}/sys/user/export"></table:exportExcel><!-- 导出按钮 -->
	       </shiro:hasPermission>
<!-- 	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
 -->
			</div>
		<div class="pull-right">
			<button  class="btn btn-success btn-sm" onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>

	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th><input type="checkbox" class="i-checks"></th>
				<th class="sort-column login_name">登录名</th>
				<th class="sort-column name">姓名</th>
				<th class="sort-column mobile">手机</th>
				<%--<th class="sort-column phone">电话</th>--%>
				<th class="sort-column email">邮箱</th>
				<th class="sort-e c.name">归属公司</th>
				<%--<th class="sort-column o.name">归属部门</th>--%>
				<th class="sort-column a.position">职务</th>
				<th class="sort-column a.enable">是否可用</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td> <input type="checkbox" id="${user.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看用户', '${ctx}/sys/user/form?id=${user.id}','800px', '680px')">${user.loginName}</a></td>
				<td>${user.name}</td>
				<%--<td>${user.phone}</td>--%>
				<td>${user.mobile}</td>
				<td>${user.email}</td>
				<%--<td>${user.company.name}</td>--%>
				<td>${user.office.name}</td>
				<td>${user.position}</td>
				<td>${fns:getDictLabel(user.enable, 'yes_no', '')}</td>
				<td>
					<shiro:hasPermission name="sys:user:view">
						<a href="#" onclick="openDialogView('查看用户', '${ctx}/sys/user/form?id=${user.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:user:edit">
						<a href="#" onclick="openDialog('修改用户', '${ctx}/sys/user/form?id=${user.id}','900px', '750px', 'officeContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:user:formal">
						<a href="#" onclick="openDialog('转正', '${ctx}/sys/user/formal?id=${user.id}','800px', '700px', 'officeContent')" class="btn btn-warning btn-xs" ><i class="fa fa-edit"></i> 转正设置</a>
					</shiro:hasPermission>

					<%--<shiro:hasPermission name="sys:user:enable">
						<c:if test="${user.enable eq '0'}">
							<a href="${ctx}/sys/user/enable?id=${user.id}&enable=1" onclick="return confirmx('确认要启用该用户吗？', this.href)" class="btn btn-warning btn-xs"><i class="fa fa-edit"></i> 启用</a>
						</c:if>
						<c:if test="${user.enable eq '1'}">
							<a href="${ctx}/sys/user/enable?id=${user.id}&enable=0" onclick="return confirmx('确认要停用该用户吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 停用</a>
						</c:if>

					</shiro:hasPermission>--%>
					<shiro:hasPermission name="sys:user:del">
						<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
	</div>
</body>
</html>