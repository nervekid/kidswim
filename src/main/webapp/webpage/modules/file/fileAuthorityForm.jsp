<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文件权限管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/webpage/include/treeview.jsp" %>
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

            var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
                data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
                        tree.checkNode(node, !node.checked, true, true);
                        return false;
                    }}};


            // 用户-机构
            var zNodes2=[
                    <c:forEach items="${officeList}" var="office">{id:"${office.id}", pId:"${not empty office.parent?office.parent.id:0}", name:"${office.name}"},
                </c:forEach>];
            // 初始化树结构
            var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
            // 不选择父节点
            tree2.setting.check.chkboxType = { "Y" : "", "N" : "" };
            // 默认选择节点


            var ids2 = "${fileAuthority.deptId}".split(",");


            for(var i=0; i<ids2.length; i++) {
                var node = tree2.getNodeByParam("id", ids2[i]);
                try{tree2.checkNode(node, true, false);}catch(e){}
            }
            // 默认展开全部节点
            //tree2.expandAll(true);

            var treeObj = $.fn.zTree.getZTreeObj("officeTree");
            var nodes = treeObj.getNodes();
            for (var i = 0; i < nodes.length; i++) { //设置节点展开
                treeObj.expandNode(nodes[i], true, false, true);
            }

            // 刷新（显示/隐藏）机构
            refreshOfficeTree();


            $("#chooseDeptbutton").click(function choose() {
                var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
                var deptName = [];
                for(var i=0; i<nodes2.length; i++) {
                    ids2.push(nodes2[i].id);
                    deptName.push(nodes2[i].name);
                }

                console.log(deptName);

                $("#listDeptValue").val(deptName);
                $("#deptId").val(ids2);


                $('#chooseDeptModal').modal('hide')
            });
		});

        function refreshOfficeTree(){
			 $("#officeTree").show();
        }

        function openDept() {
            $('#chooseDeptModal').modal({
                keyboard: true
            });
		}


        function setUserValue(index) {
            $.ajax({
                url: '${ctx}/file/fileAuthority/getUserValue',
                type: 'post',
                data:JSON.stringify({
                    id:'${fileAuthority.id}',
                }),
                cache: false,
                processData: false,
                contentType: "application/json",
                dataType: "json",
                async: false
            }).done(function(res) {
                if(res.success) {
                    $("#listUserValue").val(res.data.listUser);
                }else {
                    top.layer.alert("请求成功，查看用户信息失败",{icon: 0, title:'警告'});
                }

            }).fail(function(res) {
                top.layer.alert("请求失败，查看用户信息失败",{icon: 0, title:'警告'});
            });

            //$("#listUserValue").val("11111");
            //top.layer.close(index);
        }


		function openUser() {

            top.layer.open({
                type: 2,
                area: ['800px', '500px'],
                title: '分配用户',
                maxmin: true, //开启最大化最小化按钮
                content: '${ctx}/file/fileAuthority/openUser?id=${fileAuthority.id}' ,
                btn: ['确定'],
                yes: function(index){
                    setUserValue();
                    top.layer.close(index);

                },
                cancel: function(index){
                    setUserValue();
                }
            });
        }


       function clickaddRuleBtn(id) {
           addRule("分配规则","${ctx}/file/fileRule/openRule","1000px", "800px");
       }

       function addRule(title,url,width,height) {

           top.layer.open({
               type: 2,
               area: [width, height],
               title: title,
               maxmin: true, //开启最大化最小化按钮
               content: url,
               btn: ['确定', '关闭'],
               yes: function (index, layero) {
                   var body = top.layer.getChildFrame('body', index);
                   var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                   var inputForm = body.find('#contentTable tbody tr td input.i-checks:checkbox');

                   var idStr = "";
                   var ids = "";
                   var nameStr = "";
                   var names = "";

                   var dataArray = [];
                   var count = 0;
                   inputForm.each(function () {
                       if (true == $(this).is(':checked')) {
                           idStr += $(this).attr("id") + ",";
                           nameStr += $(this).attr("name") + ",";
                           count ++;
                       }
                       if (idStr.substr(idStr.length - 1) == ',') {
                           ids = idStr.substr(0, idStr.length - 1);
                           names = nameStr.substr(0, nameStr.length - 1);
                       }
                   });

                   if (count == 0) {
                       top.layer.alert('请选择一条数据!', {icon: 0, title: '警告'});
                       return;
                   }

                   if (count > 1) {
                       top.layer.alert('只能选择一条数据!', {icon: 0, title: '警告'});
                       return;
                   }

                   $("#ruleId").val(ids);
                   $("#ruleName").val(names);

                   top.layer.close(index);
               }
           })
       }

	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="fileAuthority" action="${ctx}/file/fileAuthority/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">文件名称：</label></td>
					<td class="width-35">
						<form:input path="fileName" htmlEscape="false" readonly="true"   class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">文件目录：</label></td>
					<td class="width-35">
						<form:input path="catalogName" htmlEscape="false"  readonly="true"  class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">创建者：</label></td>
					<td class="width-35">
						<form:input path="createFileName" htmlEscape="false"  readonly="true"  class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">文件等级范围：</label></td>
					<td class="width-35">
						<input type="level" readonly="readonly" class="form-control" value="${fns:getDictLabel(fileAuthority.level, 'file_level', '')}" />
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">部门范围：<button type="button" onclick="openDept()" class="btn btn-success btn-sm btn-lg btn-primary"><i class="fa fa-search"></i></button></label></td>
					<td class="width-35">
						<form:textarea id="listDeptValue" path="listDept" htmlEscape="false" rows="4"  readonly="true"  class="form-control "/>
						<%--<a onclick="openDept()">请选择部门</a>--%>
						<form:hidden path="deptId"/>
					</td>
					<td class="width-15 active"><label class="pull-right">用户范围：<button type="button" onclick="openUser()" class="btn btn-success btn-sm btn-lg btn-primary"><i class="fa fa-search"></i></button></label></td>
					<td class="width-35">

                        <form:textarea path="listUser" id="listUserValue" htmlEscape="false" rows="4"  readonly="true"  class="form-control "/>

						<%--<form:textarea path="listUser" id="listUserValue" htmlEscape="false" rows="4"  readonly="true"  class="form-control "/>--%>
						<%--&lt;%&ndash;<a href="" onclick="openUser()" data-placement="left" data-toggle="modal" data-target="#statusModal"> 请选择用户</a>&ndash;%&gt;--%>
						<%--<a href="" onclick="openUser()" data-toggle="modal"> 请选择用户</a>--%>

					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">文件权限规则：</label></td>
					<td class="width-35">
                        <div class="input-group">
                            <form:hidden path="ruleId" />
                            <form:input path="ruleName" htmlEscape="false"  readonly="true"  class="form-control "/>
                            <span class="input-group-btn">
                             <button type="button" onclick="clickaddRuleBtn('${fileAuthority.id}')" id="addRuleBtn" class="btn btn-success btn-sm btn-lg btn-primary"><i class="fa fa-search"></i></button>
                            </span>
                        </div>
					</td>

				</tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">备注：</label></td>
                    <td class="width-35" colspan="3">
                        <form:textarea path="remarks" htmlEscape="false" rows="3"    class="form-control "/>
                    </td>
                </tr>
		 	</tbody>



		</table>
	</form:form>


		<div class="modal fade" id="chooseDeptModal" tabindex="-1" role="dialog" aria-labelledby="chooseDeptModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title" id="chooseDeptModalLabel">
							部门选择
						</h4>
					</div>
					<div class="modal-body " style="width: 600px" >
						<div id="officeTree" class="ztree" style="margin-top:3px;"></div>
					</div>
					<div class="modal-footer">
						<button id="chooseDeptbutton" type="button" class="btn btn-success btn-sm">
							确认
						</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>

		<%--<div class="modal fade" id="chooseUserModal" tabindex="-1" role="dialog" aria-labelledby="chooseUserModalLabel" aria-hidden="true">--%>
			<%--<div class="modal-dialog">--%>
				<%--<div class="modal-content">--%>
					<%--<div class="modal-header">--%>
						<%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">--%>
							<%--&times;--%>
						<%--</button>--%>
						<%--<h4 class="modal-title" id="chooseUserModalLabel">--%>
							<%--用户选择--%>
						<%--</h4>--%>
					<%--</div>--%>
					<%--<div class="modal-body " style="width: 600px" >--%>

						<%--<div class="breadcrumb">--%>
							<%--<form id="form" action="${ctx}/file/fileAuthority/updateUser" method="post" class="hide">--%>
								<%--<input id="idsArr" type="hidden" name="idsArr" value=""/>--%>
								<%--<input id="id" type="hidden" name="id" value=""/>--%>
							<%--</form>--%>
							<%--<button id="addUserButton" type="submit"  class="btn btn-outline btn-primary btn-sm" title="添加人员"><i class="fa fa-plus"></i> 添加人员</button>--%>
						<%--</div>--%>

						<%--<table id="contentTable" class="table text-nowrap table-striped table-bordered table-hover table-condensed dataTables-example dataTable">--%>
							<%--<thead><tr><th>归属公司</th><th>归属部门</th><th>登录名</th><th>姓名</th><th>手机</th><th>操作</th></tr></thead>--%>
							<%--<tbody>--%>
							<%--<c:forEach items="${userList}" var="user">--%>
								<%--<tr>--%>
									<%--<td>${user.company.name}</td>--%>
									<%--<td>${user.office.name}</td>--%>
									<%--<td>${user.loginName}</td>--%>
									<%--<td>${user.name}</td>--%>
									<%--<td>${user.mobile}</td>--%>
									<%--<td>--%>
										<%--<a href="${ctx}/file/fileAuthority/deleteUser?userId=${user.id}&id=${fileAuthority.id}"--%>
										   <%--onclick="return confirmx('确认要移除该用户吗？', this.href)">移除</a>--%>
									<%--</td>--%>
								<%--</tr>--%>
							<%--</c:forEach>--%>
							<%--</tbody>--%>
						<%--</table>--%>
					<%--</div>--%>
					<%--<div class="modal-footer">--%>
						<%--<button id="chooseUserbutton" type="button" class="btn btn-success btn-sm">--%>
							<%--确认--%>
						<%--</button>--%>
					<%--</div>--%>
				<%--</div><!-- /.modal-content -->--%>
			<%--</div><!-- /.modal -->--%>
		<%--</div>--%>





</body>
</html>