<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>选择用户</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        var validateForm;
        function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            if(validateForm.form()){

                //设置formId
                var $obj = $("#contentTable tbody tr td input.i-checks:checked");
                var size = $obj.size();

                if(size == 0 ){
                    top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
                    return;
                }

                var str = "";
                var ids = "";
                var flag = false;
                $obj.each(function(){
                    var value = $(this).attr("id");
                    if(value == "" ){
                        flag = true;
                    }
                    str+=value + ",";
                });

                if(flag) {
                    top.layer.alert('请选择电话号码不为空的数据!', {icon: 0, title:'警告'});
                    return;
                }

                if(str.substr(str.length-1)== ','){
                    ids = str.substr(0,str.length-1);
                }
                console.log("上传的formId"+ids);

                //设置toId
                var idValue = $("#copyId").attr("value");
                console.log("上传的toId"+idValue);

                $("#toId").attr("value",idValue);
                $("#formId").attr("value",ids);

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
                }
            });

            $('#contentTable thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定
                $('#contentTable tbody tr td input.i-checks').iCheck('check');
            });

            $('#contentTable thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定
                $('#contentTable tbody tr td input.i-checks').iCheck('uncheck');
            });

        });
    </script>
</head>
<body>
<div class="wrapper wrapper-content">
    <sys:message content="${message}"/>
    <!-- 查询条件 -->
    <div class="row">
        <div class="col-sm-12">
            <form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/copyList" method="post" class="form-inline">
                <input id="copyId" name="copyId" type="hidden" value="${copyId}">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
                <div class="form-group">

                    <span>登录名：</span>
                    <form:input path="loginName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
                    <span>姓&nbsp;&nbsp;&nbsp;名：</span>
                    <form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
                    <span>归属部门：</span>
                    <sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
                                    title="部门" url="/sys/office/treeData?type=2" cssClass=" form-control input-sm" allowClear="true" notAllowSelectParent="false"/>
                </div>
            </form:form>
            <br/>
        </div>
    </div>

    <!-- 工具栏 -->
    <div class="row">
        <div class="col-sm-12">

            <div class="pull-right">
                <button  class="btn btn-success btn-sm" onclick="search()"><i class="fa fa-search"></i> 查询</button>
                <button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
            </div>
        </div>
    </div>


    <table id="contentTable"  class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
        <thead>
        <tr>
            <th><input type="checkbox" class="i-checks"></th>
            <th class="sort-column login_name">登录名</th>
            <th class="sort-column name">姓名</th>
            <th class="sort-column phone">电话</th>
            <th class="sort-column mobile">手机</th>
            <th class="sort-column c.name">归属公司</th>
            <th class="sort-column o.name">归属部门</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list}" var="user">
            <tr>
                <td> <input type="checkbox" id="${user.phone}" name="userId" class="i-checks"></td>
                <td>${user.loginName}</td>
                <td>${user.name}</td>
                <td>${user.phone}</td>
                <td>${user.mobile}</td>
                <td>${user.company.name}</td>
                <td>${user.office.name}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


    <form:form id="inputForm" modelAttribute="crmPermissionsUser" action="${ctx}/crmreport/crmPermissionsUser/copy" method="post" class="form-horizontal">
        <input id="toId" name="toId" type="hidden" value="${id}"/>
        <input id="formId" name="formId" type="hidden" value=""/>
    </form:form>

    <table:page page="${page}"></table:page>
</div>
</body>
</html>