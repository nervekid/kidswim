/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.user.entity.user;

import org.hibernate.validator.constraints.Length;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * userEntity
 * @author czh
 * @version 2017-08-24
 */
public class ComTUser extends DataEntity<ComTUser> {
	
	private static final long serialVersionUID = 1L;
	private String username;		// username
	private String password;		// password
	
	public ComTUser() {
		super();
	}

	public ComTUser(String id){
		super(id);
	}

	@Length(min=0, max=255, message="username长度必须介于 0 和 255 之间")
	@ExcelField(title="username", align=2, sort=0)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=0, max=255, message="password长度必须介于 0 和 255 之间")
	@ExcelField(title="password", align=2, sort=1)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}