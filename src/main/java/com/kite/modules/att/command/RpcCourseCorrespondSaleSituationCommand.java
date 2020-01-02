package com.kite.modules.att.command;

import java.util.List;

public class RpcCourseCorrespondSaleSituationCommand {


	private static final long serialVersionUID = 1L;

	/**1. [不可空] (未分组) 课程级别对应销售单人数 */
	private List<UnGroupLevelCorrespondCountCommand> unGroupLevelCorrespondCountCommandList;

	/**2. [不可空] 分组编号列表 */
	private List<GroupDetailsInfo> groupDetailsInfos;

	public List<UnGroupLevelCorrespondCountCommand> getUnGroupLevelCorrespondCountCommandList() {
		return unGroupLevelCorrespondCountCommandList;
	}

	public void setUnGroupLevelCorrespondCountCommandList(
			List<UnGroupLevelCorrespondCountCommand> unGroupLevelCorrespondCountCommandList) {
		this.unGroupLevelCorrespondCountCommandList = unGroupLevelCorrespondCountCommandList;
	}

	public List<GroupDetailsInfo> getGroupDetailsInfos() {
		return groupDetailsInfos;
	}

	public void setGroupDetailsInfos(List<GroupDetailsInfo> groupDetailsInfos) {
		this.groupDetailsInfos = groupDetailsInfos;
	}

}
