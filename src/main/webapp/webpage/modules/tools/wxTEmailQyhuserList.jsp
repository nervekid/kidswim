<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>企业微信推送消息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var checkBoxChecked ='${checkBoxChecked}';
            if(checkBoxChecked){
                $("#collectionId").attr("checked",true);
            }

            $("#submitWxTEmailQyhuserModal").click(function () {
                $("#sendWxTEmailQyhuserForm").submit();
                layer.load(0, {shade: [0.3, '#F5F5F5']});
            });
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
	<form:form id="searchForm" modelAttribute="wxTEmailQyhuser" action="${ctx}/tools/wxTEmailQyhuser/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${wxTEmailQyhuser.menuId}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		
				<form:input placeholder="phone" path="phone" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="64"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="tools:wxTEmailQyhuser:send">
				<button id="sendWxTEmailQyhuserBtn" class="btn btn-success btn-sm" data-placement="left"
						data-toggle="modal" data-target="#sendWxTEmailQyhuserModal">推送
				</button>
			</shiro:hasPermission>
			<%--<shiro:hasPermission name="tools:wxTEmailQyhuser:add">
				<table:addRow url="${ctx}/tools/wxTEmailQyhuser/form?menuId=${menu.id}" title="企业微信推送消息"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="tools:wxTEmailQyhuser:edit">
			    <table:editRow url="${ctx}/tools/wxTEmailQyhuser/form" title="企业微信推送消息" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="tools:wxTEmailQyhuser:del">
				<table:delRow url="${ctx}/tools/wxTEmailQyhuser/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="tools:wxTEmailQyhuser:import">
				<table:importExcel url="${ctx}/tools/wxTEmailQyhuser/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="tools:wxTEmailQyhuser:export">
	       		<table:exportExcel url="${ctx}/tools/wxTEmailQyhuser/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>--%>
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
				<th  class="sort-column kiteUserid">kite_userid</th>
				<th  class="sort-column phone">phone</th>
				<th  class="sort-column dutie">dutie</th>
				<th  class="sort-column emailaccount">emailaccount</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wxTEmailQyhuser">
			<tr>
				<td> <input type="checkbox" id="${wxTEmailQyhuser.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看企业微信推送消息', '${ctx}/tools/wxTEmailQyhuser/view?id=${wxTEmailQyhuser.id}','800px', '500px')">
					${wxTEmailQyhuser.kiteUserid}
				</a></td>
				<td>
					${wxTEmailQyhuser.phone}
				</td>
				<td>
					${wxTEmailQyhuser.dutie}
				</td>
				<td>
					${wxTEmailQyhuser.emailaccount}
				</td>
				<td>
					<shiro:hasPermission name="tools:wxTEmailQyhuser:view">
						<a href="#" onclick="openDialogView('查看企业微信推送消息', '${ctx}/tools/wxTEmailQyhuser/view?id=${wxTEmailQyhuser.id}','950px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="tools:wxTEmailQyhuser:edit">
    					<a href="#" onclick="openDialog('修改企业微信推送消息', '${ctx}/tools/wxTEmailQyhuser/form?id=${wxTEmailQyhuser.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="tools:wxTEmailQyhuser:del">
						<a href="${ctx}/tools/wxTEmailQyhuser/delete?id=${wxTEmailQyhuser.id}" onclick="return confirmx('确认要删除该企业微信推送消息吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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
		<div class="modal fade" id="sendWxTEmailQyhuserModal" tabindex="-1" role="dialog"
			 aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4>
							推送邮箱消息到企业微信
						</h4>
					</div>
					<div class="modal-body">
						<form:form id="sendWxTEmailQyhuserForm" modelAttribute="wxTEmailQyhuser"
								   action="${ctx}/tools/wxTEmailQyhuser/send"
								   method="POST" class="form-inline">

						</form:form>
					</div>
					<div class="modal-footer">
						<button id="submitWxTEmailQyhuserModal" type="button"
								class="btn btn-success btn-sm">
							确认
						</button>
					</div>
				</div>
			</div>
		</div>
</div>
</body>
</html>