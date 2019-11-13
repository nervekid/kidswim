<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>定时任务查看</title>
	<meta name="decorator" content="default"/>
</head>
<body >
	<div class="print-box">
	    <div id="print">
			 <div class="print-title">
				定时任务
			</div>
			<div class="print-info">
				<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tr>
								<th class="formTitle">cron表达式：</th>
								<td class="formValue" style="padding-right: 20px;">
					<input type="text" readonly="readonly" class="form-control" value="${taskScheduleJob.cronExpression}" />
								</td>
								<th class="formTitle">任务调用的方法名：</th>
								<td class="formValue">
					<input type="text" readonly="readonly" class="form-control" value="${taskScheduleJob.methodName}" />
								</td>
							</tr>
							<tr>
								<th class="formTitle">任务是否有状态：</th>
								<td class="formValue" style="padding-right: 20px;">
					<input type="text" readonly="readonly" class="form-control" value="${taskScheduleJob.isConcurrent}" />
								</td>
								<th class="formTitle">任务描述：</th>
								<td class="formValue">
					<input type="text" readonly="readonly" class="form-control" value="${taskScheduleJob.description}" />
								</td>
							</tr>
							<tr>
								<th class="formTitle">任务执行时调用哪个类的方法 包名+类名：</th>
								<td class="formValue" style="padding-right: 20px;">
					<input type="text" readonly="readonly" class="form-control" value="${taskScheduleJob.beanClass}" />
								</td>
								<th class="formTitle">任务状态：</th>
								<td class="formValue">
					<input type="text" readonly="readonly" class="form-control" value="${taskScheduleJob.jobStatus}" />
								</td>
							</tr>
							<tr>
								<th class="formTitle">任务分组：</th>
								<td class="formValue" style="padding-right: 20px;">
					<input type="text" readonly="readonly" class="form-control" value="${taskScheduleJob.jobGroup}" />
								</td>
								<th class="formTitle">Spring bean：</th>
								<td class="formValue">
					<input type="text" readonly="readonly" class="form-control" value="${taskScheduleJob.springBean}" />
								</td>
							</tr>
							<tr>
								<th class="formTitle">任务名：</th>
								<td class="formValue" style="padding-right: 20px;">
					<input type="text" readonly="readonly" class="form-control" value="${taskScheduleJob.jobName}" />
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