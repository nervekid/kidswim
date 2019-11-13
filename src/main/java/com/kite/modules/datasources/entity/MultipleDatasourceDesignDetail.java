/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">kite</a> All rights reserved.
 */
package com.kite.modules.datasources.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 多数据配置设计详情Entity
 * @author cxh
 * @version 2017-08-21
 */
public class MultipleDatasourceDesignDetail extends DataEntity<MultipleDatasourceDesignDetail> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 列名
	private String title;		// 标题
	private String width;		// 字段宽度
	private String type;		// 字段类型
	
	public MultipleDatasourceDesignDetail() {
		super();
	}

	public MultipleDatasourceDesignDetail(String id){
		super(id);
	}

	@ExcelField(title="列名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="标题", align=2, sort=2)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="字段宽度", align=2, sort=3)
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	
	@ExcelField(title="字段类型", align=2, sort=10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}