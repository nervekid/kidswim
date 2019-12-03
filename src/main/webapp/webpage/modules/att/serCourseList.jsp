<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var checkBoxChecked ='${checkBoxChecked}';
            if(checkBoxChecked){
                $("#collectionId").attr("checked",true);
            }
            laydate.render({
				elem: '#',
				range: '~'
			});
            laydate.render({
				elem: '#',
				type: 'datetime'
			});

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
	<form:form id="searchForm" modelAttribute="serCourse" action="${ctx}/att/serCourse/" method="post" class="form-inline">
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
			<shiro:hasPermission name="att:serCourse:add">
				<table:addRow url="${ctx}/att/serCourse/form?menuId=${menu.id}" title="课程"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serCourse:edit">
			    <table:editRow url="${ctx}/att/serCourse/form" title="课程" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serCourse:del">
				<table:delRow url="${ctx}/att/serCourse/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serCourse:import">
				<table:importExcel url="${ctx}/att/serCourse/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serCourse:export">
	       		<table:exportExcel url="${ctx}/att/serCourse/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>

			</div>
		<div class="pull-right">
			<shiro:hasPermission name="att:serCourse:edit">
				<button  data-placement="left" class="btn btn-success btn-sm" data-toggle="modal" onclick="createOffset('1','0')" data-target="#createOffsetData" >个人飞象卡兑换</button>
			</shiro:hasPermission>
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
				<th  class="sort-column code">課程編號</th>
				<th  class="sort-column coathId">教練員id</th>
				<th  class="sort-column beginYearMonth">開始年月</th>
				<th  class="sort-column endYearMonth">結束年月</th>
				<th  class="sort-column courseDate">上課日期</th>
				<th  class="sort-column courseNum">課程所屬第幾堂</th>
				<th  class="sort-column courseAddress">課程地址</th>
				<th  class="sort-column strInWeek">星期幾</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="serCourse">
			<tr>
				<td> <input type="checkbox" id="${serCourse.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看課程', '${ctx}/att/serCourse/view?id=${serCourse.id}','800px', '500px')">
					${serCourse.code}
				</a></td>
				<td>
					${serCourse.coathId}
				</td>
				<td>
					${serCourse.beginYearMonth}
				</td>
				<td>
					${serCourse.endYearMonth}
				</td>
				<td>
					<fmt:formatDate value="${serCourse.courseDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${serCourse.courseNum}
				</td>
				<td>
					${serCourse.courseAddress}
				</td>
				<td>
					${serCourse.strInWeek}
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