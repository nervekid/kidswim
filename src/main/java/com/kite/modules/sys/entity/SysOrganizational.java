/**
 * MouTai
 */
package com.kite.modules.sys.entity;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 系统组织架构Entity
 * @author lyb
 * @version 2018-10-18
 */
public class SysOrganizational extends DataEntity<SysOrganizational> {

	private static final long serialVersionUID = 1L;
	private String companyId;		// 上级部门
	private Office office;		// 归属部门
	private Office officeOne;		// 一级部门
	private User user;		// 人员ID
	private String organTag;		// 组织架构标记
	private String companyName; //上级部门名称
	private String createName;
	private String updateName;
	private String officeOneId;// 一级部门
	private String currentUserId; //当前登录人id

	private String rankFlag;		// 职级标记 rank_flag CEO:总经理, BG:事业群负责人, VP:分管副总, BU:一级部门负责人, CF:二级部门负责人
	private String superiorFlag;    // 上级标记 1:是,0:否

	private String becomeWorker;    	// 员工状态 ,枚举 staff_status 1:正式员工 2：试用期员工 3: 实习生;4: 待入职;5: 离职;

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public SysOrganizational() {
		super();
	}

	public SysOrganizational(String id){
		super(id);
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@ExcelField(title="上级部门", align=2, sort=1)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@ExcelField(title="归属部门", fieldType=Office.class, value="office.name", align=2, sort=2)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@ExcelField(title="人员ID", fieldType=User.class, value="user.name", align=2, sort=3)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ExcelField(title="组织架构标记", align=2, sort=4)
	public String getOrganTag() {
		if(StringUtils.isNotEmpty(this.organTag)){
			organTag = this.organTag.replace("?datasource=dataSource1", "");
        }
		return organTag;
	}

	public void setOrganTag(String organTag) {
		this.organTag = organTag;
	}

	public String getOfficeOneId() {
		return officeOneId;
	}

	public void setOfficeOneId(String officeOneId) {
		this.officeOneId = officeOneId;
	}

	public Office getOfficeOne() {
		return officeOne;
	}

	public void setOfficeOne(Office officeOne) {
		this.officeOne = officeOne;
	}

	public String getRankFlag() {
		return rankFlag;
	}

	public void setRankFlag(String rankFlag) {
		this.rankFlag = rankFlag;
	}

	public String getSuperiorFlag() {
		return superiorFlag;
	}

	public void setSuperiorFlag(String superiorFlag) {
		this.superiorFlag = superiorFlag;
	}

	public String getBecomeWorker() {
		return becomeWorker;
	}

	public void setBecomeWorker(String becomeWorker) {
		this.becomeWorker = becomeWorker;
	}
}