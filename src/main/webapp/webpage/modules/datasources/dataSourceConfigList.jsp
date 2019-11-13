<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>多数据配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});

        function testConnection(id){
            $.post("${ctx}/datasources/dataSourceConfig/testConnection",
                {
                    id:id
                },
                function(data,status){
                    if(data.success == true){
                        alert("连接成功！")
                    }else{
                        alert("连接失败！")
                    }
                });
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
	<form:form id="searchForm" modelAttribute="dataSourceConfig" action="${ctx}/datasources/dataSourceConfig/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>名称：</span>
				<form:input path="name" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="datasources:dataSourceConfig:add">
				<table:addRow url="${ctx}/datasources/dataSourceConfig/form" title="多数据配置"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="datasources:dataSourceConfig:edit">
			    <table:editRow url="${ctx}/datasources/dataSourceConfig/form" title="多数据配置" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="datasources:dataSourceConfig:del">
				<table:delRow url="${ctx}/datasources/dataSourceConfig/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="datasources:dataSourceConfig:import">
				<table:importExcel url="${ctx}/datasources/dataSourceConfig/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="datasources:dataSourceConfig:export">
	       		<table:exportExcel url="${ctx}/datasources/dataSourceConfig/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column name">数据源名称</th>
				<th  class="sort-column dataType">数据源类型</th>
				<th  class="sort-column uid">数据源唯一ID</th>
				<th  class="sort-column jdbcUrl">数据源连接字符串 </th>
				<th  class="sort-column status">状态 </th>
				<th  class="sort-column createDate">创建日期 </th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dataSourceConfig">
			<tr>
				<td> <input type="checkbox" id="${dataSourceConfig.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看多数据配置', '${ctx}/datasources/dataSourceConfig/form?id=${dataSourceConfig.id}','800px', '500px')">
					${dataSourceConfig.name}
				</a></td>
				<td>
						${fns:getDictLabel(dataSourceConfig.dataType, 'data_source_type', '')}
				</td>
				<td>
						${dataSourceConfig.uid}
				</td>
				<td>
						${dataSourceConfig.jdbcUrl}
				</td>
				<td>
						${fns:getDictLabel(dataSourceConfig.status, 'multip_datasource_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${dataSourceConfig.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="datasources:dataSourceConfig:view">
						<a href="#" onclick="openDialogView('查看多数据配置', '${ctx}/datasources/dataSourceConfig/form?id=${dataSourceConfig.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="datasources:dataSourceConfig:edit">
    					<a href="#" onclick="openDialog('修改多数据配置', '${ctx}/datasources/dataSourceConfig/form?id=${dataSourceConfig.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="datasources:dataSourceConfig:del">
						<a href="${ctx}/datasources/dataSourceConfig/delete?id=${dataSourceConfig.id}" onclick="return confirmx('确认要删除该多数据配置吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					<button   class="btn btn-danger btn-xs" onclick="testConnection('${dataSourceConfig.id}','${dataSourceConfig.dataType}')"><i class="fa fa-search"></i> 测试连接</button>
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