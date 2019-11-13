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
				<th  class="sort-column code">课程编号 年份+地点编号+教练员编号 例如:2019MS-CAOO3 按照规则编码</th>
				<th  class="sort-column coathId">教练员id</th>
				<th  class="sort-column beginYearMonth">开始年月</th>
				<th  class="sort-column endYearMonth">结束年月</th>
				<th  class="sort-column courseDate">上课日期</th>
				<th  class="sort-column courseNum">课程所属第几堂</th>
				<th  class="sort-column courseAddress">课程地址 字典枚举 course_addrese_flag MS:摩士 HH:斧山 KT:观塘</th>
				<th  class="sort-column strInWeek">星期几 字典枚举 week_flag 1:星期一 2:星期二 3:星期三 4:星期四 5:星期五 6:星期六 7:星期日 </th>
				<th  class="sort-column updateDate">更新时间</th>
				<th  class="sort-column remarks">备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="serCourse">
			<tr>
				<td> <input type="checkbox" id="${serCourse.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看课程', '${ctx}/att/serCourse/view?id=${serCourse.id}','800px', '500px')">
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
				<td>
					<fmt:formatDate value="${serCourse.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${serCourse.remarks}
				</td>
				<td>
					<shiro:hasPermission name="att:serCourse:view">
						<a href="#" onclick="openDialogView('查看课程', '${ctx}/att/serCourse/view?id=${serCourse.id}','950px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="att:serCourse:edit">
    					<a href="#" onclick="openDialog('修改课程', '${ctx}/att/serCourse/form?id=${serCourse.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="att:serCourse:del">
						<a href="${ctx}/att/serCourse/delete?id=${serCourse.id}" onclick="return confirmx('确认要删除该课程吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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
</div>
</body>
</html>