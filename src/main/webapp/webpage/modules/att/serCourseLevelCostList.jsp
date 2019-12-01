<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>课程等级对应收费管理</title>
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
	<form:form id="searchForm" modelAttribute="serCourseLevelCost" action="${ctx}/att/serCourseLevelCost/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<form:select placeholder="课程等级" path="courseLevelFlag"  class="form-control m-b required" onchange="search()" >
				<form:option value="" label="请选择"/>
				<form:options items="${fns:getDictList('courseLevel_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
			</form:select>
			<form:select placeholder="是否包含入场费" path="containEntranceFeeFlag"  class="form-control m-b required" onchange="search()" >
				<form:option value="" label="请选择"/>
				<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
			</form:select>
			<form:select placeholder="收费标准" path="costStandardFlag"  class="form-control m-b required" onchange="search()" >
				<form:option value="" label="请选择"/>
				<form:options items="${fns:getDictList('cost_standard_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="att:serCourseLevelCost:add">
				<table:addRow url="${ctx}/att/serCourseLevelCost/form?menuId=${menu.id}" title="课程等级对应收费"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serCourseLevelCost:edit">
			    <table:editRow url="${ctx}/att/serCourseLevelCost/form" title="课程等级对应收费" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serCourseLevelCost:del">
				<table:delRow url="${ctx}/att/serCourseLevelCost/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serCourseLevelCost:import">
				<table:importExcel url="${ctx}/att/serCourseLevelCost/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serCourseLevelCost:export">
	       		<table:exportExcel url="${ctx}/att/serCourseLevelCost/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column courseLevelFlag">课程等级</th>
				<th  class="sort-column costAmount">收费 单位(港币)</th>
				<th  class="sort-column containEntranceFeeFlag">是否包含入场费</th>
				<th  class="sort-column costStandardFlag">收费标准</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="serCourseLevelCost">
			<tr>
				<td> <input type="checkbox" id="${serCourseLevelCost.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看课程等级对应收费', '${ctx}/att/serCourseLevelCost/view?id=${serCourseLevelCost.id}','800px', '500px')">
					${fns:getDictLabel(serCourseLevelCost.courseLevelFlag, 'courseLevel_flag', '')}
				</a></td>
				<td>
					${serCourseLevelCost.costAmount}
				</td>
				<td>
					${fns:getDictLabel(serCourseLevelCost.containEntranceFeeFlag, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(serCourseLevelCost.costStandardFlag, 'cost_standard_flag', '')}
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