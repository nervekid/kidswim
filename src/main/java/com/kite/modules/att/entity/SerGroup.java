/**
 * MouTai
 */
package com.kite.modules.att.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 分组Entity
 * @author lyb
 * @version 2019-12-19
 */
public class SerGroup extends DataEntity<SerGroup> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 分组编号
	private String coathId;		// 教练员id
	private String courseAddress;		// 课程地址 字典枚举 course_addrese_flag MS:摩士 HH:斧山 KT:观塘
	private Date groupBeginTime;		// 分组开始时间
	private Date groupEndTimeTime;		// 分组结束时间
	private String groupLearnBeginTime;		// 分组上课开始时间字符串 1200 代表中午十二点
	
	public SerGroup() {
		super();
	}

	public SerGroup(String id){
		super(id);
	}

	@ExcelField(title="分组编号", align=2, sort=1)
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
	
	@ExcelField(title="课程地址 字典枚举 course_addrese_flag MS:摩士 HH:斧山 KT:观塘", align=2, sort=3)
	public String getCourseAddress() {
		return courseAddress;
	}

	public void setCourseAddress(String courseAddress) {
		this.courseAddress = courseAddress;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="分组开始时间", align=2, sort=4)
	public Date getGroupBeginTime() {
		return groupBeginTime;
	}

	public void setGroupBeginTime(Date groupBeginTime) {
		this.groupBeginTime = groupBeginTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="分组结束时间", align=2, sort=5)
	public Date getGroupEndTimeTime() {
		return groupEndTimeTime;
	}

	public void setGroupEndTimeTime(Date groupEndTimeTime) {
		this.groupEndTimeTime = groupEndTimeTime;
	}
	
	@ExcelField(title="分组上课开始时间字符串 1200 代表中午十二点", align=2, sort=6)
	public String getGroupLearnBeginTime() {
		return groupLearnBeginTime;
	}

	public void setGroupLearnBeginTime(String groupLearnBeginTime) {
		this.groupLearnBeginTime = groupLearnBeginTime;
	}
	
}