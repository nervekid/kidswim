<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>教练员管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var checkBoxChecked ='${checkBoxChecked}';
            if(checkBoxChecked){
                $("#collectionId").attr("checked",true);
            }
            laydate.render({
				elem: '#entryYear',
				type: 'month'
			});
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
	<form:form id="searchForm" modelAttribute="sysBaseCoach" action="${ctx}/att/sysBaseCoach/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<form:input placeholder="教练编码" path="code" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="10"  class=" form-control input-sm"/>
			<form:input placeholder="中文名" path="nameCn" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="20"  class=" form-control input-sm"/>
			<form:input placeholder="英文名" path="nameEn" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="20"  class=" form-control input-sm"/>
			<form:input placeholder="入职年月" path="entryYear" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="64"  cssStyle="width:100px" class=" form-control input-sm"/>
			<form:select placeholder="性别" path="sex"  class="form-control m-b required" onchange="search()" >
				<form:option value="" label="请选择"/>
				<form:options items="${fns:getDictList('sex_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
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
			<shiro:hasPermission name="att:sysBaseCoach:add">
				<table:addRow url="${ctx}/att/sysBaseCoach/form?menuId=${menu.id}" title="教练员"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:sysBaseCoach:edit">
			    <table:editRow url="${ctx}/att/sysBaseCoach/form" title="教练员" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:sysBaseCoach:del">
				<table:delRow url="${ctx}/att/sysBaseCoach/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:sysBaseCoach:import">
				<table:importExcel url="${ctx}/att/sysBaseCoach/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:sysBaseCoach:export">
	       		<table:exportExcel url="${ctx}/att/sysBaseCoach/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
<!-- 	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
 -->
			</div>
		<div class="pull-right">
			<button  class="btn btn-success btn-sm" onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
<div style="overflow:auto;">
	<!-- 表格 -->
	<table id="contentTable"  style="min-width:1100px;"  class="table table_list_box text-nowrap table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column code">教练编码</th>
				<th  class="sort-column nameCn">中文名</th>
				<th  class="sort-column nameEn">英文名</th>
				<th  class="sort-column sex">性别</th>
				<th  class="sort-column phone">电话号码</th>
				<th  class="sort-column idNo">身份证号码</th>
				<th  class="sort-column email">电邮</th>
				<th  class="sort-column address">地址</th>
				<th  class="sort-column educationLevel">教育程度</th>
				<th  class="sort-column entryYear">入职年月</th>
				<th  class="sort-column entryPosition">入职职位</th>
				<th  class="sort-column entryHourWage">入职时薪 单位(港币)</th>
				<th  class="sort-column presentPosition">现时职位</th>
				<th  class="sort-column presentHourWage">现时时薪 单位(港币)</th>
				<th  class="sort-column industryExperience">行业经验 单位(年)</th>
				<th  class="sort-column contractFlag">有否合约</th>
				<th  class="sort-column accumulatedTeachingHours">累计教导时数 单位(小时)</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysBaseCoach">
			<tr>
				<td> <input type="checkbox" id="${sysBaseCoach.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看教练员', '${ctx}/att/sysBaseCoach/view?id=${sysBaseCoach.id}','800px', '500px')">
					${sysBaseCoach.code}
				</a></td>
				<td>
					${sysBaseCoach.nameCn}
				</td>
				<td>
					${sysBaseCoach.nameEn}
				</td>
				<td>
					${fns:getDictLabel(sysBaseCoach.sex, 'sex_flag', '')}
				</td>
				<td>
					${sysBaseCoach.phone}
				</td>
				<td>
					${sysBaseCoach.idNo}
				</td>
				<td>
					${sysBaseCoach.email}
				</td>
				<td>
					${sysBaseCoach.address}
				</td>
				<td>
					${sysBaseCoach.educationLevel}
				</td>
				<td>
					${sysBaseCoach.entryYear}
				</td>
				<td>
					${sysBaseCoach.entryPosition}
				</td>
				<td>
					${sysBaseCoach.entryHourWage}
				</td>
				<td>
					${sysBaseCoach.presentPosition}
				</td>
				<td>
					${sysBaseCoach.presentHourWage}
				</td>
				<td>
					${sysBaseCoach.industryExperience}
				</td>
				<td>
					${fns:getDictLabel(sysBaseCoach.contractFlag, 'yes_no', '')}
				</td>
				<td>
					${sysBaseCoach.accumulatedTeachingHours}
				</td>
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