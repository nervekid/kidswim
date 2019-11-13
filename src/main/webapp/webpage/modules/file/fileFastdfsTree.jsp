<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>文件目录管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/webpage/include/treeview.jsp" %>
    <style type="text/css">
        .ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
    </style>
    <script type="text/javascript">
        function refresh(){//刷新
            window.location="${ctx}/file/fileFastdfs/catalogFile";
        }
    </script>
</head>


<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox">
        <div class="ibox-content">
            <sys:message content="${message}"/>

            <div id="content" class="row">
                <div id="left" style="background-color:#e7eaec" class="col-sm-1">
                    <a onclick="refresh()" class="pull-right">
                        <i class="fa fa-refresh"></i>
                    </a>
                    <div id="ztree" class="ztree"></div>
                </div>

                <div id="right"  class="col-sm-11  animated fadeInRight">
                        <iframe id="fileContent" name="fileContent" src="${ctx}/file/fileFastdfs/listAll?id=0" width="100%" height="91%" frameborder="0"></iframe>
                 </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">

    var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
        callback:{onClick:function(event, treeId, treeNode){
                var id = treeNode.id == '0' ? '' :treeNode.id;
                $('#fileContent').attr("src","${ctx}/file/fileFastdfs/listAll?catalogId="+id);
            }
        }
    };

    function refreshTree(){
        $.getJSON("${ctx}/file/fileCatalog/treeData",function(data){
            $.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
        });
    }

    refreshTree();

    var leftWidth = 220; // 左侧窗口大小
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
