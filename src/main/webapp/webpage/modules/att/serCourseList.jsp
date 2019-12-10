<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>課程管理</title>
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
            laydate.render({
            	elem: '#beginLearnSelect'
          		,type: 'time'
            });
            laydate.render({
            	elem: '#endLearnSelect'
          		,type: 'time'
            });
            laydate.render({
            	elem: '#assessmentDateStrSelect'
            });
		});


		function createOffset(flag,typeFlag) {
            $("#accounttypeStr").val(flag);
            $("#typeFlag").val(typeFlag);
        }

		 function duihuan() {
            console.log("提交,正在進行泳課排班生成...");
            var courseLevelSelectVal = $("#courseLevelSelect").val();
            if(null==courseLevelSelectVal|| courseLevelSelectVal==''){
                alert("您還沒有选择课程级别,請選擇！");
                return;
            }
            var beginLearnSelectVal = $("#beginLearnSelect").val();
            if(null==beginLearnSelectVal|| beginLearnSelectVal==''){
                alert("您還沒有选择上课时间,請選擇！");
                return;
            }
            var endLearnSelectVal = $("#endLearnSelect").val();
            if(null==endLearnSelectVal|| endLearnSelectVal==''){
                alert("您還沒有选择下课时间,請選擇！");
                return;
            }
            var courseAddressSelectVal = $("#courseAddressSelect").val();
            if(null==courseAddressSelectVal|| courseAddressSelectVal==''){
                alert("您還沒有選擇泳池地址,請選擇！");
                return;
            }
            var weekNumSelectVal = $("#weekNumSelect").val();
            if(null==weekNumSelectVal|| weekNumSelectVal==''){
                alert("您還沒有選擇星期幾,請選擇！");
                return;
            }
            var beginTimeStrSelectVal = $("#beginTimeStrSelect").val();
            if(null==beginTimeStrSelectVal|| beginTimeStrSelectVal==''){
                alert("您還沒有選擇開始日期,請選擇！");
                return;
            }
            var endTimeStrSelectVal = $("#endTimeStrSelect").val();
            if(null==endTimeStrSelectVal|| endTimeStrSelectVal==''){
                alert("您還沒有選擇結束日期,請選擇！");
                return;
            }
            var assessmentDateStrSelectVal = $("#assessmentDateStrSelect").val();
            if(null==assessmentDateStrSelectVal|| assessmentDateStrSelectVal==''){
                alert("您還沒有选择评估日期,請選擇！");
                return;
            }

            if(confirm("確定要進行課程生成嗎？")==true){
            	loading('正在生成，請稍等...');
                $.ajax({
                    type:"post",
                    url:"${ctx}/att/serCourse/generateCourseScheduling",
                    data:{"courseLevel":courseLevelSelectVal,
                    	  "beginLearn":beginLearnSelectVal,
                    	  "endLearn":endLearnSelectVal,
                    	  "courseAddress":courseAddressSelectVal,
                    	  "weekNum":weekNumSelectVal,
                    	  "beginTimeStr":beginTimeStrSelectVal,
                    	  "endTimeStr":endTimeStrSelectVal,
                    	  "assessmentDateStr":assessmentDateStrSelectVal},
                    success:function (data) {
                    	alert("生成課程成功！");
                    	$('#createOffsetData').modal('hide')
                    	search();
                    },
                    fail:function (data) {
                        alert("生成課程失敗！");
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
							泳課課程排班生成
						</h4>
						說明：取壹段時間範圍，選擇禮拜幾，時間範圍不可以少於7天。
					</div>

					<div class="modal-body">

						<div class="dropdown-toggle" style="width: 50px;height: 20px" id="username">
							<span style="text-align: left" ></span>
						</div>
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  >
							<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right"><font color="red">*</font>课程级别：</label></td>
								<td class="width-35">
									<select id="courseLevelSelect" name="courseLevelSelect" class="form-control" type="select">
										<c:forEach var="dict" items="${courseLevelDictList}" varStatus="status">
											<option value="${dict.value}" title="${dict.description}" }>${dict.label} ${dict.value}</option>
										</c:forEach>
									</select>
								</td>
							</tr>

							<tr>
								<td class="width-15 active"><label class="pull-right"><font color="red">*</font>上课时间：</label></td>
								<td class="width-35">
								   <input id="beginLearnSelect" placeholder="上课时间" name="beginLearnSelect" type="text" length="20" class="form-control"
                                   value=""/>
								</td>
							</tr>

							<tr>
								<td class="width-15 active"><label class="pull-right"><font color="red">*</font>下课时间：</label></td>
								<td class="width-35">
								   <input id="endLearnSelect" placeholder="下课时间" name="endLearnSelect" type="text" length="20" class="form-control"
                                   value=""/>
								</td>
							</tr>

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
								<td class="width-15 active"><label class="pull-right"><font color="red">*</font>禮拜幾：</label></td>
								<td class="width-35">
									<select id="weekNumSelect" name="weekNumSelect" class="form-control" type="select">
										<c:forEach var="dict" items="${weekNumDictList}" varStatus="status">
											<option value="${dict.value}" title="${dict.description}" }>${dict.label}</option>
										</c:forEach>
									</select>
								</td>
							</tr>

							<tr>
								<td class="width-15 active"><label class="pull-right"><font color="red">*</font>课程開始日期：</label></td>
								<td class="width-35">
								   <input id="beginTimeStrSelect" placeholder="開始日期" name="beginTimeStrSelect" type="text" length="20" class="form-control"
                                   value=""/>
								</td>
							</tr>

							<tr>
								<td class="width-15 active"><label class="pull-right"><font color="red">*</font>课程結束日期：</label></td>
								<td class="width-35">
								   <input id="endTimeStrSelect" placeholder="結束日期" name="endTimeStrSelect" type="text" length="20" class="form-control"
                                   value=""/>
								</td>
							</tr>

							<tr>
								<td class="width-15 active"><label class="pull-right"><font color="red">*</font>评估日期：</label></td>
								<td class="width-35">
								   <input id="assessmentDateStrSelect" placeholder="結束日期" name="assessmentDateStrSelect" type="text" length="20" class="form-control"
                                   value=""/>
								</td>
							</tr>

							</tbody>
						</table>
						<sys:message content="${message}"/>
						<input type="hidden"  id="userId">
					</div>
					<div class="modal-footer">
						<button id="submitCreateOffsetData"  type="button" class="btn btn-success btn-sm">
							確認生成
						</button>
					</div>
				</div>
			</div>
		</div>


	<!--查詢條件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="serCourse" action="${ctx}/att/serCourse/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<form:input placeholder="課程編號" path="code" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="64"  class=" form-control input-sm"/>
			<input placeholder="課程時間範圍" id="dateRange" name="dateRange" class="laydate-icon form-control layer-date" type="text"  value="${dateRange}" />
			<form:select placeholder="课程级别" path="courseLevel"  class="form-control m-b required" onchange="search()" >
				<form:option value="" label="請選擇"/>
				<form:options items="${fns:getDictList('course_level')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
			</form:select>
			<form:select placeholder="星期幾" path="strInWeek"  class="form-control m-b required" onchange="search()" >
				<form:option value="" label="請選擇"/>
				<form:options items="${fns:getDictList('week_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
			</form:select>
			<form:select placeholder="課程地址" path="courseAddress"  class="form-control m-b required" onchange="search()" >
				<form:option value="" label="請選擇"/>
				<form:options items="${fns:getDictList('course_addrese_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
			</form:select>
		 </div>
	</form:form>
	<br/>
	</div>
	</div>

	<!-- 工具欄 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="att:serCourse:add">
				<table:addRow url="${ctx}/att/serCourse/form?menuId=${menu.id}" title="課程"></table:addRow><!-- 增加按鈕 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serCourse:edit">
			    <table:editRow url="${ctx}/att/serCourse/form" title="課程" id="contentTable"></table:editRow><!-- 編輯按鈕 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serCourse:del">
				<table:delRow url="${ctx}/att/serCourse/deleteAll" id="contentTable"></table:delRow><!-- 刪除按鈕 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serCourse:import">
				<table:importExcel url="${ctx}/att/serCourse/import"  menuId="${menu.id}" ></table:importExcel><!-- 導入按鈕 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="att:serCourse:export">
	       		<table:exportExcel url="${ctx}/att/serCourse/export?menuId=${menu.id}"></table:exportExcel><!-- 導出按鈕 -->
	       	</shiro:hasPermission>

			</div>
		<div class="pull-right">
			<shiro:hasPermission name="att:serCourse:generativeCourse">
				<button  data-placement="left" class="btn btn-success btn-sm" data-toggle="modal" onclick="createOffset('1','0')" data-target="#createOffsetData" >生成課程</button>
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
				<th  class="sort-column coathId">课程等级</th>
				<th  class="sort-column beginYearMonth">课程时间范围</th>
				<th  class="sort-column courseDate">上课开始时间</th>
				<th  class="sort-column courseNum">上课结束时间</th>
				<th  class="sort-column courseNum">堂数</th>
				<th  class="sort-column courseAddress">課程地址</th>
				<th  class="sort-column strInWeek">星期幾</th>
				<th  class="sort-column strInWeek">评估日期</th>
				<th  class="sort-column strInWeek">费用(港币)</th>
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
					${fns:getDictLabel(serCourse.courseLevel, 'course_level', '')}
				</td>
				<td>
					${serCourse.beginTimeAndEndTimeStr}
				</td>
				<td>
					${serCourse.showLearnBeginTime}
				</td>
				<td>
					${serCourse.showLearnEndTimeTime}
				</td>
				<td>
					${serCourse.learnNum}
				</td>
				<td>
					${fns:getDictLabel(serCourse.courseAddress, 'course_addrese_flag', '')}
				</td>
				<td>
					${fns:getDictLabel(serCourse.strInWeek, 'week_flag', '')}
				</td>
				<td>
					<fmt:formatDate value="${serCourse.assessmentDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${serCourse.courseFee}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

		<!-- 分頁代碼 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>