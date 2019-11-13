<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/webpage/include/taglib.jsp"%>

<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
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

    <script src="${ctxStatic}/encode/jquery.base64.js"></script>

    <script type="text/javascript" >
        $(document).ready(function() {

            $("#emali").css("pointer-events","none");

            showSms();

        });

        //显示账号
        function showSms(){
            $(".login-form .login-box").css("visibility","visible");
            $(".form .item-fore2").css("visibility","visible");
            $(".login-form .login-box").css("display","");
            $(".form .item-fore2").css("display","");

            $(".login-form .emali").css("display","none");
            $(".login-form .emali").css("visibility","hidden");
        };

        function submit() {
            console.log("确认提交");

            var phone = $("#phone").val();
            var password = $("#password").val();
            var confirmPassword = $("#confirmPassword").val();

            if(password === null || password == undefined || password == '') {
                $("#font").text("密码不能为空");
                return;
            }

            if(password.length < 6) {
                $("#font").text("密码不能小于6位数");
                return;
            }

            if(confirmPassword === null || confirmPassword == undefined || confirmPassword == '') {
                $("#font").text("确认密码不能为空");
                return;
            }

            if( password != confirmPassword ){
                $("#font").text("两次输入密码不一致");
                return;
            }

            var phoneEncode = $.base64.encode(phone);
            var passwordEncode = $.base64.encode(password);
            var confirmPasswordEncode = $.base64.encode(confirmPassword);

            console.log(phoneEncode);
            console.log(passwordEncode);
            console.log(confirmPasswordEncode);

            $("#encodePhone").val(phoneEncode);
            $("#encodepasword").val(passwordEncode);

            $("#inputForm").submit();
        }

        function check(){
            var a = $("#password").val();
            var b = $("#confirmPassword").val();
            if( a != b ){
                $("#font").text("两次输入密码不一致");
            }else {
                $("#font").text("");
            }
        }

        function checkLength(){
            var password = $("#password").val();

            if(password === null || password == undefined || password == '') {
                $("#font").text("密码不能为空");
                return;
            }else {
                $("#font").text("");
            }

            if(password.length < 6) {
                $("#font").text("密码不能小于6位数");
                return;
            }else {
                $("#font").text("");
            }

        }

        function newPasswordMethod(password) {
            var reg =/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{6,32}$/;
            if(!reg.test(password)){
                alert('密码必须由6-32位数字以及大小写字母组成');
                $("#newPassword").val('');
            }
        }
        function confirmNewPasswordMethod(password) {
            var reg =/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{6,32}$/;
            if(!reg.test(password)){
                alert('密码必须由6-32位数字以及大小写字母组成');
                $("#confirmNewPassword").val('');
            }
        }
    </script>


</head>
<body>

<!-- SDK 登录 -->
<div class="w">
    <div id="logo">
        <img src="${ctxStatic}/loginStyle/images/logo-201305-b.png" alt="XBin" width="170" height="60">
        <b></b>
    </div>
</div>

<div id="content">
    <div class="login-wrap">
        <div class="w">
            <div class="login-form" style="height: 400px">

                <div class="login-tab login-tab-l">
                    <a href="#" id="sms" onclick="showSms()" clstag="pageclick|keycount|201607144|2">短信验证</a>
                </div>
                <div class="login-tab login-tab-r">
                    <a href="#" id="emali" onclick="" style="color:#9e9e9e" clstag="pageclick|keycount|201607144|2">邮箱验证</a>
                </div>

                <div class="emali">
                    <div class="mc" >
                        <div  class="emali-main" style="margin-top: 70px; position: relative">
                            <div id="code">

                            </div>
                        </div>
                    </div>
                </div>

                <div class="login-box" style="padding:10px 20px 0px 20px;">
                    <div class="mt tab-h">
                    </div>
                    <div class="msg-wrap">
                    </div>
                    <div class="mc">


                            <input id="phone" hidden="hidden" name="phone" value="${phone}">
                            <fieldset>
							     <span style="margin-left:19px;" >密码：</span>
                                 <input id="password" name="password" style="margin-left:19px;" onchange="newPasswordMethod(this.value)"  type="password" class="form-control" placeholder="请输入密码" oninput="checkLength()"/>

                                    <br/>

                                 <span style="margin-left:19px;" >确认密码：</span>
                                 <input id="confirmPassword" name="confirmPassword" type="password" onchange="confirmNewPasswordMethod(this.value)"   style="margin-left:19px;"  class="form-control" placeholder="请确认密码" oninput="check()"/>
                            </fieldset>
                            <span style="margin-left:19px;"><font color="red" id="font"></font></span>

                        <form:form id="inputForm"  action="${ctx}/sys/user/resetPasswordBySms"  method="post" class="form-horizontal form-group">
                            <input type="hidden" id="encodePhone" name="encodePhone" >
                            <input type="hidden" id="encodepasword" name="encodepasword">
                        </form:form>

                        <br/>
                        <div class="login-btn" style="margin-left: 4px;text-align: center">
                            <a href="#" onclick="submit()" class="btn-img btn-entry"  tabindex="6"
                               style="text-decoration:none;">
                                <font id="nextFont" color="white">
                                    确&nbsp;&nbsp;认
                                </font>
                            </a>
                        </div>

                        <br/>
                    </div>

                </div>


                <div style="text-align:center;position:relative;top:25px">
                    <a style="color:#FF0000;text-decoration:none;">${message}</a>
                </div>

            </div>



        </div>

        <div class="login-banner" clstag="pageclick|keycount|20150112ABD|46" style="background:url(${ctxStatic}/loginStyle/images/xuanwu.jpg);background-size: cover;
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
            Copyright&nbsp;&copy;&nbsp;2017-2027&nbsp;&nbsp;kite&nbsp;版权所有
            &nbsp;&nbsp;备案号:<a href="http://www.miitbeian.gov.cn" target="blank">粤B2-20030242号</a>
            &nbsp;&nbsp;公安备案号:<a href="https://www.beian.gov.cn" target="blank">44010602003988</a>
        </div>
    </div>
</div>

</div>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">

                <div>建议使用谷歌Chrome浏览器访问</div>
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
