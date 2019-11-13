<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/webpage/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
	<script type="text/javascript">

		function refresh(){//刷新
			window.location="${ctx}/sys/office/";
		}
	</script>
</head>
<body class="gray-bg">
	<form:form id="searchForm" modelAttribute="office"  onchange="search()" action="${ctx}/sys/office/index" method="post" class="form-inline">
		<input type="hidden" name="organTag" value="${office.organTag}">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-content">
	<sys:message content="${message}"/>
	<div id="content" class="row">

		<div id="left"  style="background-color:#e7eaec;width: 250px" class="leftBox col-sm-1">
		<%--<form:select placeholder="组织架构" path="organTag"  class="form-control m-b"  style="width:180px; margin-top:20px;">
					<form:options items="${fns:getDictList('org_flag')}"  itemLabel="label"   itemValue="value" htmlEscape="false"/>
				</form:select>--%>
			<%--<button class="btn btn-white btn-sm " style="margin-left: 150px" data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> </button>--%>
			<div id="ztree" class="ztree leftBox-content">

			</div>

		</div>
		<div id="right"  class="col-sm-11  animated fadeInRight">
			<iframe id="officeContent" name="officeContent"
			src="${ctx}/sys/office/list?id=${office.id}&parentIds=&organTag=${office.organTag}"
			width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	</div>
	</div>
	</div>
</form:form>
	<script type="text/javascript">
		var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
			callback:{onClick:function(event, treeId, treeNode){
			  	  console.log(treeNode)
					var id = treeNode.id;
					$('#officeContent').attr("src",
					"${ctx}/sys/office/list?id="+id+"&parentIds="+treeNode.id+"&organTag="+${office.organTag}
					);
				}
			}
		};

		function refreshTree(){
			$.getJSON("${ctx}/sys/office/treeData?organTagA=${office.organTag}",function(data){
				$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
			});
		}
		refreshTree();

		var leftWidth = 270; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 120);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -60);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>