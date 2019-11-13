/**
 * MouTai
 */
package com.kite.modules.file.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 文件权限Entity
 * @author yyw
 * @version 2018-08-29
 */
public class FileAuthority extends DataEntity<FileAuthority> {
	
	private static final long serialVersionUID = 1L;
	private String fileId;		// 文件表id
	private String deptId;		// 部门Id
	private String userId;		// 用户Id
	private String ruleId;		// 文件权限规则表id

	private String ruleName;	//规则名称
	private String fileName;	//文件名称
	private String catalogName;	//目录名称
	private String createFileName;	//创建人名称
	private String level;	//文件等级
	private String listUser;	//用户
	private String listParentUser;	//一级部门
	private String listDept;	//二级部门
	private String catalogId; //目录id

    private List<String> listFileId;

    private String idsArr;	//id数组 查询使用

	private String currUserId; //当前人id

	private String currDeptId; //当前部门id


	private String currParentDeptId; //上级部门id

	private String roleType;	//角色类型

	private String ifAdmin = "N";	//角色类型

	private String listAuthorityId;	//主键集合id
    private List<String> listId ;   //主键集合

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public List<String> getListId() {
        return listId;
    }

    public void setListId(List<String> listId) {
        this.listId = listId;
    }

    public String getListAuthorityId() {
		return listAuthorityId;
	}

	public void setListAuthorityId(String listAuthorityId) {
		this.listAuthorityId = listAuthorityId;
	}

	public String getCurrUserId() {
		return currUserId;
	}

	public void setCurrUserId(String currUserId) {
		this.currUserId = currUserId;
	}

	public String getCurrDeptId() {
		return currDeptId;
	}

	public void setCurrDeptId(String currDeptId) {
		this.currDeptId = currDeptId;
	}

	public String getCurrParentDeptId() {
		return currParentDeptId;
	}

	public void setCurrParentDeptId(String currParentDeptId) {
		this.currParentDeptId = currParentDeptId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getIfAdmin() {
		return ifAdmin;
	}

	public void setIfAdmin(String ifAdmin) {
		this.ifAdmin = ifAdmin;
	}

	public String getIdsArr() {
		return idsArr;
	}

	public void setIdsArr(String idsArr) {
		this.idsArr = idsArr;
	}

	public List<String> getListFileId() {
        return listFileId;
    }

    public void setListFileId(List<String> listFileId) {
        this.listFileId = listFileId;
    }

    public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getCreateFileName() {
		return createFileName;
	}

	public void setCreateFileName(String createFileName) {
		this.createFileName = createFileName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getListUser() {
		return listUser;
	}

	public void setListUser(String listUser) {
		this.listUser = listUser;
	}

	public String getListParentUser() {
		return listParentUser;
	}

	public void setListParentUser(String listParentUser) {
		this.listParentUser = listParentUser;
	}

	public String getListDept() {
		return listDept;
	}

	public void setListDept(String listDept) {
		this.listDept = listDept;
	}

	public FileAuthority() {
		super();
	}

	public FileAuthority(String id){
		super(id);
	}

	@ExcelField(title="文件表id", align=2, sort=1)
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	@ExcelField(title="部门Id", align=2, sort=3)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@ExcelField(title="用户Id", align=2, sort=4)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="文件权限规则表id", align=2, sort=5)
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
}