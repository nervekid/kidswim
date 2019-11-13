<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/webpage/include/taglib.jsp"%>

<!DOCTYPE html>

<html>

<head>
  <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
	

    <title>H+ 后台主题UI框架 - 联系人</title>


	<script src="${pageContext.request.contextPath}/static/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
      <script src="${pageContext.request.contextPath}/static/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/TableDnD/jquery.tablednd.js"></script>
	<script src="${pageContext.request.contextPath}/static/common/contabs.js"></script> 
    
     <link href="${pageContext.request.contextPath}/static/css/animate.min.css" tppabs="http://www.zi-han.net/theme/hplus/css/animate.min.css" rel="stylesheet">
     <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css-v=3.3.5.css" tppabs="http://www.zi-han.net/theme/hplus/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/static/css/style.min.css-v=4.0.0.css" tppabs="http://www.zi-han.net/theme/hplus/css/style.min.css?v=4.0.0" rel="stylesheet"> 

</head>

<body class="gray-bg dashbard-1">


   <div class="row  border-bottom white-bg dashboard-header ">

         
            <a class="text-info" style="font-size:25px">欢迎登录玄武科技eHR系统
            </a>

    </div>

    <div class="wrapper wrapper-content  fadeInRight">
        <div class="row border-bottom" id="hhh">
            <c:forEach items="${list}" var="list">
               <div class="col-sm-2" id="list${list.menuUrl}" >
                <div class="contact-box"  >
                     <a class="J_menuItem" href="#" onclick='top.openTab("${ctx}/${list.menuUrl }","${list.title}", false)'>
                    
                     <br>
                       <br>
                        <div class="col-sm-16" align="center" >
                            <h3>${list.title }</h3>
                         
                      <br>
                           <br>
                    
                        </div>
                        <div class="clearfix"></div>
                    </a>
                </div>
            </div>
             
             </c:forEach>
            
            </div>
            </div>
        </div>
    </div>
   <script src="${pageContext.request.contextPath}/static/js/jquery.min.js-v=2.1.4" tppabs="http://www.zi-han.net/theme/hplus/js/jquery.min.js?v=2.1.4"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js-v=3.3.5" tppabs="http://www.zi-han.net/theme/hplus/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="${pageContext.request.contextPath}/static/js/content.min.js-v=1.0.0" tppabs="http://www.zi-han.net/theme/hplus/js/content.min.js?v=1.0.0"></script> 
     <script>
        $(document).ready(function(){
        	 $(".contact-box").each(function(){animationHover(this,"pulse")});
        	
        	
        	
        	}); 
      
    </script> 
    
  </body>

</html>