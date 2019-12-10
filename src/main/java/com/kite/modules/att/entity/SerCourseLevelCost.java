/**
 * MouTai
 */
package com.kite.modules.att.entity;

import java.math.BigDecimal;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 课程等级对应收费Entity
 * @author lyb
 * @version 2019-11-13
 */
public class SerCourseLevelCost extends DataEntity<SerCourseLevelCost> {

	private static final long serialVersionUID = 1L;
	private String courseLevelFlag;		// 课程等级 字典枚举 courseLevel_flag 1:NA 2:BB 3:CA 4:CB 5:CC 6:AD 7:TA 8:TB
	private BigDecimal costAmount;		// 收费 单位(港币)
	private String containEntranceFeeFlag;		// 是否包含入场费 字典枚举 yes_no 1:是 0:否
	private String costStandardFlag;		// 收费标准 字典枚举 cost_standard_flag 1:每小时 2:每两个月
	private String courseAddress;		//课程等级 TB：泳隊-預備組 TA：泳隊-競賽組 BB：幼兒 CA：兒童A CB：兒童B CC：兒童C AD：成人 PP：私人

	public SerCourseLevelCost() {
		super();
	}

	public SerCourseLevelCost(String id){
		super(id);
	}

	@ExcelField(title="课程等级", dictType="course_level", align=2, sort=1)
	public String getCourseLevelFlag() {
		return courseLevelFlag;
	}

	@ExcelField(title="课程地址", dictType="course_addrese_flag", align=2, sort=2)
	public String getCourseAddress() {
		return courseAddress;
	}

	@ExcelField(title="收费 单位(港币)", align=2, sort=3)
	public BigDecimal getCostAmount() {
		return costAmount;
	}

	@ExcelField(title="收费标准", dictType="cost_standard_flag", align=2, sort=4)
	public String getCostStandardFlag() {
		return costStandardFlag;
	}

	@ExcelField(title="是否包含入场费", dictType="yes_no", align=2, sort=5)
	public String getContainEntranceFeeFlag() {
		return containEntranceFeeFlag;
	}

	public void setCourseLevelFlag(String courseLevelFlag) {
		this.courseLevelFlag = courseLevelFlag;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public void setContainEntranceFeeFlag(String containEntranceFeeFlag) {
		this.containEntranceFeeFlag = containEntranceFeeFlag;
	}

	public void setCourseAddress(String courseAddress) {
		this.courseAddress = courseAddress;
	}

	public void setCostStandardFlag(String costStandardFlag) {
		this.costStandardFlag = costStandardFlag;
	}

}