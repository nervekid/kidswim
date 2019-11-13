<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>系统组织架构管理</title>
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
	<%-- <div class="ibox-title">
		<h5></h5>
		<div class="ibox-tools">
				<label>	<input id="collectionId" type="checkbox" onclick="collectionMenu('${ctx}/sys/sysUserCollectionMenu/collectionMenu','${menu.href}','${menu.name}','${menu.id}')">&nbsp;&nbsp;&nbsp;是否收藏到主页面</label>
	   </div>
	</div> --%>

    <div class="ibox-content">
	<sys:message content="${message}"/>

	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="sysOrganizational" action="${ctx}/sys/sysOrganizational/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<input id="organTag" name="organTag" type="hidden" value="${sysOrganizational.organTag}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">

		<sys:treeselect  placeholder="归属部门" id="office" name="office.id" value="${sysOrganizational.office.id}" labelName="office.name" labelValue="${sysOrganizational.office.name}"
			title="部门" url="/sys/office/treeDataByOwer?organTagA=${sysOrganizational.organTag}" cssClass="form-control input-sm" allowClear="true" notAllowSelectParent="true"/>
		<form:input path="user.name" class=" form-control input-sm" placeholder="用户名称"/>
		<span>员工状态：</span>
		<form:select  path="becomeWorker"  class="form-control m-b" onchange="search()" style="width:160px;">
			<form:option value="" label=""/>
			<form:options items="${fns:getDictList('staff_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="sys:sysOrganizational:add">
				<table:addRow url="${ctx}/sys/sysOrganizational/form?menuId=${menu.id}&organTag=${sysOrganizational.organTag}&oId=${sysOrganizational.office.id}" title="系统组织架构"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysOrganizational:edit">
			    <table:editRow url="${ctx}/sys/sysOrganizational/form?editFlag=disabled" title="系统组织架构" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="sys:ehrSysOrganization:edit">
			    <table:personInfo url="${ctx}/sys/sysOrganizational/ehrForm?userId=${sysOrganizational.user.id}" title="个人信息展示页" label="个人信息" id="contentTable"></table:personInfo><!-- 编辑按钮 -->
			</shiro:hasPermission> --%>
			<c:if test="${sysOrganizational.organTag eq '1'}" >
				<shiro:hasPermission name="sys:ehrSysOrganization:formalStatus">
					<ehrselect:formalStaff url="${ctx}/sys/sysOrganizational/formalStatus" title="入职" label="入职" id="contentTable" officeId="${sysOrganizational.office.id}" organTag="${sysOrganizational.organTag}"></ehrselect:formalStaff><!-- 入职按钮 -->
				</shiro:hasPermission>
			</c:if>
			<shiro:hasPermission name="sys:sysOrganizational:del">
				<table:delRow url="${ctx}/sys/sysOrganizational/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysOrganizational:import">
				<table:importExcel url="${ctx}/sys/sysOrganizational/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:sysOrganizational:export">
	       		<table:exportExcel url="${ctx}/sys/sysOrganizational/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       	<%-- <form:select placeholder="组织架构" path="organTag"  class="form-control m-b required"  >
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('org_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select> --%>

<!-- 	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
 -->
			</div>
		<div class="pull-right">
						<button  class="btn btn-success btn-sm" onclick="search()"><i class="fa fa-search"></i> 查询</button>
						<button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
					</div>
	</div>
	</div>
		<div style="overflow:auto;">
	<!-- 表格 -->
			<table id="contentTable" style="min-width:1500px;overflow:hidden" class="table table_list_box text-nowrap table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column officeOne.name">人员</th>
				<c:if test="${sysOrganizational.organTag eq '1'}" >
					<th  class="sort-column userInfo.become_worker">员工状态</th>
				</c:if>
				<th  class="sort-column company.id">上级部门</th>
				<th  class="sort-column office.name">归属部门</th>
				<th  class="sort-column user.name">一级部门</th>
				<th  class="sort-column dict.value">组织架构</th>
				<c:if test="${sysOrganizational.organTag eq '4'}" >
					<th >职级</th>
					<th >是否上级</th>
				</c:if>
				<th  class="sort-column a.remarks">备注</th>
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysOrganizational">
			<tr>
				<td> <input type="checkbox" id="${sysOrganizational.id}" class="i-checks" status="${sysOrganizational.becomeWorker}" ></td>
				<td><%--<a  href="#" onclick="openDialog('个人信息展示页', '${ctx}/sys/sysOrganizational/ehrForm?userId=${sysOrganizational.user.id}','1000px', '800px')">
						${sysOrganizational.user.name}
				</a>--%>
					<c:if test="${sysOrganizational.organTag eq '1'}" >
						<a class="J_menuItem" href="#" onclick='top.openTab("${ctx}/sys/sysOrganizational/ehrForm?userId=${sysOrganizational.user.id}","个人信息：${sysOrganizational.user.name}", false)'>
								${sysOrganizational.user.name}
						</a>
					</c:if>
					<c:if test="${sysOrganizational.organTag ne '1'}" >
								${sysOrganizational.user.name}
					</c:if>
				</td>
				<c:if test="${sysOrganizational.organTag eq '1'}" >
					<td>
						${fns:getDictLabel(sysOrganizational.becomeWorker, 'staff_status', '')}
					</td>
				</c:if>
				<td>
					${sysOrganizational.companyName}
				</td>
				<td>
					${sysOrganizational.office.name}
				</td>
				<td>
					${sysOrganizational.officeOne.name}
				</td>
				<td>
					${fns:getDictLabel(sysOrganizational.organTag, 'org_flag', '')}
				</td>
				<c:if test="${sysOrganizational.organTag eq '4'}" >
					<td>
							${fns:getDictLabel(sysOrganizational.rankFlag, 'rank_flag', '')}
					</td>
					<td>
							${fns:getDictLabel(sysOrganizational.superiorFlag, 'yes_no', '')}
					</td>
				</c:if>
				<td>
					${sysOrganizational.remarks}
				</td>
				<%-- <td>
					<shiro:hasPermission name="sys:sysOrganizational:view">
						<a href="#" onclick="openDialogView('查看系统组织架构', '${ctx}/sys/sysOrganizational/view?id=${sysOrganizational.id}','950px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:sysOrganizational:edit">
    					<a href="#" onclick="openDialog('修改系统组织架构', '${ctx}/sys/sysOrganizational/form?id=${sysOrganizational.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="sys:sysOrganizational:del">
						<a href="${ctx}/sys/sysOrganizational/delete?id=${sysOrganizational.id}" onclick="return confirmx('确认要删除该系统组织架构吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td> --%>
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