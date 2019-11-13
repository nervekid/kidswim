<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="title" type="java.lang.String" required="true"%>
<%@ attribute name="width" type="java.lang.String" required="false"%>
<%@ attribute name="height" type="java.lang.String" required="false"%>
<%@ attribute name="target" type="java.lang.String" required="false"%>
<%@ attribute name="label" type="java.lang.String" required="false"%>
<button class="btn btn-success btn-sm" data-toggle="tooltip" data-placement="left" onclick="materialpublic()" title="设备 公用"> ${label==null?'设备公用':label}</button>
                        </button>
<%-- 使用方法： 1.将本tag写在查询的form之前；2.传入table的id和controller的url --%>
<script type="text/javascript">
$(document).ready(function() {
    $('#${id} thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
    	  $('#${id} tbody tr td input.i-checks').iCheck('check');
    	});

    $('#${id} thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
    	  $('#${id} tbody tr td input.i-checks').iCheck('uncheck');
    	});
    
});


	function materialpublic(){

		// var url = $(this).attr('data-url');
		  var str="";
		  var ids="";
		  
		  var str1="";//设备状态
		  var types="";
		  
		  $("#${id} tbody tr td input.i-checks:checkbox").each(function(){
		    if(true == $(this).is(':checked')){
		      str+=$(this).attr("id")+",";
		      str1+=$(this).attr("status")+",";
		    }
		  });
		  if(str.substr(str.length-1)== ','){
		    ids = str.substr(0,str.length-1);
		  }
		  if(str1.substr(str1.length-1)== ','){
			  types = str1.substr(0,str1.length-1);
		    }
		  if(ids == ""){
			top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
			return;
		  }
		  
		  if(types.indexOf("0")!=-1||types.indexOf("1")!=-1||types.indexOf("2")!=-1){
				top.layer.alert('已报废、借出、在用的设备不能调配为共用！！', {icon: 0, title:'警告'});
				return;
			  }
			top.layer.confirm('确认要公用这些设备吗?', {icon: 3, title:'系统提示'}, function(index){
			window.location = "${url}?ids="+ids;
		    top.layer.close(index);
		});
		 
		}
</script>