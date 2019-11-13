<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<fmt:setBundle basename="kite" var="kite" />
<fmt:message key="fastdfs.tomcat.url" var="fastdfsTomcatUrl" bundle="${kite}" />
<fmt:message key="file.preview.type" var="filePreviewType" bundle="${kite}" />


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

            laydate.render({
                elem:'#selectDateStr',
                range:'-'
            });

            $("#openUpload").click(function open(){

                if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，就使用自适应大小弹窗
                    width='auto';
                    height='auto';
                }else{//如果是PC端，根据用户设置的width和height显示。

                }

                top.layer.open({
                    type: 2,
                    area: ['450px', '300px'],
                    title: '上传文件',
                    maxmin: true, //开启最大化最小化按钮
                    content: "${ctx}/file/fileFastdfs/open?group=''",
                    btn: ['确定', '关闭'],
                    yes: function(index, layero){
                        var body = top.layer.getChildFrame('body', index);
                        var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                        var inputForm = body.find('#inputForm');
                        var top_iframe;

                        top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe

                        inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示

                        if(iframeWin.contentWindow.doSubmit() ){
                            top.layer.close(index);//关闭对话框。

                            setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
                        }
                        window.location.reload();

                    },
                    cancel: function(index){
                    }
                });

            });


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

        /////////////////////////////////预览//////////////////////////////////
        function review() {

            var $Checks = $("input[name='checkbox']:checked");

            if($Checks.length > 1) {
                top.layer.alert('只能选择一条数据!', {icon: 0, title:'警告'});
                return;
            } else if($Checks.length <=0) {
                top.layer.alert('请选择你要预览的文件!', {icon: 0, title:'警告'});
                return;
            }

            var ele = $Checks[0];
            var filetype = ele.getAttribute("filetype");

            var filePreviewTypeStr = '${filePreviewType}';
            console.log(filePreviewTypeStr);


            if((filePreviewTypeStr.indexOf(filetype.toUpperCase()))<=-1) {
                top.layer.alert('该文件不支持预览!', {icon: 0, title:'警告'});
                return;
            }



            var url = ele.getAttribute("url");
            console.log(url);

            //替换 /
            var path  = url.replace(/\//g,"_!!!_");

            var fileName = ele.getAttribute("filename");
            console.log(fileName);



            var path = "${fastdfsTomcatUrl}pdfjs/web/viewer.html?file=${fastdfsTomcatUrl}api/inter/fastdfs/reviewFile/"+path + "/"+filetype+"/"+encodeURIComponent(fileName)+"/";
            //a标签模拟下载
            // $("#downloadLink").attr("href",path);
            // $("#downloadLink")[0].click();

            window.open(path,"","titlebar=no,location=no,status=no");

        }

        function reviewChoose(url,fileName,fileType) {

            url = url.replace(/\//g,"_!!!_");

            var path = "${fastdfsTomcatUrl}pdfjs/web/viewer.html?file=${fastdfsTomcatUrl}api/inter/fastdfs/reviewFile/"+url + "/"+fileType+"/"+encodeURIComponent(fileName)+"/";

            window.open(path,"","titlebar=no,location=no,status=no");

        }


        function updateCatalog() {

            var $Checks = $("input[name='checkbox']:checked");

            if($Checks.length <=0) {
                top.layer.alert('请选择你要转移的文件!', {icon: 0, title:'警告'});
                return;
            }

            var listId = new Array();

            for(var i=0;i<$Checks.length;i++){
                var id = $Checks[i].getAttribute("id");
                listId.push(id)
            }

            console.log(listId);

            var catalogId = $("#catalogId").val();
            console.log(catalogId);

            $.ajax({
                url: '${ctx}/file/fileFastdfs/updataCatalog',
                type: 'post',
                data:JSON.stringify({
                    catalogId:catalogId,
                    listId:listId
                }),
                cache: false,
                processData: false,
                contentType: "application/json",
                dataType: "json",
                async: false
            }).done(function(res) {
                if(res.success) {
                    top.layer.alert("请求成功，文件转移成功",{icon: 1, title:'成功'});
                    window.location.reload();
                }else {
                    top.layer.alert("请求成功,"+res.message,{icon: 0, title:'警告'});
                }
            }).fail(function(res) {
                top.layer.alert("请求失败，文件转移失败",{icon: 0, title:'警告'});
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
                    <form:form id="searchForm" modelAttribute="fileFastdfs" action="${ctx}/file/fileFastdfs/listAll" method="post" class="form-inline">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                        <input id="menuId" name="menuId" type="hidden" value="${menu.id}"/>
                        <input id="catalogId" name="catalogId" type="hidden" value="${fileFastdfs.catalogId}"/>
                        <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
                        <div class="form-group">

                            <form:input placeholder="文件名称" path="name" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>

                           <%-- <form:select placeholder="文件等级" path="level"  class="form-control m-b" onchange="search()"  style="width:100px;">
                                <form:option value="" label=""/>
                                <form:options items="${fns:getDictList('file_level')}" itemLabel="label"   itemValue="value" htmlEscape="false"/>
                            </form:select>--%>

                            <form:input placeholder="所属人" path="belongName" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>

                            <form:input placeholder="文件创建时间" path="selectDateStr" htmlEscape="false"  onkeydown="keyDownEnter(event)"  maxlength="200"  class=" form-control input-sm"/>


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
                            <table:editRow url="${ctx}/file/fileFastdfs/form" title="文件" id="contentTable"></table:editRow><!-- 编辑按钮 -->
                        </shiro:hasPermission>
                        <shiro:hasPermission name="file:fileFastdfs:del">
                            <table:delRow url="${ctx}/file/fileFastdfs/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
                        </shiro:hasPermission>


                        <shiro:hasPermission name="file:fileFastdfs:preview">
                            <button id="review" class="btn btn-success btn-sm" onclick="review()" >预览</button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="file:fileFastdfs:download">
                            <button id="download"  class="btn btn-success btn-sm" onclick="download()">单个下载</button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="file:fileFastdfs:downloadZip">
                            <button id="downloadZip" class="btn btn-success btn-sm" onclick="downloadZip()">批量压缩下载</button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="file:fileFastdfs:upload">
                            <button id="openUpload" class="btn btn-success btn-sm">文件共享</button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="file:fileFastdfs:updataCatalog">
                            <button id="updateCatalogBtn" class="btn btn-success btn-sm" data-placement="left" data-toggle="modal"  data-target="#updateCatalogModal">文件转移</button>
                        </shiro:hasPermission>

                        <a id="reviewLink" style="" href="" hidden="hidden" target="_blank">预览</a>
                        <a id="downloadLink" style="" href="" hidden="hidden">单个下载</a>
                        <a id="downloadZipLink" style="" href="" hidden="hidden">批量压缩下载</a>

                    </div>
                    <div class="pull-right">
                        <button  class="btn btn-success btn-sm" onclick="search()" ><i class="fa fa-search"></i> 查询</button>
                        <button  class="btn btn-success btn-sm" onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
                    </div>
                </div>
            </div>


            <div class="modal fade" id="updateCatalogModal" tabindex="-1" role="dialog" aria-labelledby="uploadLabel" aria-hidden="true" >
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="modal-title" id="updateCatalogLabel">
                                文件转移
                            </h4>
                        </div>
                        <div class="modal-body " style="width: 400px;" >

                            <table style="border:0px">
                                <tr>
                                    <td style="font-size:14px;">请选择文件目录：</td>
                                    <td><sys:treeselect id="catalog" name="id" value="" labelName="parentName" labelValue=""
                                                        title="文件目录" url="/file/fileCatalog/treeData" cssClass="form-control required" />
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button id="uploadFileBtn" type="button" class="btn btn-success btn-sm" onclick="updateCatalog()">
                                确认转移
                            </button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>



            <!-- 表格 -->
            <table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                <thead>
                <tr>
                    <th> <input type="checkbox" class="i-checks"></th>
                    <th  class="sort-column a.name">文件名称</th>
                    <th  class="sort-column a.size">文件大小</th>
                    <th  class="sort-column a.type">文件类型</th>
                    <th  class="sort-column usu.name">所属人员</th>
                    <th  class="sort-column a.create_date">创建时间</th>
                    <th  class="sort-column a.remarks">备注</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="fileFastdfs">
                    <tr>
                        <td> <input type="checkbox" id="${fileFastdfs.id}" class="i-checks" name="checkbox" url="${fileFastdfs.url}" filename="${fileFastdfs.name}" filetype="${fileFastdfs.type}"></td>
                        <td><a  href="#" onclick="reviewChoose('${fileFastdfs.url}','${fileFastdfs.name}','${fileFastdfs.type}')">
                                ${fileFastdfs.name}
                        </a></td>

                        <td>
                                ${fileFastdfs.sizeStr}
                        </td>

                        <td>
                                ${fileFastdfs.type}
                        </td>

                        <td>
                                ${fileFastdfs.belongName}
                        </td>
                        <td>
                            <fmt:formatDate value="${fileFastdfs.createDate}" pattern="yyyy-MM-dd"/>
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