<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>职位表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			/*laydate.laydate({
	            elem: '#createDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });*/
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
		<%-- <div class="ibox-tools">
				<label>	<input id="collectionId" type="checkbox" onclick="collectionMenu('${ctx}/sys/sysUserCollectionMenu/collectionMenu','${menu.href}','${menu.name}','${menu.id}')">&nbsp;&nbsp;&nbsp;是否收藏到主页面</label>
	   </div> --%>
	</div>

    <div class="ibox-content">
	<sys:message content="${message}"/>

	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="userPosition" action="${ctx}/sys/sysOrganizational/positionSelectList" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">

				<form:input placeholder="职位名称" path="name" htmlEscape="false" cssStyle="width:120px"  onkeydown="keyDownEnter(event)"  maxlength="50"  class=" form-control input-sm"/>

				<form:select placeholder="职级" path="rank"  class="form-control m-b" onchange="search()"  style="width:100px;">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('functional_rank')}" itemLabel="label"   itemValue="value" htmlEscape="false"/>
				</form:select>

			<%--	<form:select placeholder="业务属性" path="businessAttributes"  class="form-control m-b" onchange="search()"  style="width:100px;">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('business_attributes')}" itemLabel="label"   itemValue="value" htmlEscape="false"/>
				</form:select>--%>

				<form:select placeholder="职能类别" path="functionalCategories"  class="form-control m-b" onchange="search()"  style="width:100px;">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('functional_categories')}" itemLabel="label"   itemValue="value" htmlEscape="false"/>
				</form:select>

				<form:select placeholder="管理层级" path="managementLayers"  class="form-control m-b" onchange="search()"  style="width:100px;">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('management_Layers')}" itemLabel="label"   itemValue="value" htmlEscape="false"/>
				</form:select>

				<form:select placeholder="关键岗位" path="keyPositions"  class="form-control m-b" onchange="search()"  style="width:100px;">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('yes_no')}" itemLabel="label"   itemValue="value" htmlEscape="false"/>
				</form:select>
		 </div>
	</form:form>
	<br/>
	</div>
	</div>

	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-right">
			<button  class="btn btn-success btn-sm" onclick="search()"><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>

	<!-- 表格 -->
	<table id="contentTable_position" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column a.name" style="text-align: center">职位名称</th>
				<th  class="sort-column rank" style="text-align: center">职级</th>
				<%--<th  class="sort-column businessAttributes" style="text-align: center">业务属性</th>--%>
				<th  class="sort-column functional_Categories" style="text-align: center">职能类别</th>
				<th  class="sort-column management_Layers" style="text-align: center">管理层级</th>
				<th  class="sort-column key_Positions" style="text-align: center">关键岗位</th>
				<%--<th  class="sort-column sort" style="text-align: center">职位排序</th>--%>
				<th  class="sort-column a.remarks" style="text-align: center">备注信息</th>
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userPosition">
			<tr>
				<td> <input type="checkbox" id="${userPosition.id}" class="i-checks"></td>
				<td style="text-align: center"><a  href="#" onclick="openDialogView('查看职位表', '${ctx}/ehr/position/userPosition/view?id=${userPosition.id}','800px', '500px')">
					${userPosition.name}
				</a></td>
				<td style="text-align: center">
					${userPosition.rankName}
				</td>
			<%--	<td style="text-align: center">
					${userPosition.businessAttributesName}
				</td>--%>
				<td style="text-align: center">
					${userPosition.functionalCategoriesName}
				</td>
				<td style="text-align: center">
					${userPosition.managementLayersName}
				</td>
				<td style="text-align: center">
					${userPosition.keyPositionsName}
				</td>
				<%--<td style="text-align: center">
					${userPosition.sort}
				</td>--%>
				<td style="text-align: center">
					${userPosition.remarks}
				</td>
				<%-- <td>
					<shiro:hasPermission name="ehr:position:userPosition:view">
						<a href="#" onclick="openDialogView('查看职位表', '${ctx}/ehr/position/userPosition/view?id=${userPosition.id}','950px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="ehr:position:userPosition:edit">
    					<a href="#" onclick="openDialog('修改职位表', '${ctx}/ehr/position/userPosition/form?id=${userPosition.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="ehr:position:userPosition:del">
						<a href="${ctx}/ehr/position/userPosition/delete?id=${userPosition.id}" onclick="return confirmx('确认要删除该职位表吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td> --%>
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