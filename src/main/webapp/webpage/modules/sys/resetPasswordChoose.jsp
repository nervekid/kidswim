<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/webpage/include/taglib.jsp"%>

<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <title>欢迎登录</title>
    <script>
        $(document).ready(function() {
            refreshCode();
            checkCookie();
            if (window.top !== window.self) {
                window.top.location = window.location;
            }
            /* $("#usercode").change(function(){
                 $("#fastlog").val('0');
             });
             $("#userpwd").change(function(){
                 $("#fastlog").val('0');
             });*/
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

    <script src="${ctxStatic}/block/jigsaw.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/css/jigsaw.css" />

    <script type="text/javascript" >
        $(document).ready(function() {

            $("#nextA").css("pointer-events","none");

            showSms();

        });

        //显示二维码
        function showEmali(){
            $(".login-form").css("height","300px");
            $(".login-form .login-box").css("visibility","hidden");
            $(".form .item-fore2").css("visibility","hidden");
            $(".login-form .login-box").css("display","none");
            $(".form .item-fore2").css("display","none");

            $("#emailDiv").css("display","");
            $("#emailDiv").css("visibility","visible");


        };
        //显示账号
        function showSms(){
            $(".login-form").css("height","300px");
            $(".login-form .login-box").css("visibility","visible");
            $(".form .item-fore2").css("visibility","visible");
            $(".login-form .login-box").css("display","");
            $(".form .item-fore2").css("display","");

            $("#emailDiv").css("display","none");
            $("#emailDiv").css("visibility","hidden");
        };

        function next() {

            var phoneVal = $("#phone").val();
            var code = $("#identifyingCode").val();

            //校验验证码是否为空
            if(code === null || code == undefined || code == '') {
                top.layer.alert("请填写验证码", {icon: 0});
                return;
            }

            //获取校验码
            $.get("${ctx}/sys/redis/get?key="+phoneVal,function(data){
                if(data.result == false){
                    top.layer.alert(data.message, {icon: 0});
                }else{
                    var dataCode = data.obj;

                    if(dataCode === null || dataCode == undefined || dataCode == '') {
                        top.layer.alert("验证码失效，请重新获取", {icon: 0});
                        return;
                    }

                    if(code != dataCode) {
                        top.layer.alert("验证码错误，请重新输入", {icon: 0});
                        return;
                    }else {
                        $("#resetForm").submit();
                    }
                }
            });

        }

        function sendCode() {



            //第一位是1开头，第二位则有3,4,5,7,8，第三位则是0-9，第三位之后则是数字0-9
            var search_str = /^1[34578][0-9]{9}$/;
            var phoneVal = $("#phone").val();
            if(!search_str.test(phoneVal)){
                top.layer.alert("请输入有效的账号！", {icon: 0});//讨厌的白色字体问题
                $('#phone').focus();
                return;
            }

            //调用发送验证码
            var phone = $("#phone").val();
            $.get("${ctx}/tools/sms/sendIdentifyCode?phone="+phone,function(data){
                if(data.result == false){
                    top.layer.alert(data.message, {icon: 0});
                }else{
                    top.layer.alert("短信已经发送成功，请留意您的手机", {icon: 0});
                    $("#span").show();
                    $("#captchaFont").attr("color","red").text("完成拼图校验,可重新发送验证码");
                }
            });
            //
            // $("#span").show();
            // $("#captchaFont").attr("color","red").text("完成拼图校验,可重新发送验证码");

            var count = 30;
            var countdown = setInterval(CountDown, 1000);
            $("#sliderContainer").hide();
            $("#sliderContainerHiden").show();

            function CountDown() {
                $("#sliderTextHidden").html("等待 " + count + "秒!");
                $("#phone").attr("readonly","readonly");
                if (count == 0) {
                    $("#sliderContainerHiden").hide();
                    $("#sliderContainer").show();
                    $("#phone").removeAttr("readonly");
                    clearInterval(countdown);
                }
                count--;
            }

        }

        function nextEmali() {

            var loginName = $("#loginName").val();
            var length = loginName.length;
            var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
            if(!(length == 11 && mobile.test(loginName))){
                top.layer.alert("请输入有效登录名！", {icon: 0});//讨厌的白色字体问题
                $('#loginName').focus();
                return;
            }
            var search_str = /^[\w\-\.]+@[\w\-\.]+(\.\w+)+$/;
            var email_val = $("#email").val();
            if(!search_str.test(email_val)){
                top.layer.alert("请输入有效邮箱地址！", {icon: 0});//讨厌的白色字体问题
                $('#email').focus();
                return;
            }
            $.get("${ctx}/sys/user/resetPasswordAjax?loginName="+loginName+"&email="+email_val,function(data){
                if(data.success == false){
                    top.layer.alert(data.msg, {icon: 0});//讨厌的白色字体问题
                    //alert(data.msg);
                    $("#sendPassBtn").html("重新发送").removeAttr("disabled");
                    clearInterval(countdown);
                }else{
                    //   top.layer.alert(data.msg, {icon: 0});//讨厌的白色字体问题

                    top.layer.confirm(data.msg, {
                        btn: ['确定'] //按钮
                    }, function (index) {
                        layer.close(index);
                        top.location = "${ctx}";
                    })

                }
            });

            var count = 30;
            var countdown = setInterval(CountDownEmali, 1000);

            function CountDownEmali() {
                $("#nextEmailA").css("pointer-events","none");
                $("#nextEmailA").html("等待 " + count + "秒!");
                $("#loginName").attr("readonly","readonly");
                $("#emali").attr("readonly","readonly");
                if (count == 0) {
                    $("#nextEmailA").html("下一步");
                    $("#nextEmailA").css("pointer-events","auto");
                    $("#loginName").removeAttr("readonly");
                    $("#emali").removeAttr("readonly");
                    clearInterval(countdown);
                }
                count--;
            }


        }

        function refreshCode(){
            console.log(11234);
            var verify=document.getElementById('checkcode1');
            verify.setAttribute('src','http://localhost:8082/kite_maven_war_exploded/getCheckCode?it='+Math.random());
        }
        //重新获取验证码
        function refresh(e) {
            e.src = e.src + "&time=" + new Date();
        }

        function  inputOnFocus( ob,v ) {
            console.log("function  inputOnFocus");
        }

        function getCheckCode() {
            //第一位是1开头，第二位则有3,4,5,7,8，第三位则是0-9，第三位之后则是数字0-9
            var search_str = /^1[345678][0-9]{9}$/;
            var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
            var phoneVal = $("#phone").val();
            if(!mobile.test(phoneVal)){
                top.layer.alert("请输入有效的手机号码！", {icon: 0});//讨厌的白色字体问题
                $('#phone').focus();
                return;
            }
            $("#getYAM").attr("disabled","disabled");
            $("#getYAM").val();
            console.log("getCheckCode");
            var sec = 0;
            var interval= setInterval(function () {
                $("#getYAM").val((60-sec)+"s后重新获取");
                sec++;
                if(sec > 59){
                    clearInterval(interval);
                    $("#getYAM").val("获取验证码");
                    $("#getYAM").removeAttr("disabled");
                }
            },1000);
            console.log(123456);
           $.get("${ctx}/tools/sms/sendIdentifyCode?phone="+phoneVal,function(data){
                if(data.result == false){
                    console.log(data);
                    top.layer.alert(data.message, {icon: 0});
                }else{
                    top.layer.alert("短信已经发送成功，请留意您的手机", {icon: 0});
                    $("#span").show();
                    $("#nextFont").attr("color","white");
                    $("#nextA").css("pointer-events","auto");
                }
            });
        }
        
        
    </script>

    <style type="text/css">
        .container {
            position: relative;
            width: 310px;
            /*上右下左*/;
            margin:15px 0px 1px 0px;
        }

    </style>

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
            <div class="login-form">

                <div class="login-tab login-tab-l">
                    <a href="#" id="zhanghao" onclick="showSms()" clstag="pageclick|keycount|201607144|2">短信验证</a>
                </div>
                <div class="login-tab login-tab-r">
                    <a href="#" id="emali" onclick="showEmali()" clstag="pageclick|keycount|201607144|2">邮箱验证</a>
                </div>

                <div class="login-box" id ="emailDiv">
                    <div class="mt tab-h">
                    </div>
                    <div class="msg-wrap">
                    </div>
                    <div class="mc" >
                        <div  class="emali-main" style="margin-top: 5px; position: relative">
                            <div class="mc" id="code">
                                <form id="resetEmaliForm" action="#">
                                    <div>
                                    <span>
                                         <span style="margin-left:19px;" >登陆账号：</span>
                                         <input id="loginName" name="loginName" type="tel" class="form-control" style="margin-left: 19px;width: 270px;" placeholder="请输入手机号码" />
                                                <i class="ace-icon fa fa-phone"></i>
                                    </span>
                                        <br/>
                                    </div>
                                    <div>
                                    <span >
                                        <span style="margin-left:19px;" >邮箱地址：</span>
                                        <input id="email" name="email" type="tel" class="form-control" style="margin-left: 19px;width: 270px;" placeholder="请输入邮箱地址" />
                                                <i class="ace-icon fa fa-envelope"></i>
                                    </span>
                                    </div>

                                </form>

                                <br/>
                                <div class="login-btn">
                                    <a id="nextEmailA" href="#" onclick="nextEmali()" class="btn-img btn-entry"  tabindex="6"
                                       style="text-decoration:none;">
                                        <font id="nextEmail" color="white"> 下一步</font>
                                    </a>
                                </div>
                                <span style="color: red">${message}</span>

                            </div>

                        </div>
                    </div>
                </div>


                <div class="login-box" style="padding:10px 20px 0px 20px">
                    <div class="mt tab-h">
                    </div>
                    <div class="msg-wrap">
                    </div>
                    <div class="mc">
                        <form id="resetForm" action="${ctx}/sys/user/openResetPassword" method="post">
                            <div>
                                <div>
                                    <span >
                                        <input style="margin-left: 20px;margin-right: 15px;width: 270px;" id="phone" name="phone"   type="tel" class="form-control  required" placeholder="请输入您的手机号码" />
                                    </span>
                                </div>

                                <div class="container" style="margin-top: 5px ">

                                  <%--  <font id="captchaFont" color="gray" style="margin-left: 7px; ">完成拼图校验,获取验证码</font>
                                    <div id="captcha" style="position: relative;margin-left: 7px;margin-top: 2px"></div>
                                    <p class="checkcode clearfix"><span></span>
                                        <input type="text" onfocus="inputOnFocus(this,'验证码')" onblur="inputOnBlur(this,'验证码')"   placeholder="验证码" id="checkcode" name="checkCode"  tabindex="3"  maxlength="5" />
                                        <img src="#" id="checkcode1" onclick="refreshCode()" title="看不清，换一个" />
                                    </p>--%>
                                    <%--  <form action="XX" method="get">
                                          验证码：<input type="text" name="checkcode"/>
                                          <img alt="点击更换验证码" id="imagecode" onclick="this.src='/sys/user/getCheckCode?random='+Math.random();"  src="/sys/user/getCheckCode"/>
                                          <input type="submit" value="提交">
                                      </form>--%>
                                </div>


                            </div>

                            <div  class="sliderContainer" id="sliderContainerHiden" style="margin-left: 20px" hidden="hidden">
                                <div class="sliderMask"  style="width: 0px;">
                                    <div class="slider" style="left: 0px;">
                                        <span class="sliderIcon"></span></div>
                                </div>
                                <span class="sliderText" id="sliderTextHidden"></span>
                            </div>
                        </form>

                        <div style="text-align:center">
                            <br/>
                            <span style="text-align:center">
                                <input id="identifyingCode" name="identifyingCode" style="margin-left: 20px;width: 270px;" maxlength="10"  class="form-control left required" placeholder="请输入验证码" />
                                <input   type="button"   id="getYAM" value="获取验证码"   onclick="getCheckCode()" style="width: 110px;  margin-right: -195px;margin-top: -30px" ></button>
                            </span>
                        </div>

                        <br/>

                        <div class="login-btn"  style="margin-top: 20px">
                            <a id="nextA" href="#" onclick="next()" class="btn-img btn-entry"  tabindex="6"
                               style="text-decoration:none;">
                                <font id="nextFont" color="gray" display: block;>
                                    下一步
                                </font>
                            </a>
                        </div>

                        <span style="color: red">${message}</span>

                    </div>
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

<script>
    jigsaw.init(document.getElementById('captcha'), function () {
        sendCode();
        this.reset();

        //设置蓝色
        $("#nextFont").attr("color","white");

        //设置可点击
        $("#nextA").css("pointer-events","auto");
    })


</script>
</body>
</html>
