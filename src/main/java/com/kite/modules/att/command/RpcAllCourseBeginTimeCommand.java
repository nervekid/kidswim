package com.kite.modules.att.command;

public class RpcAllCourseBeginTimeCommand {

	private static final long serialVersionUID = 1L;

	/**1.[不可空] 显示时间 */
	public String showTime;

	/**2.[不可空] 选择时间 */
	public String selectTime;

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getSelectTime() {
		return selectTime;
	}

	public void setSelectTime(String selectTime) {
		this.selectTime = selectTime;
	}


}
