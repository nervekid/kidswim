/**
 * MouTai
 */
package com.kite.modules.file.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 文件权限规则Entity
 * @author yyw
 * @version 2018-09-20
 */
public class FileRule extends DataEntity<FileRule> {
	
	private static final long serialVersionUID = 1L;
	private String deptId;		// 部门Id
	private String deptName;	//部门名称
	private String userId;		// 用户Id
	private String userName;	//用户名称
	private String name;	//规则名称

	private String idsArr;
	private String ruleId;

	private String createName;	//创建人名称
	private String updateName;	//修改人名称

	private String listAuthorityId;	//文件权限id

	public String getListAuthorityId() {
		return listAuthorityId;
	}

	public void setListAuthorityId(String listAuthorityId) {
		this.listAuthorityId = listAuthorityId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getIdsArr() {
		return idsArr;
	}

	public void setIdsArr(String idsArr) {
		this.idsArr = idsArr;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public FileRule() {
		super();
	}

	public FileRule(String id){
		super(id);
	}

	@ExcelField(title="部门Id", align=2, sort=1)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@ExcelField(title="用户Id", align=2, sort=2)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}