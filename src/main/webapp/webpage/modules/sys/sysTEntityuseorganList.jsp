<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>实体功能对应组织架构管理</title>
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
	<form:form id="searchForm" modelAttribute="sysTEntityuseorgan" action="${ctx}/sys/sysTEntityuseorgan/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
				<form:input placeholder="数据库表名" path="dataTableName" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="20"  class=" form-control input-sm"/>
				<form:input placeholder="数据库表中文名" path="dataTableNameCN" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="20"  class=" form-control input-sm"/>
				<form:select placeholder="组织架构" path="organTag"  class="form-control m-b"  onchange="search()" style="width:200px;">
				<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('org_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="sys:sysTEntityuseorgan:add">
				<table:addRow url="${ctx}/sys/sysTEntityuseorgan/form?menuId=${menu.id}" title="实体功能对应组织架构"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysTEntityuseorgan:edit">
			    <table:editRow url="${ctx}/sys/sysTEntityuseorgan/form" title="实体功能对应组织架构" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysTEntityuseorgan:del">
				<table:delRow url="${ctx}/sys/sysTEntityuseorgan/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysTEntityuseorgan:import">
				<table:importExcel url="${ctx}/sys/sysTEntityuseorgan/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysTEntityuseorgan:export">
	       		<table:exportExcel url="${ctx}/sys/sysTEntityuseorgan/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column dataTableName">数据库表名</th>
				<th  class="sort-column dataTableNameCN">数据库表中文名</th>
				<th  class="sort-column organTag">组织架构</th>
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysTEntityuseorgan">
			<tr>
				<td> <input type="checkbox" id="${sysTEntityuseorgan.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看实体功能对应组织架构', '${ctx}/sys/sysTEntityuseorgan/view?id=${sysTEntityuseorgan.id}','800px', '500px')">
					${sysTEntityuseorgan.dataTableName}
				</a></td>
				<td>
					${sysTEntityuseorgan.dataTableNameCN}
				</td>
				<td>
					${fns:getDictLabel(sysTEntityuseorgan.organTag, 'org_flag', '')}
				</td>
				<%-- <td>
					<shiro:hasPermission name="sys:sysTEntityuseorgan:view">
						<a href="#" onclick="openDialogView('查看实体功能对应组织架构', '${ctx}/sys/sysTEntityuseorgan/view?id=${sysTEntityuseorgan.id}','950px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:sysTEntityuseorgan:edit">
    					<a href="#" onclick="openDialog('修改实体功能对应组织架构', '${ctx}/sys/sysTEntityuseorgan/form?id=${sysTEntityuseorgan.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="sys:sysTEntityuseorgan:del">
						<a href="${ctx}/sys/sysTEntityuseorgan/delete?id=${sysTEntityuseorgan.id}" onclick="return confirmx('确认要删除该实体功能对应组织架构吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td> --%>
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