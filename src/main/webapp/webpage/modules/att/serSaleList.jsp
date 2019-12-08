<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>销售资料管理</title>
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
	<form:form id="searchForm" modelAttribute="serSale" action="${ctx}/att/serSale/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">

			<form:input placeholder="銷售單編號" path="code" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="10"  class=" form-control input-sm"/>
			<form:input placeholder="學員編號" path="studentCode" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="20"  class=" form-control input-sm"/>
			<form:select placeholder="是否付款" path="paidFlag"  class="form-control m-b required" onchange="search()" >
				<form:option value="" label="請選擇"/>
				<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="att:serSale:add">
				<table:addRow url="${ctx}/att/serSale/form?menuId=${menu.id}" title="销售资料"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serSale:edit">
			    <table:editRow url="${ctx}/att/serSale/form" title="销售资料" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serSale:del">
				<table:delRow url="${ctx}/att/serSale/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serSale:import">
				<table:importExcel url="${ctx}/att/serSale/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<%--<shiro:hasPermission name="att:serSale:export">--%>
	       		<%--<table:exportExcel url="${ctx}/att/serSale/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->--%>
	       	<%--</shiro:hasPermission>--%>
	       <%--<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>--%>
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
				<th  class="sort-column code">銷售單編號</th>
				<th  class="sort-column courseCode">課程編號</th>
				<th  class="sort-column studentCode">學員編號</th>
				<th  class="sort-column studentName">學員名稱</th>
				<th  class="sort-column discount">折扣</th>
				<th  class="sort-column payAmount">付款金額(港币)</th>
				<th  class="sort-column paid_flag">是否付款</th>
				<th  class="sort-column paid_date">付款日期</th>
				<th  class="sort-column paymentType">付款方式</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="serSale">
			<tr>
				<td> <input type="checkbox" id="${serSale.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看销售资料', '${ctx}/att/serSale/view?id=${serSale.id}','800px', '500px')">
					${serSale.code}
				</a></td>
				<td>
					${serSale.courseCode}
				</td>
				<td>
					${serSale.studentCode}
				</td>
				<td>
					${serSale.studentName}
				</td>
				<td>
					${serSale.discount}
				</td>
				<td>
					${serSale.payAmount}
				</td>
				<td>
					${fns:getDictLabel(serSale.paidFlag, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${serSale.paidDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${serSale.paymentType}
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