<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="label" type="java.lang.String" required="false"%>
<%@ attribute name="deleteLimitStatus" type="java.lang.String" required="false"%>

<button class="btn btn-success btn-sm" onclick="deleteAll()" data-toggle="tooltip" data-placement="top"><i class="fa fa-trash-o"> ${label==null?'删除':label}</i>
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

	function deleteAll(){

		// var url = $(this).attr('data-url');
		  var str="";
		  var ids="";
		  $("#${id} tbody tr td input.i-checks:checkbox").each(function(){
		    if(true == $(this).is(':checked')){
		      str+=$(this).attr("id")+",";
		    }
		  });
		  if(str.substr(str.length-1)== ','){
		    ids = str.substr(0,str.length-1);
		  }
		  if(ids == ""){
			top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
			return;
		  }
        var menuId =  $("#menuId").val();

        if(${deleteLimitStatus == null}) {
            //console.log("对应属性为空，跳过删除校验");
        }else {

            var status = '${deleteLimitStatus}';
            //console.log(status);

            //根据每一个status对象展开自己独特的判断和提示
            var info = status.split(":");
            //console.log(info);

            var obj = $("#${id} tbody tr td input.i-checks:checkbox:checked");

            //获取每一组的信息
            var messageList = info[1].split(";");
            //console.log(messageList);

            //遍历所选的信息
            for(var i=0;i<obj.length;i++) {
                var targe =  obj[i].getAttribute(info[0]);
                //console.log(targe);

                //根据每一组的信息来判断
                for(var j=0; j<messageList.length; j++) {
                    var temp = messageList[j].split("_");
                    //console.log(temp);

                    var value = temp[0];
                    var msg = temp[1];

                    if(targe == value) {
                        top.layer.alert(msg+'不能删除!', {icon: 0, title:'警告'});
                        return;
                    }
                }

            }


        }

			top.layer.confirm('确认要彻底删除数据吗?', {icon: 3, title:'系统提示'}, function(index){
			window.location = "${url}?ids="+ids+"&menuId="+menuId;
		    top.layer.close(index);
		});
		 

	}
</script>