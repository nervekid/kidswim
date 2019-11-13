<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>系统模块配置查看</title>
	<meta name="decorator" content="default"/>
</head>
<body >
	<div class="print-box">
	    <div id="print">
			 <div class="print-title">
				系统模块配置
			</div>
			<div class="print-info">
				<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tr>
								<th class="formTitle">系统模块名称：</th>
								<td class="formValue" style="padding-right: 20px;">
					<input type="text" readonly="readonly" class="form-control" value="${sysModuleConfig.name}" />
								</td>
								<th class="formTitle">系统图片：</th>
								<td class="formValue">
					<input type="text" readonly="readonly" class="form-control" value="${sysModuleConfig.image}" />
								</td>
							</tr>
							<tr>
								<th class="formTitle">系统连接：</th>
								<td class="formValue" style="padding-right: 20px;">
					<input type="text" readonly="readonly" class="form-control" value="${sysModuleConfig.url}" />
								</td>
					<th class="formTitle"></th>
					<td class="formValue"></td>
		  		</tr>
				</table>
			</div>
        </div>
	</div>
</body>
</html>