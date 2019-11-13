<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<fmt:setBundle basename="kite" var="kite" />
<fmt:message key="fastdfs.tomcat.url" var="fastdfsTomcatUrl" bundle="${kite}" />
<html>
<head>
    <title>上传文件</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">

        var validateForm;
        var fileList = "";

        function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
            if(validateForm.form()){
                //$("#inputForm").submit();
                console.log("open页面正在提交");
                doUploadList();
                return true;
            }

            return false;
        }
        $(document).ready(function() {
            validateForm = $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    //form.submit();
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

            //////////////////////////////////////////////////////////////////////
                //根据状态设置按钮是某可以点击
            if(${hidden}) {
                $("#logButton").addClass("disabled");
            }

            //根据状态设置文件上传的类型控制

            //如果接口携带目录id和目录名称，以接口的为准
            if(${not empty fileFastdfs.catalogId }) {
                $("#logId").val('${fileFastdfs.catalogId}');
            }

            if(${ not empty fileFastdfs.catalogName}) {
                $("#logName").val('${fileFastdfs.catalogName}');
            }


        });

        //文件共享
        function doUploadList() {

            console.log("doUploadList");

            var paths = $('#fileList')[0].files;

            console.log("文件数量"+paths.length);

            var formData = new FormData();

            for(var i=0;i<paths.length;i++){
                var name = paths[i].name;
                formData.append(name,paths[i]);
            }

            $.ajax({
                url: '${fastdfsTomcatUrl}api/inter/fastdfs/uploadFileList',
                type: 'post',
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
                async: false
            }).done(function(res) {
                /*调用接口存储到kite平台*/
                doUploadKite(res.object);
            }).fail(function(res) {
                top.layer.alert("请求失败，上传文件失败",{icon: 0, title:'警告'});
            });
        }

        //上传文件信息到kite
        function doUploadKite(obj) {
            var json = JSON.stringify(obj)
           // console.log("返回结果："+obj[0].file_id);

            fileList = obj;
            console.log(fileList);

            /*调用接口存储到kite平台*/
            var catalogId = $("#logId").val();
            console.log("目录id："+catalogId);

            $.ajax({
                url: '${ctx}/file/fileFastdfs/insert',
                type: 'post',
                contentType: "application/json",
                dataType: "json",
                data:JSON.stringify({
                    "listFileInfo":obj,
                    "group":${fileFastdfs.group},
                    "catalogId":catalogId,
                }),
                cache: false,
                processData: false,
                async: false
            }).done(function(res) {
                //top.layer.alert("请求成功，文件存储成功",{icon: 1, title:'成功'});

                console.log("上传后返回主键id"+res);

            }).fail(function(res) {
                top.layer.alert("请求失败，存储kite平台失败",{icon: 0, title:'警告'});
            });

        }



    </script>
</head>
<body class="hideScroll">

<form id="inputForm" modelAttribute="fileFastdfs" class="form-horizontal">
    <table class="table table-bordered text-nowrap table-condensed dataTables-example dataTable no-footer">
        <tr>
            <td style="font-size:14px;">请选择文件目录：</td>
            <td><sys:treeselect id="log" name="id" value="" labelName="parentName" labelValue=""
                                title="文件目录" url="/file/fileCatalog/treeData" cssClass="form-control required" cssStyle="width:250px;" />
            </td>
        </tr>
        <tr>
            <td style="font-size:14px;">请选择文件：</td>
            <td>
                <input type="file" id="fileList" name="fileList" multiple />
            </td>
        </tr>

    </table>

</form>
</body>
</html>