package com.kite.modules.att.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.kite.common.persistence.DataEntity;

/**
 * 课程明细Entity
 * @author lyb
 * @version 2019-12-08
 */
public class SerCourseDetails extends DataEntity<SerCourseDetails> {
	private static final long serialVersionUID = 1L;
	private String courseId; //课程id 父id
	private Date learnDate; //上课日期
	private Date learnBeginDate; //上课开始时间 yyyy-MM-dd HH:mm:ss
	private Date learnEndDate; //上课结束时间 yyyy-MM-dd HH:mm:ss
	private String coathId; //教练员id
	private String coathName; //教练员名称
	private String rollCallStatusFlag; //是否已点名 字典枚举 yes_no 0:否 1:是

	private String learnDateStr; //上课日期
	private String learnBeginDateStr; //上课开始时间 yyyy-MM-dd HH:mm:ss
	private String learnEndDateStr; //上课结束时间 yyyy-MM-dd HH:mm:ss

	public SerCourseDetails() {
		super();
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Date getLearnDate() {
		return learnDate;
	}

	public void setLearnDate(Date learnDate) {
		this.learnDate = learnDate;
	}

	public Date getLearnBeginDate() {
		return learnBeginDate;
	}

	public void setLearnBeginDate(Date learnBeginDate) {
		this.learnBeginDate = learnBeginDate;
	}

	public Date getLearnEndDate() {
		return learnEndDate;
	}

	public void setLearnEndDate(Date learnEndDate) {
		this.learnEndDate = learnEndDate;
	}

	public String getCoathId() {
		return coathId;
	}

	public void setCoathId(String coathId) {
		this.coathId = coathId;
	}

	public String getRollCallStatusFlag() {
		return rollCallStatusFlag;
	}

	public void setRollCallStatusFlag(String rollCallStatusFlag) {
		this.rollCallStatusFlag = rollCallStatusFlag;
	}

	public String getLearnDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(learnDate);
	}

	public void setLearnDateStr(String learnDateStr) {
		this.learnDateStr = learnDateStr;
	}

	public String getLearnBeginDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(learnBeginDate);
	}

	public void setLearnBeginDateStr(String learnBeginDateStr) {
		this.learnBeginDateStr = learnBeginDateStr;
	}

	public String getLearnEndDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(learnEndDate);
	}

	public void setLearnEndDateStr(String learnEndDateStr) {
		this.learnEndDateStr = learnEndDateStr;
	}

	public String getCoathName() {
		return coathName;
	}

	public void setCoathName(String coathName) {
		this.coathName = coathName;
	}


}