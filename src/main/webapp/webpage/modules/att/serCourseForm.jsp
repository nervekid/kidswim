<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>课程編輯</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回調函數，在編輯和保存動作時，供openDialog調用提交表單。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，請稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("輸入有誤，請先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			laydate.render({
				elem: '#courseBeginTime',
				trigger: 'click'
			});
			laydate.render({
				elem: '#courseEndTimeTime',
				trigger: 'click'
			});
			laydate.render({
				elem: '#assessmentDate',
				trigger: 'click'
			});
			laydate.render({
            	elem: '#beginLearnSelect'
          		,type: 'time'
       			,trigger: 'click'
            });
            laydate.render({
            	elem: '#endLearnSelect'
          		,type: 'time'
       			,trigger: 'click'
            });
		});

		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
			laydate.render({
                elem:"#serCourseDetailsList"+idx+"_learnDate",
				trigger: 'click'
            });
			laydate.render({
                elem:"#serCourseDetailsList"+idx+"_learnBeginDate",
				trigger: 'click'
            });
			laydate.render({
                elem:"#serCourseDetailsList"+idx+"_learnEndDate",
				trigger: 'click'
            });
		}

		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			delFlag.val("1");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}
			$(obj).parent().parent().hide();

		}

	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="serCourse" action="${ctx}/att/serCourse/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">課程編號：</label></td>
					<td class="width-35">
						<form:input path="code" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">課程等級：</label></td>
					<td class="width-35">
						<form:select placeholder="課程等級" path="courseLevel"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('course_level')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">課程開始時間：</label></td>
					<td class="width-35">
						<form:input path="courseBeginTime" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">課程結束時間:</label></td>
					<td class="width-35">
						<form:input path="courseEndTimeTime" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">上課開始時間：</label></td>
					<td class="width-35">
						<form:input path="learnBeginTime" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">上課結束時間：</label></td>
					<td class="width-35">
						<form:input path="phlearnEndTimeTimeone" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">堂數：</label></td>
					<td class="width-35">
						<form:input path="learnNum" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">地址：</label></td>
					<td class="width-35">
						<form:select placeholder="地址" path="courseAddress"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('course_addrese_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">星期幾：</label></td>
					<td class="width-35">
						<form:select placeholder="星期几" path="strInWeek"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('week_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">評估日期：</label></td>
					<td class="width-35">
						<form:input path="assessmentDate" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">課程費用 單位(港幣)：</label></td>
					<td class="width-35">
						<form:input path="courseFee" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">備註：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="remarks" htmlEscape="false" rows="3"   class="form-control "/>
					</td>
				</tr>
		   </tbody>
		</table>

		<!-- 資格分錄 -->
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">课程明细：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<a class="btn btn-white btn-sm" onclick="addRow('#serCourseDetailsList', serCourseDetailsRowIdx, serCourseDetailsTpl);serCourseDetailsRowIdx = serCourseDetailsRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>上課日期</th>
						<th>上課開始時間</th>
						<th>上課结束時間</th>
						<th>是否已點名</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="sysCertificatesCoachList">
				</tbody>
			</table>
			<script type="text/template" id="serCourseDetailsTpl">
			//<!--
				<tr id="serCourseDetailsList{{idx}}">
					<td class="hide">
						<input id="serCourseDetailsList{{idx}}_id" name="serCourseDetailsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="serCourseDetailsList{{idx}}_delFlag" name="serCourseDetailsList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>

					<td>
						<input id="sysCertificatesCoachList{{idx}}_learnDate" name="sysCertificatesCoachList[{{idx}}].learnDate" type="text"   style="font-size:14px ;width:99%"  maxlength="20" class="laydate-icon form-control "
						value="{{row.learnDate}}"/>
					</td>

					<td>
						<input id="sysCertificatesCoachList{{idx}}_learnBeginDate" name="sysCertificatesCoachList[{{idx}}].learnBeginDate" type="text"   style="font-size:14px ;width:99%"  maxlength="20" class="laydate-icon form-control "
						value="{{row.learnBeginDate}}"/>
					</td>

					<td>
						<input id="sysCertificatesCoachList{{idx}}_learnEndDate" name="sysCertificatesCoachList[{{idx}}].learnEndDate" type="text"   style="font-size:14px ;width:99%"  maxlength="20" class="laydate-icon form-control "
						value="{{row.learnEndDate}}"/>
					</td>

					<td>
					</td>

					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#serCourseDetailsList{{idx}}')" title="刪除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var serCourseDetailsRowIdx = 0, serCourseDetailsTpl = $("#serCourseDetailsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					var data = ${fns:toJson(serCourse.serCourseDetailsList)};
					for (var i=0; i<data.length; i++){
						addRow('#serCourseDetailsList', serCourseDetailsRowIdx, serCourseDetailsTpl, data[i]);
						serCourseDetailsRowIdx = serCourseDetailsRowIdx + 1;
					}
			</script>
			</div>
		</div>
		</div>

	</form:form>
</body>
</html>