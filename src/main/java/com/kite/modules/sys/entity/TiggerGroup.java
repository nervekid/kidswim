package com.kite.modules.sys.entity;

import java.util.List;

public class TiggerGroup {
	
	private String groupName;
	private List<TriggerModel> TriggerModels;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<TriggerModel> getTriggerModels() {
		return TriggerModels;
	}

	public void setTriggerModels(List<TriggerModel> triggerModels) {
		TriggerModels = triggerModels;
	}

	
	
	
}
