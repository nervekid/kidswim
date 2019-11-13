$("#uploadfile").fileinput({
    language: 'zh', //设置语言
    /*uploadUrl: "/apk_upload", //上传的地址*/
    allowedFileExtensions: ["jpg", "png","gif","word","bmp","pic","tif","pdf","xls","xlsx","txt","doc"],//接收的文件后缀
    uploadAsync: false, //默认异步上传
    showUpload: false, //是否显示上传按钮

    showRemove : true, //显示移除按钮
    showPreview : true, //是否显示预览
    showCaption: false,//是否显示标题
    browseClass: "btn btn-primary", //按钮样式
    dropZoneEnabled: false,//是否显示拖拽区域
    maxFileCount: 1, //表示允许同时上传的最大文件个数
    maxFileSize : 2000/*,
    enctype: 'multipart/form-data',
    validateInitialCount:true*/
});
//异步上传返回结果处理
/*$("#uploadfile").on("fileuploaded", function (event, data, previewId, index) {
    var response = data.response;
    alert(response.filePath);
    $("#fileUrl").val(response.filePath);
});*/
//上传前

/*
$('#uploadfile').on('filepreupload', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
});*/
