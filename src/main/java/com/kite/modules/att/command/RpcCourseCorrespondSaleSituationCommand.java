package com.kite.modules.att.command;

import java.util.List;

public class RpcCourseCorrespondSaleSituationCommand {


	private static final long serialVersionUID = 1L;

	/**1. [不可空] (未分组) 课程级别对应销售单人数 */
	private List<UnGroupLevelCorrespondCountCommand> unGroupLevelCorrespondCountCommandList;

	/**2. [不可空] 分组编号列表 */
	private List<String> codes;

	public List<UnGroupLevelCorrespondCountCommand> getUnGroupLevelCorrespondCountCommandList() {
		return unGroupLevelCorrespondCountCommandList;
	}

	public void setUnGroupLevelCorrespondCountCommandList(
			List<UnGroupLevelCorrespondCountCommand> unGroupLevelCorrespondCountCommandList) {
		this.unGroupLevelCorrespondCountCommandList = unGroupLevelCorrespondCountCommandList;
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}


}
