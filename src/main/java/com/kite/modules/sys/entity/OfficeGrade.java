/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kite.common.persistence.DataEntity;
import com.kite.modules.sys.utils.UserUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 菜单Entity
 * @author kite
 * @version 2013-05-15
 */
public class OfficeGrade extends DataEntity<OfficeGrade> {
	private static final long serialVersionUID = 1L;

	private String officeId;		// 直属部门ID
	private String officeOneId;		// 一级部门ID
	private String officeTwoId;		// 二级部门ID
	private String officeThreeId;		// 三级部门ID
	private String officeName;		// 直属部门名称
	private String officeOneName;		// 一级部门名称
	private String officeTwoName;		// 二级部门名称
	private String officeThreeName;		// 三级部门名称

	private String organTag;		// 组织架构标记


	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeOneId() {
		return officeOneId;
	}

	public void setOfficeOneId(String officeOneId) {
		this.officeOneId = officeOneId;
	}

	public String getOfficeTwoId() {
		return officeTwoId;
	}

	public void setOfficeTwoId(String officeTwoId) {
		this.officeTwoId = officeTwoId;
	}

	public String getOfficeThreeId() {
		return officeThreeId;
	}

	public void setOfficeThreeId(String officeThreeId) {
		this.officeThreeId = officeThreeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOfficeOneName() {
		return officeOneName;
	}

	public void setOfficeOneName(String officeOneName) {
		this.officeOneName = officeOneName;
	}

	public String getOfficeTwoName() {
		return officeTwoName;
	}

	public void setOfficeTwoName(String officeTwoName) {
		this.officeTwoName = officeTwoName;
	}

	public String getOfficeThreeName() {
		return officeThreeName;
	}

	public void setOfficeThreeName(String officeThreeName) {
		this.officeThreeName = officeThreeName;
	}

	public String getOrganTag() {
		return organTag;
	}

	public void setOrganTag(String organTag) {
		this.organTag = organTag;
	}
}