<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>多组织架构数据权限管理</title>
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

			$(list+idx).find("select[type='select']").each(function(){//查找下拉选择框（后续如果一列数据有多个选择框，需要添加属性，目前只添加了type）
				if (typeof(row)=="undefined") {
				}

				var numbers = $(list+idx).find("select[type='select']").find("option"); //获取选择框的值

				numbers.each(function(){ //循环选择框
					if (typeof(row)!="undefined") {
					if($(this).val() == row.entityOrgId){//如果选择框的值与当前后台返回的一致，设置默认选中
						$(this).attr("selected", "selected");
					}
					}
				});


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
	<form:form id="inputForm" modelAttribute="sysTDataaccess" action="${ctx}/sys/sysTDataaccess/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>多组织架构数据权限组名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
		  		</tr>
		 	</tbody>
		</table>

		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">数据库实体表数据权限语句：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<a class="btn btn-white btn-sm" onclick="addRow('#sysTDataaccessentityList', sysTDataaccessentityRowIdx, sysTDataaccessentityTpl);sysTDataaccessentityRowIdx = sysTDataaccessentityRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th style="width:40%">数据库表</th>
						<th style="width:55%">条件</th>
						<th style="width:5%">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="sysTDataaccessentityList">
				</tbody>
			</table>
			<script type="text/template" id="sysTDataaccessentityTpl">
			//<!--
				<tr style="height:100px;" id="sysTDataaccessentityList{{idx}}">
					<td class="hide">
						<input id="sysTDataaccessentityList{{idx}}_id" name="sysTDataaccessentityList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="sysTDataaccessentityList{{idx}}_parentId" name="sysTDataaccessentityList[{{idx}}].parentId" type="hidden" value="{{row.sysTDataaccess.id}}"/>
						<input id="sysTDataaccessentityList{{idx}}_delFlag" name="sysTDataaccessentityList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
			<td>
                 <select id="sysTDataaccessentityList{{idx}}_entityOrgId" name="sysTDataaccessentityList[{{idx}}].entityOrgId" class="form-control" type="select" >
                        <c:forEach var="orgEntity" items="${orgEntityList}" varStatus="status">
                            <option value="${orgEntity.id}" title="${orgEntity.dataTableName}" }>${orgEntity.dataTableName}${orgEntity.dataTableNameCN}</option>
                        </c:forEach>
                </select>
			</td>
			<td>
				<textarea id="sysTDataaccessentityList{{idx}}_conditions" name="sysTDataaccessentityList[{{idx}}].conditions" rows="4"  class="form-control ">{{row.conditions}}</textarea>
			</td>
			<td class="text-center" width="10">
				{{#delBtn}}<span class="close" onclick="delRow(this, '#sysTDataaccessentityList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
			</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var sysTDataaccessentityRowIdx = 0, sysTDataaccessentityTpl = $("#sysTDataaccessentityTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(sysTDataaccess.sysTDataaccessentityList)};

					for (var i=0; i<data.length; i++){

						addRow('#sysTDataaccessentityList', sysTDataaccessentityRowIdx, sysTDataaccessentityTpl, data[i]);
						sysTDataaccessentityRowIdx = sysTDataaccessentityRowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
	</form:form>
</body>
</html>