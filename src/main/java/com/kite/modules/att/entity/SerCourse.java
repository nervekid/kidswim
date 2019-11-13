/**
 * MouTai
 */
package com.kite.modules.att.entity;

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
	private Integer beginYearMonth;		// 开始年月
	private Integer endYearMonth;		// 结束年月
	private Date courseDate;		// 上课日期
	private Integer courseNum;		// 课程所属第几堂
	private String courseAddress;		// 课程地址 字典枚举 course_addrese_flag MS:摩士 HH:斧山 KT:观塘
	private String strInWeek;		// 星期几 字典枚举 week_flag 1:星期一 2:星期二 3:星期三 4:星期四 5:星期五 6:星期六 7:星期日 
	
	public SerCourse() {
		super();
	}

	public SerCourse(String id){
		super(id);
	}

	@ExcelField(title="课程编号 年份+地点编号+教练员编号 例如:2019MS-CAOO3 按照规则编码", align=2, sort=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="教练员id", align=2, sort=2)
	public String getCoathId() {
		return coathId;
	}

	public void setCoathId(String coathId) {
		this.coathId = coathId;
	}
	
	@ExcelField(title="开始年月", align=2, sort=3)
	public Integer getBeginYearMonth() {
		return beginYearMonth;
	}

	public void setBeginYearMonth(Integer beginYearMonth) {
		this.beginYearMonth = beginYearMonth;
	}
	
	@ExcelField(title="结束年月", align=2, sort=4)
	public Integer getEndYearMonth() {
		return endYearMonth;
	}

	public void setEndYearMonth(Integer endYearMonth) {
		this.endYearMonth = endYearMonth;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="上课日期", align=2, sort=5)
	public Date getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(Date courseDate) {
		this.courseDate = courseDate;
	}
	
	@ExcelField(title="课程所属第几堂", align=2, sort=6)
	public Integer getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(Integer courseNum) {
		this.courseNum = courseNum;
	}
	
	@ExcelField(title="课程地址 字典枚举 course_addrese_flag MS:摩士 HH:斧山 KT:观塘", align=2, sort=7)
	public String getCourseAddress() {
		return courseAddress;
	}

	public void setCourseAddress(String courseAddress) {
		this.courseAddress = courseAddress;
	}
	
	@ExcelField(title="星期几 字典枚举 week_flag 1:星期一 2:星期二 3:星期三 4:星期四 5:星期五 6:星期六 7:星期日 ", align=2, sort=8)
	public String getStrInWeek() {
		return strInWeek;
	}

	public void setStrInWeek(String strInWeek) {
		this.strInWeek = strInWeek;
	}
	
}