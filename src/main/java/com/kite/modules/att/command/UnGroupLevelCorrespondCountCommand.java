package com.kite.modules.att.command;

/**
 * 未分组级别对应人数类
 * @author lyb
 * 2019-12-28
 *
 */
public class UnGroupLevelCorrespondCountCommand {

	/**1.[不可空] 级别*/
	private String leavel;

	/**2.[不可空] 级别名称*/
	private String leveLName;

	/**3.[不可空] 人数*/
	private String countNum;

	public String getLeavel() {
		return leavel;
	}
	public void setLeavel(String leavel) {
		this.leavel = leavel;
	}
	public String getCountNum() {
		return countNum;
	}
	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}
	public String getLeveLName() {
		return leveLName;
	}
	public void setLeveLName(String leveLName) {
		this.leveLName = leveLName;
	}

}
