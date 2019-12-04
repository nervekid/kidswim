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

            $("#submitCreateOffsetData").click(function(){
				duihuan();
			});

            laydate.render({
				elem: '#beginTimeStrSelect'
			});
            laydate.render({
				elem: '#endTimeStrSelect'
			});

		});


		function createOffset(flag,typeFlag) {
            $("#accounttypeStr").val(flag);
            $("#typeFlag").val(typeFlag);
        }

		function duihuan() {
            console.log("提交,正在进行泳课排班生成...");
            var coachSelectVal = $("#coachSelect").val();
            if(null==coachSelect|| coachSelect==''){
                alert("您还没有选择教练员,请选择！");
                return;
            }
            var courseAddressSelectVal = $("#courseAddressSelect").val();
            if(null==courseAddressSelectVal|| courseAddressSelectVal==''){
                alert("您还没有选择泳池地址,请选择！");
                return;
            }
            var weekNumSelectVal = $("#weekNumSelect").val();
            if(null==weekNumSelectVal|| weekNumSelectVal==''){
                alert("您还没有选择星期几,请选择！");
                return;
            }
            var beginTimeStrSelectVal = $("#beginTimeStrSelect").val();
            if(null==beginTimeStrSelectVal|| beginTimeStrSelectVal==''){
                alert("您还没有选择开始日期,请选择！");
                return;
            }
            var endTimeStrSelectVal = $("#endTimeStrSelect").val();
            if(null==endTimeStrSelectVal|| endTimeStrSelectVal==''){
                alert("您还没有选择结束日期,请选择！");
                return;
            }

            if(confirm("确定要进行课程生成吗？")==true){
                $.ajax({
                    type:"post",
                    url:"${ctx}/att/serCourse/generateCourseScheduling",
                    data:{"courcouseAddressFlag":courseAddressSelectVal,
                    	  "coachId":coachSelectVal,
                    	  "beginTimeStr":beginTimeStrSelectVal,
                    	  "endTimeStr":endTimeStrSelectVal,
                    	  "weekNum":weekNumSelectVal},
                    }
                    success:function (data) {
                    	alert("生成课程成功！");
                    },
                    fail:function (data) {
                        alert("生成课程失败！");
                        return;
                    }
                });
            }
        }

	</script>
	<script type="text/javascript" src="${ctxStatic}/common/collectionMenu.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">

    <div class="ibox-content">
	<sys:message content="${message}"/>

		<div class="modal fade" id="createOffsetData" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4>
							泳课课程排班生成
						</h4>
						说明：取一段时间范围，选择礼拜几，时间范围不可以少于7天。
					</div>

					<div class="modal-body">

						<div class="dropdown-toggle" style="width: 50px;height: 20px" id="username">
							<span style="text-align: left" ></span>
						</div>
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  >
							<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right"><font color="red">*</font>教练员：</label></td>
								<td class="width-35">
									<sys:treeselect  id="coachSelect"  name="sysBaseCoach.id" value="" labelName="sysBaseCoach.name" labelValue=""
										 title="教练员" url="/att/sysBaseCoach/treeData" cssClass="form-control"
										 allowClear="true"  placeholder="请选择教练员！" />
								</td>
							<tr>
								<td class="width-15 active"><label class="pull-right"><font color="red">*</font>泳池：</label></td>
								<td class="width-35">
									<select id="courseAddressSelect" name="courseAddressSelect" class="form-control" type="select">
										<c:forEach var="dict" items="${courseAddressDictList}" varStatus="status">
											<option value="${dict.value}" title="${dict.description}" }>${dict.label}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right"><font color="red">*</font>礼拜几：</label></td>
								<td class="width-35">
									<select id="weekNumSelect" name="weekNumSelect" class="form-control" type="select">
										<c:forEach var="dict" items="${weekNumDictList}" varStatus="status">
											<option value="${dict.value}" title="${dict.description}" }>${dict.label}</option>
										</c:forEach>
									</select>
								</td>
							</tr>

							<tr>
								<td class="width-15 active"><label class="pull-right"><font color="red">*</font>礼拜几：</label></td>
								<td class="width-35">
								   <input id="beginTimeStrSelect" placeholder="开始日期" name="beginTimeStrSelect" type="text" length="20" class="form-control"
                                   value=""/>
								</td>
							</tr>

							<tr>
								<td class="width-15 active"><label class="pull-right"><font color="red">*</font>礼拜几：</label></td>
								<td class="width-35">
								   <input id="endTimeStrSelect" placeholder="结束日期" name="endTimeStrSelect" type="text" length="20" class="form-control"
                                   value=""/>
								</td>
							</tr>

							</tr>
							</tbody>
						</table>
						<sys:message content="${message}"/>
						<input type="hidden"  id="userId">
					</div>
					<div class="modal-footer">
						<button id="submitCreateOffsetData"  type="button" class="btn btn-success btn-sm">
							确认生成
						</button>
					</div>
				</div>
			</div>
		</div>


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

			</div>
		<div class="pull-right">
			<shiro:hasPermission name="att:serCourse:generativeCourse">
				<button  data-placement="left" class="btn btn-success btn-sm" data-toggle="modal" onclick="createOffset('1','0')" data-target="#createOffsetData" >生成课程</button>
			</shiro:hasPermission>
			<button  class="btn btn-success btn-sm" onclick="search()" ><i class="fa fa-search"></i> 查詢</button>
			<button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>

	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column code">課程編號</th>
				<th  class="sort-column coathId">教練員id</th>
				<th  class="sort-column beginYearMonth">開始年月</th>
				<th  class="sort-column endYearMonth">結束年月</th>
				<th  class="sort-column courseDate">上課日期</th>
				<th  class="sort-column courseNum">課程所屬第幾堂</th>
				<th  class="sort-column courseAddress">課程地址</th>
				<th  class="sort-column strInWeek">星期幾</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="serCourse">
			<tr>
				<td> <input type="checkbox" id="${serCourse.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看課程', '${ctx}/att/serCourse/view?id=${serCourse.id}','800px', '500px')">
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