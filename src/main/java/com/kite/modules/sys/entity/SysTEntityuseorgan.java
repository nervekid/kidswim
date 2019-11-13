/**
 * MouTai
 */
package com.kite.modules.sys.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 实体功能对应组织架构Entity
 * @author lyb
 * @version 2018-10-26
 */
public class SysTEntityuseorgan extends DataEntity<SysTEntityuseorgan> {

	private static final long serialVersionUID = 1L;
	private String dataTableName;		// 数据库表名
	private String dataTableNameCN;		// 数据库表中文名
	private String organTag;		// 组织架构
	private String createName;
	private String updateName;

	public SysTEntityuseorgan() {
		super();
	}

	public SysTEntityuseorgan(String id){
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

	@ExcelField(title="数据库表名", align=2, sort=1)
	public String getDataTableName() {
		return dataTableName;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	@ExcelField(title="数据库表中文名", align=2, sort=2)
	public String getDataTableNameCN() {
		return dataTableNameCN;
	}

	public void setDataTableNameCN(String dataTableNameCN) {
		this.dataTableNameCN = dataTableNameCN;
	}

	@ExcelField(title="组织架构", align=2, sort=3)
	public String getOrganTag() {
		return organTag;
	}

	public void setOrganTag(String organTag) {
		this.organTag = organTag;
	}

}