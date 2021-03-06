<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>教練員編輯</title>
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
				elem: '#entryYear',
				type: 'month'
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
                elem:"#sysCertificatesCoachList"+idx+"_obtainYearMonth",
				type: 'month'
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
		<form:form id="inputForm" modelAttribute="sysBaseCoach" action="${ctx}/att/sysBaseCoach/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">中文名：</label></td>
					<td class="width-35">
						<form:input path="nameCn" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">英文名：</label></td>
					<td class="width-35">
						<form:input path="nameEn" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">身份證：</label></td>
					<td class="width-35">
						<form:input path="idNo" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">性別:</label></td>
					<td class="width-35">
						<form:select placeholder="性別" path="sex"  class="form-control m-b"  disabled="true" >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('sex_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">電郵：</label></td>
					<td class="width-35">
						<form:input path="email" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">電話號碼：</label></td>
					<td class="width-35">
						<form:input path="phone" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">地址：</label></td>
					<td class="width-35">
						<form:input path="address" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">教育程度：</label></td>
					<td class="width-35">
						<form:input path="educationLevel" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">入職年月：</label></td>
					<td class="width-35">
						<form:input path="entryYear" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">入職職位：</label></td>
					<td class="width-35">
						<form:input path="entryPosition" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">入職時薪 單位(港幣)：</label></td>
					<td class="width-35">
						<form:input path="entryHourWage" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">現時職位：</label></td>
					<td class="width-35">
						<form:input path="presentPosition" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">現時時薪 單位(港幣)：</label></td>
					<td class="width-35">
						<form:input path="presentHourWage" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">行業經驗 單位(年)：</label></td>
					<td class="width-35">
						<form:input path="industryExperience" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">有否合約:</label></td>
					<td class="width-35">
						<form:select placeholder="有否合約" path="contractFlag"  class="form-control m-b" disabled="true" >
							<form:option value="" label="請選擇"/>
							<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">備註：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="remarks" readonly="true" htmlEscape="false" rows="3"   class="form-control "/>
					</td>
				</tr>
		   </tbody>
		</table>

		<!-- 資格分錄 -->
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">資格：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>資格名稱</th>
						<th>考獲年月</th>
					</tr>
				</thead>
				<tbody id="sysCertificatesCoachList">
				</tbody>
			</table>
			<script type="text/template" id="sysCertificatesCoachTpl">
			//<!--
				<tr id="sysCertificatesCoachList{{idx}}">
					<td class="hide">
						<input id="sysCertificatesCoachList{{idx}}_id" name="sysCertificatesCoachList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="sysCertificatesCoachList{{idx}}_delFlag" name="sysCertificatesCoachList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>

					<td>
						<input id="sysCertificatesCoachList{{idx}}_qualification" name="sysCertificatesCoachList[{{idx}}].qualification" readonly="true" type="text"   style="font-size:14px ;width:99%"  maxlength="20" class="laydate-icon form-control "
						value="{{row.qualification}}"/>
					</td>

					<td>
						<input id="sysCertificatesCoachList{{idx}}_obtainYearMonth" name="sysCertificatesCoachList[{{idx}}].obtainYearMonth" readonly="true" type="text"   style="font-size:14px ;width:99%"  maxlength="20" class="laydate-icon form-control layer-date "
						value="{{row.obtainYearMonth}}"/>
					</td>

				</tr>//-->
			</script>
			<script type="text/javascript">
				var sysCertificatesCoachRowIdx = 0, sysCertificatesCoachTpl = $("#sysCertificatesCoachTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					var data = ${fns:toJson(sysBaseCoach.sysCertificatesCoachList)};
					for (var i=0; i<data.length; i++){
						addRow('#sysCertificatesCoachList', sysCertificatesCoachRowIdx, sysCertificatesCoachTpl, data[i]);
						sysCertificatesCoachRowIdx =sysCertificatesCoachRowIdx + 1;
					}
			</script>
			</div>
		</div>
		</div>

	</form:form>
</body>
</html>