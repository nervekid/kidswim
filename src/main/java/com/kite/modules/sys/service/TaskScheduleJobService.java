/**
 * KITE
 */
package com.kite.modules.sys.service;

import java.util.List;

import com.kite.modules.sys.quartz.QuartzManager;
import com.kite.modules.sys.quartz.entity.ScheduleJob;
import com.kite.modules.sys.quartz.utils.ScheduleJobUtils;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.kite.modules.sys.entity.TaskScheduleJob;
import com.kite.modules.sys.dao.TaskScheduleJobDao;

/**
 * 定时任务Service
 * @author cxh
 * @version 2018-10-11
 */
@Service
@Transactional(readOnly = true)
public class TaskScheduleJobService extends CrudService<TaskScheduleJobDao, TaskScheduleJob> {

    @Autowired
	TaskScheduleJobDao taskScheduleJobDao;
	private QuartzManager quartzManager;
	@Override
	public TaskScheduleJob get(String id) {
		return super.get(id);
	}
	@Override
	public List<TaskScheduleJob> findList(TaskScheduleJob taskScheduleJob) {
		return super.findList(taskScheduleJob);
	}
	@Override
	public Page<TaskScheduleJob> findPage(Page<TaskScheduleJob> page, TaskScheduleJob taskScheduleJob) {
		return super.findPage(page, taskScheduleJob);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(TaskScheduleJob taskScheduleJob) {
		super.save(taskScheduleJob);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(TaskScheduleJob taskScheduleJob) {
		super.delete(taskScheduleJob);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(taskScheduleJobDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}

	public void initSchedule() throws SchedulerException {
		// 这里获取任务信息数据
		quartzManager = new QuartzManager();
		List<TaskScheduleJob> jobList = findList(new TaskScheduleJob());
		/*for (TaskScheduleJob scheduleJob : jobList) {
			quartzManager.addJob(ScheduleJobUtils.entityToData(scheduleJob));
		}*/
		logger.info("定时器初始化启动成功！");
	}
	@Transactional(readOnly = false)
	public void changeStatus(String jobId, String cmd) throws SchedulerException {
		TaskScheduleJob scheduleJob = get(jobId);
		if (scheduleJob == null) {
			return;
		}
		if ("stop".equals(cmd)) {
			quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
			scheduleJob.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
		} else if ("start".equals(cmd)) {
			scheduleJob.setJobStatus(ScheduleJob.STATUS_RUNNING);
			quartzManager.addJob(ScheduleJobUtils.entityToData(scheduleJob));
		}
		this.save(scheduleJob);
	}

	
}