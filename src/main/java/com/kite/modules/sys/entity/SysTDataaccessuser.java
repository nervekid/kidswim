/**
 * MouTai
 */
package com.kite.modules.sys.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 多组织架构用户对应数据权限组Entity
 * @author lyb
 * @version 2018-10-31
 */
public class SysTDataaccessuser extends DataEntity<SysTDataaccessuser> {

	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String dateAccessId;		// 数据权限组id
	private String userName;
	private String dataAccessName;
	private String organTag;

	private String createName;
	private String updateName;

	public SysTDataaccessuser() {
		super();
	}

	public SysTDataaccessuser(String id){
		super(id);
	}

	public String getOrganTag() {
		return organTag;
	}

	public void setOrganTag(String organTag) {
		this.organTag = organTag;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDataAccessName() {
		return dataAccessName;
	}

	public void setDataAccessName(String dataAccessName) {
		this.dataAccessName = dataAccessName;
	}

	@ExcelField(title="用户id", align=2, sort=1)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@ExcelField(title="数据权限组id", align=2, sort=2)
	public String getDateAccessId() {
		return dateAccessId;
	}

	public void setDateAccessId(String dateAccessId) {
		this.dateAccessId = dateAccessId;
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

}