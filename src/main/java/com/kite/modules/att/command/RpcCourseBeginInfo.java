package com.kite.modules.att.command;

/**
 * 课程开始时间信息
 * @author lyb
 *
 */
public class RpcCourseBeginInfo {

	private static final long serialVersionUID = 1L;

	private String beginTimeStr;

	private String showBeginTimeStr;

	public String getBeginTimeStr() {
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	public String getShowBeginTimeStr() {
		return showBeginTimeStr;
	}

	public void setShowBeginTimeStr(String showBeginTimeStr) {
		this.showBeginTimeStr = showBeginTimeStr;
	}

}
