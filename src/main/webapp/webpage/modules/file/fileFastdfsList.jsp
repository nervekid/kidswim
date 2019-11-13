<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<fmt:setBundle basename="kite" var="kite" />
<fmt:message key="fastdfs.tomcat.url" var="fastdfsTomcatUrl" bundle="${kite}" />

<html>
<head>
	<title>文件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var checkBoxChecked ='${checkBoxChecked}';
            if(checkBoxChecked){
                $("#collectionId").attr("checked",true);
            }
		});

		//////////////////////////////////单个下载//////////////////////////////////
		function download() {

            var $Checks = $("input[name='checkbox']:checked");

            if($Checks.length > 1) {
                top.layer.alert('只能选择一条数据!', {icon: 0, title:'警告'});
                return;
            } else if($Checks.length <=0) {
                top.layer.alert('请选择你要下载的文件!', {icon: 0, title:'警告'});
                return;
            }

            var url = $Checks[0].getAttribute("url");
            console.log(url);

            var fileName = $Checks[0].getAttribute("filename");
            console.log(fileName);

            //a标签模拟下载
            $("#downloadLink").attr("href","${fastdfsTomcatUrl}api/inter/fastdfs/downloadFile/"+encodeURIComponent(fileName)+"/?url="+url);
            $("#downloadLink")[0].click();

        }

        //////////////////////////////////////批量下载//////////////////////////
        function downloadZip() {

            var $Checks = $("input[name='checkbox']:checked");
            if($Checks.length < 1) {
                alert("请选择你要压缩的文件");
                return;
            }

            var ids = ""
            var split = "_!!!_"; //分隔符
            $Checks.each(function (index, value) {
                var fileName = value.getAttribute("filename");
                var fileUrl = value.getAttribute("url");
                ids = ids +fileUrl+split+fileName +",";
            });

            var str = ids.substring(0,ids.length-1);

            //模拟a标签
            $("#downloadZipLink").attr("href","${fastdfsTomcatUrl}api/inter/fastdfs/downloadFileZip?listFileId="+str);
            $("#downloadZipLink")[0].click();

        }

	</script>
	<script type="text/javascript" src="${ctxStatic}/common/collectionMenu.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="fileFastdfs" action="${ctx}/file/fileFastdfs/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		
			<form:input placeholder="文件名称" path="name" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>
			<form:input placeholder="文件类型" path="type" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>
			<form:input placeholder="文件等级" path="level" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>
			<form:input placeholder="创建人" path="createName" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="file:fileFastdfs:add">
				<table:addRow url="${ctx}/file/fileFastdfs/form?menuId=${menu.id}" title="文件"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="file:fileFastdfs:edit">
			    <table:editRow url="${ctx}/file/fileFastdfs/form" title="文件" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
            <shiro:hasPermission name="file:fileFastdfs:del">
                <table:delRow url="${ctx}/file/fileFastdfs/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
            </shiro:hasPermission>
			<shiro:hasPermission name="file:fileFastdfs:import">
				<table:importExcel url="${ctx}/file/fileFastdfs/import"  menuId="${menu.id}" ></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>

			<button id="review" class="btn btn-success btn-sm"  >预览</button>
			<button id="download"  class="btn btn-success btn-sm" onclick="download()">单个下载</button>
            <button id="downloadZip" class="btn btn-success btn-sm" onclick="downloadZip()">批量压缩下载</button>


        </div>
		<div class="pull-right">
			<button  class="btn btn-success btn-sm" onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>

	<a id="downloadLink" style="" href="" hidden="hidden">单个下载</a>
	<a id="downloadZipLink" style="" href="" hidden="hidden">批量压缩下载</a>

	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column name">文件名称</th>
				<th  class="sort-column size">文件大小</th>
				<th  class="sort-column level">文件等级</th>
				<th  class="sort-column type">文件类型</th>
				<th  class="sort-column group">文件分组</th>
				<th  class="sort-column createBy.id">创建人</th>
				<th  class="sort-column createDate">创建时间</th>
				<th  class="sort-column remarks">备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fileFastdfs">
			<tr>
				<td> <input type="checkbox" id="${fileFastdfs.id}" class="i-checks" name="checkbox" url="${fileFastdfs.url}" filename="${fileFastdfs.name}"></td>
				<td><a  href="#" onclick="openDialogView('查看文件', '${ctx}/file/fileFastdfs/view?id=${fileFastdfs.id}','800px', '500px')">
					${fileFastdfs.name}
				</a></td>
				<td>
					${fileFastdfs.size}
				</td>
				<td>
					${fns:getDictLabel(fileFastdfs.level, 'file_level', '')}
				</td>
				<td>
						${fileFastdfs.type}
					<%--${fns:getDictLabel(fileFastdfs.type, 'file_type', '')}--%>
				</td>
				<td>
					${fns:getDictLabel(fileFastdfs.group, 'file_group', '')}
				</td>
				<td>
					${fileFastdfs.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${fileFastdfs.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fileFastdfs.remarks}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>


