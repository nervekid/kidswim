/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">kite</a> All rights reserved.
 */
package com.kite.modules.datasources.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 多数据配置设计Entity
 * @author cxh
 * @version 2017-08-21
 */
public class MultipleDatasourceDesign extends DataEntity<MultipleDatasourceDesign> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String status;		// 状态
	private String sqlText;		// SQL语句
	private DataSourceConfig dataSourceConfig; //数据源id
	public MultipleDatasourceDesign() {
		super();
	}

	public MultipleDatasourceDesign(String id){
		super(id);
	}

	@ExcelField(title="name", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="状态", align=2, sort=2)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="SQL语句", align=2, sort=3)
	public String getSqlText() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}


	public DataSourceConfig getDataSourceConfig() {
		return dataSourceConfig;
	}

	public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
		this.dataSourceConfig = dataSourceConfig;
	}
}