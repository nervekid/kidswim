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
	<form:form id="searchForm" modelAttribute="serCourseLevelCost" action="${ctx}/att/serCourseLevelCost/" method="post" class="form-inline">
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
				<th  class="sort-column courseLevelFlag">课程等级 字典枚举 courseLevel_flag 1:NA 2:BB 3:CA 4:CB 5:CC 6:AD 7:TA 8:TB</th>
				<th  class="sort-column costAmount">收费 单位(港币)</th>
				<th  class="sort-column containEntranceFeeFlag">是否包含入场费 字典枚举 yes_no 1:是 0:否</th>
				<th  class="sort-column costStandardFlag">收费标准 字典枚举 cost_standard_flag 1:每小时 2:每两个月</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="serCourseLevelCost">
			<tr>
				<td> <input type="checkbox" id="${serCourseLevelCost.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看课程等级对应收费', '${ctx}/att/serCourseLevelCost/view?id=${serCourseLevelCost.id}','800px', '500px')">
					${serCourseLevelCost.courseLevelFlag}
				</a></td>
				<td>
					${serCourseLevelCost.costAmount}
				</td>
				<td>
					${serCourseLevelCost.containEntranceFeeFlag}
				</td>
				<td>
					${serCourseLevelCost.costStandardFlag}
				</td>
				<td>
					<shiro:hasPermission name="att:serCourseLevelCost:view">
						<a href="#" onclick="openDialogView('查看课程等级对应收费', '${ctx}/att/serCourseLevelCost/view?id=${serCourseLevelCost.id}','950px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="att:serCourseLevelCost:edit">
    					<a href="#" onclick="openDialog('修改课程等级对应收费', '${ctx}/att/serCourseLevelCost/form?id=${serCourseLevelCost.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="att:serCourseLevelCost:del">
						<a href="${ctx}/att/serCourseLevelCost/delete?id=${serCourseLevelCost.id}" onclick="return confirmx('确认要删除该课程等级对应收费吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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