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
	<link href="${ctxStatic}/common/jeeplus.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/common/jeeplus.js" type="text/javascript"></script>
	<link rel="shortcut icon" href="images/favicon.png" type="image/png">
	<!-- text fonts -->
	<link rel="stylesheet" href="${ctxStatic }/common/login/ace-fonts.css" />

	<!-- ace styles -->
	<link rel="stylesheet" href="${ctxStatic }/common/login/ace.css" />

	<!-- 引入layer插件 -->
	<script src="${ctxStatic}/layer-v2.3/layer/layer.js"></script>
	<script src="${ctxStatic}/layer-v2.3/layer/laydate/laydate.js"></script>


	<!--[if lte IE 9]>
	<link rel="stylesheet" href="../assets/css/ace-part2.css" />
	<![endif]-->
	<link rel="stylesheet" href="${ctxStatic }/common/login/ace-rtl.css" />
	<style type="text/css">

		.bound{
			background-color: white;
			background-color: rgba(255, 255, 255, 0.95);
			border-radius: 40px;
		}
	</style>
	<title>忘记密码</title>

	<script type="text/javascript">
        $(document).ready(function() {
            $('#sendPassBtn').click(function () {
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

                var count = 60;
                var countdown = setInterval(CountDown, 1000);
					function CountDown() {
						$("#sendPassBtn").attr("disabled", true);
						$("#sendPassBtn").html("等待 " + count + "秒!");
						if (count == 0) {
							$("#sendPassBtn").html("重新发送").removeAttr("disabled");
							clearInterval(countdown);
						}
						count--;
					}
                }) ;
		}) ;
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
							<%--<img src="${ctxStatic }/common/login/images/logo.png" style="width:280px">--%>
							<br>
						</h1>
					</div>

					<div class="space-6"></div>

					<div class="position-relative">

							<div id="forgot-box" class="forgot-box widget-box no-border bound visible">
								<div class="widget-body bound">
									<div class="widget-main bound">
										<h4 class="header blue lighter bigger">
											<i class="ace-icon fa fa-key"></i>
											找回密码
										</h4>

										<div class="space-6"></div>
										<p>
											请输入您的登录名以及邮箱地址（公司内部邮箱）。
										</p>

										<form id="resetForm" action="${ctx}/sys/user/">
											<fieldset>
												<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="loginName" name="loginName" style="" type="tel" class="form-control  text-muted required" placeholder="请输入登录名" />
															<i class="ace-icon fa fa-phone"></i>
														</span>
														<br/>
														<span class="block input-icon input-icon-right">
															<input id="email" name="email" type="tel" class="form-control  text-muted required" placeholder="请输入邮箱地址" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
												</label>

												<div class="clearfix">
													<button id="sendPassBtn" type="button" class="width-35 pull-right btn btn-sm btn-success">
														<i class="ace-icon fa fa-lightbulb-o"></i>
														<span class="bigger-110">发送!</span>
													</button>
												</div>
											</fieldset>
										</form>
										<div class="form-options center">
											<br/>
											<br/>
											<a href="${ctx}/a/login" data-target="#login-box" class="">
												<font color="#337ab7"><i class="ace-icon fa fa-arrow-left"></i>
													返回登录
												</font>
											</a>
										</div>
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
</body>
</html>
