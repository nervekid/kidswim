<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>学员管理</title>
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
	<form:form id="searchForm" modelAttribute="sysBaseStudent" action="${ctx}/att/sysBaseStudent/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		
				<form:input placeholder="学员编号 入学年份+月份+流水码 如:201901000001" path="code" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="64"  class=" form-control input-sm"/>
		
				<form:input placeholder="中文名" path="nameCn" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="20"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="att:sysBaseStudent:add">
				<table:addRow url="${ctx}/att/sysBaseStudent/form?menuId=${menu.id}" title="学员"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:sysBaseStudent:edit">
			    <table:editRow url="${ctx}/att/sysBaseStudent/form" title="学员" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:sysBaseStudent:del">
				<table:delRow url="${ctx}/att/sysBaseStudent/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:sysBaseStudent:import">
				<table:importExcel url="${ctx}/att/sysBaseStudent/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:sysBaseStudent:export">
	       		<table:exportExcel url="${ctx}/att/sysBaseStudent/export?menuId=${menu.id}"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column code">学员编号 入学年份+月份+流水码 如:201901000001</th>
				<th  class="sort-column nameCn">中文名</th>
				<th  class="sort-column nameEN">英文名</th>
				<th  class="sort-column idNo">身份证号码</th>
				<th  class="sort-column sex">性别 字典枚举 sex_flag 1:男 2:女</th>
				<th  class="sort-column email">电邮</th>
				<th  class="sort-column phone">电话号码</th>
				<th  class="sort-column birthday">出生日期</th>
				<th  class="sort-column contactAddress">联系地址</th>
				<th  class="sort-column attendingSchool">就读学校</th>
				<th  class="sort-column grade">年级</th>
				<th  class="sort-column studiedSwimFlag">是否曾学习过游泳 字典枚举 yes_no 1:是 0:否</th>
				<th  class="sort-column studySwimmingOrgan">习泳机构</th>
				<th  class="sort-column studiedSwimmingStyle">已懂泳式 以,号分割</th>
				<th  class="sort-column drownedFlag">是否曾遇溺 字典枚举 yes_no 1:是 0:否</th>
				<th  class="sort-column drownedAge">预溺岁数</th>
				<th  class="sort-column longTermDisease">长期病患</th>
				<th  class="sort-column longTermMedicine">长期服药</th>
				<th  class="sort-column courseLevelFlag">课程等级 字典枚举 courseLevel_flag 1:NA 2:BB 3:CA 4:CB 5:CC 6:AD 7:TA 8:TB</th>
				<th  class="sort-column contactPhone">联系人号码</th>
				<th  class="sort-column contactRelationship">联系人关系</th>
				<th  class="sort-column urgentPhone">紧急联系人号码</th>
				<th  class="sort-column urgentRelationship">紧急联系人关系</th>
				<th  class="sort-column guardianName">监护人姓名</th>
				<th  class="sort-column guardianPhone">监护人手机号码</th>
				<th  class="sort-column guardianIdNo">监护人身份证号码</th>
				<th  class="sort-column guardianRelationship">监护人关系</th>
				<th  class="sort-column facebook">facebook账号</th>
				<th  class="sort-column whatsApp">whatsapp账号</th>
				<th  class="sort-column photoId">照片id (暂缺,留位)</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysBaseStudent">
			<tr>
				<td> <input type="checkbox" id="${sysBaseStudent.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看学员', '${ctx}/att/sysBaseStudent/view?id=${sysBaseStudent.id}','800px', '500px')">
					${sysBaseStudent.code}
				</a></td>
				<td>
					${sysBaseStudent.nameCn}
				</td>
				<td>
					${sysBaseStudent.nameEN}
				</td>
				<td>
					${sysBaseStudent.idNo}
				</td>
				<td>
					${sysBaseStudent.sex}
				</td>
				<td>
					${sysBaseStudent.email}
				</td>
				<td>
					${sysBaseStudent.phone}
				</td>
				<td>
					<fmt:formatDate value="${sysBaseStudent.birthday}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sysBaseStudent.contactAddress}
				</td>
				<td>
					${sysBaseStudent.attendingSchool}
				</td>
				<td>
					${sysBaseStudent.grade}
				</td>
				<td>
					${sysBaseStudent.studiedSwimFlag}
				</td>
				<td>
					${sysBaseStudent.studySwimmingOrgan}
				</td>
				<td>
					${sysBaseStudent.studiedSwimmingStyle}
				</td>
				<td>
					${sysBaseStudent.drownedFlag}
				</td>
				<td>
					${sysBaseStudent.drownedAge}
				</td>
				<td>
					${sysBaseStudent.longTermDisease}
				</td>
				<td>
					${sysBaseStudent.longTermMedicine}
				</td>
				<td>
					${sysBaseStudent.courseLevelFlag}
				</td>
				<td>
					${sysBaseStudent.contactPhone}
				</td>
				<td>
					${sysBaseStudent.contactRelationship}
				</td>
				<td>
					${sysBaseStudent.urgentPhone}
				</td>
				<td>
					${sysBaseStudent.urgentRelationship}
				</td>
				<td>
					${sysBaseStudent.guardianName}
				</td>
				<td>
					${sysBaseStudent.guardianPhone}
				</td>
				<td>
					${sysBaseStudent.guardianIdNo}
				</td>
				<td>
					${sysBaseStudent.guardianRelationship}
				</td>
				<td>
					${sysBaseStudent.facebook}
				</td>
				<td>
					${sysBaseStudent.whatsApp}
				</td>
				<td>
					${sysBaseStudent.photoId}
				</td>
				<td>
					<shiro:hasPermission name="att:sysBaseStudent:view">
						<a href="#" onclick="openDialogView('查看学员', '${ctx}/att/sysBaseStudent/view?id=${sysBaseStudent.id}','950px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="att:sysBaseStudent:edit">
    					<a href="#" onclick="openDialog('修改学员', '${ctx}/att/sysBaseStudent/form?id=${sysBaseStudent.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="att:sysBaseStudent:del">
						<a href="${ctx}/att/sysBaseStudent/delete?id=${sysBaseStudent.id}" onclick="return confirmx('确认要删除该学员吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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