/**
 * MouTai
 */
package com.kite.modules.sys.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 多组织架构数据权限Entity
 * @author lyb
 * @version 2018-10-30
 */
public class SysTDataaccessentity extends DataEntity<SysTDataaccessentity> {

	private static final long serialVersionUID = 1L;
	private String parentId;		// 数据权限组id
	private String entityOrgId;		// 实体功能对应组织架构id
	private String conditions;		// 条件
	private SysTDataaccess sysTDataaccess;
	private String dataTableName;
	private String dataTableNameCN;
	private String createName;
	private String updateName;

	public SysTDataaccessentity() {
		super();
	}

	public SysTDataaccessentity(String id){
		super(id);
	}

	public SysTDataaccessentity(SysTDataaccess sysTDataaccess){
		this.sysTDataaccess = sysTDataaccess;
	}

	public SysTDataaccess getSysTDataaccess() {
		return sysTDataaccess;
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

	public void setSysTDataaccess(SysTDataaccess sysTDataaccess) {
		this.sysTDataaccess = sysTDataaccess;
	}

	public String getDataTableName() {
		return dataTableName;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public String getDataTableNameCN() {
		return dataTableNameCN;
	}

	public void setDataTableNameCN(String dataTableNameCN) {
		this.dataTableNameCN = dataTableNameCN;
	}

	@ExcelField(title="数据权限组id", align=2, sort=1)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@ExcelField(title="实体功能对应组织架构id", align=2, sort=2)
	public String getEntityOrgId() {
		return entityOrgId;
	}

	public void setEntityOrgId(String entityOrgId) {
		this.entityOrgId = entityOrgId;
	}

	@ExcelField(title="条件", align=2, sort=3)
	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

}