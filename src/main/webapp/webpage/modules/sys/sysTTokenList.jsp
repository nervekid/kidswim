<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>token配置信息管理</title>
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
	<form:form id="searchForm" modelAttribute="sysTToken" action="${ctx}/sys/sysTToken/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		
				<form:input placeholder="名称" path="name" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="255"  class=" form-control input-sm"/>
		
				<form:input placeholder="pid" path="pid" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="64"  class=" form-control input-sm"/>
		
				<form:input placeholder="secret" path="secret" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="255"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:sysTToken:add">
				<table:addRow url="${ctx}/sys/sysTToken/form?menuId=${menu.id}" title="token配置信息"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysTToken:edit">
			    <table:editRow url="${ctx}/sys/sysTToken/form" title="token配置信息" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysTToken:del">
				<table:delRow url="${ctx}/sys/sysTToken/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysTToken:import">
				<table:importExcel url="${ctx}/sys/sysTToken/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysTToken:export">
	       		<table:exportExcel url="${ctx}/sys/sysTToken/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column name">名称</th>
				<th  class="sort-column pid">pid</th>
				<th  class="sort-column secret">secret</th>
				<th  class="sort-column ip">ip</th>
				<th  class="sort-column accessNum">访问次数</th>
				<th  class="sort-column a.workspace_id">TAPD项目ID</th>
				<th  class="sort-column a.use_flag">是否启用</th>
				<th  class="sort-column a.remarks">备注说明</th>
				<th>操作</th>

			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysTToken">
			<tr>
				<td> <input type="checkbox" id="${sysTToken.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看token配置信息', '${ctx}/sys/sysTToken/view?id=${sysTToken.id}','800px', '500px')">
					${sysTToken.name}
				</a></td>
				<td>
					${sysTToken.pid}
				</td>
				<td>
					${sysTToken.secret}
				</td>
				<td>
						${sysTToken.ip}
				</td>
				<td>
						${sysTToken.accessNum}
				</td>
                <td>${sysTToken.workspaceId}</td>
				<td><%--${sysTToken.useflag}--%>
					${fns:getDictLabel(sysTToken.useflag, 'yes_no', '')}
				</td>
				<td>${sysTToken.remarks}</td>
				<td>
					<shiro:hasPermission name="sys:sysTToken:edit">
						<a href="#" onclick="openDialog('修改TAPD接口配置', '${ctx}/sys/sysTToken/form?id=${sysTToken.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i>编辑</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:sysTToken:del">
						<a href="${ctx}/sys/sysTToken/delete?id=${sysTToken.id}" onclick="return confirmx('确认要删除吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i>删除</a>
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