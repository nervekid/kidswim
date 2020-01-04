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

	public void setCourseLevel(String courseLevel) {
		this.courseLevel = courseLevel;
	}

}
