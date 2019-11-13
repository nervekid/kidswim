<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>多组织架构数据权限管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var checkBoxChecked ='${checkBoxChecked}';
            if(checkBoxChecked){
                $("#collectionId").attr("checked",true);
            }
		});
	</script>
	<script type="text/javascript" src="${ctxStatic}/common/collectionMenu.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<%-- <div class="ibox-title">
		<h5></h5>
		<div class="ibox-tools">
				<label>	<input id="collectionId" type="checkbox" onclick="collectionMenu('${ctx}/sys/sysUserCollectionMenu/collectionMenu','${menu.href}','${menu.name}','${menu.id}')">&nbsp;&nbsp;&nbsp;是否收藏到主页面</label>
	   </div>
	</div> --%>

    <div class="ibox-content">
	<sys:message content="${message}"/>

	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="sysTDataaccess" action="${ctx}/sys/sysTDataaccess/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		<form:input placeholder="多组织架构数据权限组名称" path="name" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="20"  class=" form-control input-sm"/>
		 </div>
	</form:form>
	<br/>
	</div>
	</div>

	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:sysTDataaccess:add">
				<table:addRow url="${ctx}/sys/sysTDataaccess/form?menuId=${menu.id}" title="多组织架构数据权限"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysTDataaccess:edit">
			    <table:editRow url="${ctx}/sys/sysTDataaccess/form" title="多组织架构数据权限" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysTDataaccess:del">
				<table:delRow url="${ctx}/sys/sysTDataaccess/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysTDataaccess:import">
				<table:importExcel url="${ctx}/sys/sysTDataaccess/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysTDataaccess:export">
	       		<table:exportExcel url="${ctx}/sys/sysTDataaccess/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
<!-- 	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
 -->
			</div>
		<div class="pull-right">
						<button  class="btn btn-success btn-sm" onclick="search()"><i class="fa fa-search"></i> 查询</button>
						<button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
					</div>
	</div>
	</div>

	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column name">多组织架构数据权限组名称</th>
				<th  class="sort-column update_date">更新时间</th>
				<th  class="sort-column remarks">备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysTDataaccess">
			<tr>
				<td> <input type="checkbox" id="${sysTDataaccess.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看多组织架构数据权限', '${ctx}/sys/sysTDataaccess/view?id=${sysTDataaccess.id}','800px', '500px')">
					${sysTDataaccess.name}
				</a></td>
				<td>
					<fmt:formatDate value="${sysTDataaccess.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sysTDataaccess.remarks}
				</td>
				<td>
					<shiro:hasPermission name="sys:sysTDataaccess:view">
						<a href="#" onclick="openDialogView('查看多组织架构数据权限', '${ctx}/sys/sysTDataaccess/view?id=${sysTDataaccess.id}','950px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:sysTDataaccess:edit">
    					<a href="#" onclick="openDialog('修改多组织架构数据权限', '${ctx}/sys/sysTDataaccess/form?id=${sysTDataaccess.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="sys:sysTDataaccess:del">
						<a href="${ctx}/sys/sysTDataaccess/delete?id=${sysTDataaccess.id}" onclick="return confirmx('确认要删除该多组织架构数据权限吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
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