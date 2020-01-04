package com.kite.modules.att.command;

import java.util.List;

public class RpcRollCallCommand {

	private static final long serialVersionUID = 1L;

	private List<RollCallCommand> rollCallCommandList;

	public static class RollCallCommand {
		/**1.[不可空] 课程明细id */
		private String courseDetailsId;

		/**2.[不可空] 学生明细id */
		private String studentId;

		/**3.[不可空] 点名状态-1:出席 2:缺席 3:请假 4:事故 */
		private String rollCallStatusFlag;

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

		public String getRollCallStatusFlag() {
			return rollCallStatusFlag;
		}

		public void setRollCallStatusFlag(String rollCallStatusFlag) {
			this.rollCallStatusFlag = rollCallStatusFlag;
		}


	}

	public List<RollCallCommand> getRollCallCommandList() {
		return rollCallCommandList;
	}

	public void setRollCallCommandList(List<RollCallCommand> rollCallCommandList) {
		this.rollCallCommandList = rollCallCommandList;
	}

}
