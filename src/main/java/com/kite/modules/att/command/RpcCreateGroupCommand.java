package com.kite.modules.att.command;

/**
 * 创建分组
 * @author lyb
 * 2019-12-21
 */
public class RpcCreateGroupCommand {

	private static final long serialVersionUID = 1L;

	/**1.[不可空] 教练id*/
	private String coathId;

	/**1.[不可空] 课程地址*/
	private String courseAddress;

	/**1.[不可空] 分组开始日期*/
	private String beginDate;

	/**1.[不可空] 分组结束日期*/
	private String endDate;

	/**1.[不可空] 分组上课开始时间*/
	private String learnBeginStr;

	/**1.[不可空] 课程级别(最高)*/
	private String courseLeavel;

	public String getCoathId() {
		return coathId;
	}

	public void setCoathId(String coathId) {
		this.coathId = coathId;
	}

	public String getCourseAddress() {
		return courseAddress;
	}

	public void setCourseAddress(String courseAddress) {
		this.courseAddress = courseAddress;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLearnBeginStr() {
		return learnBeginStr;
	}

	public void setLearnBeginStr(String learnBeginStr) {
		this.learnBeginStr = learnBeginStr;
	}

	public String getCourseLeavel() {
		return courseLeavel;
	}

	public void setCourseLeavel(String courseLeavel) {
		this.courseLeavel = courseLeavel;
	}

}
