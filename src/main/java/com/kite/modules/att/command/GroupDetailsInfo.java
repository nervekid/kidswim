package com.kite.modules.att.command;

import java.util.Date;

public class GroupDetailsInfo {

	private static final long serialVersionUID = 1L;

	/**1.[不可空] 分组编号*/
	private String code;

	/**2.[不可空] 分组教练员名称*/
	private String coachName;

	/**3.[不可空] 分组编号及人数显示*/
	private String codeAndNumShow;

	/**4.[不可空] 课程地址*/
	private String courseAddress;

	/**5.[不可空] 课程地址名称*/
	private String courseAddressName;

	/**6.[不可空] 分组开始日期*/
	private Date groupBeginTime;

	/**7.[不可空] 分组开始日期字符串*/
	private String groupBeginTimeStr;

	/**8.[不可空] 分组上课开始时间*/
	private String groupLearnBeginTime;

	/**9.[不可空] 分组上课开始时间名称*/
	private String groupLearnBeginTimeStr;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeAndNumShow() {
		return codeAndNumShow;
	}

	public void setCodeAndNumShow(String codeAndNumShow) {
		this.codeAndNumShow = codeAndNumShow;
	}

	public String getCourseAddress() {
		return courseAddress;
	}

	public void setCourseAddress(String courseAddress) {
		this.courseAddress = courseAddress;
	}

	public Date getGroupBeginTime() {
		return groupBeginTime;
	}

	public void setGroupBeginTime(Date groupBeginTime) {
		this.groupBeginTime = groupBeginTime;
	}

	public String getGroupLearnBeginTime() {
		return groupLearnBeginTime;
	}

	public void setGroupLearnBeginTime(String groupLearnBeginTime) {
		this.groupLearnBeginTime = groupLearnBeginTime;
	}

	public String getGroupBeginTimeStr() {
		return groupBeginTimeStr;
	}

	public void setGroupBeginTimeStr(String groupBeginTimeStr) {
		this.groupBeginTimeStr = groupBeginTimeStr;
	}

	public String getCourseAddressName() {
		return courseAddressName;
	}

	public void setCourseAddressName(String courseAddressName) {
		this.courseAddressName = courseAddressName;
	}

	public String getGroupLearnBeginTimeStr() {
		return groupLearnBeginTimeStr;
	}

	public void setGroupLearnBeginTimeStr(String groupLearnBeginTimeStr) {
		this.groupLearnBeginTimeStr = groupLearnBeginTimeStr;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

}
