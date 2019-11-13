package com.kite.modules.sys.listener;

import javax.servlet.ServletContext;

import com.kite.common.utils.SpringContextHolder;
import com.kite.modules.sys.service.TaskScheduleJobService;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kite.modules.sys.service.SystemService;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		WebApplicationContext initWebApplicationContext = super.initWebApplicationContext(servletContext);
		System.setProperty("org.terracotta.quartz.skipUpdateCheck", "true");
		/*try {
				//autoStartScheduler(servletContext);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return initWebApplicationContext;
	}

	@SuppressWarnings("unused")
	private void autoStartScheduler(ServletContext servletContext) throws Exception {
		System.setProperty("org.terracotta.quartz.skipUpdateCheck", "true");
		WebApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		Scheduler scheduler = (Scheduler) springContext.getBean("scheduler");
		scheduler.start();
		try {
			scheduler.pauseTrigger(new TriggerKey("notify_IllegalRecord_MsgTrigger", "carInfo"));
			scheduler.pauseTrigger(new TriggerKey("illegalRecordLogTrigger", "carInfo"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
