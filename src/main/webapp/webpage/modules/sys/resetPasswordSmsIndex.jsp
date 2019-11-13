<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>
	<meta name="description" content="User login page" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<script src="${ctxStatic}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.14.0/jquery.validate.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.14.0/localization/messages_zh.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/bootstrap/3.3.4/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/bootstrap/3.3.4/js/bootstrap.min.js"  type="text/javascript"></script>
	<link href="${ctxStatic}/awesome/4.4/css/font-awesome.min.css" rel="stylesheet" />
	<!-- jeeplus -->
	<%--<link href="${ctxStatic}/common/jeeplus.css" type="text/css" rel="stylesheet" />--%>
	<%--<script src="${ctxStatic}/common/jeeplus.js" type="text/javascript"></script>--%>
	<link rel="shortcut icon" href="images/favicon.png" type="image/png">
	<!-- text fonts -->
	<link rel="stylesheet" href="${ctxStatic }/common/login/ace-fonts.css" />

	<!-- ace styles -->
	<link rel="stylesheet" href="${ctxStatic }/common/login/ace.css" />

	<!-- 引入layer插件 -->
	<script src="${ctxStatic}/layer-v2.3/layer/layer.js"></script>
	<script src="${ctxStatic}/layer-v2.3/layer/laydate/laydate.js"></script>

	<script src="${ctxStatic}/block/jigsaw.js"></script>
	<link rel="stylesheet" href="${ctxStatic }/css/jigsaw.css" />

	<!--[if lte IE 9]>
	<link rel="stylesheet" href="../assets/css/ace-part2.css" />

	<![endif]-->
	<link rel="stylesheet" href="${ctxStatic }/common/login/ace-rtl.css" />

	<script src="${ctxStatic}/encode/jquery.base64.js"></script>

	<style type="text/css">

		.bound{
			background-color: white;
			background-color: rgba(255, 255, 255, 0.95);
			border-radius: 40px;
		}


		.container {
			position: relative;
			width: 310px;
			/*上右下左*/;
			margin:15px 0px 1px 0px;
		}

	</style>
	<title>忘记密码</title>




	<script type="text/javascript">
        $(document).ready(function() {

            $("#nextA").css("pointer-events","none");
        });
        function next() {

            <%--var phone = $("#phone").val();--%>
            <%--var phoneEncode = $.base64.encode(phone);--%>

			<%--var url = '${ctx}/sys/user/openIdentifyCode?encodePhone=' + phoneEncode;--%>
            <%--$("#nextA").attr("href",url);--%>

			<%--$("#nextA")[0].click();--%>

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
					$("#point").show();
                    $("#span").show();
                    $("#captchaFont").attr("color","red").text("完成拼图校验,可重新发送验证码");
				}
			});

            // $("#point").show();
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


	</script>
</head>


<body class="login-layout light-login">
<div class="main-container">
	<div class="main-content">
		<div class="row">
			<div class="col-sm-10 col-sm-offset-1">
				<div class="login-container">

					<div class="center">
						<h1>
							<br/>
							<br/>
						</h1>
					</div>

					<div class="space-6"></div>

					<div class="position-relative">

						<span style="color: red">${message}</span>
							<div id="forgot-box" class="forgot-box widget-box no-border bound visible">
								<div class="widget-body bound">
									<div class="widget-main bound">
										<h4 class="header blue lighter bigger">
											<i class="ace-icon fa fa-key"></i>
											找回密码
										</h4>

										<form id="resetForm" action="${ctx}/sys/user/openResetPassword" method="post">
											<div>
												<div>
													<span >
														<input style="margin-left: 15px;margin-right: 15px;width: 270px;" id="phone" name="phone"   type="tel" class="form-control  required" placeholder="请输入您的账号信息" />
													</span>
												</div>

												<div class="container" >
													<font id="captchaFont" color="gray">完成拼图校验,获取验证码</font>
													<div id="captcha" style="position: relative"></div>
												</div>

											</div>

											<div  class="sliderContainer" id="sliderContainerHiden" style="margin-left: 15px" hidden="hidden">
												<div class="sliderMask"  style="width: 0px;">
													<div class="slider" style="left: 0px;">
														<span class="sliderIcon"></span></div>
												</div>
												<span class="sliderText" id="sliderTextHidden"></span>
											</div>
										</form>

										<span id="point" hidden="hidden" style="text-align: center">
											<font color="#00cc00" size="1">短信已经发送成功，请留意您的手机，点击下一步验证</font>
										</span>


										<div style="text-align:center">
											<br/>
											<span style="text-align:center">
												<input id="identifyingCode" name="identifyingCode" style="margin-left: 92px; width: 120px;"   class="form-control  required" placeholder="请输入验证码" />
											
											</span>
										</div>
										<span id="span" class="bigger-160">

											<a id="nextA" href="#" onclick="next()"  data-target="#login-box" style="text-decoration:none;text-align: center;display: block;">
												<font id="nextFont" color="gray">
														下一步
												</font>
											</a>
											<span style="color: red">${message}</span>
										</span>

									</div><!-- /.widget-main -->

								</div><!-- /.widget-body -->
							</div><!-- /.forgot-box -->


						</div><!-- /.position-relative -->
						<div class="center">
							<%--	<h4 id="id-company-text"><font color="#A90E0E">&copy; www.jeeplus.org</font></h4>--%>
						</div>

					</div>
				</div><!-- /.col -->
			</div><!-- /.row -->
		</div><!-- /.main-content -->
	</div><!-- /.main-container -->

	<!-- basic scripts -->

	<!--[if !IE]> -->
	<script type="text/javascript">
        window.jQuery || document.write("<script src='../assets/js/jquery.js'>"+"<"+"/script>");
	</script>

	<!-- <![endif]-->

	<!--[if IE]>
	<script type="text/javascript">
		window.jQuery || document.write("<script src='../assets/js/jquery1x.js'>"+"<"+"/script>");
	</script>
	<![endif]-->
	<script type="text/javascript">
        if('ontouchstart' in document.documentElement) document.write("<script src='../assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
	</script>
	<style>
		/* Validation */
		label.error {
			color: #cc5965;
			display: inline-block;
			margin-left: 5px;
		}

		.form-control.error {
			border: 1px dotted #cc5965;
		}
	</style>
<script>
    jigsaw.init(document.getElementById('captcha'), function () {
        sendCode();
        this.reset();

        //设置蓝色
        $("#nextFont").attr("color","#337ab7");

        //设置可点击
        $("#nextA").css("pointer-events","auto");
    })


</script>

</body>
</html>
