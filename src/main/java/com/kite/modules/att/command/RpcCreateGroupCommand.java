package com.kite.modules.att.command;

import java.util.List;

/**
 * 创建分组
 * @author lyb
 * 2019-12-21
 */
public class RpcCreateGroupCommand {

	private static final long serialVersionUID = 1L;

	private String userId;

	/**1.[不可空] 教练id*/
	private String coathId;

	/**2.[不可空] 课程地址*/
	private String courseAddress;

	/**3[不可空] 分组开始日期*/
	private String beginDate;

	/**5.[不可空] 分组上课开始时间*/
	private String learnBeginStr;

	/**6.[不可空] 课程级别(最高)*/
	private String courseLeavel;

	/**7.[可空] 学生列表*/
	private List<String> saleIds;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public List<String> getSaleIds() {
		return saleIds;
	}

	public void setSaleIds(List<String> saleIds) {
		this.saleIds = saleIds;
	}

}
