<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<fmt:setBundle basename="kite" var="kite" />
<fmt:message key="fastdfs.storage.url" var="fastdfsStorageUrl" bundle="${kite}" />

<html>
<head>
	<title>HR系统个人信息修改</title>
	<meta name="decorator" content="default"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="${ctxStatic}/fileUpload/css/webuploader.css" rel="stylesheet" />
	<link href="${ctxStatic}/fileUpload/css/cxuploader.css" rel="stylesheet" />
	<link href="${ctxStatic}/fileUpload/css/common.css" rel="stylesheet" />
	<script src="${ctxStatic}/fileUpload/userUploader.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/common/contabs.js"></script>
	<script src="${ctxStatic}/fileUpload/webuploader.js" type="text/javascript"></script>

    <style type="text/css"> </style>

	<script type="text/javascript">
		var validateForm;
		var width='700px';
		var height='500px';
		if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
			width='auto';
			height='auto';
		}

		function buttonSubmit() {
			if(validateForm.form()){
				 showTip('保存成功', 'info', 3000);
				 $("#inputForm").submit();
				 showTip('保存成功', 'info', 3000);
				 return true;
			  }
			return false;
        }
		$(document).ready(function() {
			jQuery.validator.addMethod("checkPhone", function(value, element) {
				var length = value.length;
				var mobile = /^1[3456789]\d{9}$/
				return this.optional(element) || (length == 11 && mobile.test(value));
			}, "手机号码格式错误");
			jQuery.validator.addMethod("checkEmail", function(value, element, params) {
				var checkEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
				return this.optional(element) || (checkEmail.test(value));
			}, "邮箱格式错误");
			jQuery.validator.addMethod("checkIdCardVal", function(value, element, params) {
				var flag = checkIDCard(value);
				var length = value.length;
				return this.optional(element) || (length == 18 && flag);
			}, "身份证号码错误");
			validateForm = $("#inputForm").validate({
				rules: {
					phone: {
						required:true,
						checkPhone: true
					},
					email: {
						required:true,
						checkEmail: true
					},
					idCard: {
						required:true,
						checkIdCardVal: true
					}
				},
				submitHandler: function(form){
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
                elem: '#incorporationDate',
                event: 'click',//响应事件。如果没有传入event，则按照默认的click
				trigger:'click',
                done: function(value, date, endDate){
                	var incorporationDateStr = $('#incorporationDate').val();
                	$.ajax({
    	    			url: "${ctx}/sys/sysOrganizational/getCompanyAge?incorporationDateStr="+incorporationDateStr,
    					dataType: 'json',
    					method: 'POST',
    					success: function(data) {
                			$("#companyAge").val(data.companyAge);
                			$("#correctionDate").val(data.correctionDateStr);
    					},
    					error: function(data) {
    					}
    				});
                }
            });
			laydate.render({
                elem: '#correctionDate',
				trigger:'click'
            });
			laydate.render({
                elem: '#contractBeginDate',
				trigger:'click',
				event: 'click',//响应事件。如果没有传入event，则按照默认的click
				done: function(value, date, endDate){
					var contractBeginDate = $('#contractBeginDate').val();
					$.ajax({
						url: "${ctx}/sys/sysOrganizational/getContractEndDate?contractBeginDate="+contractBeginDate,
						dataType: 'json',
						method: 'POST',
						success: function(data) {
							$("#contractEndDate").val(data.contractEndDate);
						},
						error: function(data) {
						}
					});
				}
            });
			laydate.render({
                elem: '#contractEndDate',
				trigger:'click'
            });
			laydate.render({
                elem: '#workStartDate',
				trigger:'click',
                event: 'click',//响应事件。如果没有传入event，则按照默认的click
                done: function(value, date, endDate){
                	var workStartDateStr = $('#workStartDate').val();
                	$.ajax({
    	    			url: "${ctx}/sys/sysOrganizational/getLengthServiceAge?workStartDateStr="+workStartDateStr,
    					dataType: 'json',
    					method: 'POST',
    					success: function(data) {
                			$("#lengthServiceAge").val(data.lengthServiceAge);
    					},
    					error: function(data) {
    					}
    				});
                }
            });
			laydate.render({
                elem: '#highestEducationGraduationDate',
				trigger:'click'
            });
			laydate.render({
                elem: '#firstdegreeEducationGraduationDate',
				trigger:'click'
            });
/*

			var   office_lo = $("#officeLocation").val();
			console.log(office_lo);
			if (office_lo == null || office_lo==''){
				console.log("----");
				//var options = $("#officeLocation").find("option");
				var options =  document.getElementById("officeLocation").options;
				console.log(options);
				options[1].selected = true;
				for (var i=0;i<options.length ;i++){
					if (options[i].value ==1 || options[i].text=='广州') {
						console.log(options[i].text);
						//options[i].addattr("selected","selected")
						options[i].selected = true ;
					}
				}
			}
*/

			//console.log("页面加载结束");

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
				var numbers = $(list+idx).find("select[type='select']").find("option"); //获取选择框的值
				numbers.each(function(){ //循环选择框
					if (typeof(row)!="undefined") {
					var title = $(this).attr("title");
					if($(this).val() == row.certificateCategor && title == "证书类别"){//如果选择框的值与当前后台返回的一致，设置默认选中
						$(this).attr("selected", "selected");
					}
					if($(this).val() == row.certificateRank  && title == "证书等级"){//如果选择框的值与当前后台返回的一致，设置默认选中
						$(this).attr("selected", "selected");
					}
					}
				});
			});
			laydate.render({
                elem:"#certificateList"+idx+"_certificateDate",
                event: 'focus',
				trigger:'click'
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

		function addWorkRow(list, idx, tpl, row){
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
                elem:"#userWorkexperienceList"+idx+"_beginDate",
                event: 'focus',
				trigger:'click'
            });
			laydate.render({
                elem:"#userWorkexperienceList"+idx+"_endDate",
                event: 'focus',
				trigger:'click'
            });
		}

		function delWorkRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			delFlag.val("1");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}
			$(obj).parent().parent().hide();

		}

		function userPositionChange(){
			var numbers = $('#userPosition\\.id').find("option");
			numbers.each(function(){
				if ($(this)[0].selected == true) {
					var idss = $(this).val();
					$.ajax({
    	    			url: "${ctx}/sys/sysOrganizational/getUserPosition?positionId="+idss,
    					dataType: 'json',
    					method: 'POST',
    					success: function(data) {
							$('#userPosition\\.rankName').val(data.userPosition.rankName);
							$('#userPosition\\.functionalCategoriesName').val(data.userPosition.functionalCategoriesName);
							$('#userPosition\\.keyPositionsName').val(data.userPosition.keyPositionsName);
							$('#userPosition\\.managementLayersName').val(data.userPosition.managementLayersName);
    					},
    					error: function(data) {
    					}
    				});
				}

			});
		}

		function checkIDCard(idcode){
			// 加权因子
			var weight_factor = [7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2];
			// 校验码
			var check_code = ['1', '0', 'X' , '9', '8', '7', '6', '5', '4', '3', '2'];

			var code = idcode + "";
			var last = idcode[17];//最后一位

			var seventeen = code.substring(0,17);

			// ISO 7064:1983.MOD 11-2
			// 判断最后一位校验码是否正确
			var arr = seventeen.split("");
			var len = arr.length;
			var num = 0;
			for(var i = 0; i < len; i++){
				num = num + arr[i] * weight_factor[i];
			}

			// 获取余数
			var resisue = num%11;
			var last_no = check_code[resisue];

			// 格式的正则
			// 正则思路
			/*
            第一位不可能是0
            第二位到第六位可以是0-9
            第七位到第十位是年份，所以七八位为19或者20
            十一位和十二位是月份，这两位是01-12之间的数值
            十三位和十四位是日期，是从01-31之间的数值
            十五，十六，十七都是数字0-9
            十八位可能是数字0-9，也可能是X
            */
			var idcard_patter = /^[1-9][0-9]{5}([1][9][0-9]{2}|[2][0][0|1][0-9])([0][1-9]|[1][0|1|2])([0][1-9]|[1|2][0-9]|[3][0|1])[0-9]{3}([0-9]|[X])$/;

			// 判断格式是否正确
			var format = idcard_patter.test(idcode);

			// 返回验证结果，校验码和格式同时正确才算是合法的身份证号码
			return last === last_no && format ? true : false;
		}

		function checkIDCardInput() {
			if ("${!isHrManager}"){
				return ;
			}
			$("#birthdayStr").val("");
			$("#age").val(0);
			var idCard = $("#idCard").val();
			if (checkIDCard(idCard)){
				$.ajax({
					url: "${ctx}/sys/sysOrganizational/getBirthDayAndAge?idCard="+idCard,
					dataType: 'json',
					method: 'POST',
					success: function(data) {
						$("#birthdayStr").val(data.birthdayStr);
						$("#age").val(data.age);
					},
					error: function(data) {}
				});
			}
		}

		function getPositinInfo(id) {
			if ("${!isHrManager}"){
				return ;
			}
			openDialogForSelectUserPosition("选择岗位","${ctx}/sys/sysOrganizational/positionSelectList?menuId="+id+"&keyword=","800px", "500px","");
		}



		//打开对话框(添加修改)
		function openDialogForSelectUserPosition(title,url,width,height,target){
			//获取当前分录的编码集合
			var materialScrapEntryids="";//当前分录ids
			$("#contentTable tbody").find("tr").each(function(){
				var tdArr = $(this).children();
				var number = tdArr.eq(1).find("input").val();//收入类别
				materialScrapEntryids+=number+",";
			});


			top.layer.open({
				type: 2,
				area: [width, height],
				title: title,
				maxmin: true, //开启最大化最小化按钮
				content: url ,
				btn: ['确定', '关闭'],
				yes: function(index, layero){
					var body = top.layer.getChildFrame('body', index);
					var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
					var inputForm = body.find('#contentTable_position tbody tr td input.i-checks:checkbox');
					var str="";
					var ids="";
					var dataArray=[];

					var idsCount = 0;
					inputForm.each(function(){
						if(true == $(this).is(':checked')){
							str+=$(this).attr("id")+",";
							idsCount++;
						}
						if(str.substr(str.length-1)== ','){
							ids = str.substr(0,str.length-1);
						}
					});

					if(ids == ""){
						top.layer.alert('请至少选择一个职位!', {icon: 0, title:'警告'});
						return;
					}
					if(idsCount >1){
						console.log(idsCount)
						top.layer.alert('最多选择一个职位!当前选择职位数为：'+idsCount ,{icon: 0, title:'警告'});
						return;
					}
					//发送请求，查找出勾选的设备
					$.ajax({
						url : "${ctx}/sys/sysOrganizational/getUserPosition?positionId="+ids,
						type : "POST",
						contentType: "application/json;charset=utf-8",
						dataType : "json",
						success : function(dataReturn) {
							console.log(dataReturn);

							$("#userPosition\\.id").val(dataReturn.userPosition.id);
							$("#userPosition\\.name").val(dataReturn.userPosition.name);

							$("#userPosition\\.functionalCategories").val(dataReturn.userPosition.functionalCategories);
							$("#userPosition\\.functionalCategoriesName").val(dataReturn.userPosition.functionalCategoriesName);

							$("#userPosition\\.rank").val(dataReturn.userPosition.rank);
							$("#userPosition\\.rankName").val(dataReturn.userPosition.rankName);
							top.layer.close(index);//关闭对话框。
						},
						error:function(msg){
							alert("职位选择异常！");
						}
					})
				},
				cancel: function(index){
				}
			});
		}

		// 打开证件照上传框
		function uploadDocumentImg() {
			if ("${!isHrManager}"){
				return ;
			}
			layer.open({
				id: "upload_document_img",
				type: 2,
				area: [width, height],
				title: "相片上传",
				content: "${ctx}/sys/sysOrganizational/imageEdit?userId=${sysEhrPersonalInfoCommand.userId}"
			});
		}

		function resetDocumengImg(url) {
			$("#avatarImg").attr("src", "${fastdfsStorageUrl}" + url);
			$("#avatarId").val(url);
		}
	</script>
</head>
<body class="hideScrollsmall" >
		<form:form id="inputForm" modelAttribute="sysEhrPersonalInfoCommand" action="${ctx}/sys/sysOrganizational/saveEhr" method="post" class="form-horizontal">
		<form:hidden path="userId"/>
		<sys:message content="${message}"/>
		<table id="postionTD" class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>工号：</label></td>
					<td class="width-35">
						<form:input path="no" htmlEscape="false" class="form-control required" disabled="${!isHrManager}"/>
					</td>
					<td rowspan="7" class="width-15 active"><label class="pull-right">证件相：</label></td>
					<td rowspan="7" class="width-35" style="text-align:center;">
						<img id="avatarImg" alt="image" style="height:350px;width:350px; margin:0 auto;" onerror=" this.src ='${ctxStatic}/images/userinfobig.jpg'" src="${fastdfsStorageUrl}${sysEhrPersonalInfoCommand.avatarId}" onclick="uploadDocumentImg()" />
						<form:hidden path="avatarId" id="avatarId" />
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>姓名：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" class="form-control required" disabled="${!isHrManager}"/>
					</td>

				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>移动电话：</label></td>
					<td class="width-35">
						<form:input path="phone" htmlEscape="false" class="form-control required" disabled="${!isHrManager}"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>电子邮箱：</label></td>
					<td class="width-35">
						<form:input path="email" htmlEscape="false" class="form-control required" disabled="${!isHrManager}"/>
					</td>

				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>性别：</label></td>
					<td class="width-35">
						<form:select path="sex" class="form-control required" disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">员工状态：</label></td>
					<td class="width-35">
                        <form:hidden path="becomeWorker"/>
						<form:select path="becomeWorker" class="form-control required" disabled="true">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('staff_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>是否实习生：</label></td>
					<td class="width-35">
					<form:select path="internFlag" class="form-control required" disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">基本信息</label></td>
					<th colspan="3">
						<hr style="width:100%;height:5px;border:none;border-top:5px ridge #337ab7;" />
					</th>
				</tr>


				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="officeId" name="officeId" value="${sysEhrPersonalInfoCommand.officeId}" labelName="office.name" labelValue="${sysEhrPersonalInfoCommand.officeName}"
										title="部门" url="/sys/office/treeData?type=2&organTagA=1" cssClass="form-control required" allowClear="true" notAllowSelectParent="false" disabled="${isHrManager eq 'true'?'null': 'disabled'}" />
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>岗位：</label></td>
					<td class="width-35">
						<%--<form:select onBlur="userPositionChange()" onChange="userPositionChange()" disabled="true" onKeydown="userPositionChange()"
									 path="userPosition.id"  onclick="getPositinInfo('${sysEhrPersonalInfoCommand.userPosition.id}')" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getUserPositionList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select>--%>
							<div class="input-group">
								<form:input path="userPosition.id"  readonly="true"  cssClass="form-control required  hidden"  htmlEscape="false"/>
								<form:input path="userPosition.name" placeholder="请点击选择岗位"  readonly="true"   class="btn btn-success btn-primary"
											 hidden="false"  cssClass="form-control required" htmlEscape="false" onclick="getPositinInfo('${sysEhrPersonalInfoCommand.userPosition.id}')"/>
								<span class="input-group-btn">
	       		 						<button type="button"   class="btn btn-success <c:if test="${fn:contains(cssClass, 'input-sm')}"> btn-sm </c:if><c:if test="${fn:contains(cssClass, 'input-lg')}"> btn-lg </c:if>  btn-primary ${disabled} ${hideBtn ? 'hide' : ''}"  onclick="getPositinInfo('${sysEhrPersonalInfoCommand.userPosition.id}')"><i class="fa fa-search"></i>
	        	   					 	 </button>
       							 </span>
							</div>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">职能类别：</label></td>
					<td class="width-35">
						<form:input path="userPosition.functionalCategories" readonly="true" htmlEscape="false"  cssClass="hidden"  class="form-control"/>
						<form:input path="userPosition.functionalCategoriesName" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">职级：</label></td>
					<td class="width-35">
						<form:input path="userPosition.rank" readonly="true"   cssClass="hidden" htmlEscape="false"    class="form-control"/>
						<form:input path="userPosition.rankName" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>组织分类：</label></td>
					<td class="width-35">
						<form:select path="organizationalClassification" class="form-control required" disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('organizational_classification')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>业务属性：</label></td>
					<td class="width-35">
						<form:select  path="businessAttributes" class="form-control required" disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('business_attributes')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>


				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>合同主体：</label></td>
					<td class="width-35">
						<form:select path="contractSubject" class="form-control required" disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('social_security_subject')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>社保主体：</label></td>
					<td class="width-35">
						<form:select path="socialSecuritySubject" class="form-control required" disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('social_security_subject')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">关键岗位：</label></td>
					<td class="width-35">
						<form:input path="userPosition.keyPositionsName" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">管理层级：</label></td>
					<td class="width-35">
						<form:input path="userPosition.managementLayersName" readonly="true" htmlEscape="false"    class="form-control"/>
					</td>

				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>合同开始时间：</label></td>
					<td class="width-35">
						<input id="contractBeginDate" name="contractBeginDate"  type="text"  class="form-control required" ${isHrManager eq 'true'? null: 'disabled'}
						value="<fmt:formatDate value="${sysEhrPersonalInfoCommand.contractBeginDate}" pattern="yyyy-MM-dd"/>" />
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>合同到期时间：</label></td>
					<td class="width-35">
						<input id="contractEndDate" name="contractEndDate"  type="text"  class="form-control required" ${isHrManager eq 'true'? null: 'disabled'}
						value="<fmt:formatDate value="${sysEhrPersonalInfoCommand.contractEndDate}" pattern="yyyy-MM-dd"/>" />
					</td>
				</tr>

				<tr>


					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>入职日期：</label></td>
					<td class="width-35">
						<input id="incorporationDate" name="incorporationDate" type="text"  class="form-control required" ${isHrManager eq 'true'? null: 'disabled'}
							   value="<fmt:formatDate value="${sysEhrPersonalInfoCommand.incorporationDate}" pattern="yyyy-MM-dd"/>" />
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>转正日期：</label></td>
					<td class="width-35">
						<input id="correctionDate" name="correctionDate"  type="text"  class="form-control required" ${isHrManager eq 'true'? null: 'disabled'}
						value="<fmt:formatDate value="${sysEhrPersonalInfoCommand.correctionDate}" pattern="yyyy-MM-dd"/>" />
					</td>
				</tr>

				<tr>

					<td class="width-15 active"><label class="pull-right">司龄：</label></td>
					<td class="width-35">
						<form:input readonly="true" path="companyAge" htmlEscape="false"   class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>人员类别：</label></td>
					<td class="width-35">
						<form:select path="personCategory" class="form-control required">
							<%--<form:option value="" label=""/>--%>
							<form:options items="${fns:getDictList('person_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>开始工作的时间：</label></td>
					<td class="width-35">
						<input id="workStartDate"  name="workStartDate" type="text" class="form-control required" ${isHrManager eq 'true'? null: 'disabled'}
						value="<fmt:formatDate value="${sysEhrPersonalInfoCommand.workStartDate}" pattern="yyyy-MM-dd"/>" />
					</td>
					<td class="width-15 active"><label class="pull-right">工龄：</label></td>
					<td class="width-35">
						<form:input path="lengthServiceAge" htmlEscape="false"  readonly="true"  class="form-control "/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">08年及以后合同签订次数：</label></td>
					<td class="width-35">
						<form:input path="contractSignNum" htmlEscape="false" class="form-control" disabled="${!isHrManager}"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">个人信息</label></td>
					<th colspan="3">
						<hr style="width:100%;height:5px;border:none;border-top:5px ridge #337ab7;" />
					</th>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>身份证：</label></td>
					<td class="width-35">
						<form:input path="idCard" htmlEscape="false"  onkeyup="checkIDCardInput()"  onmousedown="checkIDCardInput()"
									onmouseup="checkIDCardInput()"  onmouseout="checkIDCardInput()"  class="form-control required" disabled="${!isHrManager}"/>
					</td>
					<td class="width-15 active"><label class="pull-right">出生日期：</label></td>
					<td class="width-35">
						<form:input path="birthdayStr" htmlEscape="false"   readonly="true" class="form-control "/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">年龄：</label></td>
					<td class="width-35">
						<form:input path="age" htmlEscape="false"  readonly="true"  class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">民族：</label></td>
					<td class="width-35">
						<form:select path="nation" class="form-control " disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('nation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>政治面貌：</label></td>
					<td class="width-35">
						<form:select path="politicalPpearance" class="form-control required" disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('political_ppearance')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>婚姻状况：</label></td>
					<td class="width-35">
						<form:select path="maritalStatus" class="form-control required" disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('marital_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">籍贯：</label></td>
					<td class="width-35">
						<form:input path="nativePlace" htmlEscape="false" class="form-control" disabled="${!isHrManager}"/>
					</td>
					<td class="width-15 active"><label class="pull-right">户口所在地：</label></td>
					<td class="width-35">
						<form:input path="registeredResidence" htmlEscape="false" class="form-control" disabled="${!isHrManager}"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">户口性质：</label></td>
					<td class="width-35">
						<form:select path="registrationNature" class="form-control " disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('registration_nature')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>办公所在地：</label></td>
					<td class="width-35">
						<form:select path="officeLocation" class="form-control required" disabled="${!isHrManager}">
							<form:options items="${fns:getDictList('office_location')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">家庭住址：</label></td>
					<td colspan="3" class="width-35">
						<form:input path="familyAddress" htmlEscape="false" class="form-control" disabled="${!isHrManager}"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">现住地：</label></td>
					<td colspan="3" class="width-35">
						<form:input path="presentAddress" htmlEscape="false" class="form-control" disabled="${!isHrManager}"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">紧急联系人</label></td>
					<th colspan="3">
						<hr style="width:100%;height:5px;border:none;border-top:5px ridge #337ab7;" />
					</th>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">紧急联系人：</label></td>
					<td class="width-35">
						<form:input path="emergencyContact" htmlEscape="false" class="form-control" disabled="${!isHrManager}"/>
					</td>
					<td class="width-15 active"><label class="pull-right">关系：</label></td>
					<td class="width-35">
						<form:input path="relationship" htmlEscape="false" class="form-control" disabled="${!isHrManager}"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">紧急联系人号码：</label></td>
					<td class="width-35">
						<form:input path="relationshipphone" htmlEscape="false" class="form-control" disabled="${!isHrManager}"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">学历信息</label></td>
					<th colspan="3">
						<hr style="width:100%;height:5px;border:none;border-top:5px ridge #337ab7;" />
					</th>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>最高学历：</label></td>
					<td class="width-35">
						<form:select path="highestEducational" class="form-control required" disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('highest_educational')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>最高学历学校类别：</label></td>
					<td class="width-35">
						<form:select path="schoolCategories" class="form-control required" disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('school_categories')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>最高学历毕业院校：</label></td>
					<td class="width-35">
						<form:input path="highestEducationalGraduation" htmlEscape="false"    class="form-control required" disabled="${!isHrManager}"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">最高学历专业：</label></td>
					<td class="width-35">
						<form:input path="highestEducationMajor" htmlEscape="false" class="form-control" disabled="${!isHrManager}"/>
					</td>
					<td class="width-15 active"><label class="pull-right">最高学历毕业时间：</label></td>
					<td class="width-35">
						<input id="highestEducationGraduationDate" name="highestEducationGraduationDate"  type="text"  class="form-control " disabled="${!isHrManager}"
						value="<fmt:formatDate value="${sysEhrPersonalInfoCommand.highestEducationGraduationDate}" pattern="yyyy-MM-dd"/>" />
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">第一学历：</label></td>
					<td class="width-35">
						<form:select path="firstdegree" class="form-control " disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('highest_educational')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">第一学历学校类别：</label></td>
					<td class="width-35">
						<form:select path="firstdegreeSchoolCategories" class="form-control " disabled="${!isHrManager}">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('school_categories')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">第一学历毕业院校：</label></td>
					<td class="width-35">
						<form:input path="firstdegreeEducationalGraduation" htmlEscape="false" class="form-control" disabled="${!isHrManager}"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">第一学历专业：</label></td>
					<td class="width-35">
						<form:input path="firstdegreeEducationMajor" htmlEscape="false"    class="form-control" disabled="${!isHrManager}"/>
					</td>
					<td class="width-15 active"><label class="pull-right">第一学历毕业时间：</label></td>
					<td class="width-35">
						<input id="firstdegreeEducationGraduationDate" name="firstdegreeEducationGraduationDate"  type="text"  class="form-control " disabled="${!isHrManager}"
						value="<fmt:formatDate value="${sysEhrPersonalInfoCommand.firstdegreeEducationGraduationDate}" pattern="yyyy-MM-dd"/>" />
					</td>
				</tr>

		 	</tbody>
		</table>
		<!-- 证书分录 -->
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">技能证书：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<c:if test="${isHrManager}">
				<a class="btn btn-white btn-sm" onclick="addRow('#certificateList', certificateRowIdx, certificateTpl);certificateRowIdx = certificateRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			</c:if>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>证书类别</th>
						<th>证书等级</th>
						<th>证书名称</th>
						<th>获证时间</th>
						<c:if test="${isHrManager}">
							<th width="10">&nbsp;</th>
						</c:if>
					</tr>
				</thead>
				<tbody id="certificateList">
				</tbody>
			</table>
			<script type="text/template" id="certificateTpl">
			//<!--
				<tr id="certificateList{{idx}}">
					<td class="hide">
						<input id="certificateList{{idx}}_id" name="certificateList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="certificateList{{idx}}_delFlag" name="certificateList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>

					<td>
                 		<select id="certificateList{{idx}}_certificateCategor" name="certificateList[{{idx}}].certificateCategor" class="form-control" type="select" ${isHrManager eq 'true'? null: 'disabled'}>
                        	<c:forEach var="dict" items="${certificateCategorList}" varStatus="status">
                        		<option value="${dict.value}" title="${dict.description}" }>${dict.label}</option>
                       		</c:forEach>
                		</select>
					</td>

					<td>
                 		<select id="certificateList{{idx}}_certificateRank" name="certificateList[{{idx}}].certificateRank" class="form-control" type="select" ${isHrManager eq 'true'? null: 'disabled'}>
                        	<c:forEach var="dict" items="${certificationRankList}" varStatus="status">
                        		<option value="${dict.value}" title="${dict.description}" }>${dict.label}</option>
                       		</c:forEach>
                		</select>
					</td>

					<td>
						<input id="certificateList{{idx}}_certificateName" name="certificateList[{{idx}}].certificateName" type="text"   style="font-size:14px ;width:99%"  maxlength="20" class="laydate-icon form-control layer-date "
						value="{{row.certificateName}}" ${isHrManager eq 'true'? null: 'disabled'}/>
					</td>

					<td>
						<input id="certificateList{{idx}}_certificateDate" name="certificateList[{{idx}}].certificateDate" type="text"   style="font-size:14px ;width:99%"  maxlength="20" class="laydate-icon form-control layer-date "
						value="{{row.certificateDateStr}}" ${isHrManager eq 'true'? null: 'disabled'}/>
					</td>
					<c:if test="${isHrManager}">
						<td class="text-center" width="10">
							{{#delBtn}}<span class="close" onclick="delRow(this, '#certificateList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
						</td>
					</c:if>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var certificateRowIdx = 0, certificateTpl = $("#certificateTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					var data = ${fns:toJson(sysEhrPersonalInfoCommand.certificateList)};

					for (var i=0; i<data.length; i++){

						addRow('#certificateList', certificateRowIdx, certificateTpl, data[i]);
						certificateRowIdx = certificateRowIdx + 1;
					}
			</script>
			</div>
		</div>
		</div>

		<!-- 工作经历分录 -->
		 <div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">工作经历：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<c:if test="${isHrManager}">
				<a class="btn btn-white btn-sm" onclick="addWorkRow('#userWorkexperienceList', userWorkexperienceRowIdx, userWorkexperienceTpl);userWorkexperienceRowIdx = userWorkexperienceRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			</c:if>
			<table id="contentTable" class="table table-striped text-nowrap table-bordered table-hover table-condensed dataTables-example dataTable">
				<thead>
					<tr>
						<th class="hide"></th>
						<th width="150">开始时间</th>
						<th width="150">结束时间</th>
						<th width="200">单位名称</th>
						<th width="150">任职部门</th>
						<th width="150">担任职务</th>
						<th width="300">主要工作内容，业绩，荣誉等</th>
						<c:if test="${isHrManager}">
							<th width="10">&nbsp;</th>
						</c:if>
					</tr>
				</thead>
				<tbody id="userWorkexperienceList">
				</tbody>
			</table>
			<script type="text/template" id="userWorkexperienceTpl">
			//<!--
				<tr id="userWorkexperienceList{{idx}}">
					<td class="hide">
						<input id="userWorkexperienceList{{idx}}_id" name="userWorkexperienceList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="userWorkexperienceList{{idx}}_delFlag" name="userWorkexperienceList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					<td>
						<input id="userWorkexperienceList{{idx}}_beginDate" name="userWorkexperienceList[{{idx}}].beginDate" type="text"   style="font-size:14px;width:99%" maxlength="20" class="laydate-icon form-control layer-date "
						value="{{row.beginDateStr}}" ${isHrManager eq 'true'? null: 'disabled'}/>
					<td>
						<input id="userWorkexperienceList{{idx}}_endDate" name="userWorkexperienceList[{{idx}}].endDate" type="text"   style="font-size:14px;width:99%" maxlength="20" class="laydate-icon form-control layer-date "
						value="{{row.endDateStr}}" ${isHrManager eq 'true'? null: 'disabled'}/>

					<td>
						<input id="userWorkexperienceList{{idx}}_unitName" name="userWorkexperienceList[{{idx}}].unitName" type="text"   style="width:99%"   value="{{row.unitName}}" class="form-control" ${isHrManager eq 'true'? null: 'disabled'}/>
					</td>

					<td>
						<input id="userWorkexperienceList{{idx}}_department" name="userWorkexperienceList[{{idx}}].department" type="text"   style="width:99%"   value="{{row.department}}" class="form-control" ${isHrManager eq 'true'? null: 'disabled'}/>
					</td>

					<td>
						<input id="userWorkexperienceList{{idx}}_assumeOffice" name="userWorkexperienceList[{{idx}}].assumeOffice" type="text"   style="width:99%"   value="{{row.assumeOffice}}" class="form-control" ${isHrManager eq 'true'? null: 'disabled'}/>
					</td>

					<td>
						<textarea id="userWorkexperienceList{{idx}}_jobContent" name="userWorkexperienceList[{{idx}}].jobContent" rows="4"  class="form-control " style="width:99%" ${isHrManager eq 'true'? null: 'disabled'}>{{row.jobContent}}</textarea>
					</td>
					<c:if test="${isHrManager}">
						<td class="text-center" width="10">
							{{#delBtn}}<span class="close" onclick="delWorkRow(this, '#userWorkexperienceList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
						</td>
					</c:if>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var userWorkexperienceRowIdx = 0, userWorkexperienceTpl = $("#userWorkexperienceTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				var data = ${fns:toJson(sysEhrPersonalInfoCommand.userWorkexperienceList)};

				for (var i=0; i<data.length; i++){

					addWorkRow('#userWorkexperienceList', userWorkexperienceRowIdx, userWorkexperienceTpl, data[i]);
					userWorkexperienceRowIdx = userWorkexperienceRowIdx + 1;
				}
			</script>
			</div>
		</div>
		</div>

		<!-- 电子档案 -->
		<c:if test="${isHrManager}">
			<div class="head">员工电子档案</div>
			<div class="content">
				<div class="headfile">简历</div>
				<div class="uploder-container">

					<div  class="cxuploder">
						<div class="queueList">
							<div class="placeholder">
								<div class="filePicker"></div>
								<p>将简历拖到这里</p>
							</div>
						</div>

						<div class="statusBar" style="display:none;">


							<div class="btns">
								<div  class="jxfilePicker"></div>

							</div>
							<div class="info"></div>
						</div>
					</div>
				</div>
				<div class="headfile">证书</div>
					<div class="uploder-container">
						<div  class="cxuploder">
							<div class="queueList">
								<div class="placeholder">
									<div class="filePicker"></div>
									<p>将证书拖到这里</p>
								</div>
							</div>

							<div class="statusBar" style="display:none;">


								<div class="btns">
									<div  class="jxfilePicker"></div>

								</div>
								<div class="info"></div>
							</div>
						</div>
					</div>
				<div class="headfile">合同</div>
				<div class="uploder-container">
					<div  class="cxuploder">
						<div class="queueList">
							<div class="placeholder">
								<div class="filePicker"></div>
								<p>将合同拖到这里</p>
							</div>
						</div>

						<div class="statusBar" style="display:none;">


							<div class="btns">
								<div  class="jxfilePicker"></div>

							</div>
							<div class="info"></div>
						</div>
					</div>
				</div>
				<div class="headfile">其他</div>
				<div class="uploder-container">
					<div  class="cxuploder">
						<div class="queueList">
							<div class="placeholder">
								<div class="filePicker"></div>
								<p>将其他拖到这里</p>
							</div>
						</div>

						<div class="statusBar" style="display:none;">


							<div class="btns">
								<div  class="jxfilePicker"></div>

							</div>
							<div class="info"></div>
						</div>
					</div>
				</div>
			</div>
			<button  class="btn btn-success btn-sm" style="font-size:22px;width:30%;height:15%; margin-left:35%; margin-top:2%; margin-bottom:1%;">确定</button>
		</c:if>
	</form:form>
</body>
</html>