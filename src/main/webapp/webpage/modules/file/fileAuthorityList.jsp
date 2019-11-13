<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<fmt:setBundle basename="kite" var="kite" />
<fmt:message key="fastdfs.tomcat.url" var="fastdfsTomcatUrl" bundle="${kite}" />

<html>
<head>
	<title>文件权限管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        $(document).ready(function() {
            var checkBoxChecked ='${checkBoxChecked}';
            if(checkBoxChecked){
                $("#collectionId").attr("checked",true);
            }

            $('#contentTable thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定
                $('#contentTable tbody tr td input.i-checks').iCheck('check');
            });

            $('#contentTable thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定
                $('#contentTable tbody tr td input.i-checks').iCheck('uncheck');
            });


            $("#addRuleBtn").click(function () {

                var $Checks = $("input[name='checkbox']:checked");

                if($Checks.length <=0) {
                    top.layer.alert('请选择要绑定的文件!', {icon: 0, title:'警告'});
                    return;
                }

                var listId = new Array();

                var flag = 0;
                for(var i=0;i<$Checks.length;i++){
                    var id = $Checks[i].getAttribute("id");
                    listId.push(id);

                    var ruleId = $Checks[i].getAttribute("fileRule");
                    if(ruleId != null && ruleId != undefined && ruleId != '') {
                        flag = 1;
					}
                }

                console.log(listId);

				//绑定判断
                if(flag) {
                    top.layer.confirm('存在已经绑定的文件，是否覆盖重新绑定?', {icon: 3, title:'系统提示'},function (index) {
                        addRule(listId);
                        top.layer.close(index);
                    });
                }else {
                    addRule(listId);
				}

            });

        });

        function addRule(listId) {
            console.log("进入绑定");
            openDialog("分配规则","${ctx}/file/fileRule/openRule?listAuthorityId="+listId,"1000px", "800px");
        }


        function uploadFileLevel() {

            var $Checks = $("input[name='checkbox']:checked");

            if($Checks.length <=0) {
                top.layer.alert('请选择要更新的文件!', {icon: 0, title:'警告'});
                return;
            }

            var listId = new Array();

            for(var i=0;i<$Checks.length;i++){
                var id = $Checks[i].getAttribute("fileid");
                listId.push(id)
            }

            console.log(id);

            //校验是否选择状态
            var statusValue = $("#levelValue").val();

            //校验
            if(statusValue == '') {
                top.layer.alert('请选择你要更新的状态!', {icon: 0, title:'警告'});
                return;
            }

            $("#listFileId").val(listId);

            //提交表单
            $("#uploadFileLevelForm").submit();

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
					<form:form id="searchForm" modelAttribute="fileAuthority" action="${ctx}/file/fileAuthority/fileAuthorityList" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
						<input id="catalogId" name="catalogId" type="hidden" value="${fileAuthority.catalogId}"/>
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
						<div class="form-group">

							<form:input placeholder="文件名称" path="fileName" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>

							<form:input placeholder="创建人" path="createFileName" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>

							<form:select placeholder="文件等级" path="level" id="level" class="form-control m-b" onchange="search()"  style="width:100px;">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('file_level')}" itemLabel="label"   itemValue="value" htmlEscape="false"/>
							</form:select>
						</div>
					</form:form>
					<br/>
				</div>
			</div>

			<!-- 工具栏 -->
			<div class="row">
				<div class="col-sm-12">
					<div class="pull-left">

						<shiro:hasPermission name="file:fileFastdfs:edit">
							<table:editRow url="${ctx}/file/fileAuthority/form" title="文件" id="contentTable" width="1000px" height="800px"></table:editRow><!-- 编辑按钮 -->
						</shiro:hasPermission>

						<shiro:hasAnyPermissions name="file:fileAuthority:uploadFileLevel">
							<button id="uploadFileLevelBtn" class="btn btn-success btn-sm"  data-placement="left" data-toggle="modal"  data-target="#uploadFileLevelModal">文件等级修改</button>
						</shiro:hasAnyPermissions>

						<shiro:hasAnyPermissions name="file:fileAuthority:addRule">
							<button id="addRuleBtn" class="btn btn-success btn-sm"  data-placement="left">文件规则绑定</button>
						</shiro:hasAnyPermissions>

					</div>
					<div class="pull-right">
						<button  class="btn btn-success btn-sm" onclick="search()" ><i class="fa fa-search"></i> 查询</button>
						<button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
					</div>
				</div>
			</div>

			<div class="modal fade" id="uploadFileLevelModal" tabindex="-1" role="dialog" aria-labelledby="uploadLabel" aria-hidden="true" >
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
								&times;
							</button>
							<h4 class="modal-title" id="uploadFileLevelLabel">
								文件等级修改
							</h4>
						</div>
						<div class="modal-body " style="width: 400px;" >

							<table style="border:0px">
								<tr>
									<td style="font-size:14px;">请选择等级：</td>
									<td>

										<form:form id="uploadFileLevelForm" modelAttribute="fileAuthority" action="${ctx}/file/fileAuthority/uploadFileLevel" method="POST" class="form-inline" >
											<input id="listFileId" name="listFileId" type="hidden" value=""/>
											<form:select placeholder="文件等级" id="levelValue" path="level"  class="form-control m-b"   style="width:200px;">
												<form:option value="" label=""/>
												<form:options items="${fns:getDictList('file_level')}" itemLabel="label"   itemValue="value" htmlEscape="false"/>
											</form:select>
										</form:form>

									</td>
								</tr>
							</table>
						</div>
						<div class="modal-footer">
							<button id="button" type="button" class="btn btn-success btn-sm" onclick="uploadFileLevel()">
								确认修改
							</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal -->
			</div>




			<!-- 表格 -->
			<div style="overflow:auto;">
			<table id="contentTable" class="table table-striped text-nowrap table-bordered table-hover table-condensed dataTables-example dataTable">
				<thead>
				<tr>
					<th> <input type="checkbox" id="parentCheckbox" class="i-checks"></th>
					<th  class="sort-column ff.NAME">文件名称</th>
					<th  class="sort-column fc.name">文件目录</th>
					<th  class="sort-column su.name">文件创建者</th>
					<th  class="sort-column ff.level">文件等级范围</th>
					<th  class="sort-column a.deptId">部门范围</th>
					<th  class="sort-column a.userId">用户范围</th>
					<th  class="sort-column fr.name">文件权限规则</th>
					<th  class="sort-column a.remarks ">备注</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="fileAuthority">
					<tr>
						<td> <input type="checkbox" id="${fileAuthority.id}" class="i-checks" name="checkbox" fileid="${fileAuthority.fileId}" fileRule="${fileAuthority.ruleId}"></td>
						<td><a  href="#" onclick="openDialogView('查看文件', '${ctx}/file/fileAuthority/view?id=${fileAuthority.id}','800px', '500px')">
								${fileAuthority.fileName}
						</a></td>
						<td>
								${fileAuthority.catalogName}
						</td>
						<td>
								${fileAuthority.createFileName}
						</td>
						<td>
								${fns:getDictLabel(fileAuthority.level, 'file_level', '')}
						</td>
						<td>
								${fileAuthority.listDept}
						</td>
						<td>
								${fileAuthority.listUser}
						</td>
						<td>
								${fileAuthority.ruleName}
						</td>
						<td>
								${fileAuthority.remarks}
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			</div>
			<!-- 分页代码 -->
			<table:page page="${page}"></table:page>
			<br/>
			<br/>
		</div>
	</div>
</div>
</body>
</html>