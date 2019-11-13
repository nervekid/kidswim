/**
 * MouTai
 */
package com.kite.modules.sys.entity;

import com.kite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 系统通讯录Entity
 * @author 黄开蔼
 * @version 2019-03-26
 */
public class SysTAddressbook extends DataEntity<SysTAddressbook> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户ID
	private String province;		// 省份
	private String catname;		// 运营商
	private String officeOneName;		// 部门
	private String mobile;		// 手机号码
	private String city;		// 城市
	
	public SysTAddressbook() {
		super();
	}

	public SysTAddressbook(String id){
		super(id);
	}

	@ExcelField(title="部门", align=2, sort=1)
	public String getOfficeOneName() {
		return officeOneName;
	}

	public void setOfficeOneName(String officeOneName) {
		this.officeOneName = officeOneName;
	}

	@NotNull(message="用户ID不能为空")
	@ExcelField(title="用户ID", fieldType=User.class, value="user.name", align=2, sort=2)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ExcelField(title="手机号码", align=2, sort=3)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title="省份", align=2, sort=4)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@ExcelField(title="城市", align=2, sort=5)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@ExcelField(title="运营商", align=2, sort=6)
	public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}
	
}