<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="按钮id"%> <%--按钮id --%>
<%@ attribute name="name" type="java.lang.String" required="true" description="按钮名称"%> <%--按钮名称 --%>
<%@ attribute name="catalogName" type="java.lang.String" required="true" description="目录名称"%>  <%--目录名称--%>
<%@ attribute name="group" type="java.lang.String" required="true" description="文件分组必填" %>  <%--文件分组 目前为空--%>
<%@ attribute name="hidden" type="java.lang.String" required="false" description="按钮是否隐藏，允许编辑  true false"%>  <%--按钮是否隐藏，允许编辑  true false--%>
<%--<%@ attribute name="writePosition" type="java.lang.String" required="false" description="回写页面的id"%>&lt;%&ndash;回写页面的id&ndash;%&gt;--%>
<%@ attribute name="uploadUrl" type="java.lang.String" required="false" description="上传的URL"%><%--上传的URL--%>
<%@ attribute name="insertPosition" type="java.lang.String" required="false" description="保存的url位置"%><%--上传的URL--%>


<%--界面信息 --%>
<ol id="fileName"></ol>
<button id="${id}Btn" class="btn btn-success btn-sm" onclick="uploadFile()">${name}</button>


<script type="text/javascript">

    $(document).ready(function() {
        console.log("数据初始化");
        //将实体数据填充ol标签

        var fileList = $("#${insertPosition}").val();

        var arr=new Array();
        arr = fileList.split(';');

        var fileNameHtml = '';
        $.each(arr,function(i,file){
            if(file !== null && file !== undefined && file !== '') {
                var temp = new Array();
                temp = file.split(',');

                var path = temp[0];
                var name = temp[1];

                var url = "${uploadUrl}api/inter/fastdfs/downloadFile/"+encodeURIComponent(name)+"/?url="+path;
                var info = "<li><a target='_blank' href="+url+">"+name+"</a>&nbsp;&nbsp;<a id='"+path+"' name='"+name+"' onclick='delFile(this)'>x</a></li>";
                fileNameHtml = fileNameHtml + info;
            }

        });


        $("#fileName").html(fileNameHtml);
    });

    //////////////////////////////获取目录信息////////////////////////////////////
    function uploadFile() {
        console.log("上传");

        //获取目录id
        $.ajax({
            url: '${ctx}/file/fileCatalog/getByCatalogName',
            type: 'post',
            contentType: "application/json",
            dataType: "json",
            data:JSON.stringify({
                "name":'${catalogName}'
            }),
            cache: false,
            processData: false,
            async: false
        }).done(function(res) {
            console.log(res);
            if(res.success) {
                var catalogId = res.data.id;
                //开始上传
                openUploadCatalog(catalogId);
            } else {
                parent.layer.alert("请求成功，获取目录信息失败",{icon: 0, title:'警告'});
            }

        }).fail(function(res) {
            top.layer.alert("请求失败，获取目录信息失败",{icon: 0, title:'警告'});
        });

    }

    //////////////////////////////打开上传界面////////////////////////////////////
    //格式：路径，名称；路径，名称；路径，名称；路径，名称；路径，名称；
    function openUploadCatalog(catalogId) {
        top.layer.open({
            type: 2,
            area: ['450px', '300px'],
            title: '上传文件',
            maxmin: true, //开启最大化最小化按钮
            content: "${ctx}/file/fileFastdfs/open?group=${group}&catalogId="+catalogId+"&catalogName=${catalogName}&hidden=${hidden}",
            btn: ['确定', '关闭'],
            yes: function(index, layero){

                var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();

                console.log("触发后回写");

                if(iframeWin.contentWindow.doSubmit() ){
                    var fileList = iframeWin.contentWindow.fileList;

                    console.log(fileList.length > 0);
                    if(fileList.length > 0) {

                            $.each(fileList,function(i,file){

                                //获取文件信息
                                var path = file.file_id;
                                var name = file.name;

                                var url = "${uploadUrl}api/inter/fastdfs/downloadFile/"+encodeURIComponent(name)+"/?url="+path;

                                //回写页面
                                var info = "<li><a target='_blank' href="+url+">"+name+"</a>&nbsp;&nbsp;<a id='"+path+"' name='"+name+"' onclick='delFile(this)'>x</a></li>";
                                var originalInfo = $('#fileName').html();
                                $('#fileName').html(originalInfo+info);

                                //回写id信息，供页面存储
                                var originalUrl = $("#${insertPosition}").val();
                                var pathName = path+","+name+";";
                                $("#${insertPosition}").val(originalUrl+pathName);

                            });

                    }

                    top.layer.close(index);//关闭对话框。
                    setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
                }

                return;
            },
            cancel: function(index){
            }
        });
    }

    function delFile(obj) {

        console.log(obj.parentNode);

        //删除该标签
        obj.parentNode.parentNode.removeChild(obj.parentNode);

        //删除文件id信息
        var delUrl = obj.getAttribute("id");
        var delName = obj.getAttribute("name");

        //获取对应的数据，并且替代为空
        var pathName = delUrl+","+delName+";";

        var url = $("#${insertPosition}").val();

        var insertUrl = url.replace(pathName, "");

        $("#${insertPosition}").val(insertUrl);

    }
</script>