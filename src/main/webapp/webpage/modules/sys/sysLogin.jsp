<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/webpage/include/taglib.jsp"%>

<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/images/favicon.ico" type="image/x-icon" />
    <title>欢迎登录</title>
    <script>
    $(document).ready(function() {
        if (window.top !== window.self) {
            window.top.location = window.location;
        }
    });
    </script>
    <!-- Start: injected by Adguard -->

    <!-- End: injected by Adguard -->
    <link rel="icon"/>
    <link type="text/css" rel="stylesheet"
          href="${ctxStatic}/loginStyle/css/login.css"
          source="widget"/>
   <%-- <script type="text/javascript" src="${ctxStatic}/loginStyle/js/jquery-1.6.4.js"></script>--%>
    <script src="${ctxStatic}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery-validation/1.14.0/jquery.validate.js" type="text/javascript"></script>
    <script src="https://rescdn.qqmail.com/node/ww/wwopenmng/js/sso/wwLogin-1.0.0.js" type="text/javascript" charset="utf-8"></script>
    <script src="${ctxStatic}/bootstrap/3.3.4/js/bootstrap.min.js"  type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap/3.3.4/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript"  src="${ctxStatic}/bootstrap3-dialog/js/bootstrap-dialog.min.js"></script>
    <link href="${ctxStatic}/bootstrap3-dialog/css/bootstrap-dialog.min.css" rel="stylesheet">
    <script src="${ctxStatic}/layer-v2.3/layer/layer.js"></script>
    <script src="${ctxStatic}/layer-v2.3/layer/laydate/laydate.js"></script>
     <script type="text/javascript" >
     $(document).ready(function() {

    	 $.browser.chrome = /chrom(e|ium)/.test(navigator.userAgent.toLowerCase());
         if ($.browser.chrome) {
         }
         else {
        	 $('#myModal').modal();

         }

    	   //显示二维码
    	    function showQrCode(){
    	    	$(".login-form .login-box").css("visibility","hidden");
           	    $(".form .item-fore2").css("visibility","hidden");
           	    $(".login-form .login-box").css("display","none");
        	    $(".form .item-fore2").css("display","none");

           		 $(".login-form .qrcode-login").css("display","");
           		 $(".login-form .qrcode-login").css("visibility","visible");

    	    };
    	    //显示账号
    	    function showLogin(){
    	    	$(".login-form .login-box").css("visibility","visible");
           	    $(".form .item-fore2").css("visibility","visible");
           	    $(".login-form .login-box").css("display","");
        	    $(".form .item-fore2").css("display","");

	           	 $(".login-form .qrcode-login").css("display","none");
	           	 $(".login-form .qrcode-login").css("visibility","hidden");

    	    };
    	    //初始显示二维码
            showLogin();
    	    //showQrCode();


	    	$("#saoma").click(function(){
	    		 showQrCode();
	    	});

	    	$("#zhanghao").click(function(){
	    		showLogin();
	    	});
	    	$("#submit").click(function(){
                if($('#loginname').val()=='' || $('#password').val()==''){
                    top.layer.alert("用户名和密码都不能为空", {icon: 0});//讨厌的白色字体问题
                    return ;
                }
                $("#loginForm").submit();
	    	});


             var message =  '${message}';
             if(message!=''){
                 top.layer.alert(message, {icon: 0});//讨厌的白色字体问题

                 var username='${username}';
                 $(".qrcode-login").css('display','none');
                 $(".login-box").css('display','block');
                 $(".qrcode-login").css('visibility','hidden');
                 $(".login-box").css('visibility','visible');
                 $("#entry").css("visibility","visible");
                 $("#entry").css("display","block");
                 $("#loginname").val(username);
             }

     });
     // 如果在框架或在对话框中，则弹出提示并跳转到首页
     if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
         alert('未登录或登录超时。请重新登录，谢谢！');
         top.location = "${ctx}";
     }

     function keyDownEnter(e){
         var ev= window.event||e;
         if (ev.keyCode == 13) {
             $("#submit").click()
         }
     }
     </script>


</head>
<body>

<!-- SDK 登录 -->
<div class="w">
    <div id="logo">
            <img src="${ctxStatic}/loginStyle/images/swimlog.jpg" alt="XBin" width="170" height="60">
        <b></b>
    </div>
    <p style="padding-top: 21px;padding-left:30px;font-size: 24px;"><b>&nbsp;&nbsp;歡迎登陸 kidswim管理系統</b></p>
</div>

<div id="content">
    <div class="login-wrap">
        <div class="w">
            <div class="login-form">

              <%--  <div class="login-tab login-tab-l">

                    <a href="javascript:void(0)" id="saoma" clstag="pageclick|keycount|201607144|1">微信登录</a>
                </div>--%>
                <div class="login-tab login-tab-r"  style="width:345px;margin: auto">
                    <a href="javascript:void(0)" id="zhanghao" clstag="pageclick|keycount|201607144|2">手机登录</a>
                </div>
                 <div class="qrcode-login">
                    <div class="mc" >

                        <div  class="qrcode-main" style="margin-bottom: 12px">
                           <%--     <div id="code"></div>--%>
	 <script >   window.WwLogin({
         "id" : "code",  //显示二维码的容器id
         "appid" : "wx9ef3606633c222d5",//企业微信的cropID，在 企业微信管理端->我的企业 中查看
         "agentid" : "1000037",
         "redirect_uri" :"http%3a%2f%2fkite.wxchina.com%2fxw_qyh%2fsendmessage%2fload",   //重定向地址，需要进行UrlEncode
         "state" : "3828293919281",   //用于保持请求和回调的状态，授权请求后原样带回给企业。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议企业带上该参数
         "href" : "https://kite.wxchina.com/static/loginStyle/css/wxqrcode.css",    //自定义样式链接，企业可根据实际需求覆盖默认样式。详见文档底部FAQ

 });</script>

                        </div>

                    </div>
                </div>


                <div class="login-box" style="padding:10px 20px 0px 20px">

                    <div class="mt tab-h">
                    </div>
                    <div class="msg-wrap">
                        <div class="msg-warn hide"><b></b>公共场所不建议自动登录，以防账号丢失</div>
                        <div class="msg-error hide"><b></b></div>
                    </div>
                    <div class="mc">
                        <div class="form">
                            <form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
                                <input id="returnUrl" type="hidden" name="returnUrl" value="${returnUrl}"/>
                                <div class="item item-fore1">
                                    <label for="loginname" class="login-label name-label"></label>
                                    <input id="loginname" type="text" class="itxt" name="username" tabindex="1"
                                           autocomplete="off" style="padding: 10px 0px 10px 50px;line-height:36px;height:36px;"
                                           placeholder="手机号码" onkeydown="keyDownEnter(event)"  />
                                    <span class="clear-btn"></span>
                                </div>

                                <div id="entry" class="item item-fore2">
                                    <label class="login-label pwd-label" for="password"></label>
                                    <input type="password" id="password" name="password" class="itxt itxt-error" style="padding: 10px 0px 10px 50px;line-height:36px;height:36px;"
                                           tabindex="2" autocomplete="off" placeholder="密码" onkeydown="keyDownEnter(event)" />
                                    <span class="clear-btn"></span>
                                    <span class="capslock"><b></b>大小写锁定已打开</span>
                                </div>

                                <c:if test="${isValidateCodeLogin}">
                                    <div  class="item item-fore3">
                                        <label for="validateCode">验证码</label>
                                        <sys:validateCode name="validateCode"  inputCssStyle="margin-bottom:5px;padding-left:10px"/>
                                    </div>
                                </c:if>
                                <div class="item item-fore4">
                                    <input  type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''} class="ace" />
                                    <span class="lbl"> 记住我</span>

                                    <%--<a href="${ctx}/sys/user/resetPasswordIndex"  class="pull-right" style="margin-right: -30px">--%>
                                        <%--<font color="#337ab7"><i class="ace-icon fa fa-arrow-left"></i>--%>
                                            <%--忘记密码-邮箱</font>--%>
                                    <%--</a>--%>
                                    <%--<br/>--%>
                                    <%--<a href="${ctx}/sys/user/resetPasswordSmsIndex"  class="pull-right" style="margin-right: -30px">--%>
                                        <%--<font color="#337ab7"><i class="ace-icon fa fa-arrow-left"></i>--%>
                                            <%--忘记密码-短信</font>--%>
                                    <%--</a>--%>
                                    <%--<br/>--%>
                                   <%-- <a href="${ctx}/sys/user/resetPasswordChoose"  class="pull-right" style="margin-right: -30px;text-decoration:none;">
                                        <font color="#337ab7"><i class="ace-icon fa fa-arrow-left"></i>
                                            忘记密码</font>
                                    </a>--%>
                                </div>





                                <div class="item item-fore5">
                                    <div class="login-btn">
                                        <a href="javascript:;" class="btn-img btn-entry" id="submit" tabindex="6"
                                           clstag="pageclick|keycount|201607144|3">登&nbsp;&nbsp;&nbsp;&nbsp;录</a>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>

                </div>
                <div style="text-align:center;position:relative;top:25px"> <a style="color:#FF0000;text-decoration:none;">建议使用Google Chrome浏览器访问</a></div>
            </div>

        </div>

        <div class="login-banner" clstag="pageclick|keycount|20150112ABD|46" style="background:url(${ctxStatic}/loginStyle/images/backgrounppic.jpg);background-size: cover;
                width: 100%;">
            <div class="w">
                <div id="banner-bg" class="i-inner">
                </div>
            </div>

        </div>

    </div>
</div>


<div class="w">
    <div id="footer-2013">
        <div class="links">
            <%--<a rel="nofollow" target="_blank" href="//www.jd.com/intro/about.aspx">
                关于我们
            </a>
            |
            <a rel="nofollow" target="_blank" href="//www.jd.com/contact/">
                联系我们
            </a>
            |--%>
            <a rel="nofollow" target="_blank" href="http://oa.wxchina.com:7890/oa/themes/mskin/login/login.jsp">
                登录OA
            </a>
            |
            <a rel="nofollow" target="_blank" href="http://www.wxchina.com/#page1">
                玄武官网
            </a>

            |
            <a rel="nofollow" target="_blank" href="http://mail.wxchina.com/#lang=cn">
                邮箱地址
            </a>

        </div>
        <div class="copyright">
            Copyright&nbsp;&copy;&nbsp;2017-2027&nbsp;&nbsp;kidswim&nbsp;版權所有
            &nbsp;&nbsp;聯系地址:<a href="http://www.miitbeian.gov.cn" target="blank">粤B2-20030242号</a>
            &nbsp;&nbsp;聯系號碼:<a href="https://www.beian.gov.cn" target="blank">44010602003988</a>
        </div>
    </div>
</div>

</div>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
          <div class="modal-dialog" role="document">
              <div class="modal-content">
               <div class="modal-header">

 				<div>建議使用谷歌Chrome瀏覽器訪問</div>
	 				<div class="modal-footer">
                     <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
             		</div>
         </div>
     </div>
</div>
</div>

<!-- SDK 登录 -->
</body>
</html>
