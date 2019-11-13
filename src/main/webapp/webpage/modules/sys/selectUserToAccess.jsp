<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>分配角色</title>
	<meta name="decorator" content="blank"/>
	<%@include file="/webpage/include/treeview.jsp" %>
	<script type="text/javascript">

		var officeTree;
		var selectedTree;//zTree已选择对象

		// 初始化
		$(document).ready(function(){
			officeTree = $.fn.zTree.init($("#officeTree"), setting, officeNodes);
			selectedTree = $.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
		});

		var setting = {view: {selectedMulti:false,nameIsHTML:true,showTitle:false,dblClickExpand:false},
				data: {simpleData: {enable: true}},
				callback: {onClick: treeOnClick}};

		var officeNodes=[
	            <c:forEach items="${officeList}" var="office">
	            {id:"${office.id}",
	             pId:"${not empty office.parent?office.parent.id:0}",
	             name:"${office.name}"},
	            </c:forEach>];

		var pre_selectedNodes =[
   		        <c:forEach items="${userList}" var="user">
   		        {id:"${user.id}",
   		         pId:"0",
   		         name:"<font color='red' style='font-weight:bold;'>${user.name}</font>"},
   		        </c:forEach>];

		var selectedNodes =[
		        <c:forEach items="${userList}" var="user">
		        {id:"${user.id}",
		         pId:"0",
		         name:"<font color='red' style='font-weight:bold;'>${user.name}</font>"},
		        </c:forEach>];

		var pre_ids = "${selectIds}".split(",");
		var ids = "${selectIds}".split(",");

		//点击选择项回调
		function treeOnClick(event, treeId, treeNode, clickFlag){
			var organTag = $("#organTagID").val();
			$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode);
			if("officeTree"==treeId){
				var tree = $.fn.zTree.getZTreeObj(treeId);
				var childNodes = tree.transformToArray(treeNode);
				var officeIds = [] ;
				for(var i=0; i<childNodes.length; i++) {
					officeIds.push(childNodes[i].id);
				}
				$.get("${ctx}/sys/sysTDataaccessuser/users?officeId=" + treeNode.id + "&organTag=" + organTag+"&officeIds="+officeIds, function(userNodes){
					$.fn.zTree.init($("#userTree"), setting, userNodes);
				});
			}
			if("userTree"==treeId){
				if($.inArray(String(treeNode.id), ids)<0){
					selectedTree.addNodes(null, treeNode);
					ids.push(String(treeNode.id));
				}
			};
			if("selectedTree"==treeId){
				if($.inArray(String(treeNode.id), pre_ids)<0){
					selectedTree.removeNode(treeNode);
					ids.splice($.inArray(String(treeNode.id), ids), 1);
				}else{
					top.$.jBox.tip("角色原有成员不能清除！", 'info');
				}
			}
		};
		function clearAssign(){
			var submit = function (v, h, f) {
			    if (v == 'ok'){
					var tips="";
					if(pre_ids.sort().toString() == ids.sort().toString()){
						tips = "未给分配用户！";
					}else{
						tips = "已选人员清除成功！";
					}
					ids=pre_ids.slice(0);
					selectedNodes=pre_selectedNodes;
					$.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
			    	top.$.jBox.tip(tips, 'info');
			    } else if (v == 'cancel'){
			    	// 取消
			    	top.$.jBox.tip("取消清除操作！", 'info');
			    }
			    return true;
			};
			tips="确定清除已选人员？";
			top.$.jBox.confirm(tips, "清除确认", submit);
		};
	</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="sysTDataaccessuser"  onchange="search()" action="${ctx}/sys/sysTDataaccessuser/usertorole" method="post" class="form-inline">
<input id="id" name="id" type="hidden" value="${sysTDataaccessuser.id}"/>
<input id="organTagID" name="organTagID" type="hidden" value="${sysTDataaccessuser.organTag}"/>
	<div id="assignUser" class="row wrapper wrapper-content">
		<div class="col-sm-4" style="border-right: 1px solid #A8A8A8;">
			<p>所在部门：</p>
			<form:select placeholder="组织架构" path="organTag"  class="form-control m-b"  style="width:180px; margin-top:20px;">
					<form:options items="${fns:getDictList('org_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
				</form:select>
			<div id="ztree" class="ztree leftBox-content"></div>
			<div id="officeTree" class="ztree"></div>
		</div>
		<div class="col-sm-4">
			<p>待选人员：</p>
			<div id="userTree" class="ztree"></div>
		</div>
		<div class="col-sm-4" style="padding-left:16px;border-left: 1px solid #A8A8A8;">
			<p>已选人员：</p>
			<div id="selectedTree" class="ztree"></div>
		</div>
	</div>
</form:form>

</body>
</html>
