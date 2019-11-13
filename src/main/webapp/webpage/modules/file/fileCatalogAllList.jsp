<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>文件目录管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/webpage/include/treetable.jsp" %>
    <script type="text/javascript">

        $(document).ready(function() {
            var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
            var data = ${fns:toJson(list)}
            console.log(data);
            var rootId = "${not empty fileCatalog.id ? fileCatalog.id : '0'}";
            console.log(rootId);
            addRow("#treeTableList", tpl, data, rootId, true);
            $("#treeTable").treeTable({expandLevel : 5});
        });

        function addRow(list, tpl, data, pid, root){
            for (var i=0; i<data.length; i++){
                var row = data[i];
                if ((${fns:jsGetVal('row.parentId')}) == pid){
                    $(list).append(Mustache.render(tpl, {
                        dict: {

                        }, pid: (root?0:pid), row: row
                    }));
                    addRow(list, tpl, data, row.id);
                }
            }
        }

        function refresh(){
            window.location="${ctx}/file/fileCatalog/listAll";
        }

    </script>
</head>
<body>
    <div class="wrapper wrapper-content">
        <sys:message content="${message}"/>
        <div class="row">
        <div class="col-sm-12">
        <div class="pull-left">
            <shiro:hasPermission name="file:fileCatalog:add">
                <table:addRow url="${ctx}/file/fileCatalog/form?parent.id=${fileCatalog.id}" title="文件目录" width="800px" height="620px" target="fileCatalogContent"></table:addRow><!-- 增加按钮 -->
            </shiro:hasPermission>
            <button class="btn btn-success btn-sm" data-toggle="tooltip" data-placement="left" onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
        </div>
        </div>
        </div>

        <table id="treeTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
            <thead>
                <tr>
                    <th>文件目录名称</th>
                    <th>上级目录</th>
                    <th>备注</th>
                    <shiro:hasPermission name="file:fileCatalog:edit">
                        <th>操作</th>
                    </shiro:hasPermission>
                </tr>
            </thead>
            <tbody id="treeTableList">

            </tbody>
        </table>
    </div>



    <script type="text/template" id="treeTableTpl">
        <tr id="{{row.id}}" pId="{{pid}}">
            <td><a  href="#" onclick="openDialogView('查看目录', '${ctx}/file/fileCatalog/view?id={{row.id}}','800px', '620px')">{{row.name}}</a></td>
            <td>{{row.parentName}}</td>
            <td>{{row.remarks}}</td>
            <td>
                <shiro:hasPermission name="file:fileCatalog:view">
                    <a href="#" onclick="openDialogView('查看目录', '${ctx}/file/fileCatalog/view?id={{row.id}}','800px', '620px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="file:fileCatalog:edit">
                    <a href="#" onclick="openDialog('修改目录', '${ctx}/file/fileCatalog/form?id={{row.id}}','800px', '620px', 'fileCatalogContent')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="file:fileCatalog:del">
                    <a href="${ctx}/file/fileCatalog/delete?id={{row.id}}" onclick="return confirmx('要删除该目录及所有子目录项吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="file:fileCatalog:add">
                    <a href="#" onclick="openDialog('添加下级目录', '${ctx}/file/fileCatalog/form?parentId={{row.id}}','800px', '620px', 'fileCatalogContent')" class="btn  btn-primary btn-xs"><i class="fa fa-plus"></i> 添加下级目录</a>
                </shiro:hasPermission>
            </td>
        </tr>
    </script>


</body>
</html>
