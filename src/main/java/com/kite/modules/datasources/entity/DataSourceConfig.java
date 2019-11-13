/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">kite</a> All rights reserved.
 */
package com.kite.modules.datasources.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 多数据配置Entity
 * @author cxh
 * @version 2017-08-21
 */
public class DataSourceConfig extends DataEntity<DataSourceConfig> {
	
	private static final long serialVersionUID = 1L;
	private String uid;		// 数据源唯一ID,由调接口方传入
	private String name;		// name
	private String jdbcUrl;		// 数据源连接字符串(JDBC)
	private String user;		// 数据源登录用户名
	private String password;		// 数据源登录密码
	private String dataType;		// 数据库类型
	private String status; //状态

	public DataSourceConfig() {
		super();
	}

	public DataSourceConfig(String id){
		super(id);
	}

	@ExcelField(title="数据源唯一ID,由调接口方传入", align=2, sort=1)
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@ExcelField(title="name", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="数据源连接字符串(JDBC)", align=2, sort=3)
	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	
	@ExcelField(title="数据源登录用户名", align=2, sort=4)
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	@ExcelField(title="数据源登录密码", align=2, sort=5)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@ExcelField(title="数据库类型", align=2, sort=6)
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}