<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>在线用户管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
        });

        function refresh(){//刷新
            window.location="${ctx}/sys/online/list";
        }
    </script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-content">
            <sys:message content="${message}"/>
                <div class="ibox-tools">
                    <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
                </div>
                <br/>
            <!-- 表格 -->
            <table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                <thead>
                <tr>
                    <th>用户</th>
                    <th>用户主机IP</th>
                    <th>登录时间</th>
                    <th>最后访问时间</th>
                    <th>状态</th>
                    <th>User-Agent</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${onlineSessionList}" var="online">
                    <tr>
                        <td>
                            ${online.username}
                        </td>
                        <td>
                                ${online.host}
                        </td>
                        <td>
                            <fmt:formatDate value="${online.startTimestamp}" pattern="yyyy-MM-dd HH:mm:dd"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${online.lastAccessTime}" pattern="yyyy-MM-dd HH:mm:dd"/>
                        </td>
                        <td>
                                ${online.status}
                        </td>
                        <td>
                                ${online.userAgent}
                        </td>
                        <td>
                            <a href="${ctx}/sys/online/forceLogout?id=${online.id}" onclick="return confirmx('确认要踢出此用户吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 踢出</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <br/>
            <br/>
        </div>
    </div>
</div>
</body>
</html>