<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>教练员资格管理</title>
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
	<form:form id="searchForm" modelAttribute="sysCertificatesCoach" action="${ctx}/att/sysCertificatesCoach/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		 </div>
	</form:form>
	<br/>
	</div>
	</div>

	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="att:sysCertificatesCoach:add">
				<table:addRow url="${ctx}/att/sysCertificatesCoach/form?menuId=${menu.id}" title="教练员资格"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:sysCertificatesCoach:edit">
			    <table:editRow url="${ctx}/att/sysCertificatesCoach/form" title="教练员资格" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:sysCertificatesCoach:del">
				<table:delRow url="${ctx}/att/sysCertificatesCoach/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:sysCertificatesCoach:import">
				<table:importExcel url="${ctx}/att/sysCertificatesCoach/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:sysCertificatesCoach:export">
	       		<table:exportExcel url="${ctx}/att/sysCertificatesCoach/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column coathId">教练员id</th>
				<th  class="sort-column qualification">资格名称</th>
				<th  class="sort-column obtainYearMonth">考获年月</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysCertificatesCoach">
			<tr>
				<td> <input type="checkbox" id="${sysCertificatesCoach.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看教练员资格', '${ctx}/att/sysCertificatesCoach/view?id=${sysCertificatesCoach.id}','800px', '500px')">
					${sysCertificatesCoach.coathId}
				</a></td>
				<td>
					${sysCertificatesCoach.qualification}
				</td>
				<td>
					${sysCertificatesCoach.obtainYearMonth}
				</td>
				<td>
					<shiro:hasPermission name="att:sysCertificatesCoach:view">
						<a href="#" onclick="openDialogView('查看教练员资格', '${ctx}/att/sysCertificatesCoach/view?id=${sysCertificatesCoach.id}','950px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="att:sysCertificatesCoach:edit">
    					<a href="#" onclick="openDialog('修改教练员资格', '${ctx}/att/sysCertificatesCoach/form?id=${sysCertificatesCoach.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="att:sysCertificatesCoach:del">
						<a href="${ctx}/att/sysCertificatesCoach/delete?id=${sysCertificatesCoach.id}" onclick="return confirmx('确认要删除该教练员资格吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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