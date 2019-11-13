/**
 * MouTai
 */
package com.kite.modules.sys.entity;

import java.util.List;
import com.google.common.collect.Lists;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 多组织架构数据权限Entity
 * @author lyb
 * @version 2018-10-30
 */
public class SysTDataaccess extends DataEntity<SysTDataaccess> {

	private static final long serialVersionUID = 1L;
	private String name;		// 多组织架构数据权限组名称
	private List<SysTDataaccessentity> sysTDataaccessentityList = Lists.newArrayList();		// 子表列表
	private String createName;
	private String updateName;

	public SysTDataaccess() {
		super();
	}

	public SysTDataaccess(String id){
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

	@ExcelField(title="多组织架构数据权限组名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SysTDataaccessentity> getSysTDataaccessentityList() {
		return sysTDataaccessentityList;
	}

	public void setSysTDataaccessentityList(List<SysTDataaccessentity> sysTDataaccessentityList) {
		this.sysTDataaccessentityList = sysTDataaccessentityList;
	}
}