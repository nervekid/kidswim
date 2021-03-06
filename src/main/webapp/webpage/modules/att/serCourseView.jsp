<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>課程查看</title>
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
			$(list+idx).find("select[type='select']").each(function(){//查找下拉選擇框（後續如果壹列數據有多個選擇框，需要添加屬性，目前只添加了type）
				var numbers = $(list+idx).find("select[type='select']").find("option"); //獲取選擇框的值
				numbers.each(function(){ //循環選擇框
					if (typeof(row)!="undefined") {
					var title = $(this).attr("title");
					if($(this).val() == row.rollCallStatusFlag){//如果選擇框的值與當前後臺返回的壹致，設置默認選中
						$(this).attr("selected", "selected");
					}
					}
				});
			});

			laydate.render({
                elem:"#serCourseDetailsList"+idx+"_learnDate"
            });
			laydate.render({
                elem:"#serCourseDetailsList"+idx+"_learnBeginDate"
            });
			laydate.render({
                elem:"#serCourseDetailsList"+idx+"_learnEndDate"
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
						<form:input readonly="true" path="code" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">課程等級：</label></td>
					<td class="width-35">
						<form:select disabled="true" placeholder="課程等級" path="courseLevel"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('course_level')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">課程開始日期：</label></td>
					<td class="width-35">
						<input type="text"  readonly="true" class="form-control" value="<fmt:formatDate value="${serCourse.courseBeginTime}" pattern="yyyy-MM-dd"/>" />
					</td>
					<td class="width-15 active"><label class="pull-right">課程結束日期:</label></td>
					<td class="width-35">
						<input type="text" readonly="true" class="form-control" value="<fmt:formatDate value="${serCourse.courseEndTimeTime}" pattern="yyyy-MM-dd"/>" />
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">上課開始時間：</label></td>
					<td class="width-35">
						<form:input readonly="true" path="showLearnBeginTime" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">上課結束時間：</label></td>
					<td class="width-35">
						<form:input readonly="true" path="showLearnEndTimeTime" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">堂數：</label></td>
					<td class="width-35">
						<form:input readonly="true" path="learnNum" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">課程地址：</label></td>
					<td class="width-35">
						<form:select disabled="true" placeholder="課程地址" path="courseAddress"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('course_addrese_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">星期幾：</label></td>
					<td class="width-35">
						<form:select disabled="true" placeholder="課程地址" path="strInWeek"  class="form-control m-b"  >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('week_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">評估日期：</label></td>
					<td class="width-35">
						<input type="text" readonly="true" class="form-control" value="<fmt:formatDate value="${serCourse.assessmentDate}" pattern="yyyy-MM-dd"/>" />
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">課程費用(港幣)：</label></td>
					<td class="width-35">
						<form:input readonly="true" path="courseFee" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">備註：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea readonly="true" path="remarks" htmlEscape="false" rows="3"   class="form-control "/>
					</td>
				</tr>
		   </tbody>
		</table>

		<!-- 明細課程分錄 -->
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">課程明細：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>上課日期</th>
						<th>上課開始時間</th>
						<th>上課結束時間</th>
						<th>是否已點名</th>
					</tr>
				</thead>
				<tbody id="serCourseDetailsList">
				</tbody>
			</table>
			<script type="text/template" id="serCourseDetailsListTpl">
			//<!--
				<tr id="serCourseDetailsList{{idx}}">
					<td class="hide">
						<input id="serCourseDetailsList{{idx}}_id" name="serCourseDetailsList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="serCourseDetailsList{{idx}}_delFlag" name="serCourseDetailsList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>


					<td>
						<input  readonly="true" id="serCourseDetailsList{{idx}}_learnDateStr" name="serCourseDetailsList[{{idx}}].learnDateStr" type="text"   style="font-size:14px ;width:99%"  maxlength="20" class="laydate-icon form-control "
						value="{{row.learnDateStr}}"/>
					</td>

					<td>
						<input  readonly="true" id="serCourseDetailsList{{idx}}_learnBeginDateStr" name="serCourseDetailsList[{{idx}}].learnBeginDateStr" type="text"   style="font-size:14px ;width:99%"  maxlength="20" class="laydate-icon form-control "
						value="{{row.learnBeginDateStr}}"/>
					</td>

					<td>
						<input  readonly="true" id="serCourseDetailsList{{idx}}_learnEndDateStr" name="serCourseDetailsList[{{idx}}].learnEndDateStr" type="text"   style="font-size:14px ;width:99%"  maxlength="20" class="laydate-icon form-control "
						value="{{row.learnEndDateStr}}"/>
					</td>

					<td>
						<select disabled="true" id="serCourseDetailsList{{idx}}_rollCallStatusFlag" name="serCourseDetailsList[{{idx}}].rollCallStatusFlag" class="form-control" type="select">
									<c:forEach var="dict" items="${yesNoList}" varStatus="status">
										<option value="${dict.value}" title="${dict.description}" }>${dict.label}</option>
									</c:forEach>
						</select>

					</td>

				</tr>//-->
			</script>
			<script type="text/javascript">
				var serCourseDetailsListRowIdx = 0, serCourseDetailsListTpl = $("#serCourseDetailsListTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					var data = ${fns:toJson(serCourse.serCourseDetailsList)};
					for (var i=0; i<data.length; i++){
						addRow('#serCourseDetailsList', serCourseDetailsListRowIdx,serCourseDetailsListTpl, data[i]);
						serCourseDetailsListRowIdx = serCourseDetailsListRowIdx + 1;
					}
			</script>
			</div>
		</div>
		</div>

	</form:form>
</body>
</html>