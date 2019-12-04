/**
 * MouTai
 */
package com.kite.modules.att.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 课程Entity
 * @author lyb
 * @version 2019-11-13
 */
public class SerCourse extends DataEntity<SerCourse> {

	private static final long serialVersionUID = 1L;
	private String code;		// 课程编号 年份+地点编号+教练员编号 例如:2019MS-CAOO3 按照规则编码
	private String coathId;		// 教练员id
	private String beginYearMonth;		// 开始年月
	private String endYearMonth;		// 结束年月
	private Date courseDate;		// 上课日期
	private Integer courseNum;		// 课程所属第几堂
	private String courseAddress;		// 课程地址 字典枚举 course_addrese_flag MS:摩士 HH:斧山 KT:观塘
	private String strInWeek;		// 星期几 字典枚举 week_flag 1:星期一 2:星期二 3:星期三 4:星期四 5:星期五 6:星期六 7:星期日
	private Date beginDate;			//课程开始时间
	private Date endDate;			//课程结束时间

	private String coachName; //课程教练名称
	private String dateRange; //时间选择范围
	private Date beginTime;	  //查询开始时间
	private Date endTime;	  //查询结束时间

	public SerCourse() {
		super();
	}

	@ExcelField(title="课程编号", align=2, sort=1)
	public String getCode() {
		return code;
	}

	@ExcelField(title="教练员", align=2, sort=2)
	public String getCoachName() {
		return coachName;
	}

	@ExcelField(title="开始年月", align=2, sort=3)
	public String getBeginYearMonth() {
		return beginYearMonth;
	}

	@ExcelField(title="结束年月", align=2, sort=4)
	public String getEndYearMonth() {
		return endYearMonth;
	}

	@ExcelField(title="上课日期", align=2, sort=5)
	public String getCourseDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(courseDate);
	}

	@ExcelField(title="课程所属第几堂", align=2, sort=6)
	public Integer getCourseNum() {
		return courseNum;
	}

	@ExcelField(title="课程地址", dictType="course_addrese_flag", align=2, sort=7)
	public String getCourseAddress() {
		return courseAddress;
	}

	@ExcelField(title="星期几", dictType="week_flag", align=2, sort=8)
	public String getStrInWeek() {
		return strInWeek;
	}

	@ExcelField(title="生成课程开始日期", align=2, sort=5)
	public String getBeginDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(beginDate);
	}

	@ExcelField(title="生成课程结束日期", align=2, sort=5)
	public String getEndDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(endDate);
	}

	public SerCourse(String id){
		super(id);
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCoathId() {
		return coathId;
	}

	public void setCoathId(String coathId) {
		this.coathId = coathId;
	}

	public void setBeginYearMonth(String beginYearMonth) {
		this.beginYearMonth = beginYearMonth;
	}

	public void setEndYearMonth(String endYearMonth) {
		this.endYearMonth = endYearMonth;
	}

	public Date getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(Date courseDate) {
		this.courseDate = courseDate;
	}

	public void setCourseNum(Integer courseNum) {
		this.courseNum = courseNum;
	}

	public void setCourseAddress(String courseAddress) {
		this.courseAddress = courseAddress;
	}

	public void setStrInWeek(String strInWeek) {
		this.strInWeek = strInWeek;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}