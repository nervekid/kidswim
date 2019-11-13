/**
 * MouTai
 */
package com.kite.modules.sys.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 组织架构职级表Entity
 * @author lyb
 * @version 2019-08-19
 */
public class SysOrganizationalApproval extends DataEntity<SysOrganizationalApproval> {
	
	private static final long serialVersionUID = 1L;
	private String orgId;		// 组织架构id
	private String rankFlag;		// 职级标记 rank_flag CEO:总经理, BG:事业群负责人, VP:分管副总, BU:一级部门负责人, CF:二级部门负责人
	private String superiorFlag;		// 上级标记 1:是,0:否
	
	public SysOrganizationalApproval() {
		super();
	}

	public SysOrganizationalApproval(String id){
		super(id);
	}

	@ExcelField(title="组织架构id", align=2, sort=1)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@ExcelField(title="职级标记 rank_flag CEO:总经理, BG:事业群负责人, VP:分管副总, BU:一级部门负责人, CF:二级部门负责人", align=2, sort=2)
	public String getRankFlag() {
		return rankFlag;
	}

	public void setRankFlag(String rankFlag) {
		this.rankFlag = rankFlag;
	}
	
	@ExcelField(title="上级标记 1:是,0:否", align=2, sort=3)
	public String getSuperiorFlag() {
		return superiorFlag;
	}

	public void setSuperiorFlag(String superiorFlag) {
		this.superiorFlag = superiorFlag;
	}
	
}