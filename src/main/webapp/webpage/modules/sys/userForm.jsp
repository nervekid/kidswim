<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<fmt:setBundle basename="kite" var="kite" />
<fmt:message key="fastdfs.storage.url" var="fastdfsStorageUrl" bundle="${kite}" />

<html>
<head>
	<title>用戶管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/webpage/include/treeview.jsp" %>
	<script type="text/javascript" src="${ctxStatic}/pinyin/jQuery.Hz2Py-min.js"></script>
	<%--<script type="text/javascript" src="${ctxStatic}/pinyin/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/pinyin/jQuery.Hz2Py-min.js"></script>--%>
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
			$("#no").focus();
			validateForm = $("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}//設置了遠程驗證，在初始化時必須預先調用壹次。
				},
				messages: {
					loginName: {remote: "用戶登錄名已存在"},
					confirmNewPassword: {equalTo: "輸入與上面相同的密碼"}
				},
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

            //在ready函數中預先調用壹次遠程校驗函數，是壹個無奈的回避案。(劉高峰）
            //否則打開修改對話框，不做任何更改直接submit,這時再觸發遠程校驗，耗時較長，
            //submit函數在等待遠程校驗結果然後再提交，而layer對話框不會阻塞會直接關閉同時會銷毀表單，因此submit沒有提交就被銷毀了導致提交表單失敗。
            $("#inputForm").validate().element($("#loginName"));

            /////////////角色樹/////////////////////////////
            <%--<c:forEach items="${allRoles}" var="role" varStatus="status">--%>
            	<%--console.log('${role}');--%>
            	<%--console.log('${status.count}');--%>
			<%--</c:forEach>--%>

            <%--var setting = {--%>
                <%--check:{--%>
                    <%--enable:true,nocheckInherit:true--%>
                <%--},--%>
                <%--view:{--%>
                    <%--selectedMulti:false--%>
                <%--},--%>
                <%--data:{--%>
                    <%--simpleData:{--%>
                        <%--enable:true--%>
                    <%--}--%>
                <%--},--%>
                <%--callback:{--%>
                    <%--beforeClick:function(id, node){--%>
                        <%--tree.checkNode(node, !node.checked, true, true);--%>
                        <%--return false;--%>
                    <%--},--%>
					<%--//父標簽不給選擇--%>
                    <%--beforeCheck:function (id,node)	 {--%>
                        <%--return !node.isParent;--%>
                    <%--}--%>
                <%--}--%>
            <%--};--%>

            <%--// 用戶-機構--%>
            <%--&lt;%&ndash;<c:forEach items="${allRoles}" var="role">&ndash;%&gt;--%>
            <%--&lt;%&ndash;console.log('${role}');&ndash;%&gt;--%>
            <%--&lt;%&ndash;console.log('${role.id}')&ndash;%&gt;--%>
            <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>

            <%--var zNodes2=[<c:forEach items="${allRoles}" var="role">{id:"${role.id}",--%>
																	<%--pId:"${not empty role.sysModules?role.sysModules:0}",--%>
																	<%--type:"${role.roleLabel}",--%>
																	<%--name:"${role.simpleName}"--%>
																	<%--},--%>
				<%--</c:forEach>--%>
            <%--];--%>

            <%--// 初始化樹結構--%>
            <%--var tree2 = $.fn.zTree.init($("#roleTree"), setting, zNodes2);--%>

            <%--// 不選擇父節點--%>
            <%--tree2.setting.check.chkboxType = { "Y" : "s", "N" : "s" };--%>

            <%--// 默認選擇節點--%>
            <%--var str = "${user.roleIdList}";--%>
            <%--str = str.substring(1,str.length-1);--%>
            <%--var ids2 = str.split(", ");--%>
            <%--for(var i=0; i<ids2.length; i++) {--%>
                <%--var node = tree2.getNodeByParam("id", ids2[i]);--%>
                <%--try{tree2.checkNode(node, true, false);}catch(e){}--%>
            <%--}--%>

            <%--// 默認展開全部節點--%>
            <%--tree2.expandAll(false);--%>

            <%--var treeObj = $.fn.zTree.getZTreeObj("roleTree");--%>

            <%--// var nodes = treeObj.getNodes();--%>
            <%--// for (var i = 0; i < nodes.length; i++) { //設置節點展開--%>
            <%--//     treeObj.expandNode(nodes[i], true, false, true);--%>
            <%--// }--%>

            <%--//設置父標簽不可以選擇--%>
            <%--// var nodes = treeObj.transformToArray(treeObj.getNodes());--%>
            <%--// for (var i=0, l=nodes.length; i < l; i++) {--%>
             <%--//    if (nodes[i].isParent){--%>
             <%--//        treeObj.setChkDisabled(nodes[i], true);--%>
             <%--//    }--%>
            <%--// }--%>


            <%--// 刷新（顯示/隱藏）機構--%>
            <%--refreshRoleTree();--%>

            <%--$("#chooseRoleButton").click(function choose() {--%>
                <%--var ids2 = [], nodes2 = tree2.getCheckedNodes(true);--%>

                <%--// var roleName = [];--%>
                <%--// for(var i=0; i<nodes2.length; i++) {--%>
                <%--//     ids2.push(nodes2[i].id);--%>
                <%--//     roleName.push(nodes2[i].name);--%>
                <%--// }--%>
                <%--//--%>
                <%--// console.log(ids2);--%>
                <%--//--%>
                <%--// $("#roleNameStr").val(roleName);--%>
                <%--// $("#listRoleId").val(ids2);--%>

				<%--var roleName = new Map();--%>
                <%--for(var i=0; i<nodes2.length; i++) {--%>
                    <%--var node = nodes2[i];--%>
                    <%--ids2.push(node.id);--%>
                    <%--var type = node.type;--%>
					<%--var name = node.name;--%>


					<%--if(roleName.hasOwnProperty(type)) {--%>
						<%--var temp = roleName[type];--%>
                        <%--roleName[type]= temp+"   "+name;--%>
					<%--}else {--%>
                        <%--roleName[type]= name;--%>
					<%--}--%>

                <%--}--%>

                <%--var html = "";--%>
                <%--for(var role in roleName){--%>
                    <%--console.log(role+"........."+roleName[role]);--%>
                    <%--var temp = "【"+role+"】"+ roleName[role]+"\n";--%>
                    <%--html+=temp;--%>
                <%--}--%>

                <%--$("#roleNameStr").text(html);--%>
                <%--$("#listRoleId").val(ids2);--%>

                <%--$('#chooseRoleModal').modal('hide')--%>
            <%--});--%>

		});

        function refreshRoleTree(){
            $("#roleTree").show();
        }
        function newPasswordMethod(password) {
            var reg =/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{6,32}$/;
            if(!reg.test(password)){
                alert('密碼必須由6-32位數字以及大小寫字母組成');
                $("#newPassword").val('');
            }
        }
        function confirmNewPasswordMethod(password) {
            var reg =/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{6,32}$/;
            if(!reg.test(password)){
                alert('密碼必須由6-32位數字以及大小寫字母組成');
                $("#confirmNewPassword").val('');
            }
        }

        function openRole() {
            $('#chooseRoleModal').modal({
                keyboard: true
            });
        }
        function checkEmail(value) {
            if(!value.endsWith("@wxchina.com")){
                alert('郵件要以@wxchina.com格式結尾');
                $("#email").val('');
            }
        }
        function nameChange() {
            $('#email').val($('#name').toPinyin()+ '@wxchina.com');
        }
        function createPassword() {
            var id = '${user.id}';
            var newpassword = $('#newPassword').val();
            if(id=='' && newpassword==''){
                var pass = '${createPassword}';
              /*  $('#newPassword').val($('#loginName').val()+ 'Wx666');
                $('#confirmNewPassword').val($('#loginName').val()+ 'Wx666');*/
                $('#newPassword').val(pass);
                $('#confirmNewPassword').val(pass);
            }
            $('#mobile').val($('#loginName').val());
        }

	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<input type="hidden" name="loginFlag" value="1">
		<input type="hidden" name="enable" value="1">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
                    <td class="active"><label class="pull-right"><font color="red">*</font>姓名:</label></td>
                    <td><form:input path="name" onBlur="nameChange()" onChange="nameChange()" onKeydown="nameChange()" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>

		      <tr>
                 <td class="active"><label class="pull-right"><font color="red">*</font>登錄名:</label></td>
                 <td><input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
                 <form:input path="loginName" onBlur="createPassword()" onChange="createPassword()" onKeydown="createPassword()"
							 htmlEscape="false" maxlength="50" class="form-control required userName"/>
                 </td>
                  <td class="active"><label class="pull-right"><font color="red">*</font>手機:</label></td>
                  <td><form:input path="mobile" htmlEscape="false" maxlength="100" class="form-control required"/></td>
		      </tr>


		      <tr>
		         <td class="active"><label class="pull-right"><c:if test="${empty user.id}"><font color="red">*</font></c:if>密碼:</label></td>
		         <td><input id="newPassword" name="newPassword" type="password" value="" onchange="newPasswordMethod(this.value)"  maxlength="50" minlength="3" class="form-control ${empty user.id?'required':''}"/>
					<c:if test="${not empty user.id}"><span class="help-inline">若不修改密碼，請留空。</span></c:if></td>
		         <td class="active"><label class="pull-right"><c:if test="${empty user.id}"><font color="red">*</font></c:if>確認密碼:</label></td>
					 <td><input id="confirmNewPassword" name="confirmNewPassword" type="password"  onchange="confirmNewPasswordMethod(this.value)"   class="form-control ${empty user.id?'required':''}" value="" maxlength="50" minlength="3" equalTo="#newPassword"/></td>
		      </tr>

		       <tr>
		         <td class="active"><label class="pull-right"><font color="red">*</font>郵箱:</label></td>
		         <td><form:input path="email" htmlEscape="false" maxlength="100" onchange="checkEmail(this.value)" class="form-control email required"/></td>
		         <%--<td class="active"><label class="pull-right">電話:</label></td>
		         <td><form:input path="phone" htmlEscape="false" maxlength="100" class="form-control "/></td>--%>
                   <%--<td class="active"><label class="pull-right">是否允許登錄:</label></td>
                   <td><form:select path="loginFlag"  class="form-control">
                       <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                   </form:select></td>--%>
				   <td class="width-15 active"><label class="pull-right"><font color="red">*</font>歸屬部門：</label></td>
				   <td class="width-35">
					   <sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
									   title="部門" url="/sys/office/treeData?type=2&organTagA=1" cssClass="form-control required" allowClear="true" notAllowSelectParent="false"/>
				   </td>
		      </tr>

		         <%--<td class="active"><label class="pull-right"><font color="red">*</font>用戶角色:</label></td>--%>
		         <%--<td colspan="3">--%>
		         	<%--<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" cssClass="i-checks required"/>--%>
		         	<%--<label id="roleIdList-error" class="error" for="roleIdList"></label>--%>
		         <%--</td>--%>
				  <td class="active"><label class="pull-right"><font color="red">*</font>用戶角色:</label></td>
				  <td colspan="3">
					  <%--<a onclick="openRole()"> <font size="5">+</font></a>--%>
					  <%--<form:textarea id="roleNameStr" path="roleNameStr" htmlEscape="false" rows="6"  cssStyle="height: 170px" readonly="true"  class="form-control "/>--%>
					  <%--<form:hidden path="listRoleId"/>--%>
					  <sys:treeChoose showPosition="roleNameStr" insertPosition="listRoleId" cssStyle="height: 170px" row="6" ifChooseParent="N"
									  title="角色選擇" treeData="${allRoles}" expandAll="N"/>
				  </td>
		      </tr>
		       <tr>
		         <td class="active"><label class="pull-right">備註:</label></td>
		         <td colspan="3"><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control"/></td>
		      </tr>

		      <c:if test="${not empty user.id}">
		       <tr>
		         <td class=""><label class="pull-right">創建時間:</label></td>
		         <td><span class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></span></td>
		         <td class=""><label class="pull-right">最後登陸:</label></td>
		         <td><span class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;時間：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></span></td>
		      </tr>
		     </c:if>
           </tbody>
	</form:form>


	  <div class="modal fade" id="chooseRoleModal" tabindex="-1" role="dialog" aria-labelledby="chooseDeptModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
			  <div class="modal-content">
				  <div class="modal-header">
					  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						  &times;
					  </button>
					  <h4 class="modal-title" id="chooseDeptModalLabel">
						  角色選擇
					  </h4>
				  </div>
				  <div class="modal-body " style="width: 600px" >
					  <div id="roleTree" class="ztree" style="margin-top:3px;"></div>
				  </div>
				  <div class="modal-footer">
					  <button id="chooseRoleButton" type="button" class="btn btn-success btn-sm">
						  確認
					  </button>
				  </div>
			  </div><!-- /.modal-content -->
		  </div><!-- /.modal -->
	  </div>

</body>
</html>