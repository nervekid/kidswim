<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<fmt:setBundle basename="kite" var="kite" />
<fmt:message key="fastdfs.tomcat.url" var="fastdfsTomcatUrl" bundle="${kite}" />
<html>
<head>
    <title>档案回收站</title>
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

            laydate.render({
                elem:'#selectDateStr',
                range:'-'
            });
        });
        
        function deleteFastdfsList() {
            var $Checks = $("input[name='checkbox']:checked");

            if($Checks.length <=0) {
                top.layer.alert('请选择你要删除的档案!', {icon: 0, title:'警告'});
                return;
            }

            var listId = new Array();
            var listUrl = new Array();

            for(var i=0;i<$Checks.length;i++){
                var id = $Checks[i].getAttribute("id");
                listId.push(id)

                var url = $Checks[i].getAttribute("url");
                listUrl.push(url);
            }

            $.ajax({
                url: '${fastdfsTomcatUrl}api/inter/fastdfs/deleteAll',
                type: 'post',
                data:JSON.stringify({
                    list_file_url:listUrl
                }),
                cache: false,
                processData: false,
                contentType: "application/json",
                dataType: "json",
                async: false
            }).done(function(res) {
                //KITE文件删除
                deleteList(listId);
            }).fail(function(res) {
                top.layer.alert("请求失败，删除档案失败",{icon: 0, title:'警告'});
            });

        }


        function deleteList(list) {
            top.layer.confirm('删除档案后将不可恢复，请确认是否继续删除?', {icon: 3, title:'系统提示'}, function(index){
                $.ajax({
                    url: '${ctx}/file/fileRecycle/deleteAll',
                    type: 'post',
                    data:JSON.stringify({
                        listId:list
                    }),
                    cache: false,
                    processData: false,
                    contentType: "application/json",
                    dataType: "json",
                    async: false
                }).done(function(res) {
                    if(res.success) {
                        top.layer.alert("请求成功，档案删除成功",{icon: 1, title:'成功'});
                        window.location.reload();
                    }else {
                        top.layer.alert("请求成功，删除档案失败",{icon: 0, title:'警告'});
                    }
                }).fail(function(res) {
                    top.layer.alert("请求失败，删除档案失败",{icon: 0, title:'警告'});
                });
            });

        }


        function renew() {
            var $Checks = $("input[name='checkbox']:checked");

            if($Checks.length <=0) {
                top.layer.alert('请选择你要恢复的档案!', {icon: 0, title:'警告'});
                return;
            }

            var listId = new Array();

            for(var i=0;i<$Checks.length;i++){
                var id = $Checks[i].getAttribute("id");
                listId.push(id)
            }

            console.log(listId);

            $.ajax({
                url: '${ctx}/file/fileRecycle/renew',
                type: 'post',
                data:JSON.stringify({
                    listId:listId
                }),
                cache: false,
                processData: false,
                contentType: "application/json",
                dataType: "json",
                async: false
            }).done(function(res) {
                if(res.success) {
                    top.layer.alert("请求成功，档案恢复成功",{icon: 1, title:'成功'});
                    window.location.reload();
                }else {
                    top.layer.alert("请求成功，恢复档案失败",{icon: 0, title:'警告'});
                }
            }).fail(function(res) {
                top.layer.alert("请求失败，恢复档案失败",{icon: 0, title:'警告'});
            });

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
                    <form:form id="searchForm" modelAttribute="fileFastdfs" action="${ctx}/file/fileRecycle/recycle" method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
                        <input id="catalogId" name="catalogId" type="hidden" value="${fileFastdfs.catalogId}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">

                            <form:input placeholder="档案名称" path="name" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>
                            <form:input placeholder="档案类型" path="type" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>

                         <%--   <form:select placeholder="文件等级" path="level"  class="form-control m-b" onchange="search()"  style="width:100px;">
                                <form:option value="" label=""/>
                                <form:options items="${fns:getDictList('file_level')}" itemLabel="label"   itemValue="value" htmlEscape="false"/>
                            </form:select>--%>
                            <form:input placeholder="所属人" path="belongName" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>

                            <form:input placeholder="档案创建时间" path="selectDateStr" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>

                        </div>
                    </form:form>
                    <br/>
                </div>
            </div>

            <!-- 工具栏 -->
            <div class="row">
                <div class="col-sm-12">
                    <div class="pull-left">
                        <shiro:hasPermission name="file:recycle:del">
                            <button id="delete" class="btn btn-success btn-sm" onclick="deleteFastdfsList()" >档案删除</button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="file:recycle:renew">
                            <button id="renew" class="btn btn-success btn-sm" onclick="renew()" >档案恢复</button>
                        </shiro:hasPermission>
                    </div>
                    <div class="pull-right">
                        <button  class="btn btn-success btn-sm" onclick="search()" ><i class="fa fa-search"></i> 查询</button>
                        <button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
                    </div>
                </div>
            </div>

            <div style="overflow: auto">
            <!-- 表格 -->
            <table id="contentTable" class="table table-striped text-nowrap table-bordered table-hover table-condensed dataTables-example dataTable">
                <thead>
                <tr>
                    <th> <input type="checkbox" id="parentCheckbox" class="i-checks"></th>
                    <th  class="sort-column a.name">档案名称</th>
                    <th  class="sort-column usu.name">所属人员</th>
                    <th  class="sort-column fc.name">档案类型</th>
                    <th  class="sort-column a.size">档案大小</th>

                    <th  class="sort-column a.type">档案类型</th>

                    <th  class="sort-column csu.name">创建人</th>
                    <th  class="sort-column a.create_date">创建时间</th>
                    <th  class="sort-column csu.name">删除人</th>
                    <th  class="sort-column a.del_date">删除时间</th>
                    <th  class="sort-column a.remarks">备注</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="fileFastdfs">
                    <tr>
                        <td> <input type="checkbox" id="${fileFastdfs.id}" class="i-checks" name="checkbox" url="${fileFastdfs.url}" filename="${fileFastdfs.name}" filetype="${fileFastdfs.type}"></td>
                        <td><a  href="#" onclick="openDialogView('查看档案', '${ctx}/file/fileFastdfs/view?id=${fileFastdfs.id}','800px', '500px')">
                                ${fileFastdfs.name}
                        </a></td>
                        <td>
                                ${fileFastdfs.belongName}
                        </td>
                        <td>
                                ${fileFastdfs.catalogName}
                        </td>
                        <td>
                                ${fileFastdfs.sizeStr}
                        </td>
                        <td>
                                ${fileFastdfs.type}
                        </td>
                        <td>
                                ${fileFastdfs.createBy.name}
                        </td>
                        <td>
                            <fmt:formatDate value="${fileFastdfs.createDate}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td>
                            ${fileFastdfs.delName}
                        </td>
                        <td>
                                <%--${fileFastdfs.delDate}--%>
                            <fmt:formatDate value="${fileFastdfs.delDate}" pattern="yyyy-MM-dd"/>
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
            </div>
            <br/>
            <br/>
        </div>
    </div>
</div>
</body>
</html>