<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>定时任务管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var checkBoxChecked ='${checkBoxChecked}';
            if(checkBoxChecked){
                $("#collectionId").attr("checked",true);
            }

		});
        function triggerOperate(operate){
            location.href = operate;
        }
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
	<form:form id="searchForm" modelAttribute="taskScheduleJob" action="${ctx}/sys/taskScheduleJob/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		
				<form:input placeholder="任务调用的方法名" path="methodName" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="255"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:taskScheduleJob:add">
				<table:addRow url="${ctx}/sys/taskScheduleJob/form?menuId=${menu.id}" title="定时任务" width="1000px" height="650px"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:taskScheduleJob:edit">
			    <table:editRow url="${ctx}/sys/taskScheduleJob/form" title="定时任务" id="contentTable"  width="1000px" height="650px"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:taskScheduleJob:del">
				<table:delRow url="${ctx}/sys/taskScheduleJob/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:taskScheduleJob:import">
				<table:importExcel url="${ctx}/sys/taskScheduleJob/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:taskScheduleJob:export">
	       		<table:exportExcel url="${ctx}/sys/taskScheduleJob/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column cronExpression">cron表达式</th>
				<th  class="sort-column beanClass">任务执行时调用哪个类的方法 包名+类名</th>
				<th  class="sort-column methodName">任务调用的方法名</th>
				<th  class="sort-column jobStatus">任务状态</th>
				<th  class="sort-column jobGroup">任务分组</th>
				<th  class="sort-column jobName">任务名</th>
				<th  class="sort-column description">任务描述</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="taskScheduleJob">
			<tr>
				<td> <input type="checkbox" id="${taskScheduleJob.id}" class="i-checks"></td>
				<td>
					${taskScheduleJob.cronExpression}
				</td>
				<td>
						${taskScheduleJob.beanClass}
				</td>
				<td>
					${taskScheduleJob.methodName}
				</td>

				<td>
					${taskScheduleJob.jobStatus==1?'启动':'暂停'}
				</td>
				<td>
					${taskScheduleJob.jobGroup}
				</td>
				<td>
					${taskScheduleJob.jobName}
				</td>
				<td>
						${taskScheduleJob.description}
				</td>

				<td>
					<c:if test="${taskScheduleJob.jobStatus==1}">
						<button type="button" class="btn btn-danger btn-xs" onclick="triggerOperate('${ctx}/sys/taskScheduleJob/changeJobStatus?cmd=stop&id=${taskScheduleJob.id}')">停止</button>
					</c:if>
					<c:if test="${taskScheduleJob.jobStatus!=1}">
						<button type="button" class="btn btn-success btn-xs" onclick="triggerOperate('${ctx}/sys/taskScheduleJob/changeJobStatus?cmd=start&id=${taskScheduleJob.id}')">启动</button>
					</c:if>
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