<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="title" type="java.lang.String" required="true"%>
<%@ attribute name="width" type="java.lang.String" required="false"%>
<%@ attribute name="height" type="java.lang.String" required="false"%>
<%@ attribute name="target" type="java.lang.String" required="false"%>
<%@ attribute name="label" type="java.lang.String" required="false"%>
<button class="btn btn-success btn-sm" data-toggle="tooltip" data-placement="left" onclick="edit()" title="编辑"><i class="fa fa-file-text-o"></i> ${label==null?'编辑':label}</button>
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

	function edit(){
		  var size = $("#${id} tbody tr td input.i-checks:checked").size();
		  if(size == 0 ){
				top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
				return;
			  }

		  if(size > 1 ){
				top.layer.alert('只能选择一条数据!', {icon: 0, title:'警告'});
				return;
			  }
        var str1="";//设备状态
        var types="";
        var materialStatus = "";
        var materialclass = $("#materialclass").val();
        $("#${id} tbody tr td input.i-checks:checkbox").each(function(){
            if(true == $(this).is(':checked')){
                str1+=$(this).attr("auditStatus")+",";
                materialStatus=$(this).attr("materialStatus")
            }
        });
        if(str1.substr(str1.length-1)== ','){
            types = str1.substr(0,str1.length-1);
        }

        if(types!='undefined'&& types!='' && types!=0){
            top.layer.alert('审核过的单据不能进行编辑！！', {icon: 0, title:'警告'});
            return;
        }
        if(materialStatus!='undefined' && materialStatus!='' && materialStatus==0){
            top.layer.alert('报废的单据不能进行编辑！！', {icon: 0, title:'警告'});
            return;
        }

        var id =  $("#${id} tbody tr td input.i-checks:checkbox:checked").attr("id");
        var url ="${url}";
        if(url.indexOf("?") > 0) {
            url += "&id=" + id;
        }else {
            url += "?id=" + id;
        }
        if(materialclass!=''){
            url+="&materialclass="+materialclass;
        }
        var menuId = $("#menuId").val();
        if(menuId!=''){
            url+="&menuId="+menuId;
        }

		    openDialog('${title}'+"编辑",url,"${width==null?'800px':width}", "${height==null?'500px':height}","${target}");
		}
</script>