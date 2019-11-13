<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文件权限规则管理</title>
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
				}});


				var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
							tree.checkNode(node, !node.checked, true, true);
							return false;
						}}};

				// 用户-机构
				var zNodes2=[<c:forEach items="${officeList}" var="office">{id:"${office.id}", pId:"${not empty office.parent?office.parent.id:0}", name:"${office.name}"},</c:forEach>];

				// 初始化树结构
				var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);

				// 不选择父节点
            	tree2.setting.check.chkboxType = { "Y" : "", "N" : "" };

            	// 默认选择节点
			    var ids2 = "${fileRule.deptId}".split(",");

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

                $("#deptName").val(deptName);
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
                url: '${ctx}/file/fileRule/getUserValue',
                type: 'post',
                data:JSON.stringify({
                    id:$('#pathId').val(),
                }),
                cache: false,
                processData: false,
                contentType: "application/json",
                dataType: "json",
                async: false
            }).done(function(res) {
                if(res.success) {
                    $("#userName").val(res.data.userName);
                }else {
                    top.layer.alert("请求成功，查看用户信息失败",{icon: 0, title:'警告'});
                }

            }).fail(function(res) {
                top.layer.alert("请求失败，查看用户信息失败",{icon: 0, title:'警告'});
            });

        }

        function openUser() {

            var ruleId = guid();

            top.layer.open({
                type: 2,
                area: ['800px', '500px'],
                title: '分配用户',
                maxmin: true, //开启最大化最小化按钮
                content: '${ctx}/file/fileRule/openUser?id=${fileRule.id}&ruleId='+ ruleId,
                btn: ['确定'],
                yes: function(index){

                    //若是第一次存储，则设置前端生成的ruleId为主键id
					if(${fileRule.id eq null}) {
                        $("#pathId").val(ruleId);
                    }

                    setUserValue();
                    top.layer.close(index);
                },
                cancel: function(index){
                    setUserValue();
                }
            });
        }


        function S4() {
            return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
        }
        function guid() {
            return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
        }


	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="fileRule" action="${ctx}/file/fileRule/save" method="post" class="form-horizontal">
		<form:hidden path="id" id="pathId" value="${fileRule.id}" />
		<form:hidden path="menuId"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">规则名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control"/>
					</td>
					<td class="width-15 active"><label class="pull-right">部门：<button type="button" onclick="openDept()" class="btn btn-success btn-sm btn-lg btn-primary"><i class="fa fa-search"></i></button></label></td>
					<td class="width-35">
						<form:textarea id="deptName" path="deptName" htmlEscape="false" rows="4"  readonly="true"  class="form-control "/>
						<%--<a onclick="openDept()">请选择部门</a>--%>
						<form:hidden path="deptId"/>
					</td>

				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">用户：<button type="button" onclick="openUser()" class="btn btn-success btn-sm btn-lg btn-primary"><i class="fa fa-search"></i></button></label></td>
					<td class="width-35">
						<form:textarea path="userName" id="userName" htmlEscape="false" rows="4"  readonly="true"  class="form-control "/>
						<%--<a href="" onclick="openUser()" data-toggle="modal"> 请选择用户</a>--%>
					</td>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false"   rows="4"  class="form-control "/>
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

</body>
</html>