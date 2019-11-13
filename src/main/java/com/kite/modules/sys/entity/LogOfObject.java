/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.entity;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;
import java.util.Map;

/**
 * 日志Entity
 * @author kite
 * @version 2014-8-19
 */
public class LogOfObject extends DataEntity<LogOfObject> {

	private static final long serialVersionUID = 1L;
	private String type; 		// 日志类型（1：接入日志；2：错误日志）
	private String title;		// 日志标题

	private String oldObj;		//实体之前的Json 保存
	private String newObj;		//实体改变之后的Json 保存

	private Date beginDate;		// 开始日期
	private Date endDate;		// 结束日期

	// 日志类型（1：接入日志；2：错误日志）
	public static final String TYPE_ACCESS = "1";
	public static final String TYPE_EXCEPTION = "2";

	public LogOfObject(){
		super();
	}

	public LogOfObject(String id){
		super(id);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOldObj() {
		return oldObj;
	}

	public void setOldObj(String oldObj) {
		this.oldObj = oldObj;
	}

	public String getNewObj() {
		return newObj;
	}

	public void setNewObj(String newObj) {
		this.newObj = newObj;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}