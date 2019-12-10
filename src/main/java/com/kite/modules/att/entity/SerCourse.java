/**
 * MouTai
 */
package com.kite.modules.att.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	private String courseLevel; 	//课程等级 TB：泳隊-預備組 TA：泳隊-競賽組 BB：幼兒 CA：兒童A CB：兒童B CC：兒童C AD：成人 PP：私人
	private Date courseBeginTime;   //课程开始时间
	private Date courseEndTimeTime; //课程结束时间
	private String learnBeginTime;	//上课开始时间字符串 1200 代表中午十二点
	private String learnEndTimeTime; //上课结束时间字符串 1200 代表中午十二点
	private int learnNum;			//堂数
	private String courseAddress;		// 课程地址 字典枚举 course_addrese_flag MS:摩士 HH:斧山 KT:观塘
	private String strInWeek;		// 星期几 字典枚举 week_flag 1:星期一 2:星期二 3:星期三 4:星期四 5:星期五 6:星期六 7:星期日
	private Date assessmentDate;	//评估日期
	private BigDecimal courseFee; 	//课程费用 按照课程收费标准及折扣进行计算 单位(港币)

	private String beginTimeAndEndTimeStr; //课程开始于结束的时间范围

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

	@ExcelField(title="课程编号", dictType="course_level", align=2, sort=2)
	public String getCourseLevel() {
		return courseLevel;
	}

	@ExcelField(title="课程开始时间", align=2, sort=3)
	public String getCourseBeginTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(courseBeginTime);
	}

	public Date getCourseBeginTime() {
		return courseBeginTime;
	}

	@ExcelField(title="课程结束时间", align=2, sort=4)
	public String getCourseEndTimeTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(courseEndTimeTime);
	}

	@ExcelField(title="上课开始时间", align=2, sort=5)
	public String getLearnBeginTime() {
		String firstStr = learnBeginTime.substring(0,2);
		String secondStr = learnBeginTime.substring(2,4);
		return firstStr + ":" + secondStr;
	}

	@ExcelField(title="上课结束时间", align=2, sort=6)
	public String getLearnEndTimeTime() {
		String firstStr = learnEndTimeTime.substring(0,2);
		String secondStr = learnEndTimeTime.substring(2,4);
		return firstStr + ":" + secondStr;
	}

	@ExcelField(title="堂数", align=2, sort=7)
	public int getLearnNum() {
		return learnNum;
	}

	@ExcelField(title="课程地址", dictType="course_addrese_flag", align=2, sort=8)
	public String getCourseAddress() {
		return courseAddress;
	}

	@ExcelField(title="星期几", dictType="week_flag", align=2, sort=9)
	public String getStrInWeek() {
		return strInWeek;
	}

	@ExcelField(title="评估日期", align=2, sort=10)
	public String getAssessmentDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(assessmentDate);
	}

	@ExcelField(title="课程费用", align=2, sort=11)
	public BigDecimal getCourseFee() {
		return courseFee;
	}

	public SerCourse(String id){
		super(id);
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCourseAddress(String courseAddress) {
		this.courseAddress = courseAddress;
	}

	public void setStrInWeek(String strInWeek) {
		this.strInWeek = strInWeek;
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

	public Date getAssessmentDate() {
		return assessmentDate;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setCourseLevel(String courseLevel) {
		this.courseLevel = courseLevel;
	}

	public void setCourseBeginTime(Date courseBeginTime) {
		this.courseBeginTime = courseBeginTime;
	}

	public Date getCourseEndTimeTime() {
		return courseEndTimeTime;
	}

	public void setCourseEndTimeTime(Date courseEndTimeTime) {
		this.courseEndTimeTime = courseEndTimeTime;
	}

	public void setLearnEndTimeTime(String learnEndTimeTime) {
		this.learnEndTimeTime = learnEndTimeTime;
	}

	public void setLearnBeginTime(String learnBeginTime) {
		this.learnBeginTime = learnBeginTime;
	}

	public void setLearnNum(int learnNum) {
		this.learnNum = learnNum;
	}

	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public void setCourseFee(BigDecimal courseFee) {
		this.courseFee = courseFee;
	}

	public String getBeginTimeAndEndTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(courseBeginTime) + "~" + sdf.format(courseEndTimeTime);
	}

	public void setBeginTimeAndEndTimeStr(String beginTimeAndEndTimeStr) {
		this.beginTimeAndEndTimeStr = beginTimeAndEndTimeStr;
	}

}