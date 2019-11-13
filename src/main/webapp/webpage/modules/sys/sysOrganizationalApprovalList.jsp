<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>组织架构职级表管理</title>
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
	<div class="ibox-title">
		<h5></h5>
		<div class="ibox-tools">
				<label>	<input id="collectionId" type="checkbox" onclick="collectionMenu('${ctx}/sys/sysUserCollectionMenu/collectionMenu','${menu.href}','${menu.name}','${menu.id}')">&nbsp;&nbsp;&nbsp;是否收藏到主页面</label>
	   </div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="sysOrganizationalApproval" action="${ctx}/sys/sysOrganizationalApproval/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		
				<form:input placeholder="职级标记 rank_flag CEO:总经理, BG:事业群负责人, VP:分管副总, BU:一级部门负责人, CF:二级部门负责人" path="rankFlag" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="10"  class=" form-control input-sm"/>
		
				<form:input placeholder="上级标记 1:是,0:否" path="superiorFlag" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="64"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:sysOrganizationalApproval:add">
				<table:addRow url="${ctx}/sys/sysOrganizationalApproval/form?menuId=${menu.id}" title="组织架构职级表"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysOrganizationalApproval:edit">
			    <table:editRow url="${ctx}/sys/sysOrganizationalApproval/form" title="组织架构职级表" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysOrganizationalApproval:del">
				<table:delRow url="${ctx}/sys/sysOrganizationalApproval/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysOrganizationalApproval:import">
				<table:importExcel url="${ctx}/sys/sysOrganizationalApproval/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysOrganizationalApproval:export">
	       		<table:exportExcel url="${ctx}/sys/sysOrganizationalApproval/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
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
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column rankFlag">职级标记 rank_flag CEO:总经理, BG:事业群负责人, VP:分管副总, BU:一级部门负责人, CF:二级部门负责人</th>
				<th  class="sort-column superiorFlag">上级标记 1:是,0:否</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysOrganizationalApproval">
			<tr>
				<td> <input type="checkbox" id="${sysOrganizationalApproval.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看组织架构职级表', '${ctx}/sys/sysOrganizationalApproval/view?id=${sysOrganizationalApproval.id}','800px', '500px')">
					${sysOrganizationalApproval.rankFlag}
				</a></td>
				<td>
					${sysOrganizationalApproval.superiorFlag}
				</td>
				<td>
					<shiro:hasPermission name="sys:sysOrganizationalApproval:view">
						<a href="#" onclick="openDialogView('查看组织架构职级表', '${ctx}/sys/sysOrganizationalApproval/view?id=${sysOrganizationalApproval.id}','950px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:sysOrganizationalApproval:edit">
    					<a href="#" onclick="openDialog('修改组织架构职级表', '${ctx}/sys/sysOrganizationalApproval/form?id=${sysOrganizationalApproval.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="sys:sysOrganizationalApproval:del">
						<a href="${ctx}/sys/sysOrganizationalApproval/delete?id=${sysOrganizationalApproval.id}" onclick="return confirmx('确认要删除该组织架构职级表吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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