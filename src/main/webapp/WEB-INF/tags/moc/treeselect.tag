<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）"%>
<%@ attribute name="labelName" type="java.lang.String" required="true" description="输入框名称（Name）"%>
<%@ attribute name="labelValue" type="java.lang.String" required="true" description="输入框值（Name）"%>
<%@ attribute name="title" type="java.lang.String" required="true" description="选择框标题"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="树结构数据地址"%>
<%@ attribute name="checked" type="java.lang.Boolean" required="false" description="是否显示复选框，如果不需要返回父节点，请设置notAllowSelectParent为true"%>
<%@ attribute name="extId" type="java.lang.String" required="false" description="排除掉的编号（不能选择的编号）"%>
<%@ attribute name="isAll" type="java.lang.Boolean" required="false" description="是否列出全部数据，设置true则不进行数据权限过滤（目前仅对Office有效）"%>
<%@ attribute name="notAllowSelectRoot" type="java.lang.Boolean" required="false" description="不允许选择根节点"%>
<%@ attribute name="notAllowSelectParent" type="java.lang.Boolean" required="false" description="不允许选择父节点"%>
<%@ attribute name="module" type="java.lang.String" required="false" description="过滤栏目模型（只显示指定模型，仅针对CMS的Category树）"%>
<%@ attribute name="selectScopeModule" type="java.lang.Boolean" required="false" description="选择范围内的模型（控制不能选择公共模型，不能选择本栏目外的模型）（仅针对CMS的Category树）"%>
<%@ attribute name="allowClear" type="java.lang.Boolean" required="false" description="是否允许清除"%>
<%@ attribute name="allowInput" type="java.lang.Boolean" required="false" description="文本框可填写"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="smallBtn" type="java.lang.Boolean" required="false" description="缩小按钮显示"%>
<%@ attribute name="hideBtn" type="java.lang.Boolean" required="false" description="是否显示按钮"%>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="是否限制选择，如果限制，设置为disabled"%>
<%@ attribute name="dataMsgRequired" type="java.lang.String" required="false" description=""%>
<%@ attribute name="yearMonth" type="java.lang.String" required="false" description="年月"%>
<%@ attribute name="tipStr" type="java.lang.String" required="false" description="提示"%>
<%@ attribute name="showNum" type="java.lang.Boolean" required="false"  description="是否显示企业编码"%>
<%@ attribute name="setIdValue" type="java.lang.String" required="false" description="需要填充ID单元格"%>
<%@ attribute name="setNumberValue" type="java.lang.String" required="false" description="需要填充编码单元格"%>
<%@ attribute name="setBusinessTypeValue" type="java.lang.String" required="false" description="需要填充业务类型单元格"%>
<%@ attribute name="setPayTypeValue" type="java.lang.String" required="false" description="需要填充付费类型单元格"%>
<%@ attribute name="setBusinessTypeNameValue" type="java.lang.String" required="false" description="需要填充业务类型中文单元格"%>
<%@ attribute name="setPayTypeNameValue" type="java.lang.String" required="false" description="需要填充付费类型中文单元格"%>
<%@ attribute name="setCustomerNameValue" type="java.lang.String" required="false" description="需要填充客户名称中文单元格"%>
<%@ attribute name="otherKey" type="java.lang.String" required="false" description="自定义的KEY"%>
<%@ attribute name="otherValue" type="java.lang.String" required="false" description="自定义的VALUE"%>
<%@ attribute name="otherKey2" type="java.lang.String" required="false" description="自定义的KEY2"%>
<%@ attribute name="otherValue2" type="java.lang.String" required="false" description="自定义的VALUE2"%>
<%@ attribute name="otherKey3" type="java.lang.String" required="false" description="自定义的KEY3"%>
<%@ attribute name="otherValue3" type="java.lang.String" required="false" description="自定义的VALUE3"%>

<input id="${id}Id" name="${name}" class="${cssClass}" type="hidden" value="${value}"/>
<div class="input-group">
	<input id="${id}Name" placeholder="${tipStr}" name="${labelName}" ${allowInput?'':'readonly="readonly"'}  type="text" value="${labelValue}" data-msg-required="${dataMsgRequired}"
		   class="${cssClass}" style="${cssStyle}"/>
	<span class="input-group-btn">
	       		 <button type="button"  id="${id}Button" class="btn btn-success <c:if test="${fn:contains(cssClass, 'input-sm')}"> btn-sm </c:if><c:if test="${fn:contains(cssClass, 'input-lg')}"> btn-lg </c:if>  btn-primary ${disabled} ${hideBtn ? 'hide' : ''}"><i class="fa fa-search"></i>
	             </button>
       		 </span>

</div>
<label id="${id}Name-error" class="error" for="${id}Name" style="display:none"></label>
<script type="text/javascript">
    $("#${id}Button, #${id}Name").click(function(){
        // 是否限制选择，如果限制，设置为disabled
        if ($("#${id}Button").hasClass("disabled")){
            return true;
        }
        var yearmonth="${yearMonth}";
        var yearmonthValue=$(yearmonth).val();

       if(yearmonthValue == '') {
           top.layer.msg("请选择年月信息", {icon: 0});
           return false;
       }

        // 正常打开
        top.layer.open({
            type: 2,
            area: ['300px', '420px'],
            title:"选择${title}",
            ajaxData:{selectIds: $("#${id}Id").val()},
            content: "${ctx}/tag/treeselectAch?url="+encodeURIComponent("${url}")+"&module=${module}&checked=${checked}&extId=${extId}&isAll=${isAll}&yearMonth="+yearmonthValue ,
            btn: ['确定', '关闭']
            ,yes: function(index, layero){ //或者使用btn1
                var tree = layero.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
                var ids = [], names = [], nodes = [];
                if ("${checked}" == "true"){
                    nodes = tree.getCheckedNodes(true);
                }else{
                    nodes = tree.getSelectedNodes();
                }
                console.log(nodes.length+"changdu");
                for(var i=0; i<nodes.length; i++) {//<c:if test="${checked && notAllowSelectParent}">
                    if (nodes[i].isParent){
                        continue; // 如果为复选框选择，则过滤掉父节点
                    }//</c:if><c:if test="${notAllowSelectRoot}">
                    if (nodes[i].level == 0){
                        //top.$.jBox.tip("不能选择根节点（"+nodes[i].name+"）请重新选择。");
                        top.layer.msg("不能选择根节点（"+nodes[i].name+"）请重新选择。", {icon: 0});
                        return false;
                    }//</c:if><c:if test="${notAllowSelectParent}">
                    if (nodes[i].isParent){
                        //top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
                        //layer.msg('有表情地提示');
                        top.layer.msg("不能选择父节点（"+nodes[i].name+"）请重新选择。", {icon: 0});
                        return false;
                    }//</c:if><c:if test="${not empty module && selectScopeModule}">
                    if (nodes[i].module == ""){
                        //top.$.jBox.tip("不能选择公共模型（"+nodes[i].name+"）请重新选择。");
                        top.layer.msg("不能选择公共模型（"+nodes[i].name+"）请重新选择。", {icon: 0});
                        return false;
                    }else if (nodes[i].module != "${module}"){
                        //top.$.jBox.tip("不能选择当前栏目以外的栏目模型，请重新选择。");
                        top.layer.msg("不能选择当前栏目以外的栏目模型，请重新选择。", {icon: 0});
                        return false;
                    }//</c:if>
                    ids.push(nodes[i].id);
                    var showNum=${showNum};
                    var aaa;
                    if (showNum!= true) {
                        aaa = nodes[i].name;
                    }else {
                        aaa = nodes[i].name + '(' + nodes[i].num + ')';
                    }

                    names.push(aaa);

                    var ifsetIdValue = '${setIdValue}';
                    console.log(ifsetIdValue);
                    if(ifsetIdValue != '') {
                       var $setIdValue = $("#"+ifsetIdValue);
                       console.log($setIdValue);
                        var id = nodes[i].idnum;
                        console.log("id:"+id);
                       $setIdValue.val(id);
                    }

                    var ifsetNumberValue = '${setNumberValue}';
                    if(ifsetNumberValue != '') {
                        var $setNumberValue =  $("#"+ifsetNumberValue);
                        var num = nodes[i].num;
                        console.log("num:"+num);
                        $setNumberValue.val(num);
					}

                    var ifsetBusinessTypeValue = '${setBusinessTypeValue}';
                    if(ifsetBusinessTypeValue != '') {
                        var $setBusinessTypeValue =  $("#"+ifsetBusinessTypeValue);
                        var businessType = nodes[i].businessType;
                        console.log("businessType:"+businessType);
                        $setBusinessTypeValue.val(businessType);
                    }

                    var ifsetPayTypeValue = '${setPayTypeValue}';
                    if(ifsetPayTypeValue != '') {
                        var $setPayTypeValue =  $("#"+ifsetPayTypeValue);
                        var payType = nodes[i].payType;
                        console.log("payType:"+payType);
                        $setPayTypeValue.val(payType);
                    }

                    var ifsetBusinessTypeNameValue = '${setBusinessTypeNameValue}';
                    if(ifsetBusinessTypeNameValue != '') {
                        var $setBusinessTypeNameValue =  $("#"+ifsetBusinessTypeNameValue);
                        var businessTypeName = nodes[i].businessTypeName;
                        console.log("businessTypeName:"+businessTypeName);
                        $setBusinessTypeNameValue.val(businessTypeName);
                    }

                    var ifsetPayTypeNameValue = '${setPayTypeNameValue}';
                    if(ifsetPayTypeNameValue != '') {
                        var $setPayTypenameValue =  $("#"+ifsetPayTypeNameValue);
                        var payTypeName = nodes[i].payTypeName;
                        console.log("payTypeName:"+payTypeName);
                        $setPayTypenameValue.val(payTypeName);
                    }

                    var ifsetCustomerNameValue = '${setCustomerNameValue}';
                    if(ifsetCustomerNameValue != '') {
                        var $setCustomerNameValue =  $("#"+ifsetCustomerNameValue);
                        var customerName = nodes[i].customerName;
                        console.log("customerName:"+customerName);
                        $setCustomerNameValue.val(customerName);
                    }

					//自定义
					var otherKey = '${otherKey}';
					var otherValue = '${otherValue}';
					console.log("otherKey="+otherKey);
                    console.log("otherValue="+otherValue);
                    if(otherKey != '' && otherValue != '') {
                        var $otherKey =  $("#"+otherKey);
                        for(var prop in nodes[i]) {
                            if(prop == otherValue ) {
                                $otherKey.val(nodes[i][prop]);
                            }

                        }

                    }

                    //自定义
                    var otherKey2 = '${otherKey2}';
                    var otherValue2 = '${otherValue2}';
                    console.log("otherKey2="+otherKey2);
                    console.log("otherValue2="+otherValue2);
                    if(otherKey2 != '' && otherValue2 != '') {
                        var $otherKey2 =  $("#"+otherKey2);
                        console.log("111"+$otherKey2.val());
                        for(var prop in nodes[i]) {
                            if(prop == otherValue2 ) {
                                $otherKey2.val(nodes[i][prop]);
                            }
                        }
                    }

                    //自定义
                    var otherKey3 = '${otherKey3}';
                    var otherValue3 = '${otherValue3}';
                    console.log("otherKey3="+otherKey3);
                    console.log("otherValue3="+otherValue3);
                    if(otherKey3 != '' && otherValue3 != '') {
                        var $otherKey3 =  $("#"+otherKey3);
                        console.log("111"+$otherKey3.val());
                        for(var prop in nodes[i]) {
                            if(prop == otherValue3 ) {
                                $otherKey3.val(nodes[i][prop]);
                            }
                        }
                    }

                    //<c:if test="${!checked}">
                    break; // 如果为非复选框选择，则返回第一个选择  </c:if>
                }
                $("#${id}Id").val(ids.join(",").replace(/u_/ig,""));
                $("#${id}Name").val(names.join(","));
                $("#${id}Name").focus();
                top.layer.close(index);
            },
            cancel: function(index){ //或者使用btn2
                //按钮【按钮二】的回调
            }
        });

    });
</script>