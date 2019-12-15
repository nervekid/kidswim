/**
 * MouTai
 */
package com.kite.modules.att.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 課程Entity
 * @author lyb
 * @version 2019-11-13
 */
public class SerCourse extends DataEntity<SerCourse> {

	private static final long serialVersionUID = 1L;
	private String code;		// 課程編號 年份+地點編號+教練員編號 例如:2019MS-CAOO3 按照規則編碼
	private String courseLevel; 	//課程等級 TB：泳隊-預備組 TA：泳隊-競賽組 BB：幼兒 CA：兒童A CB：兒童B CC：兒童C AD：成人 PP：私人
	private Date courseBeginTime;   //課程開始時間
	private Date courseEndTimeTime; //課程結束時間
	private String learnBeginTime;	//上課開始時間字符串 1200 代表中午十二點
	private String learnEndTimeTime; //上課結束時間字符串 1200 代表中午十二點
	private int learnNum;			//堂數
	private String courseAddress;		// 課程地址 字典枚舉 course_addrese_flag MS:摩士 HH:斧山 KT:觀塘
	private String strInWeek;		// 星期幾 字典枚舉 week_flag 1:星期壹 2:星期二 3:星期三 4:星期四 5:星期五 6:星期六 7:星期日
	private Date assessmentDate;	//評估日期
	private BigDecimal courseFee; 	//課程費用 按照課程收費標準及折扣進行計算 單位(港幣)

	private String dateRange; //時間選擇範圍
	private Date beginTime;	  //查詢開始時間
	private Date endTime;	  //查詢結束時間

	private List<SerCourseDetails> serCourseDetailsList = new ArrayList<SerCourseDetails>();

	public SerCourse() {
		super();
	}

	@ExcelField(title="課程編號", align=2, sort=1)
	public String getCode() {
		return code;
	}

	@ExcelField(title="課程編號", dictType="course_level", align=2, sort=2)
	public String getCourseLevel() {
		return courseLevel;
	}

	@ExcelField(title="課程開始時間", align=2, sort=3)
	public String getCourseBeginTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(courseBeginTime);
	}

	public Date getCourseBeginTime() {
		return courseBeginTime;
	}

	@ExcelField(title="課程結束時間", align=2, sort=4)
	public String getCourseEndTimeTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(courseEndTimeTime);
	}

	@ExcelField(title="上課開始時間", align=2, sort=5)
	public String getShowLearnBeginTime() {
		String firstStr = learnBeginTime.substring(0,2);
		String secondStr = learnBeginTime.substring(2,4);
		return firstStr + ":" + secondStr;
	}

	@ExcelField(title="上課結束時間", align=2, sort=6)
	public String getShowLearnEndTimeTime() {
		String firstStr = learnEndTimeTime.substring(0,2);
		String secondStr = learnEndTimeTime.substring(2,4);
		return firstStr + ":" + secondStr;
	}


	@ExcelField(title="堂數", align=2, sort=7)
	public int getLearnNum() {
		return learnNum;
	}

	@ExcelField(title="課程地址", dictType="course_addrese_flag", align=2, sort=8)
	public String getCourseAddress() {
		return courseAddress;
	}

	@ExcelField(title="星期幾", dictType="week_flag", align=2, sort=9)
	public String getStrInWeek() {
		return strInWeek;
	}

	@ExcelField(title="評估日期", align=2, sort=10)
	public String getAssessmentDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(assessmentDate);
	}

	@ExcelField(title="課程費用", align=2, sort=11)
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

	public String getLearnBeginTime() {
		return learnBeginTime;
	}

	public String getLearnEndTimeTime() {
		return learnEndTimeTime;
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
	}

	public void setShowLearnBeginTime(String showLearnBeginTime) {
	}

	public void setShowLearnEndTimeTime(String showLearnEndTimeTime) {
	}

	public List<SerCourseDetails> getSerCourseDetailsList() {
		return serCourseDetailsList;
	}

	public void setSerCourseDetailsList(List<SerCourseDetails> serCourseDetailsList) {
		this.serCourseDetailsList = serCourseDetailsList;
	}

}