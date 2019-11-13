package com.kite.modules.sys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/sys/scheduler")
public class SchedulerManagerController {

	/*@Resource
	private Scheduler scheduler;

	@RequestMapping({ "/list", "" })
	public String dashboard(String hasError, Model model) {
		getBaseMv(model);
		model.addAttribute("nowDate",new Date());
		return "modules/sys/schedulerManager";
	}

	@RequestMapping(value = "/run", method = RequestMethod.GET)
	public String run(Model model) {
		try {
			scheduler.start();
			List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
			if(null != triggerGroupNames && !triggerGroupNames.isEmpty()){
				for(int i = 0; i < triggerGroupNames.size(); i++){
					Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(triggerGroupNames.get(i)));
					if(null != triggerKeys){
						for(TriggerKey triggerKey : triggerKeys){
							scheduler.resumeTrigger(triggerKey);
							scheduler.pauseTrigger(triggerKey);
						}
					}
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "redirect:"+ Global.getAdminPath()+"/sys/scheduler/list/";

	}
	@RequestMapping(value = "/stop", method = RequestMethod.GET)
	public String stop() {
		try {
			scheduler.standby();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "redirect:"+ Global.getAdminPath()+"/sys/scheduler/list/";
	}


	private void getBaseMv(Model model) {
		model.addAttribute("scheduler", scheduler);
		try {
			if (scheduler.isInStandbyMode()) {
				return;
			}
			List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
			List<TiggerGroup> tiggerGroups = new ArrayList<TiggerGroup>();
			if(null != triggerGroupNames && !triggerGroupNames.isEmpty()){
				for(int i = 0; i < triggerGroupNames.size(); i++){
					Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(triggerGroupNames.get(i)));
					if(null != triggerKeys){
						List<TriggerModel> triggerModels = new ArrayList<TriggerModel>();
						for(TriggerKey triggerKey : triggerKeys){
							// 获取具体的Trigger
							Trigger trigger = scheduler.getTrigger(triggerKey);
							TriggerModel triggerModel = new TriggerModel();
							Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
							triggerModel.setStatus(scheduler.getTriggerState(triggerKey).ordinal());
							triggerModel.setTrigger(trigger);
							triggerModels.add(triggerModel);
						}
						// 保存Group
						TiggerGroup tiggerGroup = new TiggerGroup();
						tiggerGroup.setGroupName(triggerGroupNames.get(i));
						tiggerGroup.setTriggerModels(triggerModels);
						tiggerGroups.add(tiggerGroup);
					}
				}
			}
			model.addAttribute("tiggerGroups", tiggerGroups);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	*//**
	 * 启动
	 *//*
	@RequestMapping(value = "/resumeTrigger", method = RequestMethod.GET)
	public String resumeTrigger(String name, String group) {
		try {
			TriggerKey triggerKey = new TriggerKey(name, group);
			scheduler.resumeTrigger(triggerKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:"+ Global.getAdminPath()+"/sys/scheduler/list/";
	}

	*//**
	 * 停止
	 *//*
	@RequestMapping(value = "/pauseTrigger", method = RequestMethod.GET)
	public String pauseTrigger(String name, String group) {
		try {
			TriggerKey triggerKey = new TriggerKey(name, group);
			scheduler.pauseTrigger(triggerKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:"+ Global.getAdminPath()+"/sys/scheduler/list/";
	}


	*//**
	 * 立即启动
	 *//*
	@RequestMapping(value = "/triggerTrigger", method = RequestMethod.GET)
	public String triggerTrigger(String name, String group, Model model) {
		try {
			TriggerKey key = new TriggerKey(name, group);
			Trigger trigger = scheduler.getTrigger(key);
			JobKey jobKey = trigger.getJobKey();
			scheduler.triggerJob(jobKey);
		} catch (Exception e) {
			model.addAttribute("hasError", "true");
			e.printStackTrace();
		}
		return "redirect:"+ Global.getAdminPath()+"/sys/scheduler/list/";
	}*/
}
