<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@include file="/webpage/include/treeview.jsp" %>
<%@ attribute name="showPosition" type="java.lang.String" required="true" description="所选内容展示的位置id"%>
<%@ attribute name="insertPosition" type="java.lang.String" required="true" description="存储的位置id"%>
<%@ attribute name="row" type="java.lang.String" required="false" description="内容展示的大小"%>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="内容展示的css样式"%>
<%@ attribute name="ifChooseParent" type="java.lang.String" required="false" description="父标签是否允许选择 Y/N"%>
<%@ attribute name="title" type="java.lang.String" required="false" description="打开的页面标题"%>
<%@ attribute name="treeData" type="java.util.List" required="false" description="树的内容"%>
<%--<%@ attribute name="treeId" type="java.lang.String" required="false" description="树的id"%>--%>
<%--<%@ attribute name="treePId" type="java.lang.String" required="false" description="树的父id"%>--%>
<%--<%@ attribute name="treeType" type="java.lang.String" required="false" description="树的分组类型"%>--%>
<%--<%@ attribute name="treeName" type="java.lang.String" required="false" description="树的名称"%>--%>
<%@ attribute name="expandAll" type="java.lang.String" required="false" description="是否展开树 Y/N"%>




<a id="open"> <font size="5">+</font></a>
<form:textarea id="${showPosition}" path="${showPosition}" htmlEscape="false" rows="${row}"  cssStyle=" ${cssStyle}" readonly="true"  class="form-control"/>
<form:hidden path="${insertPosition}"/>


<div class="modal fade" id="chooseModal" tabindex="-1" role="dialog" aria-labelledby="chooseModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="chooseModalLabel">
                    ${title}
                </h4>
            </div>

            <div class="modal-body " style="width: 600px" >
                <div id="modalTree" class="ztree" style="margin-top:3px;"></div>
            </div>

            <div class="modal-footer">
                <button id="chooseButton" type="button" class="btn btn-success btn-sm">
                    确认
                </button>
            </div>
        </div>
    </div>
</div>


<script  type="text/javascript">

    $(document).ready(function() {


        //点击事件，打开页面
        $("#open").click(function(){
            $('#chooseModal').modal({
                keyboard: true
            });
        });

        $("#chooseButton").click(function choose() {
            console.log("dianji")
            var ids2 = [], nodes2 = tree2.getCheckedNodes(true);

            var roleName = new Map();
            for(var i=0; i<nodes2.length; i++) {
            var node = nodes2[i];
            ids2.push(node.id);
            var type = node.type;
            var name = node.name;


            if(roleName.hasOwnProperty(type)) {
            var temp = roleName[type];
                roleName[type]= temp+"   "+name;
            }else {
                roleName[type]= name;
            }

            }

            var html = "";
            for(var role in roleName){
            console.log(role+"........."+roleName[role]);
            var temp = "【"+role+"】"+ roleName[role]+"\n";
                html+=temp;
            }

            $("#${showPosition}").text(html);
            $("#${insertPosition}").val(ids2);

            $('#chooseModal').modal('hide')
            });

        //树的基本定义
        var setting = {
            check:{
                enable:true,nocheckInherit:true
            },
            view:{
                selectedMulti:false
            },
            data:{
                simpleData:{
                    enable:true
                }
            },
            callback:{
                beforeClick:function(id, node){
                    tree.checkNode(node, !node.checked, true, true);
                    return false;
                },
                //父标签不给选择
                beforeCheck:function (id,node)	 {
                    if(${ifChooseParent eq 'N'}) {
                        return !node.isParent;
                    }else{
                        return node.isParent;
                    }

                }
            }
        };


        var zNodes2=[<c:forEach items="${treeData}" var="data">{id:"${data.id}",
                                                                pId:"${not empty data.sysModules?data.sysModules:0}",
                                                                type:"${data.roleLabel}",
                                                                name:"${data.simpleName}"
                                                                },
                    </c:forEach>
        ];

        // 初始化树结构
        var tree2 = $.fn.zTree.init($("#modalTree"), setting, zNodes2);

        //
        tree2.setting.check.chkboxType = { "Y" : "s", "N" : "s" };

        // 默认选择节点
        var str = "${user.roleIdList}";
        str = str.substring(1,str.length-1);
        var ids2 = str.split(", ");

        for(var i=0; i<ids2.length; i++) {
            var node = tree2.getNodeByParam("id", ids2[i]);
            try{tree2.checkNode(node, true, false);}catch(e){}
        }

        // 默认展开全部节点

        var treeObj = $.fn.zTree.getZTreeObj("modalTree");

        if(${expandAll eq 'Y'}) {
            var nodes = treeObj.getNodes();
            for (var i = 0; i < nodes.length; i++) { //设置节点展开
                treeObj.expandNode(nodes[i], true, false, true);
            }
        }else {
            tree2.expandAll(false);
        }

        //刷新
        $("#modalTree").show();

    });

</script>
