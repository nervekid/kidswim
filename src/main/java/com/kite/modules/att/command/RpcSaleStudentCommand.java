package com.kite.modules.att.command;

/**
 * 泳班系统-远程调用-销售单用户信息
 * @author lyb 2019-12-20
 *
 */
public class RpcSaleStudentCommand {

	private static final long serialVersionUID = 1L;

	/**1.[不可空] 销售单id*/
	private String saleId;

	/**2.[不可空] 学员id*/
	private String stuId;

	/**3.[不可空] 学员编号*/
	private String stuCode;

	/**4.[不可空] 学员名称 中文*/
	private String stuName;

	/**5.[不可空] 学员性别*/
	private String sexName;

	/**6.[不可空] 学员课程级别*/
	private String courseLevel;

	/**7.[不可空] 学员课程级别值*/
	private String courseLevelValue;

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getCourseLevel() {
		return courseLevel;
	}

	public void setCourseLevel(String courseLevel) {
		this.courseLevel = courseLevel;
	}

	public String getStuCode() {
		return stuCode;
	}

	public void setStuCode(String stuCode) {
		this.stuCode = stuCode;
	}

	public String getCourseLevelValue() {
		return courseLevelValue;
	}

	public void setCourseLevelValue(String courseLevelValue) {
		this.courseLevelValue = courseLevelValue;
	}

}
