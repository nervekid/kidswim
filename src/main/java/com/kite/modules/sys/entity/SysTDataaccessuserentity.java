/**
 * MouTai
 */
package com.kite.modules.sys.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 多组织架构用户对应数据权限组分录Entity
 * @author lyb
 * @version 2018-11-12
 */
public class SysTDataaccessuserentity extends DataEntity<SysTDataaccessuserentity> {

	private static final long serialVersionUID = 1L;
	private String parentId;		// 数据权限组对应父id
	private String userId;		// 用户id
	private String userName;

	public SysTDataaccessuserentity() {
		super();
	}

	public SysTDataaccessuserentity(String id){
		super(id);
	}

	@ExcelField(title="数据权限组对应父id", align=2, sort=1)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@ExcelField(title="用户id", align=2, sort=2)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}