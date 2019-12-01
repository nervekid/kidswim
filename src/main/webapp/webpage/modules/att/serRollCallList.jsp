<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>點名管理</title>
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

	<!--查詢條件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="serRollCall" action="${ctx}/att/serRollCall/" method="post" class="form-inline">
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

	<!-- 工具欄 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="att:serRollCall:add">
				<table:addRow url="${ctx}/att/serRollCall/form?menuId=${menu.id}" title="點名"></table:addRow><!-- 增加按鈕 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serRollCall:edit">
			    <table:editRow url="${ctx}/att/serRollCall/form" title="點名" id="contentTable"></table:editRow><!-- 編輯按鈕 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serRollCall:del">
				<table:delRow url="${ctx}/att/serRollCall/deleteAll" id="contentTable"></table:delRow><!-- 刪除按鈕 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serRollCall:import">
				<table:importExcel url="${ctx}/att/serRollCall/import"  menuId="${menu.id}" ></table:importExcel><!-- 導入按鈕 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serRollCall:export">
	       		<table:exportExcel url="${ctx}/att/serRollCall/export?menuId=${menu.id}"></table:exportExcel><!-- 導出按鈕 -->
	       	</shiro:hasPermission>

			</div>
		<div class="pull-right">
			<button  class="btn btn-success btn-sm" onclick="search()" ><i class="fa fa-search"></i> 查詢</button>
			<button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>

	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column courseId">課程編碼</th>
				<th  class="sort-column studentId">學員</th>
				<th  class="sort-column rollCallStatusFlag">點名狀態</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="serRollCall">
			<tr>
				<td> <input type="checkbox" id="${serRollCall.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看點名', '${ctx}/att/serRollCall/view?id=${serRollCall.id}','800px', '500px')">
					${serRollCall.courseId}
				</a></td>
				<td>
					${serRollCall.studentId}
				</td>
				<td>
					${serRollCall.rollCallStatusFlag}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

		<!-- 分頁代碼 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>