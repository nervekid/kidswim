<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户接收短信情况管理</title>
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
	<form:form id="searchForm" modelAttribute="messageRecord" action="${ctx}/sys/messageRecord/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		
				<form:input placeholder="用户名称" path="userName" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="255"  class=" form-control input-sm"/>
		
				<form:input placeholder="用户手机号" path="userPhone" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="255"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
	       		<table:exportExcel url="${ctx}/sys/messageRecord/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
		<div style="overflow:auto;">
	<!-- 表格 -->
			<table id="contentTable" style="min-width:1500px;overflow:hidden" class="table table_list_box text-nowrap table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th class="text-center">用户名</th>
				<th class="text-center">用户手机</th>
				<th class="text-center">用户邮箱</th>
				<th class="text-center">用户登录名</th>
				<th class="text-center">最近一次是否接收成功</th>
				<th class="text-center">累计接收成功数</th>
				<th class="text-center">累计接收失败数</th>
				<th class="text-center">最近一次接收成功</th>
				<th class="text-center">发送内容</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="messageRecord">
			<tr>
				<td> <input type="checkbox" id="${messageRecord.id}" class="i-checks"></td>
				<td class="text-center">${messageRecord.userName}</td>
				<td class="text-center">${messageRecord.userPhone}</td>
				<td class="text-center">${messageRecord.userEmail}</td>
				<td class="text-center">${messageRecord.userLoginName}</td>
				<c:choose>
					<c:when test="${messageRecord.isSuccess eq '0'}">
						<td class="text-center">成功</td>
					</c:when>
					<c:otherwise>
						<td class="text-center">失败</td>
					</c:otherwise>
				</c:choose>
				<td class="text-center">${messageRecord.successCount}</td>
				<td class="text-center">${messageRecord.failCount}</td>
				<td class="text-center">${messageRecord.updateSendMessageDate}</td>
				<td class="text-center">${messageRecord.content}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		</div>
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>
<style>
	.text-center{
		text-align: center;
	}

</style>