<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>學員管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var checkBoxChecked ='${checkBoxChecked}';
            if(checkBoxChecked){
                $("#collectionId").attr("checked",true);
            }
            /* $("#contentTable").bootstrapTable({
				fixedColumns: true,//固定列
				fixedNumber:3
			}); */
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
	<form:form id="searchForm" modelAttribute="sysBaseStudent" action="${ctx}/att/sysBaseStudent/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<form:input placeholder="學員編號" path="code" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="64"  class=" form-control input-sm"/>
			<form:input placeholder="中文名" path="nameCn" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="20"  class=" form-control input-sm"/>
			<form:input placeholder="英文名" path="nameEn" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="20"  class=" form-control input-sm"/>
			<form:input placeholder="已懂泳式" path="studiedSwimmingStyle" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="20"  class=" form-control input-sm"/>
			<form:select placeholder="性別" path="sex"  class="form-control m-b required" onchange="search()" >
				<form:option value="" label="請選擇"/>
				<form:options items="${fns:getDictList('sex_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
			</form:select>
			<form:select placeholder="課程等級" path="courseLevelFlag"  class="form-control m-b required" onchange="search()" >
				<form:option value="" label="請選擇"/>
				<form:options items="${fns:getDictList('courseLevel_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
			</form:select>
			<form:select placeholder="是否曾遇溺" path="drownedFlag"  class="form-control m-b required" onchange="search()" >
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

			</div>
		<div class="pull-right">
			<button  class="btn btn-success btn-sm" onclick="search()" ><i class="fa fa-search"></i> 查詢</button>
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
				<th  class="sort-column code">學員編號</th>
				<th  class="sort-column nameCn">中文名</th>
				<th  class="sort-column nameEn">英文名</th>
				<th  class="sort-column idNo">身份證號碼</th>
				<th  class="sort-column sex">性別 字典枚舉</th>
				<th  class="sort-column email">電郵</th>
				<th  class="sort-column phone">電話號碼</th>
				<th  class="sort-column birthday">出生日期</th>
				<th  class="sort-column contactAddress">聯系地址</th>
				<th  class="sort-column attendingSchool">就讀學校</th>
				<th  class="sort-column grade">年級</th>
				<th  class="sort-column studiedSwimFlag">是否曾學習過遊泳</th>
				<th  class="sort-column studySwimmingOrgan">習泳機構</th>
				<th  class="sort-column studiedSwimmingStyle">已懂泳式</th>
				<th  class="sort-column drownedFlag">是否曾遇溺 </th>
				<th  class="sort-column drownedAddressFlag">遇溺地点 </th>
				<th  class="sort-column drownedAge">遇溺歲數</th>
				<th  class="sort-column longTermDisease">長期病患</th>
				<th  class="sort-column longTermMedicine">長期服藥</th>
				<th  class="sort-column courseLevelFlag">課程等級</th>
				<th  class="sort-column contactPhone">聯系人號碼</th>
				<th  class="sort-column contactRelationship">聯系人關系</th>
				<th  class="sort-column urgentPhone">緊急聯系人號碼</th>
				<th  class="sort-column urgentRelationship">緊急聯系人關系</th>
				<th  class="sort-column guardianName">監護人姓名</th>
				<th  class="sort-column guardianPhone">監護人手機號碼</th>
				<th  class="sort-column guardianIdNo">監護人身份證號碼</th>
				<th  class="sort-column guardianRelationship">監護人關系</th>
				<th  class="sort-column facebook">facebook賬號</th>
			</tr>
		</thead>
		<tbody style="height: 600px">
		<c:forEach items="${page.list}" var="sysBaseStudent">
			<tr>
				<td> <input type="checkbox" id="${sysBaseStudent.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看學員', '${ctx}/att/sysBaseStudent/view?id=${sysBaseStudent.id}','800px', '500px')">
					${sysBaseStudent.code}
				</a></td>
				<td>
					${sysBaseStudent.nameCn}
				</td>
				<td>
					${sysBaseStudent.nameEn}
				</td>
				<td>
					${sysBaseStudent.idNo}
				</td>
				<td>
					${fns:getDictLabel(sysBaseStudent.sex, 'sex_flag', '')}
				</td>
				<td>
					${sysBaseStudent.email}
				</td>
				<td>
					${sysBaseStudent.phone}
				</td>
				<td>
					<fmt:formatDate value="${sysBaseStudent.birthday}" pattern="yyyy-MM-dd"/>
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
					${fns:getDictLabel(sysBaseStudent.studiedSwimFlag, 'yes_no', '')}
				</td>
				<td>
					${sysBaseStudent.studySwimmingOrgan}
				</td>
				<td>
					${sysBaseStudent.studiedSwimmingStyle}
				</td>
				<td>
					${fns:getDictLabel(sysBaseStudent.drownedFlag, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(sysBaseStudent.drownedAddressFlag, 'drowned_address_flag', '')}
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
					${fns:getDictLabel(sysBaseStudent.courseLevelFlag, 'courseLevel_flag', '')}
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