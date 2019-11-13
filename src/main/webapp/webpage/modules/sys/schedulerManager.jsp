<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>   
<head>
    <title>定时器管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/scheduler/dashboard.js"></script>
<script type="text/javascript">
function schedulerOperate(operate){
	location.href = operate; 
}

function triggerOperate(operate){
	location.href = operate;
}
</script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
<div class="ibox-content">
    <div class="row">
        <div class="col-sm-12 m-b-xs">
            定时器状态：
            <c:if test="${!scheduler.inStandbyMode && scheduler.started}">
                <span class="running">运行中……</span>
                <button type="button" class="btn btn-danger" onclick="schedulerOperate('${ctx}/sys/scheduler/stop')">停止</button>
            </c:if>
            <c:if test="${scheduler.inStandbyMode || !scheduler.started}">
                <span class="stoped">停止中……</span>
                <button type="button" class="btn btn-primary" onclick="schedulerOperate('${ctx}/sys/scheduler/run')">启动</button>
            </c:if>
            &nbsp;&nbsp;
            <button type="button" class="btn btn-success" onclick="location.href='${ctx}/sys/scheduler/list'">刷新</button>
            &nbsp;&nbsp;
            <span class="glyphicon glyphicon-refresh" />
            当前时间：
            <span id="time">  <fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
        </div>
    </div>

        <c:forEach items="${tiggerGroups}" var="group">
         <div class="panel-heading">
            <span class="glyphicon glyphicon-cog"></span>组名:${group.groupName}
        </div>

            <table id="contentTable" class="table table-striped table-bordered  table-hover table-condensed  dataTables-example dataTable no-footer">
            <thead>
                <tr>
                    <th>任务名</th>
                    <th>描述</th>
                    <th>周期表达式</th>
                    <th>上一次运行时间</th>
                    <th>下一次运行时间</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${group.triggerModels}" var="triggerModel">
                <tr>
                    <td>${triggerModel.trigger.name}</td>
                    <td>${triggerModel.trigger.description}</td>
                    <td>${triggerModel.trigger.cronExpression}</td>
                    <td>
                        <fmt:formatDate value="${triggerModel.trigger.previousFireTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                    <td>
                        <fmt:formatDate value="${triggerModel.trigger.nextFireTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                    <td>
                        <c:if test="${triggerModel.status == 1}"> <font color="green">正常</font>
                        </c:if>
                        <c:if test="${triggerModel.status == 2}">暂停</c:if>
                       <%-- <c:if test="${triggerModel.status == 1}">暂停</c:if>
                        <c:if test="${triggerModel.status == 2}">完成</c:if>
                        <c:if test="${triggerModel.status == 3}"> <font color="red">错误</font>
                        </c:if>
                        <c:if test="${triggerModel.status == 4}">
                            <font color="red">阻塞</font>
                        </c:if>
                        <c:if test="${triggerModel.status == -1}">无</c:if>--%>
                    </td>
                    <td>
                        <c:if test="${triggerModel.status == 2}">
                            <button type="button" class="btn btn-success btn-xs" onclick="triggerOperate('${ctx}/sys/scheduler/resumeTrigger?name=${triggerModel.trigger.name}&group=${group.groupName}')">启动</button>
                        </c:if>
                        <c:if test="${triggerModel.status != 2}">
                            <button type="button" class="btn btn-danger btn-xs" onclick="triggerOperate('${ctx}/sys/scheduler/pauseTrigger?name=${triggerModel.trigger.name}&group=${group.groupName}')">暂停</button>
                        </c:if>
                        <button type="button" class="btn btn-warning btn-xs" onclick="triggerOperate('${ctx}/sys/scheduler/triggerTrigger?name=${triggerModel.trigger.name}&group=${group.groupName}')">立即启动</button>
                        &nbsp;
                       <%-- <a role="button" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#editModal" onclick="remoteUrl('${ctx}/sys/schedulerManager/editTrigger?name=${triggerModel.trigger.name}&group=${group.groupName}')">编辑</a>--%>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:forEach>
    </div>
    <div class="modal inmodal fade" id="editModal" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">任务编辑：</h4>
                </div>
                <div class="modal-body">
                    <p><strong>常用表达式，</strong> <a href="http://www.jeasyuicn.com/cron/" target="_blank">在线cron表达式生成器</a></p>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="saveTrigger('${ctx}/sys/schedulerManager/saveTrigger')">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>