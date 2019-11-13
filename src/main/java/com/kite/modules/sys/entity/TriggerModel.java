package com.kite.modules.sys.entity;

import org.quartz.Trigger;

public class TriggerModel {
	private Trigger trigger;
	private Integer status;

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
