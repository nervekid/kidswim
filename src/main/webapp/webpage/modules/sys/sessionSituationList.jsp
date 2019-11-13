<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>系统会话管理</title>
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
	<form:form id="searchForm" modelAttribute="sessionSituation" action="${ctx}/sys/sessionSituation/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>用户名称：</span>
				<form:input path="userName" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="64"  class=" form-control input-sm"/>
		 </div>
	</form:form>
	<br/>
	</div>
	</div>

	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:sessionSituation:add">
				<table:addRow url="${ctx}/sys/sessionSituation/form?menuId=${menu.id}" title="系统会话"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sessionSituation:edit">
			    <table:editRow url="${ctx}/sys/sessionSituation/form" title="系统会话" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sessionSituation:del">
				<table:delRow url="${ctx}/sys/sessionSituation/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sessionSituation:import">
				<table:importExcel url="${ctx}/sys/sessionSituation/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sessionSituation:export">
	       		<table:exportExcel url="${ctx}/sys/sessionSituation/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sessionId">会话id</th>
				<th  class="userName">用户名称</th>
				<th  class="createTime">会话创建时间</th>
				<th  class="stopTime">会话退出时间</th>
				<th  class="expireTime">会话超时时间</th>
				<th  class="planExitTime">计划会话存活时间</th>
				<th  class="actualExitTime">实际会话存活时间</th>
				<th  class="isAbnormal">是否异常</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sessionSituation">
			<tr>
				<td> <input type="checkbox" id="${sessionSituation.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看系统会话', '${ctx}/sys/sessionSituation/view?id=${sessionSituation.id}','800px', '500px')">
					${sessionSituation.sessionId}
				</a></td>
				<td>
					${sessionSituation.userName}
				</td>
				<td>
					<fmt:formatDate value="${sessionSituation.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${sessionSituation.stopTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${sessionSituation.expireTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sessionSituation.planExitTime}
				</td>
				<td>
					${sessionSituation.actualExitTime}
				</td>
				<td>
					<c:choose>
   						<c:when test="${sessionSituation.isAbnormal== '1'}">
   							会话异常失效
   						</c:when>
	   					<c:otherwise>
	   						会话正常
	   					</c:otherwise>
					</c:choose>
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