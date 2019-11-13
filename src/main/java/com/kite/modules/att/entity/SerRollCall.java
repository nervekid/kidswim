/**
 * MouTai
 */
package com.kite.modules.att.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 点名Entity
 * @author lyb
 * @version 2019-11-13
 */
public class SerRollCall extends DataEntity<SerRollCall> {
	
	private static final long serialVersionUID = 1L;
	private String courseId;		// 课程id
	private String studentId;		// 学员id
	private String rollCallStatusFlag;		// 点名状态 字典枚举 rollCall_status_flag 0:缺席 1:出席 2:调课
	
	public SerRollCall() {
		super();
	}

	public SerRollCall(String id){
		super(id);
	}

	@ExcelField(title="课程id", align=2, sort=1)
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	@ExcelField(title="学员id", align=2, sort=2)
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@ExcelField(title="点名状态 字典枚举 rollCall_status_flag 0:缺席 1:出席 2:调课", align=2, sort=3)
	public String getRollCallStatusFlag() {
		return rollCallStatusFlag;
	}

	public void setRollCallStatusFlag(String rollCallStatusFlag) {
		this.rollCallStatusFlag = rollCallStatusFlag;
	}
	
}