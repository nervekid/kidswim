package com.kite.modules.sys.listener;

import com.kite.common.utils.SpringContextHolder;
import com.kite.modules.sys.service.TaskScheduleJobService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ScheduleJobInitListener implements ApplicationListener<ContextRefreshedEvent> {

	TaskScheduleJobService bean = SpringContextHolder.getApplicationContext()
			.getBean(TaskScheduleJobService.class);


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			bean.initSchedule();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}