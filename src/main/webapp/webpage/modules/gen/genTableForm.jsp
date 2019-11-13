<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>

<html>
<head>
    <title>表单管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/jquery-combox/jquery.combox.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/jquery-combox/styles/style.css" type="text/css"/>
    <script type="text/javascript">
        var validateForm;

        function doSubmit() {
            return validateForm.form() ? ($("#inputForm").submit(), !0) : !1
        };$(document).ready(function () {
            validateForm = $("#inputForm").validate({
                ignore: "", submitHandler: function (a) {
                    loading("正在提交，请稍等...");
                    $("input[type=checkbox]").each(function () {
                        $(this).after('<input type="hidden" name="' + $(this).attr("name") + '" value="' + ($(this).attr("checked") ? "1" : "0") + '"/>');
                        $(this).attr("name", "_" + $(this).attr("name"))
                    });
                    a.submit()
                }, errorContainer: "#messageBox", errorPlacement: function (a, b) {
                    $("#messageBox").text("输入有误，请先更正。");
                    b.is(":checkbox") || b.is(":radio") || b.parent().is(".input-append") ? a.appendTo(b.parent().parent()) : a.insertAfter(b)
                }
            });
            resetColumnNo();
            $("#tableType").change(function () {
                "3" == $("#tableType").val() ? addForTreeTable() : removeForTreeTable()
            });
            var b, c;
            $("#contentTable1").tableDnD({
                onDragClass: "myDragClass", onDrop: function (a, d) {
                    c = $(d).index();
                    var f = $("#tab-2 #contentTable2 tbody tr:eq(" + c + ")"),
                        e = $("#tab-2 #contentTable2 tbody tr:eq(" + b + ")");
                    b < c ? e.insertAfter(f) : e.insertBefore(f);
                    f = $("#tab-3 #contentTable3 tbody tr:eq(" + c + ")");
                    e = $("#tab-3 #contentTable3 tbody tr:eq(" + b + ")");
                    b < c ? e.insertAfter(f) : e.insertBefore(f);
                    f = $("#tab-4 #contentTable4 tbody tr:eq(" + c + ")");
                    e = $("#tab-4 #contentTable4 tbody tr:eq(" + b + ")");
                    b < c ? e.insertAfter(f) : e.insertBefore(f);
                    resetColumnNo()
                }, onDragStart: function (a, c) {
                    b = $(c).index()
                }
            })
        });

        function resetColumnNo() {
            $("#tab-4 #contentTable4 tbody tr").each(function (b, c) {
                $(this).find("span[name*=columnList],select[name*=columnList],input[name*=columnList]").each(function () {
                    var a = $(this).attr("name"), c = a.split(".")[1], c = "columnList[" + b + "]." + c;
                    $(this).attr("name", c);
                    0 <= a.indexOf(".sort") && ($(this).val(b), $(this).next().text(b))
                });
                $(this).find("label[id*=columnList]").each(function () {
                    var a = $(this).attr("id").split(".")[1], a = "columnList[" + b + "]." + a;
                    $(this).attr("id", a);
                    $(this).attr("for", "columnList[" + b + "].jdbcType")
                });
                $(this).find("input[name*=name]").each(function () {
                    var a = $(this).attr("name").split(".")[1], a = "page-columnList[" + b + "]." + a;
                    $(this).attr("name", a)
                });
                $(this).find("input[name*=comments]").each(function () {
                    var a = $(this).attr("name").split(".")[1], a = "page-columnList[" + b + "]." + a;
                    $(this).attr("name", a)
                })
            });
            $("#tab-3 #contentTable3 tbody tr").each(function (b, c) {
                $(this).find("span[name*=columnList],select[name*=columnList],input[name*=columnList]").each(function () {
                    var a = $(this).attr("name"), c = a.split(".")[1], c = "columnList[" + b + "]." + c;
                    $(this).attr("name", c);
                    0 <= a.indexOf(".sort") && ($(this).val(b), $(this).next().text(b))
                });
                $(this).find("label[id*=columnList]").each(function () {
                    var a = $(this).attr("id").split(".")[1], a = "columnList[" + b + "]." + a;
                    $(this).attr("id", a);
                    $(this).attr("for", "columnList[" + b + "].jdbcType")
                });
                $(this).find("input[name*=name]").each(function () {
                    var a = $(this).attr("name").split(".")[1], a = "page-columnList[" + b + "]." + a;
                    $(this).attr("name", a)
                });
                $(this).find("input[name*=comments]").each(function () {
                    var a = $(this).attr("name").split(".")[1], a = "page-columnList[" + b + "]." + a;
                    $(this).attr("name", a)
                })
            });
            $("#tab-2 #contentTable2 tbody tr").each(function (b, c) {
                $(this).find("span[name*=columnList],select[name*=columnList],input[name*=columnList]").each(function () {
                    var a = $(this).attr("name"), c = a.split(".")[1], c = "columnList[" + b + "]." + c;
                    $(this).attr("name", c);
                    0 <= a.indexOf(".sort") && ($(this).val(b), $(this).next().text(b))
                });
                $(this).find("label[id*=columnList]").each(function () {
                    var a = $(this).attr("id").split(".")[1], a = "columnList[" + b + "]." + a;
                    $(this).attr("id", a);
                    $(this).attr("for", "columnList[" + b + "].jdbcType")
                });
                $(this).find("input[name*=name]").each(function () {
                    var a = $(this).attr("name").split(".")[1], a = "page-columnList[" + b + "]." + a;
                    $(this).attr("name", a)
                });
                $(this).find("input[name*=comments]").each(function () {
                    var a = $(this).attr("name").split(".")[1], a = "page-columnList[" + b + "]." + a;
                    $(this).attr("name", a)
                })
            });
            $("#tab-1 #contentTable1 tbody tr").each(function (b, c) {
                $(this).find("span[name*=columnList],select[name*=columnList],input[name*=columnList]").each(function () {
                    var a = $(this).attr("name"), c = a.split(".")[1], c = "columnList[" + b + "]." + c;
                    $(this).attr("name", c);
                    0 <= a.indexOf(".sort") && ($(this).val(b), $(this).next().text(b))
                });
                $(this).find("label[id*=columnList]").each(function () {
                    var a = $(this).attr("id").split(".")[1], a = "columnList[" + b + "]." + a;
                    $(this).attr("id", a);
                    $(this).attr("for", "columnList[" + b + "].jdbcType")
                });
                $(this).find("input[name*=name]").change(function () {
                    var a = "page-" + $(this).attr("name");
                    $("#tab-2 #contentTable2 tbody tr input[name='" + a + "']").val($(this).val());
                    $("#tab-3 #contentTable3 tbody tr input[name='" + a + "']").val($(this).val());
                    $("#tab-4 #contentTable4 tbody tr input[name='" + a + "']").val($(this).val())
                });
                $(this).find("input[name*=comments]").change(function () {
                    var a = "page-" + $(this).attr("name");
                    $("#tab-2 #contentTable2 tbody tr input[name='" + a + "']").val($(this).val());
                    $("#tab-3 #contentTable3 tbody tr input[name='" + a + "']").val($(this).val());
                    $("#tab-4 #contentTable4 tbody tr input[name='" + a + "']").val($(this).val())
                })
            });
            $("#contentTable1 tbody tr span[name*=jdbcType]").combox({datas: "varchar(64) nvarchar(64) integer double datetime longblob longtext decimal".split(" ")});
            $("#contentTable2 tbody tr select[name*=javaType]").change(function () {
                var b = $(this).children("option:selected").val(), c = $(this);
                if ("Custom" == b || "newadd" == $(this).children("option:selected").attr("class")) top.layer.open({
                    type: 1,
                    title: "输入自定义java对象",
                    area: ["600px", "360px"],
                    shadeClose: !0,
                    content: '<div class="wrapper wrapper-content"><div class="col-md-12"><div class="form-group"> <label class="col-sm-3 control-label">包名：</label> <div class="col-sm-9"> <input type="text" id="packagePath" name="" class="form-control required" placeholder="请输入自定义对象所在的包路径"> <span class="help-block m-b-none">必须是存在的package</span> </div> </div> <div class="form-group"> <label class="col-sm-3 control-label">类名：</label> <div class="col-sm-9"> <input type="text" id="className" name="" class="form-control required" placeholder="请输入自定义对象的类名"> <span class="help-block m-b-none">必须是存在的class对象</span> </div> </div></div></div>',
                    btn: ["确定", "关闭"],
                    yes: function (a, b) {
                        var f = top.$("#packagePath").val(), e = top.$("#className").val(), g = f + "." + e;
                        top.$("<option>").val(g).text(e);
                        "" == e.trim() || "" == f.trim() ? top.layer.alert("包名和类名都不允许为空!", {icon: 0}) : (c.children("option:selected").text(e), c.children("option:selected").val(g), c.children("option:selected").attr("class", "newadd"), top.layer.close(a))
                    },
                    cancel: function (a) {
                    }
                }), "Custom" != b && "newadd" == $(this).children("option:selected").attr("class") && (top.$("#packagePath").val($(this).children("option:selected").val().substring(0, $(this).children("option:selected").val().lastIndexOf("."))), top.$("#className").val($(this).children("option:selected").text()))
            })
        };

        function addColumn() {
            var b = $("#template1").clone();
            b.removeAttr("style");
            b.removeAttr("id");
            var c = $("#template2").clone();
            c.removeAttr("style");
            c.removeAttr("id");
            var a = $("#template3").clone();
            a.removeAttr("style");
            a.removeAttr("id");
            var d = $("#template4").clone();
            d.removeAttr("style");
            d.removeAttr("id");
            $("#tab-1 #contentTable1 tbody").append(b);
            $("#tab-2 #contentTable2 tbody").append(c);
            $("#tab-3 #contentTable3 tbody").append(a);
            $("#tab-4 #contentTable4 tbody").append(d);
            b.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            });
            c.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            });
            a.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            });
            d.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            });
            resetColumnNo();
            $("#contentTable1").tableDnD({
                onDragClass: "myDragClass", onDrop: function (a, b) {
                    toIndex = $(b).index();
                    var c = $("#tab-2 #contentTable2 tbody tr:eq(" + toIndex + ")"),
                        d = $("#tab-2 #contentTable2 tbody tr:eq(" + fromIndex + ")");
                    fromIndex < toIndex ? d.insertAfter(c) : d.insertBefore(c);
                    c = $("#tab-3 #contentTable3 tbody tr:eq(" + toIndex + ")");
                    d = $("#tab-3 #contentTable3 tbody tr:eq(" + fromIndex + ")");
                    fromIndex < toIndex ? d.insertAfter(c) : d.insertBefore(c);
                    c = $("#tab-4 #contentTable4 tbody tr:eq(" + toIndex + ")");
                    d = $("#tab-4 #contentTable4 tbody tr:eq(" + fromIndex + ")");
                    fromIndex < toIndex ? d.insertAfter(c) : d.insertBefore(c);
                    resetColumnNo()
                }, onDragStart: function (a, b) {
                    fromIndex = $(b).index()
                }
            });
            return !1
        };

        function removeForTreeTable() {
            $("#tab-1 #contentTable1 tbody").find("#tree_11,#tree_12,#tree_13,#tree_14").remove();
            $("#tab-2 #contentTable2 tbody").find("#tree_21,#tree_22,#tree_23,#tree_24").remove();
            $("#tab-3 #contentTable3 tbody").find("#tree_31,#tree_32,#tree_33,#tree_34").remove();
            $("#tab-4 #contentTable4 tbody").find("#tree_41,#tree_42,#tree_43,#tree_44").remove();
            resetColumnNo();
            return !1
        };

        function addForTreeTable() {
            if (!$("#tab-1 #contentTable1 tbody").find("input[name*=name][value=parent_id]").val()) {
                var b = $("#template1").clone();
                b.removeAttr("style");
                b.attr("id", "tree_11");
                b.find("input[name*=name]").val("parent_id");
                b.find("input[name*=comments]").val("父级编号");
                b.find("span[name*=jdbcType]").val("varchar(64)");
                var c = $("#template2").clone();
                c.removeAttr("style");
                c.attr("id", "tree_21");
                c.find("input[name*=name]").val("parent_id");
                c.find("select[name*=javaType]").val("This");
                c.find("input[name*=javaField]").val("parent.id|name");
                c.find("input[name*=isList]").removeAttr("checked");
                c.find("select[name*=showType]").val("treeselect");
                var a = $("#template3").clone();
                a.removeAttr("style");
                a.attr("id", "tree_31");
                a.find("input[name*=name]").val("parent_id");
                var d = $("#template4").clone();
                d.removeAttr("style");
                d.attr("id", "tree_41");
                d.find("input[name*=name]").val("parent_id");
                d.find("input[name*=isNull]").removeAttr("checked");
                $("#tab-1 #contentTable1 tbody").append(b);
                $("#tab-2 #contentTable2 tbody").append(c);
                $("#tab-3 #contentTable3 tbody").append(a);
                $("#tab-4 #contentTable4 tbody").append(d);
                b.find("input:checkbox").iCheck({
                    checkboxClass: "icheckbox_square-green",
                    radioClass: "iradio_square-blue",
                    increaseArea: "20%"
                });
                c.find("input:checkbox").iCheck({
                    checkboxClass: "icheckbox_square-green",
                    radioClass: "iradio_square-blue",
                    increaseArea: "20%"
                });
                a.find("input:checkbox").iCheck({
                    checkboxClass: "icheckbox_square-green",
                    radioClass: "iradio_square-blue",
                    increaseArea: "20%"
                });
                d.find("input:checkbox").iCheck({
                    checkboxClass: "icheckbox_square-green",
                    radioClass: "iradio_square-blue",
                    increaseArea: "20%"
                })
            }
            ;$("#tab-1 #contentTable1 tbody").find("input[name*=name][value=parent_ids]").val() || (b = $("#template1").clone(), b.removeAttr("style"), b.attr("id", "tree_12"), b.find("input[name*=name]").val("parent_ids"), b.find("input[name*=comments]").val("所有父级编号"), b.find("span[name*=jdbcType]").val("varchar(2000)"), c = $("#template2").clone(), c.removeAttr("style"), c.attr("id", "tree_22"), c.find("input[name*=name]").val("parent_ids"), c.find("select[name*=javaType]").val("String"), c.find("input[name*=javaField]").val("parentIds"), c.find("select[name*=queryType]").val("like"), c.find("input[name*=isList]").removeAttr("checked"), a = $("#template3").clone(), a.removeAttr("style"), a.attr("id", "tree_32"), a.find("input[name*=name]").val("parent_ids"), d = $("#template4").clone(), d.removeAttr("style"), d.attr("id", "tree_42"), d.find("input[name*=name]").val("parent_ids"), d.find("input[name*=isNull]").removeAttr("checked"), $("#tab-1 #contentTable1 tbody").append(b), $("#tab-2 #contentTable2 tbody").append(c), $("#tab-3 #contentTable3 tbody").append(a), $("#tab-4 #contentTable4 tbody").append(d), b.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            }), c.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            }), a.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            }), d.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            }));
            $("#tab-1 #contentTable1 tbody").find("input[name*=name][value=name]").val() || (b = $("#template1").clone(), b.removeAttr("style"), b.attr("id", "tree_13"), b.find("input[name*=name]").val("name"), b.find("input[name*=comments]").val("名称"), b.find("span[name*=jdbcType]").val("varchar(100)"), c = $("#template2").clone(), c.removeAttr("style"), c.attr("id", "tree_23"), c.find("input[name*=name]").val("name"), c.find("select[name*=javaType]").val("String"), c.find("input[name*=javaField]").val("name"), c.find("input[name*=isQuery]").attr("checked", "checked"), c.find("select[name*=queryType]").val("like"), a = $("#template3").clone(), a.removeAttr("style"), a.attr("id", "tree_33"), a.find("input[name*=name]").val("name"), d = $("#template4").clone(), d.removeAttr("style"), d.attr("id", "tree_43"), d.find("input[name*=name]").val("name"), d.find("input[name*=isNull]").removeAttr("checked"), $("#tab-1 #contentTable1 tbody").append(b), $("#tab-2 #contentTable2 tbody").append(c), $("#tab-3 #contentTable3 tbody").append(a), $("#tab-4 #contentTable4 tbody").append(d), b.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            }), c.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            }), a.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            }), d.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            }));
            $("#tab-1 #contentTable1 tbody").find("input[name*=name][value=sort]").val() || (b = $("#template1").clone(), b.removeAttr("style"), b.attr("id", "tree_14"), b.find("input[name*=name]").val("sort"), b.find("input[name*=comments]").val("排序"), b.find("span[name*=jdbcType]").val("decimal(10,0)"), c = $("#template2").clone(), c.removeAttr("style"), c.attr("id", "tree_24"), c.find("input[name*=name]").val("sort"), c.find("select[name*=javaType]").val("Integer"), c.find("input[name*=javaField]").val("sort"), c.find("input[name*=isList]").removeAttr("checked"), a = $("#template3").clone(), a.removeAttr("style"), a.attr("id", "tree_34"), a.find("input[name*=name]").val("sort"), d = $("#template4").clone(), d.removeAttr("style"), d.attr("id", "tree_44"), d.find("input[name*=name]").val("sort"), d.find("input[name*=isNull]").removeAttr("checked"), $("#tab-1 #contentTable1 tbody").append(b), $("#tab-2 #contentTable2 tbody").append(c), $("#tab-3 #contentTable3 tbody").append(a), $("#tab-4 #contentTable4 tbody").append(d), b.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            }), c.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            }), a.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            }), d.find("input:checkbox").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-blue",
                increaseArea: "20%"
            }));
            resetColumnNo();
            return !1
        };

        function delColumn() {
            $("input[name='ck']:checked").closest("tr").each(function () {
                var b = $(this).find("input[name*=name]").attr("name");
                $(this).remove();
                $("#tab-2 #contentTable2 tbody tr input[name='page-" + b + "']").closest("tr").remove();
                $("#tab-3 #contentTable3 tbody tr input[name='page-" + b + "']").closest("tr").remove();
                $("#tab-4 #contentTable4 tbody tr input[name='page-" + b + "']").closest("tr").remove()
            });
            resetColumnNo();
            return !1
        };
    </script>
</head>
<body>

<!-- 锁定 -->
<div class="wrapper wrapper-content">
    <table style="display:none">
        <tr id="template1" style="display:none">
            <td>
                <input type="hidden" name="columnList[0].sort" value="0" maxlength="200" class="form-control required   digits"/>
                <label>0</label>
                <input type="hidden" class="form-control" name="columnList[0].isInsert" value="1"/>
                <input type="hidden" class="form-control" name="columnList[0].isEdit" value="1"/>
            </td>
            <td>
                <input type="checkbox" class="form-control  " name="ck" value="1"/>
            </td>
            <td>
                <input type="text" class="form-control required" name="columnList[0].name" value=""/>
            </td>
            <td>
                <input type="text" class="form-control required" name="columnList[0].comments" value="" maxlength="200" class="required"/>
            </td>
            <td>
                <span name="template_columnList[0].jdbcType" class="required" value="varchar(64)"/>
            </td>
            <td>
                <input type="checkbox" class="form-control" name="columnList[0].isPk" value="1"/>
            </td>
        </tr>
        <tr id="template2" style="display:none">
            <td>
                <input type="text" class="form-control" readOnly="readonly" name="page-columnList[0].name" value=""/>
            </td>
            <td>
                <input type="text" class="form-control" name="page-columnList[0].comments" value="" maxlength="200" readonly="readonly"/>
            </td>
            <td>
                <select name="columnList[0].javaType" class="form-control required m-b">
                    <c:forEach var="dict" items="${config.javaTypeList}" varStatus="status">
                        <option value="${dict.value}" title="${dict.description}"  ${dict.value=='String'?'selected':''}>${dict.label}</option>
                    </c:forEach>
                    <option value="Custom" class="newadd">自定义输入</option>
                </select>
            </td>
            <td>
                <input type="text" name="columnList[0].javaField" value="" maxlength="200" class="form-control required "/>
            </td>
            <td>
                <input type="checkbox" class="form-control  " name="columnList[0].isForm" value="1" checked/>
            </td>
            <td>
                <input type="checkbox" class="form-control  " name="columnList[0].isList" value="1" checked/>
            </td>
            <td>
                <input type="checkbox" class="form-control  " name="columnList[0].isQuery" value="1"/>
            </td>
            
            <!-- 自定义修改添加按钮bug -->
                  <td>
                                    <select name="columnList[0].queryType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.queryTypeList}" varStatus="status">
                                            <option value="${fns:escapeHtml(dict.value)}" title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                        </c:forEach>
                                    </select>
                                </td>
            
      
            
            
            <td>
                <select name="columnList[0].showType" class="form-control required  m-b">
                    <c:forEach var="dict" items="${config.showTypeList}" varStatus="status">
                        <option value="${dict.value}" title="${dict.description}"  ${dict.value==column.showType?'selected':''}>${dict.label}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <input type="text" name="columnList[0].dictType" value=" ${column.dictType}" maxlength="200" class="form-control   "/>
            </td>
        </tr>

        <tr id="template3" style="display:none">
            <td>
                <input type="text" class="form-control" readOnly="readonly" name="page-columnList[0].name" value=""/>
            </td>
            <td>
                <input type="text" class="form-control" name="page-columnList[0].comments" value="" maxlength="200" readonly="readonly"/>
            </td>
            <td>
                <input type="text" name="columnList[0].tableName" value="" maxlength="200" class="form-control  "/>
            </td>
            <td>
                <input type="text" name="columnList[0].fieldLabels" value="" maxlength="200" class="form-control  "/>
            </td>
            <td>
                <input type="text" name="columnList[0].fieldKeys" value="" maxlength="200" class="form-control  "/>
            </td>
            <td>
                <input type="text" name="columnList[0].searchLabel" value="" maxlength="200" class="form-control  "/>
            </td>
            <td>
                <input type="text" name="columnList[0].searchKey" value="" maxlength="200" class="form-control  "/>
            </td>

        </tr>

        <tr id="template4" style="display:none">
            <td>
                <input type="text" class="form-control" readOnly="readonly" name="page-columnList[0].name" value=""/>
            </td>
            <td>
                <input type="text" class="form-control" name="page-columnList[0].comments" value="" maxlength="200" readonly="readonly"/>
            </td>
            <td>
                <input type="checkbox" class="form-control " name="columnList[0].isNull" value="1" checked/>
            </td>
            <td>
                <select name="columnList[0].validateType" class="form-control  m-b">
                    <c:forEach var="dict" items="${config.validateTypeList}" varStatus="status">
                        <option value="${dict.value}" title="${dict.description}"  ${dict.value==column.validateType?'selected':''}>${dict.label}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <input type="text" name="columnList[0].minLength" value="" maxlength="200" class="form-control  "/>
            </td>
            <td>
                <input type="text" name="columnList[0].maxLength" value="" maxlength="200" class="form-control  "/>
            </td>
            <td>
                <input type="text" name="columnList[0].minValue" value="" maxlength="200" class="form-control  "/>
            </td>
            <td>
                <input type="text" name="columnList[0].maxValue" value="" maxlength="200" class="form-control  "/>
            </td>
        </tr>
    </table>
    <!-- 业务表添加 -->
    <form:form id="inputForm" modelAttribute="genTable" method="post" action="${ctx}/gen/genTable/save" class="form-horizontal">
    <form:hidden path="id"/>
    <form:hidden path="isSync"/>
    <sys:message content="${message}"/>
    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>
        <tr>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>表名:</label></td>
            <td class="width-35">
                <form:input path="name" maxlength="200" class="required  form-control"/>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>说明:</label></td>
            <td class="width-35">
                <form:input path="comments" maxlength="200" class="required  form-control"/>
            </td>
        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">表类型</label></td>
            <td class="width-35">
                <form:select path="tableType" class="form-control m-b">
                    <form:options items="${fns:getDictList('table_type')}" itemValue="value" itemLabel="label"/>
                </form:select>
                <span class="help-inline">如果是附表，请指定主表表名和当前表的外键</span>
            </td>
            <td class="width-15 active"><label class="pull-right"><font color="red">*</font>类名:</label></td>
            <td class="width-35">
                <form:input path="className" maxlength="200" class="required  form-control"/>
            </td>

        </tr>
        <tr>
            <td class="width-15 active"><label class="pull-right">主表表名:</label></td>
            <td class="width-35">
                <form:select path="parentTable" class="form-control">
                    <form:option value=""></form:option>
                    <form:options items="${tableList}" itemValue="name" itemLabel="nameAndComments"/>
                </form:select>
            </td>
            <td class="width-15 active"><label class="pull-right">当前表外键：</label></td>
            <td class="width-35">
                <form:input path="parentTableFk" maxlength="200" class="form-control"/>
            </td>
        </tr>

        </tbody>
    </table>
    <button class="btn btn-white btn-sm" onclick="return addColumn()"><i class="fa fa-plus"> 增加</i></button>
    <button class="btn btn-white btn-sm" onclick="return delColumn()"><i class="fa fa-minus"> 删除</i></button>
    <br/>

    <div class="tabs-container">
        <ul class="nav nav-tabs">
            <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true"> 数据库属性</a>
            </li>
            <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">页面属性</a>
            </li>
            <li class=""><a data-toggle="tab" href="#tab-4" aria-expanded="false">页面校验</a>
            </li>
            <li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">grid选择框（自定义java对象）</a>
            </li>

        </ul>
        <div class="tab-content">
            <div id="tab-1" class="tab-pane active">
                <div class="panel-body">
                    <table id="contentTable1" class="table table-striped table-bordered table-hover  dataTables-example dataTable">
                        <thead>
                        <tr>
                            <th width="40px">序号</th>
                            <th>操作</th>
                            <th title="数据库字段名">列名</th>
                            <th title="默认读取数据库字段备注">说明</th>
                            <th title="数据库中设置的字段类型及长度">物理类型</th>
                            <th title="是否是数据库主键">主键</th>
                            <!-- <th title="字段是否可为空值，不可为空字段自动进行空值验证">可空</th> -->
                            <!--<th title="选中后该字段被加入到insert语句里">插入</th>
                                                    <th title="选中后该字段被加入到update语句里">编辑</th>  -->
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty genTable.name}">
                            <!-- id -->
                            <tr>
                                <td>
                                    <input type="hidden" name="columnList[0].sort" value="0" maxlength="200" class="form-control required   digits"/>
                                    <label>0</label>
                                    <input type="hidden" name="columnList[0].isInsert" value="1"/>
                                    <input type="hidden" name="columnList[0].isEdit" value="0"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[0].name" value="id"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[0].comments" value="主键" maxlength="200" class="required"/>
                                </td>
                                <td>
                                    <span name="columnList[0].jdbcType" class="required " value="varchar(64)"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[0].isPk" value="1" checked/>
                                </td>
                            </tr>
                            <!-- create_by -->
                            <tr>
                                <td>
                                    <input type="hidden" name="columnList[1].sort" value="1" maxlength="200" class="form-control required   digits"/>
                                    <label>1</label>
                                    <input type="hidden" name="columnList[1].isInsert" value="1"/>
                                    <input type="hidden" name="columnList[1].isEdit" value="0"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[1].name" value="create_by"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[1].comments" value="创建者" maxlength="200" class="required"/>
                                </td>
                                <td>
                                    <span name="columnList[1].jdbcType" class="required " value="varchar(64)"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[1].isPk" value="1"/>
                                </td>

                            </tr>

                            <!-- create_date -->
                            <tr>
                                <td>
                                    <input type="hidden" name="columnList[2].sort" value="2" maxlength="200" class="form-control required   digits"/>
                                    <label>2</label>
                                    <input type="hidden" name="columnList[2].isInsert" value="1"/>
                                    <input type="hidden" name="columnList[2].isEdit" value="0"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[2].name" value="create_date"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[2].comments" value="创建时间" maxlength="200" class="required"/>
                                </td>
                                <td>
                                    <span name="columnList[2].jdbcType" class="required " value="datetime"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[2].isPk" value="1"/>
                                </td>

                            </tr>

                            <!-- update_by -->
                            <tr>
                                <td>
                                    <input type="hidden" name="columnList[3].sort" value="3" maxlength="200" class="form-control required   digits"/>
                                    <label>3</label>
                                    <input type="hidden" name="columnList[3].isInsert" value="1"/>
                                    <input type="hidden" name="columnList[3].isEdit" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[3].name" value="update_by"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[3].comments" value="更新者" maxlength="200" class="required"/>
                                </td>
                                <td>
                                    <span name="columnList[3].jdbcType" class="required " value="varchar(64)"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[3].isPk" value="1"/>
                                </td>
                            </tr>

                            <!-- update_date -->
                            <tr>
                                <td>
                                    <input type="hidden" name="columnList[4].sort" value="4" maxlength="200" class="form-control required   digits"/>
                                    <label>4</label>
                                    <input type="hidden" name="columnList[4].isInsert" value="1"/>
                                    <input type="hidden" name="columnList[4].isEdit" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[4].name" value="update_date"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[4].comments" value="更新时间" maxlength="200" class="required"/>
                                </td>
                                <td>
                                    <span name="columnList[4].jdbcType" class="required " value="datetime"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[4].isPk" value="1"/>
                                </td>
                            </tr>

                            <!-- remarks -->
                            <tr>
                                <td>
                                    <input type="hidden" name="columnList[5].sort" value="5" maxlength="200" class="form-control required   digits"/>
                                    <label>5</label>
                                    <input type="hidden" name="columnList[5].isInsert" value="1"/>
                                    <input type="hidden" name="columnList[5].isEdit" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[5].name" value="remarks"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[5].comments" value="备注" maxlength="200" class="required"/>
                                </td>
                                <td>
                                    <span name="columnList[5].jdbcType" class="required " value="nvarchar(255)"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[5].isPk" value="1"/>
                                </td>

                            </tr>

                            <!-- del_flag -->
                            <tr>
                                <td>
                                    <input type="hidden" name="columnList[6].sort" value="0" maxlength="200" class="form-control required   digits"/>
                                    <label>6</label>
                                    <input type="hidden" name="columnList[6].isInsert" value="1"/>
                                    <input type="hidden" name="columnList[6].isEdit" value="0"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[6].name" value="del_flag"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="columnList[6].comments" value="逻辑删除标记（0：显示；1：隐藏）" maxlength="200" class="required"/>
                                </td>
                                <td>
                                    <span name="columnList[6].jdbcType" class="required " value="varchar(64)"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[6].isPk" value="1"/>
                                </td>
                            </tr>
                        </c:if>
                        <c:forEach var="column" items="${genTable.columnList}" varStatus="vs">
                            <tr ${column.delFlag eq '1'?' class="error" title="已删除的列，保存之后消失！"':''}>
                                <td>
                                    <input type="hidden" name="columnList[${vs.index}].sort" value="${column.sort}" maxlength="200" class="form-control required   digits"/>
                                    <label>
                                            ${column.sort}
                                    </label>
                                    <input type="hidden" name="columnList[${vs.index}].isInsert" value="${column.isInsert}"/>
                                    <input type="hidden" name="columnList[${vs.index}].isEdit" value="${column.isEdit}"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks " name="ck" value="1"/>
                                </td>
                                <td>
                                    <input type="hidden" name="columnList[${vs.index}].id" value="${column.id}"/>
                                    <input type="hidden" name="columnList[${vs.index}].delFlag" value="${column.delFlag}"/>
                                    <input type="hidden" name="columnList[${vs.index}].genTable.id" value="${column.genTable.id}"/>
                                    <input type="text" name="columnList[${vs.index}].name" value="${column.name}" class="form-control required"/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[${vs.index}].comments" value="${column.comments}" maxlength="200" class="form-control required"/>
                                </td>
                                <td>
                                    <span name="columnList[${vs.index}].jdbcType" class="required" value="${column.jdbcType}"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[${vs.index}].isPk" value="1" ${column.isPk eq '1' ? 'checked' : ''}/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div id="tab-2" class="tab-pane">
                <div class="panel-body">
                    <table id="contentTable2" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                        <thead>
                        <tr>
                            <th title="数据库字段名" width="15%">列名</th>
                            <th title="默认读取数据库字段备注">说明</th>
                            <th title="实体对象的属性字段类型" width="15%">Java类型</th>
                            <th title="实体对象的属性字段（对象名.属性名|属性名2|属性名3，例如：用户user.id|name|loginName，属性名2和属性名3为Join时关联查询的字段）"> Java属性名称 <i class="icon-question-sign"></i></th>
                            <th title="选中后该字段被加入到查询列表里">表单</th>
                            <th title="选中后该字段被加入到查询列表里">列表</th>
                            <th title="选中后该字段被加入到查询条件里">查询</th>
                            <th title="该字段为查询字段时的查询匹配放松" width="15%">查询匹配方式</th>
                            <th title="字段在表单中显示的类型" width="15%">显示表单类型</th>
                            <th title="显示表单类型设置为“下拉框、复选框、点选框”时，需设置字典的类型">字典类型</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty genTable.name}">
                            <!-- id -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[0].name" value="id"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[0].comments" value="主键" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <select name="columnList[0].javaType" class="form-control required m-b">
                                        <c:forEach var="dict" items="${config.javaTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value=='String'?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                        <option value="Custom" class="newadd">自定义输入</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[0].javaField" value="id" maxlength="200" class="form-control required "/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[0].isForm" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[0].isList" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[0].isQuery" value="1"/>
                                </td>
                                <td>
                                    <select name="columnList[0].queryType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.queryTypeList}" varStatus="status">
                                            <option value="${fns:escapeHtml(dict.value)}" title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <select name="columnList[0].showType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.showTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}" }>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[0].dictType" value="${column.dictType}" maxlength="200" class="form-control"/>
                                </td>

                            </tr>
                            <!-- create_by -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[1].name" value="create_by"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[1].comments" value="创建者" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <select name="columnList[1].javaType" class="form-control required m-b">
                                        <c:forEach var="dict" items="${config.javaTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value=='String'?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                        <option value="Custom" class="newadd">自定义输入</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[1].javaField" value="createBy.id" maxlength="200" class="form-control required "/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[1].isForm" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[1].isList" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[1].isQuery" value="1"/>
                                </td>
                                <td>
                                    <select name="columnList[1].queryType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.queryTypeList}" varStatus="status">
                                            <option value="${fns:escapeHtml(dict.value)}" title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <select name="columnList[1].showType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.showTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value=='input'?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[1].dictType" value="${column.dictType}" maxlength="200" class="form-control"/>
                                </td>
                            </tr>

                            <!-- create_date -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[2].name" value="create_date"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[2].comments" value="创建日期" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <select name="columnList[2].javaType" class="form-control required m-b">
                                        <c:forEach var="dict" items="${config.javaTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value=='java.util.Date'?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                        <option value="Custom" class="newadd">自定义输入</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[2].javaField" value="createDate" maxlength="200" class="form-control required "/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[2].isForm" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[2].isList" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[2].isQuery" value="1"/>
                                </td>
                                <td>
                                    <select name="columnList[2].queryType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.queryTypeList}" varStatus="status">
                                            <option value="${fns:escapeHtml(dict.value)}" title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <select name="columnList[2].showType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.showTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value=='dateselect'?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[2].dictType" value="${column.dictType}" maxlength="200" class="form-control"/>
                                </td>
                            </tr>

                            <!-- update_by -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[3].name" value="update_by"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[3].comments" value="更新者" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <select name="columnList[3].javaType" class="form-control required m-b">
                                        <c:forEach var="dict" items="${config.javaTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value=='String'?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                        <option value="Custom" class="newadd">自定义输入</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[3].javaField" value="updateBy.id" maxlength="200" class="form-control required "/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[3].isForm" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[3].isList" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[3].isQuery" value="1"/>
                                </td>
                                <td>
                                    <select name="columnList[3].queryType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.queryTypeList}" varStatus="status">
                                            <option value="${fns:escapeHtml(dict.value)}" title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <select name="columnList[3].showType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.showTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"
                                                    ${dict.value=='input'?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[3].dictType" value="${column.dictType}" maxlength="200" class="form-control"/>
                                </td>
                            </tr>

                            <!-- update_date -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[4].name" value="update_date"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[4].comments" value="更新日期" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <select name="columnList[4].javaType" class="form-control required m-b">
                                        <c:forEach var="dict" items="${config.javaTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value=='java.util.Date'?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                        <option value="Custom" class="newadd">自定义输入</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[4].javaField" value="updateDate" maxlength="200" class="form-control required "/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[4].isForm" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[4].isList" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[4].isQuery" value="1"/>
                                </td>
                                <td>
                                    <select name="columnList[4].queryType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.queryTypeList}" varStatus="status">
                                            <option value="${fns:escapeHtml(dict.value)}" title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <select name="columnList[4].showType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.showTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value=='dateselect'?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[4].dictType" value="${column.dictType}" maxlength="200" class="form-control"/>
                                </td>
                            </tr>

                            <!-- remarks -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[5].name" value="remarks"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[5].comments" value="备注" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <select name="columnList[5].javaType" class="form-control required m-b">
                                        <c:forEach var="dict" items="${config.javaTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value=='String'?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                        <option value="Custom" class="newadd">自定义输入</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[5].javaField" value="remarks" maxlength="255" class="form-control required "/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[5].isForm" value="1" checked/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[5].isList" value="1" checked/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[5].isQuery" value="1"/>
                                </td>
                                <td>
                                    <select name="columnList[5].queryType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.queryTypeList}" varStatus="status">
                                            <option value="${fns:escapeHtml(dict.value)}" title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <select name="columnList[5].showType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.showTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value=='textarea'?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[5].dictType" value="${column.dictType}" maxlength="200" class="form-control"/>
                                </td>
                            </tr>

                            <!-- del_flag -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[6].name" value="del_flag"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[6].comments" value="逻辑删除标记（0：显示；1：隐藏）" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <select name="columnList[6].javaType" class="form-control required m-b">
                                        <c:forEach var="dict" items="${config.javaTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value=='String'?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                        <option value="Custom" class="newadd">自定义输入</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[6].javaField" value="delFlag" maxlength="255" class="form-control required "/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[6].isForm" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[6].isList" value="1"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[6].isQuery" value="1"/>
                                </td>
                                <td>
                                    <select name="columnList[6].queryType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.queryTypeList}" varStatus="status">
                                            <option value="${fns:escapeHtml(dict.value)}" title="${dict.description}">${fns:escapeHtml(dict.label)}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <select name="columnList[6].showType" class="form-control required  m-b">
                                        <c:forEach var="dict" items="${config.showTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value=='radiobox'?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[6].dictType" value="del_flag" maxlength="200" class="form-control"/>
                                </td>
                            </tr>
                        </c:if>
                        <c:forEach var="column" items="${genTable.columnList}" varStatus="vs">
                            <tr  ${column.delFlag eq '1'?' class="error" title="已删除的列，保存之后消失！"':''} >
                                <td>
                                    <input type="text" readonly="readonly" name="page-columnList[${vs.index}].name" value="${column.name}" class="form-control required"/>
                                </td>
                                <td>
                                    <input type="text" readonly="readonly" name="page-columnList[${vs.index}].comments" value="${column.comments}" maxlength="200" class="form-control"/>
                                </td>
                                <td>
                                    <select name="columnList[${vs.index}].javaType" class="form-control required">
                                            <%--column.javaType是否包含在javaTypeList中--%>
                                        <c:set var="iscontain" value="0"></c:set>
                                        <c:forEach var="dict" items="${config.javaTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value==column.javaType?'selected':''}>${dict.label}</option>
                                            <c:if test="${dict.value eq column.javaType}">
                                                <c:set var="iscontain" value="1"></c:set>
                                            </c:if>
                                        </c:forEach>

                                        <c:if test="${iscontain == '0'}">
                                            <option value="${column.javaType}" selected class="newadd">
                                                    ${fns:substringAfterLast(column.javaType, ".")}
                                            </option>
                                        </c:if>
                                        <c:if test="${iscontain != '0'}">
                                            <option value="Custom" class="newadd">自定义输入</option>
                                        </c:if>

                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[${vs.index}].javaField" value="${column.javaField}" maxlength="200" class="form-control required"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[${vs.index}].isForm" value="1"  ${column.isForm eq '1' ? 'checked' : ''}/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[${vs.index}].isList" value="1" ${column.isList eq '1' ? 'checked' : ''}/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[${vs.index}].isQuery" value="1" ${column.isQuery eq '1' ? 'checked' : ''}/>
                                </td>
                                <td>
                                    <select name="columnList[${vs.index}].queryType" class="required form-control m-b">
                                        <c:forEach var="dict" items="${config.queryTypeList}" varStatus="status">
                                            <option value="${fns:escapeHtml(dict.value)}" title="${dict.description}" ${fns:escapeHtml(dict.value)==column.queryType?'selected':''}>${fns:escapeHtml(dict.label)}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <select name="columnList[${vs.index}].showType" class="required form-control m-b">
                                        <c:forEach var="dict" items="${config.showTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value==column.showType?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[${vs.index}].dictType" value="${column.dictType}" maxlength="200" class="form-control "/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div id="tab-3" class="tab-pane">
                <div class="panel-body">
                    <table id="contentTable3" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                        <thead>
                        <tr>
                            <th title="数据库字段名" width="15%">列名</th>
                            <th title="默认读取数据库字段备注">说明</th>
                            <th title="实体对象的属性字段类型" width="15%">table表名</th>
                            <th title="实体对象的属性字段说明（label1|label2|label3，用户名|登录名|角色）">JAVA属性说明<i class="icon-question-sign"></i></th>
                            <th title="选中后该字段被加入到查询列表里">JAVA属性名称</th>
                            <th title="选中后该字段被加入到查询列表里">检索标签</th>
                            <th title="选中后该字段被加入到查询条件里">检索key</th>

                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty genTable.name}">
                            <!-- id -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[0].name" value="id"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[0].comments" value="主键" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[0].tableName" value="${column.tableName}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[0].fieldLabels" value="${column.fieldLabels}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[0].fieldKeys" value="${column.fieldKeys}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[0].searchLabel" value="${column.searchLabel}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[0].searchKey" value="${column.searchKey}" maxlength="200" class="form-control  "/>
                                </td>


                            </tr>
                            <!-- create_by -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[1].name" value="create_by"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[1].comments" value="创建者" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[1].tableName" value="${column.tableName}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[1].fieldLabels" value="${column.fieldLabels}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[1].fieldKeys" value="${column.fieldKeys}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[1].searchLabel" value="${column.searchLabel}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[1].searchKey" value="${column.searchKey}" maxlength="200" class="form-control  "/>
                                </td>
                            </tr>

                            <!-- create_date -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[2].name" value="create_date"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[2].comments" value="创建时间" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[2].tableName" value="${column.tableName}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[2].fieldLabels" value="${column.fieldLabels}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[2].fieldKeys" value="${column.fieldKeys}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[2].searchLabel" value="${column.searchLabel}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[2].searchKey" value="${column.searchKey}" maxlength="200" class="form-control  "/>
                                </td>
                            </tr>

                            <!-- update_by -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[3].name" value="update_by"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[3].comments" value="更新者" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[3].tableName" value="${column.tableName}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[3].fieldLabels" value="${column.fieldLabels}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[3].fieldKeys" value="${column.fieldKeys}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[3].searchLabel" value="${column.searchLabel}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[3].searchKey" value="${column.searchKey}" maxlength="200" class="form-control  "/>
                                </td>
                            </tr>

                            <!-- update_date -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[4].name" value="update_date"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[4].comments" value="更新时间" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[4].tableName" value="${column.tableName}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[4].fieldLabels" value="${column.fieldLabels}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[4].fieldKeys" value="${column.fieldKeys}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[4].searchLabel" value="${column.searchLabel}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[4].searchKey" value="${column.searchKey}" maxlength="200" class="form-control  "/>
                                </td>
                            </tr>

                            <!-- remarks -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[5].name" value="remarks"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[5].comments" value="备注" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[5].tableName" value="${column.tableName}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[5].fieldLabels" value="${column.fieldLabels}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[5].fieldKeys" value="${column.fieldKeys}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[5].searchLabel" value="${column.searchLabel}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[5].searchKey" value="${column.searchKey}" maxlength="200" class="form-control  "/>
                                </td>
                            </tr>

                            <!-- del_flag -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[6].name" value="del_flag"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[6].comments" value="逻辑删除标记（0：显示；1：隐藏）" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[6].tableName" value="${column.tableName}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[6].fieldLabels" value="${column.fieldLabels}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[6].fieldKeys" value="${column.fieldKeys}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[6].searchLabel" value="${column.searchLabel}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[6].searchKey" value="${column.searchKey}" maxlength="200" class="form-control  "/>
                                </td>
                            </tr>
                        </c:if>
                        <c:forEach var="column" items="${genTable.columnList}" varStatus="vs">
                            <tr ${column.delFlag eq '1'?' class="error" title="已删除的列，保存之后消失！"':''} >
                                <td>
                                    <input type="text" readonly="readonly" name="page-columnList[${vs.index}].name" value="${column.name}" class="form-control required"/>
                                </td>
                                <td>
                                    <input type="text" readonly="readonly" name="page-columnList[${vs.index}].comments" value="${column.comments}" maxlength="200" class="form-control"/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[${vs.index }].tableName" value="${column.tableName}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[${vs.index }].fieldLabels" value="${column.fieldLabels}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[${vs.index }].fieldKeys" value="${column.fieldKeys}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[${vs.index }].searchLabel" value="${column.searchLabel}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[${vs.index }].searchKey" value="${column.searchKey}" maxlength="200" class="form-control "/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div id="tab-4" class="tab-pane">
                <div class="panel-body">
                    <table id="contentTable4" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                        <thead>
                        <tr>
                            <th title="数据库字段名" width="15%">列名</th>
                            <th title="默认读取数据库字段备注">说明</th>
                            <th title="字段是否可为空值，不可为空字段自动进行空值验证">可空</th>
                            <th title="校验类型">校验类型<i class="icon-question-sign"></i></th>
                            <th title="最小长度">最小长度</th>
                            <th title="最大长度">最大长度</th>
                            <th title="最小值">最小值</th>
                            <th title="最大值">最大值</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty genTable.name}">
                            <!-- id -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[0].name" value="id"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[0].comments" value="主键" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[0].isNull" value="1"/>
                                </td>
                                <td>
                                    <select name="columnList[0].validateType" class="form-control m-b">
                                        <c:forEach var="dict" items="${config.validateTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value==column.validateType?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[0].minLength" value="${column.minLength}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[0].maxLength" value="${column.maxLength}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[0].minValue" value="${column.minValue}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[0].maxValue" value="${column.maxValue}" maxlength="200" class="form-control "/>
                                </td>

                            </tr>
                            <!-- create_by -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[1].name" value="create_by"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[1].comments" value="创建者" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[1].isNull" value="1"/>
                                </td>
                                <td>
                                    <select name="columnList[1].validateType" class="form-control m-b">
                                        <c:forEach var="dict" items="${config.validateTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value==column.validateType?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[1].minLength" value="${column.minLength}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[1].maxLength" value="${column.maxLength}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[1].minValue" value="${column.minValue}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[1].maxValue" value="${column.maxValue}" maxlength="200" class="form-control "/>
                                </td>
                            </tr>

                            <!-- create_date -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[2].name" value="create_date"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[2].comments" value="创建时间" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[2].isNull" value="1"/>
                                </td>
                                <td>
                                    <select name="columnList[2].validateType" class="form-control m-b">
                                        <c:forEach var="dict" items="${config.validateTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value==column.validateType?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[2].minLength" value="${column.minLength}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[2].maxLength" value="${column.maxLength}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[2].minValue" value="${column.minValue}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[2].maxValue" value="${column.maxValue}" maxlength="200" class="form-control "/>
                                </td>
                            </tr>

                            <!-- update_by -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[3].name" value="update_by"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[3].comments" value="更新者" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[3].isNull" value="1"/>
                                </td>
                                <td>
                                    <select name="columnList[3].validateType" class="form-control m-b">
                                        <c:forEach var="dict" items="${config.validateTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value==column.validateType?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[3].minLength" value="${column.minLength}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[3].maxLength" value="${column.maxLength}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[3].minValue" value="${column.minValue}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[3].maxValue" value="${column.maxValue}" maxlength="200" class="form-control "/>
                                </td>
                            </tr>

                            <!-- update_date -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[4].name" value="update_date"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[4].comments" value="更新时间" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[4].isNull" value="1"/>
                                </td>
                                <td>
                                    <select name="columnList[4].validateType" class="form-control m-b">
                                        <c:forEach var="dict" items="${config.validateTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value==column.validateType?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[4].minLength" value="${column.minLength}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[4].maxLength" value="${column.maxLength}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[4].minValue" value="${column.minValue}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[4].maxValue" value="${column.maxValue}" maxlength="200" class="form-control "/>
                                </td>
                            </tr>

                            <!-- remarks -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[5].name" value="remarks"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[5].comments" value="备注" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[5].isNull" value="1" checked/>
                                </td>
                                <td>
                                    <select name="columnList[5].validateType" class="form-control m-b">
                                        <c:forEach var="dict" items="${config.validateTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value==column.validateType?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[5].minLength" value="${column.minLength}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[5].maxLength" value="${column.maxLength}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[5].minValue" value="${column.minValue}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[5].maxValue" value="${column.maxValue}" maxlength="200" class="form-control  "/>
                                </td>
                            </tr>

                            <!-- del_flag -->
                            <tr>
                                <td>
                                    <input type="text" class="form-control" readonly="readonly" name="page-columnList[6].name" value="del_flag"/>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="page-columnList[6].comments" value="逻辑删除标记（0：显示；1：隐藏）" maxlength="200" readonly="readonly"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[6].isNull" value="1"/>
                                </td>
                                <td>
                                    <select name="columnList[6].validateType" class="form-control m-b">
                                        <c:forEach var="dict" items="${config.validateTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value==column.validateType?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[6].minLength" value="${column.minLength}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[6].maxLength" value="${column.maxLength}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[6].minValue" value="${column.minValue}" maxlength="200" class="form-control "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[6].maxValue" value="${column.maxValue}" maxlength="200" class="form-control "/>
                                </td>
                            </tr>

                        </c:if>
                        <c:forEach var="column" items="${genTable.columnList}" varStatus="vs">
                            <tr ${column.delFlag eq '1'?' class="error" title="已删除的列，保存之后消失！"':''}   >
                                <td>
                                    <input type="text" readonly="readonly" name="page-columnList[ ${vs.index} ].name" value="${column.name}" class="form-control required"/>
                                </td>
                                <td>
                                    <input type="text" readonly="readonly" name="page-columnList[${vs.index}].comments" value="${column.comments}" maxlength="200" class="form-control"/>
                                </td>
                                <td>
                                    <input type="checkbox" class="i-checks" name="columnList[${vs.index}].isNull" value="1"  ${column.isNull eq '1' ? 'checked' : ''}/>
                                </td>
                                <td>
                                    <select name="columnList[${vs.index}].validateType" class="form-control m-b">
                                        <c:forEach var="dict" items="${config.validateTypeList}" varStatus="status">
                                            <option value="${dict.value}" title="${dict.description}"  ${dict.value==column.validateType?'selected':''}>${dict.label}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="columnList[${vs.index}].minLength" value="${column.minLength}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[${vs.index}].maxLength" value="${column.maxLength}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[${vs.index}].minValue" value="${column.minValue}" maxlength="200" class="form-control  "/>
                                </td>
                                <td>
                                    <input type="text" name="columnList[${vs.index}].maxValue" value="${column.maxValue}" maxlength="200" class="form-control  "/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</form:form>
</body>
</html>
