<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<fmt:setBundle basename="kite" var="kite" />
<fmt:message key="fastdfs.storage.url" var="fastdfsStorageUrl" bundle="${kite}" />

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>玄武开发平台</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/images/favicon.ico" type="image/x-icon" />
	<%@ include file="/webpage/include/head.jsp"%>
	<script src="${ctxStatic}/common/inspinia.js?v=3.2.0"></script>
	<script src="${ctxStatic}/common/contabs.js"></script>
    <meta name="keywords" content="玄武开发平台">
    <meta name="description" content="玄武开发平台">
    <script type="text/javascript">
	$(document).ready(function() {
		 if('${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}' == '天蓝主题'){
			    // 蓝色主题
			        $("body").removeClass("skin-2");
			        $("body").removeClass("skin-3");
			        $("body").addClass("skin-1");
		 }else  if('${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}' == '橙色主题'){
			    // 黄色主题
			        $("body").removeClass("skin-1");
			        $("body").removeClass("skin-2");
			        $("body").addClass("skin-3");
		 }else {
			 // 默认主题
			        $("body").removeClass("skin-2");
			        $("body").removeClass("skin-3");
			        $("body").removeClass("skin-1");
		 };
	 });

	function crmLogin() {
		var url = "${ctx}/crm/login/crmObtainSignature";
		$.ajax({
		     url:url,
		     dataType:'json',
		     type:'get',
		     success:function(data){
		    	 if (data.status == '1') {
		    		 var link = 'http://crm.wxchina.com/login.html?signature=' + data.signature + '&usermobile=' + data.usermobile + "&timestamp=" + data.timestamp;
		    		 window.open(link);
		    	 }
		    	 else {
		    		 alert("无法打开CRM");
		    	 }
		     },
		     error:function(data) {
		    	 alert("无法打开CRM");
		     }});

      	}

	function updateImage() {

	    //获取目录id
        $.ajax({
            url: '${ctx}/file/fileCatalog/getByCatalogName',
            type: 'post',
            contentType: "application/json",
            dataType: "json",
            data:JSON.stringify({
                "name":'头像管理'
            }),
            cache: false,
            processData: false,
            async: false
        }).done(function(res) {
            console.log(res);
            if(res.success) {
                var catalogId = res.data.id;
                //开始上传
                openUploadCatalog(catalogId);
            } else {
                parent.layer.alert("请求成功，获取目录信息失败",{icon: 0, title:'警告'});
            }

        }).fail(function(res) {
            top.layer.alert("请求失败，获取目录信息失败",{icon: 0, title:'警告'});
        });

    }

    function openUploadCatalog(catalogId) {
        top.layer.open({
            type: 2,
            area: ['450px', '300px'],
            title: '上传文件',
            maxmin: true, //开启最大化最小化按钮
            content: "${ctx}/file/fileFastdfs/open?group=''&catalogId="+catalogId+"&catalogName=头像管理&hidden=true",
            btn: ['确定', '关闭'],
            yes: function(index, layero){
                console.log("123");
                var body = top.layer.getChildFrame('body', index);
                var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var inputForm = body.find('#inputForm');
                console.log("123"+inputForm);
                var top_iframe;

                var pre_ids = layero.find("iframe")[0].contentWindow
                console.log("123"+pre_ids);

                top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe

                inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示

                if(iframeWin.contentWindow.doSubmit() ){
                    top.layer.close(index);//关闭对话框。
                    setTimeout(function(){top.layer.close(index)}, 100);//延时0.1秒，对应360 7.1版本bug
                }



                //window.location.reload();

            },
            cancel: function(index){
            }
        });
    }

	</script>

</head>

<body class="fixed-sidebar full-height-layout gray-bg">

    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span><img alt="image" class="img-circle" style="height:64px;width:64px;" src="${fastdfsStorageUrl}${fns:getUser().photo}" /></span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold">${fns:getUser().name}</strong></span>
                             <%--   <span class="text-muted text-xs block">${fns:getUser().roleNames}<b class="caret"></b></span>
                                </span> --%>
                            </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li><a class="J_menuItem" href="${ctx}/sys/user/imageEdit">修改头像</a>
                                    <%--<li><a class="J_menuItem" onclick="updateImage()">修改头像</a>--%>
                                </li>
                                <li><a class="J_menuItem" href="${ctx }/sys/user/info">个人资料</a>
                                </li>
                                <li>
                                <li>
                                    <a class="J_menuItem" href="${ctx}/sys/user/modifyPwd">更换密码</a>

                                </li>
                               <%-- <li><a class="J_menuItem" href="${ctx }/iim/contact/index">我的通讯录</a>
                                </li>
                                <li><a class="J_menuItem" href="${ctx }/iim/mailBox/list">信箱</a>
                                </li>

                                <li><a onclick="changeStyle()" href="#">切换到ACE模式</a>
                                </li> --%>

                                <li class="divider"></li>
                                <li><a href="${ctx}/logout">安全退出</a>
                                </li>
                            </ul>
                        </div>
                        <div class="logo-element">JP
                        </div>
                    </li>

                  <t:menu  menu="${fns:getTopMenu()}"></t:menu>




                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">

         <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">

                     <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                        <%--<form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                            <div class="form-group">
                                <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
                            </div>
                        </form>--%>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">


                        <%--	 <a class="dropdown-toggle count-infboo" data-toggle="dropdown" href="#">
                     <li data-toggle="tooltip" data-placement="bottom" title="登录CRM" onclick="crmLogin()" >CRM</li>
                            </a>--%>
                        <li class="dropdown">

                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-bell"></i> <span class="label label-primary">${count }</span>
                            </a>
                            <ul class="dropdown-menu dropdown-alerts">
                                <li>

                                    <c:forEach items="${page.list}" var="xwPushmessage">
                                        <div>
                                            <a class="J_menuItem" title="我的消息通知" href="${ctx}/xw_tools/xwPushmessage/oneList?id=${xwPushmessage.id}&">
                                                <i class="fa fa-envelope fa-fw"></i> ${fns:abbr(xwPushmessage.title,50)}
                                            </a>
                                            <span class="pull-right text-muted small">${fns:getTime(xwPushmessage.updateDate)}前</span>
                                        </div>
                                    </c:forEach>

                               <%-- <c:forEach items="${page.list}" var="oaNotify">

                                        <div>
                                        	   <a class="J_menuItem" href="${ctx}/oa/oaNotify/?id=${oaNotify.id}&">
                                            	<i class="fa fa-envelope fa-fw"></i> ${fns:abbr(oaNotify.title,50)}
                                               </a>
                                            <span class="pull-right text-muted small">${fns:getTime(oaNotify.updateDate)}前</span>
                                        </div>

								</c:forEach>--%>

                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="text-center link-block">
                                       您有${count }条未读消息 <a  title="我的消息通知" class="J_menuItem" href="${ctx}/xw_tools/xwPushmessage/self ">
                                            <strong>查看所有 </strong>
                                            <i class="fa fa-angle-right"></i>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>

                      <!-- 国际化功能预留接口 -->
                        <li class="dropdown">
							<a id="lang-switch" class="lang-selector dropdown-toggle" href="#" data-toggle="dropdown" aria-expanded="true">
								<span class="lang-selected">
										<img  class="lang-flag" onerror=" this.src = '${ctxStatic}/images/userinfobig.jpg' " src="${ctxStatic}/common/img/china.png" alt="中国">
										<span class="lang-id">中国</span>
										<span class="lang-name">中文</span>
									</span>
							</a>

							<!--Language selector menu-->
							<ul class="head-list dropdown-menu with-arrow">
								<li>
									<!--English-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/china.png" alt="中国">
										<span class="lang-id">中国</span>
										<span class="lang-name">中文</span>
									</a>
								</li>
								<%--<li>
									<!--English-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/united-kingdom.png" alt="English">
										<span class="lang-id">EN</span>
										<span class="lang-name">English</span>
									</a>
								</li>
								<li>
									<!--France-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/france.png" alt="France">
										<span class="lang-id">FR</span>
										<span class="lang-name">Français</span>
									</a>
								</li>
								<li>
									<!--Germany-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/germany.png" alt="Germany">
										<span class="lang-id">DE</span>
										<span class="lang-name">Deutsch</span>
									</a>
								</li>
								<li>
									<!--Italy-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/italy.png" alt="Italy">
										<span class="lang-id">IT</span>
										<span class="lang-name">Italiano</span>
									</a>
								</li>
								<li>
									<!--Spain-->
									<a class="lang-select">
										<img class="lang-flag" src="${ctxStatic}/common/img/spain.png" alt="Spain">
										<span class="lang-id">ES</span>
										<span class="lang-name">Español</span>
									</a>
								</li>--%>
							</ul>
						</li>
                    </ul>
                </nav>
            </div>





            <div class="row content-tabs">



                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">



                     <div class="page-tabs-content">

                        <a href="javascript:;" class="active J_menuTab" data-id="${ctx}/home">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose"  data-toggle="dropdown">关闭操作<span class="caret"></span>

                    </button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>
                <a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/home" frameborder="0" data-id="${ctx}/home" seamless></iframe>
            </div>
            <div class="footer">
                <%--<div class="pull-left"><a href="http://www.kite.org">http://www.kite.org</a> &copy; 2017-2025</div>--%>
            </div>
        </div>
        <!--右侧部分结束-->

    </div>
</body>

<!-- 语言切换插件，为国际化功能预留插件 -->
<script type="text/javascript">

$(document).ready(function(){

	$("a.lang-select").click(function(){
		$(".lang-selected").find(".lang-flag").attr("src",$(this).find(".lang-flag").attr("src"));
		$(".lang-selected").find(".lang-flag").attr("alt",$(this).find(".lang-flag").attr("alt"));
		$(".lang-selected").find(".lang-id").text($(this).find(".lang-id").text());
		$(".lang-selected").find(".lang-name").text($(this).find(".lang-name").text());

	});


});

function changeStyle(){
   $.get('${pageContext.request.contextPath}/theme/ace?url='+window.top.location.href,function(result){   window.location.reload();});
}

</script>

</html>