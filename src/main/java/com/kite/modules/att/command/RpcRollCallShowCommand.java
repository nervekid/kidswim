package com.kite.modules.att.command;

public class RpcRollCallShowCommand {

	private static final long serialVersionUID = 1L;

	/**1.[不可空] 课程明细id*/
	private String courseDetailsId;

	/**2.[不可空] 学生id*/
	private String studentId;

	/**3.[不可空] 学生名称*/
	private String studentName;

	/**4.[不可空] 课程级别*/
	private String courseLevel;

	/**5.[不可空] 点名状态值 1:出席 2:缺席 3:请假 4:事故 0:未点名*/
	private String rollCallStatusFlag;

	/**6.[不可空] 点名状态名称 1:出席 2:缺席 3:请假 4:事故 0:未点名*/
	private String rollCallStatusName;

	public String getCourseDetailsId() {
		return courseDetailsId;
	}

	public void setCourseDetailsId(String courseDetailsId) {
		this.courseDetailsId = courseDetailsId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCourseLevel() {
		return courseLevel;
	}


	public String getRollCallStatusName() {
		return rollCallStatusName;
	}

	public void setRollCallStatusName(String rollCallStatusName) {
		this.rollCallStatusName = rollCallStatusName;
	}

	public void setCourseLevel(String courseLevel) {
		this.courseLevel = courseLevel;
	}

	public String getRollCallStatusFlag() {
		return rollCallStatusFlag;
	}

	public void setRollCallStatusFlag(String rollCallStatusFlag) {
		this.rollCallStatusFlag = rollCallStatusFlag;
	}

}
