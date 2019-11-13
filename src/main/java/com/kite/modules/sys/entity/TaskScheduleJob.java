/**
 * MouTai
 */
package com.kite.modules.sys.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 定时任务Entity
 * @author cxh
 * @version 2018-10-11
 */
public class TaskScheduleJob extends DataEntity<TaskScheduleJob> {
	
	private static final long serialVersionUID = 1L;
	private String cronExpression;		// cron表达式
	private String methodName;		// 任务调用的方法名
	private String isConcurrent;		// 任务是否有状态
	private String description;		// 任务描述
	private String beanClass;		// 任务执行时调用哪个类的方法 包名+类名
	private String jobStatus;		// 任务状态
	private String jobGroup;		// 任务分组
	private String springBean;		// Spring bean
	private String jobName;		// 任务名
	
	public TaskScheduleJob() {
		super();
	}

	public TaskScheduleJob(String id){
		super(id);
	}

	@ExcelField(title="cron表达式", align=2, sort=1)
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	@ExcelField(title="任务调用的方法名", align=2, sort=2)
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	@ExcelField(title="任务是否有状态", align=2, sort=3)
	public String getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}
	
	@ExcelField(title="任务描述", align=2, sort=4)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ExcelField(title="任务执行时调用哪个类的方法 包名+类名", align=2, sort=6)
	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	
	@ExcelField(title="任务状态", align=2, sort=8)
	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	
	@ExcelField(title="任务分组", align=2, sort=9)
	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	
	@ExcelField(title="Spring bean", align=2, sort=12)
	public String getSpringBean() {
		return springBean;
	}

	public void setSpringBean(String springBean) {
		this.springBean = springBean;
	}
	
	@ExcelField(title="任务名", align=2, sort=13)
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
}