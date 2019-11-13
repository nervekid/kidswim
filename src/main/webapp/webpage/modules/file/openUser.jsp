<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>分配用户</title>
    <meta name="decorator" content="default"/>
</head>
<body>

<div class="wrapper wrapper-content">
    <sys:message content="${message}"/>
    <div class="breadcrumb">
        <form id="form" action="${ctx}/file/fileAuthority/updateUser" method="post" class="hide">
            <input id="idsArr" type="hidden" name="idsArr" value=""/>
            <input id="id" type="hidden" name="id" value=""/>
        </form>
        <button id="addUserButton" type="submit"  class="btn btn-outline btn-primary btn-sm" title="添加人员"><i class="fa fa-plus"></i> 添加人员</button>
        <script type="text/javascript">
            $("#addUserButton").click(function(){

                top.layer.open({
                    type: 2,
                    area: ['800px', '600px'],
                    title:"选择用户",
                    maxmin: true, //开启最大化最小化按钮
                    content: "${ctx}/file/fileAuthority/listTree?id=${fileAuthority.id}" ,
                    btn: ['确定', '关闭'],
                    yes: function(index, layero){
                        var pre_ids = layero.find("iframe")[0].contentWindow.pre_ids;
                        var ids = layero.find("iframe")[0].contentWindow.ids;
                        if(ids[0]==''){
                            ids.shift();
                            pre_ids.shift();
                        }
                        if(pre_ids.sort().toString() == ids.sort().toString()){
                            top.$.jBox.tip("未给该权限分配新成员！", 'info');
                            return false;
                        };
                        // 执行保存
                        loading('正在提交，请稍等...');
                        var idsArr = "";
                        for (var i = 0; i<ids.length; i++) {
                            idsArr = (idsArr + ids[i]) + (((i + 1)== ids.length) ? '':',');
                        }
                        $('#idsArr').val(idsArr);

                        $('#id').val('${fileAuthority.id}');

                        $('#form').submit();

                        top.layer.close(index);
                    },
                    cancel: function(index){
                    }
                });
            });
        </script>
    </div>
    <table id="contentTable" class="table text-nowrap table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
        <thead><tr><th>归属公司</th><th>归属部门</th><th>登录名</th><th>姓名</th><th>手机</th><th>操作</th></tr></thead>
        <tbody>
        <c:forEach items="${userList}" var="user">
            <tr>
                <td>${user.company.name}</td>
                <td>${user.office.name}</td>
                <td>${user.loginName}</td>
                <td>${user.name}</td>
                <td>${user.mobile}</td>
                <td>
                    <a href="${ctx}/file/fileAuthority/deleteUser?userId=${user.id}&id=${fileAuthority.id}"
                       onclick="return confirmx('确认要移除该用户吗？', this.href)">移除</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
