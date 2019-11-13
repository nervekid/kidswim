<%@ page contentType="text/html;charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
  <head>
      <meta name="decorator" content="default"/>
    <title>多例回显</title>
	<link href="${ctxStatic}/fileUpload/css/webuploader.css" rel="stylesheet" />
	<link href="${ctxStatic}/fileUpload/css/cxuploader.css" rel="stylesheet" />
	<link href="${ctxStatic}/fileUpload/css/common.css" rel="stylesheet" />
      <script type="text/javascript" src="${ctxStatic}/fileUpload/webuploader.js"></script>
      <script type="text/javascript" src="${ctxStatic}/fileUpload/userUploader.js"></script>

  </head>

  <body>
		<div class="content">			
			<ul>
				<li>
					<div class="uploder-container">										      		
                        <p> 请上传简历类文档</p>
                        <div  class="cxuploder">
                            <div class="queueList">
                                <div class="placeholder">
                                    <div class="filePicker"></div>
                                    <p>将简历类拖到这里</p>
                                </div>
                            </div>
                            
                            <div class="statusBar" style="display:none;">
                                
                                
                                <div class="btns">
                                    <div  class="jxfilePicker"></div>	

                                </div>
                                <div class="info"></div>
                            </div>
                        </div>
                    </div>

				</li>
				<li>
					<div class="uploder-container">										      		
                        <p> 请上传证书类文档</p>
                        <div  class="cxuploder">
                            <div class="queueList">
                                <div class="placeholder">
                                    <div class="filePicker"></div>
                                    <p>将证书类拖到这里</p>
                                </div>
                            </div>
                            
                            <div class="statusBar" style="display:none;">
                                
                                
                                <div class="btns">
                                    <div  class="jxfilePicker"></div>	

                                </div>
                                <div class="info"></div>
                            </div>
                        </div>
                    </div>
				</li>

			</ul>
		</div>
  </body>
  

</html>
