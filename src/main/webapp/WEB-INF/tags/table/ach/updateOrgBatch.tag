<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<script type="text/javascript">
$(document).ready(function() {
		laydate.render({
	        elem: '#yearmonth',
	        format: 'yyyyMM',
	        type: 'month'
	    });
});
</script>


<button id="updateBatch" class="btn btn-success btn-sm" data-toggle="tooltip" data-placement="left" title="更新组织架构">更新组织架构</button>
<div id="updateBatchBox" class="hide">
	<span style="font-size:17px;float:left;margin-top:2px;">请选择更新年月：</span>
	<input id="yearmonth" name="yearmonth" type="text" length="10" style="width:150px;" class="laydate-icon form-control layer-date required"/>
</div>
<script src="${ctxStatic}/laydate/laydate.js"></script>
<script type="text/javascript">
$(document).ready(function() {

	$("#updateBatch").click(function(){
		top.layer.open({
		    type: 1,
		    area: [500, 300],
		    title:"更新组织架构",
		    content:$("#updateBatchBox").html() ,
		    btn: ['确定', '关闭'],
		    btn1: function(index, layero){
		    	var ymValue = $("#yearmonth").val();
		    	$.ajax({
					//导入校验数据,错误提示
	    			url: "${url}?ym="+ymValue,
					dataType: 'json',
					method: 'POST',
					success: function(data) {
						},
					error: function(xhr) {
						}
					});

			  },
			  btn3: function(index){
				  top.layer.close(index);
    	       }
		});
	});

});
</script>
