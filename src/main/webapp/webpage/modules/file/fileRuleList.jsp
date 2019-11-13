<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文件权限规则管理</title>
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

    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="fileRule" action="${ctx}/file/fileRule/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<form:input placeholder="规则名称" path="name" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>

			<form:input placeholder="创建人" path="createName" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>

		</div>
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="file:fileRule:add">
				<table:addRow url="${ctx}/file/fileRule/form?menuId=${menu.id}" title="文件权限规则" height="800px" width="1000px"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="file:fileRule:edit">
			    <table:editRow url="${ctx}/file/fileRule/form" title="文件权限规则" id="contentTable" height="800px" width="1000px"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="file:fileRule:del">
				<table:delRow url="${ctx}/file/fileRule/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>

			</div>
		<div class="pull-right">
			<button  class="btn btn-success btn-sm" onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column a.name">规则名称</th>
				<th  class="sort-column a.deptId">部门</th>
				<th  class="sort-column a.userId">用户</th>
				<th  class="sort-column a.create_by">创建人</th>
				<th  class="sort-column a.create_date">创建日期</th>
				<th  class="sort-column a.update_by">更新人</th>
				<th  class="sort-column a.update_date">更新时间</th>
				<th  class="sort-column a.remarks">备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fileRule">
			<tr>
				<td> <input type="checkbox" id="${fileRule.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看文件权限规则', '${ctx}/file/fileRule/view?id=${fileRule.id}','800px', '500px')">
					${fileRule.name}
				</a></td>
				<td>
					${fileRule.deptName}
				</td>
				<td>
					${fileRule.userName}
				</td>
				<td>
					${fileRule.createName}
				</td>
				<td>
					<fmt:formatDate value="${fileRule.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fileRule.updateName}
				</td>
				<td>
					<fmt:formatDate value="${fileRule.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fileRule.remarks}
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