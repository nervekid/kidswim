<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="materialclass" type="java.lang.String" required="false"%>
<%@ attribute name="menuId" type="java.lang.String" required="false"%>
<%@ attribute name="title" type="java.lang.String" required="false"%>
<%-- 使用方法： 1.将本tag写在查询的form之前；2.传入controller的url --%>
<button id="btnImport" class="btn btn-success btn-sm " data-toggle="tooltip" data-placement="left" title="导入">
<i class="fa fa-folder-open-o"></i>
	<c:choose>
		<c:when test="${empty title}">
			导入
		</c:when>
		<c:otherwise>
			${title}
		</c:otherwise>
	</c:choose>
</button>

<div id="importBox" class="hide">
		<form id="importForm" action="${url}" method="post" enctype="multipart/form-data"
			 style="padding-left:20px;text-align:center;" ><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
			<input type="hidden" name="materialclass" value="${materialclass}">
			<input type="hidden" name="menuId" value="${menuId}">
		</form>

</div>
<script src="${ctxStatic}/jquery/jquery-form.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#btnImport").click(function(){
		top.layer.open({
		    type: 1,
		    area: [500, 300],
		    title:"导入数据",
		    content:$("#importBox").html() ,
		    btn: ['下载模板','确定', '关闭'],
			    btn1: function(index, layero){
				  window.location.href='${url}/template';
			  },
		    btn2: function(index, layero){
		    	formSubmit(index);
			  },

			  btn3: function(index){
				  top.layer.close(index);
    	       }
		});
	});

	function formSubmit(index) {
		var inputForm =top.$("#importForm");
        var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe
        inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
   		top.$("#importForm").submit();
   		top.layer.close(index);
	    $('#importForm').ajaxSubmit({
	    	success:function(){
	    		$.ajax({
					//导入校验数据,错误提示
	    			url: "${url}/tip",
					dataType: 'json',
					method: 'POST',
					success: function(data) {
						if (data.isAlert == '1') {
							showTip("导入失败,数据校验失败,请下载错误原因Excel", "error", "3000");
							}
						},
					error: function(xhr) {
						}
					});
	        },
            error:function(){
            }
	    });
        return false;
	}

});

</script>