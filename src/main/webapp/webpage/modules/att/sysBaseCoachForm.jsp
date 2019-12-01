<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>教练员编辑</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
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
						<form:input path="nameCn" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">英文名：</label></td>
					<td class="width-35">
						<form:input path="nameEn" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">身份证：</label></td>
					<td class="width-35">
						<form:input path="idNo" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">性别:</label></td>
					<td class="width-35">
						<form:select placeholder="性别" path="sex"  class="form-control m-b"  >
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('sex_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">电邮：</label></td>
					<td class="width-35">
						<form:input path="email" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">电话号码：</label></td>
					<td class="width-35">
						<form:input path="phone" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">地址：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">教育程度：</label></td>
					<td class="width-35">
						<form:input path="educationLevel" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">入职年月：</label></td>
					<td class="width-35">
						<form:input path="entryYear" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">入职职位：</label></td>
					<td class="width-35">
						<form:input path="entryPosition" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">入职时薪 单位(港币)：</label></td>
					<td class="width-35">
						<form:input path="entryHourWage" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">现时职位：</label></td>
					<td class="width-35">
						<form:input path="presentPosition" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">现时时薪 单位(港币)：</label></td>
					<td class="width-35">
						<form:input path="presentHourWage" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">行业经验 单位(年)：</label></td>
					<td class="width-35">
						<form:input path="industryExperience" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">有否合约:</label></td>
					<td class="width-35">
						<form:select placeholder="有否合约" path="contractFlag"  class="form-control m-b"  >
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="remarks" htmlEscape="false" rows="3"   class="form-control "/>
					</td>
				</tr>
		   </tbody>
		</table>

		<!-- 资格分录 -->
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">资格：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<a class="btn btn-white btn-sm" onclick="addRow('#sysCertificatesCoachList', sysCertificatesCoachRowIdx, sysCertificatesCoachTpl);sysCertificatesCoachRowIdx = sysCertificatesCoachRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>资格名称</th>
						<th>考获年月</th>
						<th width="10">&nbsp;</th>
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
						<input id="sysCertificatesCoachList{{idx}}_qualification" name="sysCertificatesCoachList[{{idx}}].qualification" type="text"   style="font-size:14px ;width:99%"  maxlength="20" class="laydate-icon form-control "
						value="{{row.qualification}}"/>
					</td>

					<td>
						<input id="sysCertificatesCoachList{{idx}}_obtainYearMonth" name="sysCertificatesCoachList[{{idx}}].obtainYearMonth" type="text"   style="font-size:14px ;width:99%"  maxlength="20" class="laydate-icon form-control layer-date "
						value="{{row.obtainYearMonth}}"/>
					</td>

					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#sysCertificatesCoachList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
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