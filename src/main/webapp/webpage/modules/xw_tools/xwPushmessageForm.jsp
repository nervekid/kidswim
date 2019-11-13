<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>消息推送管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
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
			
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body class="hideScroll">
<form:form id="inputForm" modelAttribute="xwPushmessage" action="${ctx}/xw_tools/xwPushmessage/save" method="post" class="form-horizontal">
	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		<tbody>
		<tr>
			<td  class="width-15 active"><label class="pull-right">标题：</label></td>
			<td class="width-35" ><form:input path="title" htmlEscape="false" maxlength="200" class="form-control required"/></td>
		</tr>
		<tr>
			<td  class="width-15 active">	<label class="pull-right">内容：</label></td>
			<td class="width-35" colspan="3" ><form:textarea path="content" htmlEscape="false" rows="6" maxlength="2000" class="form-control required"/></td>
		</tr>
		<tr>
			<td  class="width-15 active">	<label class="pull-right">接受人：</label></td>
			<td class="width-35" colspan="3"><table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
				<thead>
				<tr>
					<th>接受人</th>
					<th>接受部门</th>
					<th>阅读状态</th>
					<th>阅读时间</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${xwPushmessage.xwPushmessageRecordList}" var="xwPushmessageRecord">
					<tr>
						<td>
								${xwPushmessageRecord.user.name}
						</td>
						<td>
								${xwPushmessageRecord.user.office.name}
						</td>
						<td>
								${fns:getDictLabel(xwPushmessageRecord.readFlag, 'xw_message_status', '')}
						</td>
						<td>
							<fmt:formatDate value="${xwPushmessageRecord.readDate}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
				已查阅：${xwPushmessage.readNum} &nbsp; 未查阅：${xwPushmessage.unReadNum} &nbsp; 总共：${xwPushmessage.readNum + xwPushmessage.unReadNum}</td>
		</tr>
		</tbody>
	</table>
</form:form>
</body>
</html>