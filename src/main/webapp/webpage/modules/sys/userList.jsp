<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用戶管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<div class="wrapper wrapper-content">
    <sys:message content="${message}"/>
		<!-- 查詢條件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">

			<span>登錄名：</span>
				<form:input path="loginName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span>歸屬部門：</span>
				<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
				title="部門" url="/sys/office/treeData?type=2" cssClass=" form-control input-sm" allowClear="true" notAllowSelectParent="false"/>
			<span>姓&nbsp;&nbsp;&nbsp;名：</span>
				<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
			<span>是否轉正：</span>
			<form:select  path="formalFlag"  class="form-control m-b" onchange="search()" style="width:160px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('formal_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		 </div>
	</form:form>
	<br/>
	</div>
	</div>

	<!-- 工具欄 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:user:add">
				<table:addRow url="${ctx}/sys/user/form" title="用戶" width="900px" height="750px" target="officeContent"></table:addRow><!-- 增加按鈕 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:edit">
			    <table:editRow url="${ctx}/sys/user/form" id="contentTable"  title="用戶" width="900px" height="750px" target="officeContent"></table:editRow><!-- 編輯按鈕 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:del">
				<table:delRow url="${ctx}/sys/user/deleteAll" id="contentTable"></table:delRow><!-- 刪除按鈕 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:import">
				<table:importExcel url="${ctx}/sys/user/import"></table:importExcel><!-- 導入按鈕 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:export">
	       		<table:exportExcel url="${ctx}/sys/user/export"></table:exportExcel><!-- 導出按鈕 -->
	       </shiro:hasPermission>
<!-- 	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
 -->
			</div>
		<div class="pull-right">
			<button  class="btn btn-success btn-sm" onclick="search()" ><i class="fa fa-search"></i> 查詢</button>
			<button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>

	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th><input type="checkbox" class="i-checks"></th>
				<th class="sort-column login_name">登錄名</th>
				<th class="sort-column name">姓名</th>
				<th class="sort-column mobile">手機</th>
				<th class="sort-column email">郵箱</th>
				<th class="sort-column a.enable">是否可用</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td> <input type="checkbox" id="${user.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看用戶', '${ctx}/sys/user/form?id=${user.id}','800px', '680px')">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>${user.mobile}</td>
				<td>${user.email}</td>
				<td>${fns:getDictLabel(user.enable, 'yes_no', '')}</td>
				<td>
					<shiro:hasPermission name="sys:user:view">
						<a href="#" onclick="openDialogView('查看用戶', '${ctx}/sys/user/form?id=${user.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:user:edit">
						<a href="#" onclick="openDialog('修改用戶', '${ctx}/sys/user/form?id=${user.id}','900px', '750px', 'officeContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:user:formal">
						<a href="#" onclick="openDialog('轉正', '${ctx}/sys/user/formal?id=${user.id}','800px', '700px', 'officeContent')" class="btn btn-warning btn-xs" ><i class="fa fa-edit"></i> 轉正設置</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:user:del">
						<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('確認要刪除該用戶嗎？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 刪除</a>
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